package net.diamonddev.libgenetics;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibGeneticsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("libGenetics");

	@Override
	public void onInitialize() {

		LOGGER.info("Initialized libGenetics.");
	}
}
