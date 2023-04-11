package net.diamonddev.libgentest;


import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.*;
import net.diamonddev.libgenetics.core.GeneticsMod;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class GeneticsTest {

    public static class TestListener extends CognitionDataListener {

        public TestListener() {
            super("test", GeneticsMod.id("test_listener"));
        }

        @Override
        public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {

        }

        @Override
        public void onFinishReload() {

        }

        @Override
        public void onClearCachePhase() {

        }
    }
    public static class TestType implements CognitionResourceType {

        @Override
        public Identifier getId() {
            return GeneticsMod.id("test_type");
        }

        @Override
        public void addJsonKeys(ArrayList<String> keys) {
            keys.add("key");
        }
    }
    public static class TestPacket implements NerveS2CPacket<TestPacket, TestPacket.Data> {

        @Override
        public PacketByteBuf write(Data data) {
            var buf = NerveNetworker.getNewBuf();
            buf.writeString(data.payload);
            return buf;
        }

        @Override
        public Data read(PacketByteBuf buf) {
            var data = new Data();
            data.payload = buf.readString();
            return data;
        }

        @Override
        public ClientPlayNetworking.PlayChannelHandler receive(Identifier channel) {
            return ((client, handler, buf, responseSender) -> {
                Data data = new Data();
                client.execute(() -> {
                    System.out.println(data.payload);
                });
            });
        }

        public static class Data implements NervePacketData {
            public String payload;
        }
    }

    public static final TestListener listener = new TestListener();
    public static final TestType type = new TestType();
    public static NervePacketRegistry.NervePacketRegistryEntry<TestPacket, TestPacket.Data> packet;

    public static void testInit() {
        CognitionRegistry.registerListener(listener);
        listener.getManager().registerType(type);
        packet = NervePacketRegistry.register(GeneticsMod.id("test_packet"), new TestPacket());
    }
}
