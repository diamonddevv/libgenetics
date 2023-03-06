package net.diamonddev.libgenetics.common.api.v1.config.chromosome;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class ChromosomeConfigFileWrapper {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String filename;
    private final Logger logger;
    private final ChromosomeConfigFile config;

    protected ChromosomeConfigFileWrapper(ChromosomeConfigFile configFile) {
        this.filename = configFile.getFilePathFromConfigDirectory();
        this.config = configFile;
        this.logger = LogManager.getLogger(filename + " Config File Manager");
    }

    public <T> T read(Class<T> readClass) {

        String path = getPath();

        StringBuilder amendPath = new StringBuilder(); // create required directories because im weird
        String[] filePathParts = path.split("/");
        int pl = filePathParts.length;
        for (String directory : filePathParts) {
            if (pl > 1) {
                amendPath.append(directory);
                new File(amendPath.toString()).mkdir();
                amendPath.append("/");
                pl--;
            }
        }

        try {
            boolean created = false;
            File file = new File(path);
            if (file.createNewFile()) {
                logger.info("Created Config File at {}", file);
                created = true;
            }

            FileReader fileReader = new FileReader(file);

            JsonObject expectedJson = gson.toJsonTree(config, readClass).getAsJsonObject();
            JsonObject json = gson.fromJson(fileReader, JsonObject.class);

            JsonObject toPrint;
            if (json != null) {
                for (String key : expectedJson.keySet()) {
                    if (!json.has(key)) {
                        json.add(key, expectedJson.get(key));
                    }
                }
                toPrint = json;
            } else {
                toPrint = expectedJson;
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.flush();

            fileWriter.write(gson.toJson(JsonParser.parseString(toPrint.toString())));

            T returnedJson = gson.fromJson(json, readClass);

            fileWriter.flush();
            fileReader.close();
            fileWriter.close();

            return returnedJson;
        } catch (IOException e) {
            throw new RuntimeException("Malformed JSON Config File at '" + path + "'");
        }
    }

    public JsonObject read() {
        return read(JsonObject.class);
    }

    public JsonObject readNoFileManagement() {
        return readNoFileManagement(JsonObject.class);
    }
    public <T> T readNoFileManagement(Class<T> clazz) {
        String path = getPath();

        try {
            JsonReader r = new JsonReader(new FileReader(path));
            T obj = gson.fromJson(r, clazz);
            r.close();
            return obj;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        String path = FabricLoaderImpl.INSTANCE.getConfigDir() + "/" + filename;
        return path.replace('\\', '/'); // correct path
    }
}
