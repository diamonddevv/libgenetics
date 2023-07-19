package net.diamonddev.libgentest;


import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileRegistry;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer.ConfigSerializer;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer.JsonConfigSerializer;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.serializer.PropertiesConfigSerializer;
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
            super("test", GeneticsMod.id("test_listener"), "test");
        }

        @Override
        public void onReloadForEachResource(CognitionDataResource resource, Identifier path) {
            System.out.println(TestType.getAsString(resource));
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

        public static String getAsString(CognitionDataResource resource) {
            return resource.getString("key");
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

    public static class TestJsonConfig implements ChromosomeConfigFile {

        @Override
        public String getFilePathFromConfigDirectory() {
            return "testdata/json.json";
        }

        @Override
        public ConfigSerializer getSerializer() {
            return new JsonConfigSerializer();
        }

        public boolean aBoolean = true;
        public int anInt = 5;
    }
    public static class TestPropsConfig implements ChromosomeConfigFile {

        @Override
        public String getFilePathFromConfigDirectory() {
            return "testdata/props.properties";
        }

        @Override
        public ConfigSerializer getSerializer() {
            return new PropertiesConfigSerializer();
        }

        public boolean aBoolean = true;
        public int anInt = 5;
    }



    public static final TestListener listener = new TestListener();
    public static final TestType type = new TestType();

    public static NervePacketRegistry.NervePacketRegistryEntry<TestPacket, TestPacket.Data> packet;

    public static TestJsonConfig json;
    public static TestPropsConfig props;

    public static void testInit() {
//        CognitionRegistry.registerListener(listener);
//        listener.getManager().registerType(type);
//
//        packet = NervePacketRegistry.register(GeneticsMod.id("test_packet"), new TestPacket());
//
//        json = ChromosomeConfigFileRegistry.registerAndReadAsSelf(GeneticsMod.id("json"), new TestJsonConfig(), TestJsonConfig.class);
//        props = ChromosomeConfigFileRegistry.registerAndReadAsSelf(GeneticsMod.id("props"), new TestPropsConfig(), TestPropsConfig.class);
    }
}
