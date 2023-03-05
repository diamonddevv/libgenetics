package net.diamonddev.libgenetics.common.api.v1.nbt.hippocampus;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Set;

public class CompoundedNbtComponent extends NbtComponent<HashMap<String, NbtCompound>> {
    public CompoundedNbtComponent(String key) {
        super(key);
    }

    @Override
    public HashMap<String, NbtCompound> read(NbtCompound nbt) {
        HashMap<String, NbtCompound> resultHash = new HashMap<>();

        NbtCompound compoundedComponents = nbt.getCompound(key);
        Set<String> keys = compoundedComponents.getKeys();
        keys.forEach(key -> resultHash.put(key, compoundedComponents.getCompound(key)));

        return resultHash;
    }

    @Override
    public void write(NbtCompound nbt, HashMap<String, NbtCompound> data) {
        NbtCompound writeCompoundedComponents = new NbtCompound();
        data.forEach(writeCompoundedComponents::put);
        nbt.put(key, writeCompoundedComponents);
    }

    public <T> void modify(NbtComponent<T> component, T data, NbtCompound superCompound) {
        HashMap<String, NbtCompound> map = this.read(superCompound);

        NbtCompound compound = getOrCreateCompoundFromMap(map, component.key);
        component.write(compound, data);

        map.put(component.key, compound);

        this.write(superCompound, map);
    }

    public <T> T getValue(NbtComponent<T> component, NbtCompound superCompound) {
        HashMap<String, NbtCompound> map = this.read(superCompound);

        NbtCompound compound = getOrCreateCompoundFromMap(map, component.key);
        return component.read(compound);
    }

    private static NbtCompound getOrCreateCompoundFromMap(HashMap<String, NbtCompound> map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return new NbtCompound();
        }
    }
}
