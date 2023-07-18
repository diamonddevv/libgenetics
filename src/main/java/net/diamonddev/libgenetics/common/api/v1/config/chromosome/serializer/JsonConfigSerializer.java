package net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonConfigSerializer implements ConfigSerializer {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String getExpectedFileExtension() {
        return "json";
    }

    @Override
    public <T> T readFileToClass(ChromosomeConfigFile config, Class<T> readClass, FileReader reader) {
       return gson.fromJson(reader, readClass);
    }

    @Override
    public <T> void writeClassToFile(ChromosomeConfigFile config, Class<T> clazz, FileWriter writer, FileReader reader) throws IOException {
        JsonObject expectedJson = gson.toJsonTree(config, clazz).getAsJsonObject();
        JsonObject json = gson.fromJson(reader, JsonObject.class);

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

        writer.flush();
        writer.write(gson.toJson(JsonParser.parseString(toPrint.toString())));
    }

}
