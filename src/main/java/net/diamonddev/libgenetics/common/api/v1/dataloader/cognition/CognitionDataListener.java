package net.diamonddev.libgenetics.common.api.v1.dataloader.cognition;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CognitionDataListener implements SimpleSynchronousResourceReloadListener {

    /*
        This is the reabstraction of the Absract Recipe Loader from my mod Dialabs. It is a system that removes the need for making new data listeners and instead can
        load many from one JSON location, into a JSON Object parsable with Java.
     */

    private final Logger RESOURCE_MANAGER_LOGGER;
    private final String managerName;
    private final CognitionResourceManager manager;
    private final String resourcePath;
    private final Identifier id;

    public CognitionDataListener(String managerName, Identifier id) {
        this(managerName, id, managerName + "_data");
    }
    public CognitionDataListener(String managerName, Identifier id, String resourcePath) {
        this.managerName = managerName;
        this.resourcePath = resourcePath;

        this.id = id;

        RESOURCE_MANAGER_LOGGER = LogManager.getLogger("LibGenetics Resource Loader Manager [" + managerName + "]");
        this.manager = new CognitionResourceManager();
    }

    public static ArrayList<CognitionDataListener> listeners = new ArrayList<>();

    public static void registerListener(CognitionDataListener listener) {
        listeners.add(listener);
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(listener);
    }

    public CognitionResourceManager getManager() {
        return this.manager;
    }


    private static final Gson gson = new Gson();

    @Override
    public Identifier getFabricId() {
        return id;
    }

    @Override
    public void reload(ResourceManager manager) {
        // Clear Cache
        this.getManager().CACHE.clear();


        // Read
        for (Identifier id : manager.findResources(resourcePath, path -> path.getPath().endsWith(".json")).keySet()) {
            if (manager.getResource(id).isPresent()) {
                try (InputStream stream = manager.getResource(id).get().getInputStream()) {
                    // Consume stream
                    InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); // Create Reader
                    JsonObject json = gson.fromJson(reader, JsonObject.class);
                    Identifier typeId = new Identifier(json.get(CognitionResourceManager.IDPARAM).getAsString());

                    // Read from Type
                    CognitionResourceType type = this.getManager().getType(typeId);

                    // Read JSON
                    CognitionDataResource resource = new CognitionDataResource(type, id);

                    // Add keys
                    ArrayList<String> jsonKeys = new ArrayList<>();
                    type.addJsonKeys(jsonKeys);
                    jsonKeys.forEach(s -> resource.getHash().put(s, json.get(s)));

                    // Add
                    this.getManager().CACHE.getOrCreateKey(type).add(resource);

                } catch (Exception e) {
                    RESOURCE_MANAGER_LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                }
            }
        }
    }
}
