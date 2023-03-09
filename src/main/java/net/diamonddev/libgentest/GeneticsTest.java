package net.diamonddev.libgentest;

import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.diamonddev.libgenetics.core.GeneticsMod;

public class GeneticsTest {

    public static NervePacketRegistry.NervePacketRegistryEntry<TestPacket, TestPacket.TestPacketData> testPacketRegistryEntry;

    public static void testInit() {
        testPacketRegistryEntry = new NervePacketRegistry.NervePacketRegistryEntry<>(
                GeneticsMod.id("test_channel"), new TestPacket()
        );

        NervePacketRegistry.register(GeneticsMod.id("test_packet"), testPacketRegistryEntry);
    }
}
