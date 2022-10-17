package net.diamonddev.libgenetics.server;

import net.diamonddev.libgenetics.common.api.v1.integration.ModIntegrationAPI;
import net.fabricmc.api.DedicatedServerModInitializer;

public class libGeneticsServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ModIntegrationAPI.initializeAll();
    }
}
