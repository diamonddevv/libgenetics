package net.diamonddev.libgenetics.common.api.v1.util;

import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.RegistryEntry;

public class TagUtil {
    public static <T> boolean isInTag(TagKey<T> tag, T object) {
        RegistryEntry<T> entry = RegistryEntry.of(object);
        return entry.isIn(tag);
    }
}
