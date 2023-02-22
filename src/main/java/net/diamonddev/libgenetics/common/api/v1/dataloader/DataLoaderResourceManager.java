package net.diamonddev.libgenetics.common.api.v1.dataloader;

import net.diamonddev.libgenetics.common.api.v1.util.KeyedArrayCache;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Consumer;

public class DataLoaderResourceManager {

    public DataLoaderResourceManager() {

    }

    // CACHE
    public final KeyedArrayCache<DataLoaderResourceType, DataLoaderResource> CACHE = new KeyedArrayCache<>();

    private final HashMap<Identifier, DataLoaderResourceType> TYPES = new HashMap<>();

    ///
    public void registerType(DataLoaderResourceType type) {
        TYPES.put(type.getId(), type);
    }
    
    public static void registerType(DataLoaderListener listener,DataLoaderResourceType type) {
        listener.getManager().registerType(type);
    }

    public DataLoaderResourceType getType(Identifier typeId) {
        return TYPES.get(typeId);
    }

    public void forEachRecipe(DataLoaderResourceType type, Consumer<DataLoaderResource> consumer) {
        CACHE.get(type).forEach(consumer);
    }

    public static void forEachRecipe(DataLoaderListener listener, DataLoaderResourceType type, Consumer<DataLoaderResource> consumer) {
        listener.getManager().forEachRecipe(type, consumer);
    }
    //
    public static final String IDPARAM = "resource_type";
}
