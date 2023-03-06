package net.diamonddev.libgenetics.common.api.v1.nbt.cerebellum;

import net.minecraft.nbt.NbtCompound;

public abstract class CerebellumNbtComponent<T> {

    public final String key;

    public CerebellumNbtComponent(String key) {
        this.key = key;
    }
    public abstract T read(NbtCompound nbt);
    public abstract void write(NbtCompound nbt, T data);
}
