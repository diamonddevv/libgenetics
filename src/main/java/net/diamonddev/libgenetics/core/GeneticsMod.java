package net.diamonddev.libgenetics.core;

import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceManager;
import net.diamonddev.libgenetics.core.command.DataLoaderResourceManagerArgument;
import net.diamonddev.libgenetics.core.command.LibGeneticsCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneticsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("libGenetics");
	public static final String MODID = "libgenetics";
	public static final String version = FabricLoaderImpl.INSTANCE.getModContainer(MODID).orElseThrow().getMetadata().getVersion().getFriendlyString();

	@Override
	public void onInitialize() {
		// cmd args
		ArgumentTypeRegistry.registerArgumentType(id("dataloader_manager_command_arg"),
				DataLoaderResourceManagerArgument.class, ConstantArgumentSerializer.of(DataLoaderResourceManagerArgument::resourceManager));

		// cmd
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> LibGeneticsCommand.register(dispatcher));

		LOGGER.info("Initialized libGenetics " + version);
	}
	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
	public static boolean isDevEnv() {
		return FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment();
	}

}
