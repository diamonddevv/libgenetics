package net.diamonddev.libgenetics.common.api.v1.util;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyedArrayCache<K, V> extends HashMap<K, ArrayList<V>> {
    public ArrayList<V> getOrCreateKey(K key) {
        return this.computeIfAbsent(key, k -> new ArrayList<>());
    }
}
