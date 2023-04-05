package net.diamonddev.libgentest;

import net.diamonddev.libgenetics.common.api.v1.network.nerve.NervePacketRegistry;
import net.diamonddev.libgenetics.core.GeneticsMod;

public class GeneticsTestClient {
    public static void testInit() {
        NervePacketRegistry.initClientS2CReciever(GeneticsMod.id("test_packet"));
    }
}
