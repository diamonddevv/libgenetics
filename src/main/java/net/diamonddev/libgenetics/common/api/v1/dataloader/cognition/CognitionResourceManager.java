package net.diamonddev.libgenetics.common.api.v1.dataloader.cognition;

import net.diamonddev.libgenetics.common.api.v1.util.KeyedArrayCache;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Consumer;

public class CognitionResourceManager<T> {

    public CognitionResourceManager() {

    }

    // CACHE
    public final KeyedArrayCache<CognitionResourceType<T>, CognitionDataResource<T>> CACHE = new KeyedArrayCache<>();

    private final HashMap<Identifier, CognitionResourceType<T>> TYPES = new HashMap<>();

    ///
    public void registerType(CognitionResourceType<T> type) {
        TYPES.put(type.getId(), type);
    }
    
    public static <T> void registerType(CognitionDataListener<T> listener, CognitionResourceType<T> type) {
        listener.getManager().registerType(type);
    }

    public CognitionResourceType<T> getType(Identifier typeId) {
        return TYPES.get(typeId);
    }

    public void forEachResource(CognitionResourceType<T> type, Consumer<CognitionDataResource<T>> consumer) {
        CACHE.get(type).forEach(consumer);
    }

    public static <T> void forEachResource(CognitionDataListener<T> listener, CognitionResourceType<T> type, Consumer<CognitionDataResource<T>> consumer) {
        listener.getManager().forEachResource(type, consumer);
    }
    //
    public static final String IDPARAM = "resource_type";
}
