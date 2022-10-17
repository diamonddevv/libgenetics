package net.diamonddev.libgenetics.common.api.v1.util;

public class DirtyObject<T> {


    private T value;
    private boolean dirty;

    public DirtyObject(T initialValue) {
        this.value = initialValue;
        this.dirty = false;
    }

    public void set(T value) {
        if (!this.dirty) {
            this.value = value;
            this.markDirty();
        }
    }

    public T get() {
        return this.value;
    }

    public void clean() {
        this.dirty = false;
    }

    private void markDirty() {
        this.dirty = true;
    }
}
