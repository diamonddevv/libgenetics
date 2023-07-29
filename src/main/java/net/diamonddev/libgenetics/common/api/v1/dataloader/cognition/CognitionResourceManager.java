package net.diamonddev.libgenetics.common.api.v1.dataloader.cognition;

import net.diamonddev.libgenetics.common.api.v1.util.KeyedArrayCache;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class CognitionResourceManager {

    public CognitionResourceManager() {

    }

    // CACHE
    public final KeyedArrayCache<CognitionResourceType, CognitionDataResource> CACHE = new KeyedArrayCache<>();

    private final HashMap<Identifier, CognitionResourceType> TYPES = new HashMap<>();

    public void registerType(CognitionResourceType type) {
        TYPES.put(type.getId(), type);
    }
    ///

    public CognitionResourceType getType(Identifier typeId) {
        return TYPES.get(typeId);
    }

    public void forEachResource(CognitionResourceType type, Consumer<CognitionDataResource> consumer) {
        CACHE.get(type).forEach(consumer);
    }

    public List<CognitionDataResource> getAllResources(CognitionResourceType type) {
        return CACHE.get(type);
    }

    //
    public static final String IDPARAM = "resource_type";
}
