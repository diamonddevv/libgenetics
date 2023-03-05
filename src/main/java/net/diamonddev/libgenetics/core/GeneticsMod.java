package net.diamonddev.libgenetics.core;

import com.google.gson.annotations.SerializedName;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.JsonConfigFile;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.JsonConfigFileRegistry;
import net.diamonddev.libgenetics.core.command.DataLoaderResourceManagerArgument;
import net.diamonddev.libgenetics.core.command.JsonConfigFileIdentifierArgument;
import net.diamonddev.libgenetics.core.command.LibGeneticsCommand;
import net.diamonddev.libgentest.GeneticsTest;
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

	public static LibGeneticsConfig LIBGENETICS_CONFIG;
	@Override
	public void onInitialize() {
		// Config
		LIBGENETICS_CONFIG = JsonConfigFileRegistry.registerAndReadAsSelf(id("libgenetics_default_config"), new LibGeneticsConfig(), LibGeneticsConfig.class);

		// cmd args
		ArgumentTypeRegistry.registerArgumentType(id("dataloader_manager_command_arg"),
				DataLoaderResourceManagerArgument.class, ConstantArgumentSerializer.of(DataLoaderResourceManagerArgument::resourceManager));
		ArgumentTypeRegistry.registerArgumentType(id("json_config_file_command_arg"),
				JsonConfigFileIdentifierArgument.class, ConstantArgumentSerializer.of(JsonConfigFileIdentifierArgument::config));

		// cmd
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> LibGeneticsCommand.register(dispatcher));

		// test
		if (hasDevTools()) GeneticsTest.testInit();

		LOGGER.info("Initialized libGenetics.");
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
	public static boolean hasDevTools() {
		return LIBGENETICS_CONFIG.dev.hasDevTests;
	}
	public static class LibGeneticsConfig implements JsonConfigFile {
		@Override
		public String getFilePathFromConfigDirectory() {
			return ".diamonddev/libgenetics.json";
		}

		@SerializedName("libgenetics_cmd_permission_level")
		public int libgeneticsCommandPermissionLevel = 4;

		@SerializedName("development")
		public Dev dev = new Dev();

		public static class Dev {
			@SerializedName("unlock_dev_tests")
			public boolean hasDevTests = FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment();
		}
	}
}
