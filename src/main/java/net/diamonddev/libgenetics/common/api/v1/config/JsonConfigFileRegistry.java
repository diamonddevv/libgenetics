package net.diamonddev.libgenetics.common.api.v1.config;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class JsonConfigFileRegistry {
    public static HashMap<Identifier, JsonConfigFileWrapper> HASH = new HashMap<>();


    private static <V> V register(Identifier id, JsonConfigFile configFile, Class<V> readClass) {
        JsonConfigFileWrapper wrapper = new JsonConfigFileWrapper(configFile);
        HASH.put(id, wrapper);
        return wrapper.read(readClass);
    }
    public static <T extends JsonConfigFile> T registerAndReadAsSelf(Identifier id, T configFile, Class<T> clazz) {
        return register(id, configFile, clazz);
    }

    public static JsonObject registerAndReadAsJsonObject(Identifier id, JsonConfigFile configFile) {
        return register(id, configFile, JsonObject.class);
    }
}
