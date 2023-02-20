package net.diamonddev.libgenetics.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneticsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("libGenetics");
	public static final String MODID = "libgenetics";
	public static final String version = FabricLoaderImpl.INSTANCE.getModContainer(MODID).orElseThrow().getMetadata().getVersion().getFriendlyString();

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized libGenetics " + version);
	}
	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
	public static boolean isDevEnv() {
		return FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment();
	}

}
