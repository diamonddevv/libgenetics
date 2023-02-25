package net.diamonddev.libgenetics.common.api.v1.config;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class JsonConfigFileRegistry {
    public static HashMap<Identifier, Class<? extends JsonConfigFile>> HASH = new HashMap<>();


    private static <T> T register(Identifier id, Class<? extends JsonConfigFile> configFile, Class<T> readClass) {
        HASH.put(id, configFile);
        return new JsonConfigFileWrapper(configFile).read(readClass);
    }
    public static <T extends JsonConfigFile> T registerAndReadAsSelf(Identifier id, Class<T> configFile) {
        return register(id, configFile, configFile);
    }

    public static JsonObject registerAndReadAsJsonObject(Identifier id, Class<? extends JsonConfigFile> configFile) {
        return register(id, configFile, JsonObject.class);
    }
}
