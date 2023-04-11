package net.diamonddev.libgenetics.common.api.v1.dataloader.cognition;

import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CognitionRegistry {
    private CognitionRegistry() {}

    public static ArrayList<CognitionDataListener> listeners = new ArrayList<>();

    public static <T> void registerType(CognitionDataListener listener, CognitionResourceType type) {
        listener.getManager().registerType(type);
    }

    public static <T> void forEachResource(CognitionDataListener listener, CognitionResourceType type, Consumer<CognitionDataResource> consumer) {
        listener.getManager().forEachResource(type, consumer);
    }

    public static void registerListener(CognitionDataListener listener) {
        listeners.add(listener);
        ResourceManagerHelper.get(listener.restype).registerReloadListener(listener);
    }

    public static String getStringMappedRegistry() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = listeners.size();
        for (CognitionDataListener listener : listeners) {
            builder.append("\"").append(listener.getFabricId()).append("\"=[name=\"").append(listener.getName()).append("\",types=[");
            int j = listener.getManager().CACHE.keySet().size();
            for (CognitionResourceType type : listener.getManager().CACHE.keySet()) {
                builder.append("[id=").append(type.getId()).append("]");
                j--;
                if (j != 0) {
                    builder.append(",");
                }
            }

            i--;
            if (i != 0) {
                builder.append(",");
            }
        }
        builder.append("]");

        return builder.toString();
    }
}
