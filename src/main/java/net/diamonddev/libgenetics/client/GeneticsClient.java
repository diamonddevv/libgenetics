package net.diamonddev.libgenetics.client;

import net.diamonddev.libgenetics.core.GeneticsMod;
import net.diamonddev.libgentest.GeneticsTestClient;
import net.fabricmc.api.ClientModInitializer;

public class GeneticsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (GeneticsMod.hasDevTools()) GeneticsTestClient.testInit();
    }
}
