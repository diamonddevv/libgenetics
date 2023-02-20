package net.diamonddev.libgenetics.common.api.v1.dataloader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.diamonddev.libgenetics.core.GeneticsMod;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DataLoaderListener implements SimpleSynchronousResourceReloadListener {

    /*
        This is the reabstraction of the Absract Recipe Loader from my mod Dialabs. It is a system that removes the need for making new data listeners and instead can
        load many from one JSON location, into a JSON Object parsable with Java.
     */

    private final Logger RESOURCE_MANAGER_LOGGER;
    private final String managerName;

    public DataLoaderListener(String managerName) {
        this.managerName = managerName;

        RESOURCE_MANAGER_LOGGER = LogManager.getLogger("LibGenetics Resource Loader Manager [" + managerName + "]");
    }


    private static final Gson gson = new Gson();

    @Override
    public Identifier getFabricId() {
        return GeneticsMod.id(managerName + "_data_loader_listener");
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear Cache
        DataLoaderResourceManager.CACHE.clear();


        // Read
        for (Identifier id : manager.findResources(managerName + "_data", path -> path.getPath().endsWith(".json")).keySet()) {
            if (manager.getResource(id).isPresent()) {
                try (InputStream stream = manager.getResource(id).get().getInputStream()) {
                    // Consume stream
                    InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); // Create Reader
                    JsonObject json = gson.fromJson(reader, JsonObject.class);
                    Identifier typeId = new Identifier(json.get(DataLoaderResourceManager.IDPARAM).getAsString());

                    // Read from Type
                    DataLoaderResourceType type = DataLoaderResourceManager.getType(typeId);

                    // Read JSON
                    DataLoaderResource resource = new DataLoaderResource(type, id);

                    // Add keys
                    ArrayList<String> jsonKeys = new ArrayList<>();
                    type.addJsonKeys(jsonKeys);
                    jsonKeys.forEach(s -> resource.getHash().put(s, json.get(s)));

                    // Add
                    DataLoaderResourceManager.CACHE.getOrCreateKey(type).add(resource);

                } catch (Exception e) {
                    RESOURCE_MANAGER_LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                }
            }
        }
    }
}
