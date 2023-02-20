package net.diamonddev.libgenetics.common.api.v1.dataloader;

import net.diamonddev.libgenetics.common.api.v1.util.KeyedArrayCache;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Consumer;

public class DataLoaderResourceManager {
    // CACHE
    public static final KeyedArrayCache<DataLoaderResourceType, DataLoaderResource> CACHE = new KeyedArrayCache<>();

    private static final HashMap<Identifier, DataLoaderResourceType> TYPES = new HashMap<>();

    ///
    public static void registerType(DataLoaderResourceType type) {
        TYPES.put(type.getId(), type);
    }

    public static DataLoaderResourceType getType(Identifier typeId) {
        return TYPES.get(typeId);
    }

    public static void forEachRecipe(DataLoaderResourceType recipeType, Consumer<DataLoaderResource> recipeConsumer) {
        CACHE.get(recipeType).forEach(recipeConsumer);
    }
    //
    public static final String IDPARAM = "resource_type";
}
