package net.diamonddev.libgenetics.client;

import net.diamonddev.libgenetics.common.api.v1.integration.ModIntegrationAPI;
import net.fabricmc.api.ClientModInitializer;

public class libGeneticsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModIntegrationAPI.initializeAll();
    }
}
