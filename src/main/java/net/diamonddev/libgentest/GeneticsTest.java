package net.diamonddev.libgentest;

import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceType;
import net.diamonddev.libgenetics.core.GeneticsMod;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class GeneticsTest {
    public static DataLoaderListener listener = new DataLoaderListener("manger", GeneticsMod.id("listener"), "test");
    public static DataLoaderResourceType type = new DataLoaderResourceType() {
        @Override
        public Identifier getId() {
            return GeneticsMod.id("type");
        }

        @Override
        public void addJsonKeys(ArrayList<String> keys) {
            keys.add("yes");
            keys.add("no");
        }
    };

    public static class SerializationPojo {
        public boolean yes;
        public boolean no;
    }
    public static void testInit() {
        DataLoaderListener.registerListener(listener);
        listener.getManager().registerType(type);
    }
}
