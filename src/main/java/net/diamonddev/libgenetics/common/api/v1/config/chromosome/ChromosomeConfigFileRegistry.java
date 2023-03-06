package net.diamonddev.libgenetics.common.api.v1.config.chromosome;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ChromosomeConfigFileRegistry {
    public static HashMap<Identifier, ChromosomeConfigFileWrapper> HASH = new HashMap<>();


    private static <V> V register(Identifier id, ChromosomeConfigFile configFile, Class<V> readClass) {
        ChromosomeConfigFileWrapper wrapper = new ChromosomeConfigFileWrapper(configFile);
        HASH.put(id, wrapper);
        return wrapper.read(readClass);
    }
    public static <T extends ChromosomeConfigFile> T registerAndReadAsSelf(Identifier id, T configFile, Class<T> clazz) {
        return register(id, configFile, clazz);
    }

    public static JsonObject registerAndReadAsJsonObject(Identifier id, ChromosomeConfigFile configFile) {
        return register(id, configFile, JsonObject.class);
    }
}
