package net.diamonddev.libgenetics.common.api.v1.integration;

import java.util.ArrayList;
import java.util.Collection;

public class ModIntegrationAPI {

    // Thanks BCLib (https://github.com/quiqueck/BCLib), seriously helped create this whole system

    public static final Collection<AbstractModIntegration> MOD_INTEGRATIONS = new ArrayList<>();

    /**
     * Registers a mod integration
     *
     * @param integration AbstractModIntegration instance to register
     * @return Registered Integration
     */
    public static AbstractModIntegration register(AbstractModIntegration integration) {
        MOD_INTEGRATIONS.add(integration);
        return integration;
    }

    /**
     * Initialize all registered integrations.
     */
    public static void initializeAll() {
        MOD_INTEGRATIONS.forEach(integration -> {
            if (integration.getModLoaded()) {
                integration.integrationlogger.info("Mod Loaded - Integration Loading!");
                integration.init();
            }
        });
    }

}
