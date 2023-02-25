package net.diamonddev.libgenetics.common.api.v1.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonConfigFileWrapper<T extends JsonConfigFile> {

    private static Gson gson = new Gson();

    private final String filename;
    private final Logger logger;

    protected JsonConfigFileWrapper(Class<T> configFile) { //eh

        this.logger = LogManager.getLogger(filename + " Config File Manager");
    }

    public <T> T read(Class<T> readClass) {
        String path = FabricLoaderImpl.INSTANCE.getConfigDir() + "/" + filename;
        try {
            File file = new File(path);
            if (file.mkdir()) {
                logger.info("Created Config File at {}", file);
            }

            FileReader fileReader = new FileReader(file);
            JsonReader jsonReader = new JsonReader(fileReader);

            T json = gson.fromJson(jsonReader, readClass);

            jsonReader.close();
            fileReader.close();

            return json;
        } catch (IOException e) {
            throw new RuntimeException("Malformed JSON Config File at '" + path + "'");
        }
    }

    public JsonObject read() {
        return read(JsonObject.class);
    }
}
