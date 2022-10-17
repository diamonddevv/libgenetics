package net.diamonddev.libgenetics.core;

import net.diamonddev.libgenetics.common.api.v1.util.IdentifierBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class libGeneticsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("libGenetics");
	public static final String modid = "libgenetics";
	public static final String version = FabricLoaderImpl.INSTANCE.getModContainer(modid).orElseThrow().getMetadata().getVersion().getFriendlyString();
	public static final IdentifierBuilder ID = new IdentifierBuilder(modid);

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized libGenetics " + version);
	}

	public static boolean isDevEnv() {
		return FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment();
	}

}
