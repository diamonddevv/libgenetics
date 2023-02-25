package net.diamonddev.libgenetics.common.api.v1.util;


import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.Collection;

public class GeneralUtil {
    public static <T> boolean isInTag(TagKey<T> tag, T object) {
        RegistryEntry<T> entry = RegistryEntry.of(object);
        return entry.isIn(tag);
    }

    public static <T> ArrayList<T> toArrList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }
    public static <O, N> Collection<N> remapCollection(Collection<O> collection, ReturnableConsumer<N, O> remapFunction) {
        Collection<N> remapped = new ArrayList<>();
        collection.forEach(o -> {
            N n = remapFunction.accept(o);
            remapped.add(n);
        });

        return remapped;
    }
}
