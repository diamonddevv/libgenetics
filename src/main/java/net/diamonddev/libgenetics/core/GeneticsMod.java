package net.diamonddev.libgenetics.core;

import com.google.gson.annotations.SerializedName;
import net.diamonddev.libgenetics.common.api.LibGeneticsEntrypointApi;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileRegistry;
import net.diamonddev.libgenetics.core.command.ChromosomeConfigFileIdentifierArgument;
import net.diamonddev.libgenetics.core.command.CognitionResourceManagerArgument;
import net.diamonddev.libgenetics.core.command.LibGeneticsCommand;
import net.diamonddev.libgentest.GeneticsTest;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GeneticsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("libGenetics");
	public static final String MODID = "libgenetics";

	public static LibGeneticsConfig LIBGENETICS_CONFIG;

	private static final String ENTRYPOINT = "libgenetics";
	public static List<EntrypointContainer<LibGeneticsEntrypointApi>> ENTRYPOINT_APIS;

	@Override
	public void onInitialize() {
		// Entrypoints
		ENTRYPOINT_APIS = loadEntrypoints();

		// Config
		LIBGENETICS_CONFIG = ChromosomeConfigFileRegistry.registerAndReadAsSelf(id("libgenetics_default_config"), new LibGeneticsConfig(), LibGeneticsConfig.class);

		// cmd args
		ArgumentTypeRegistry.registerArgumentType(id("dataloader_manager_command_arg"),
				CognitionResourceManagerArgument.class, ConstantArgumentSerializer.of(CognitionResourceManagerArgument::resourceManager));
		ArgumentTypeRegistry.registerArgumentType(id("json_config_file_command_arg"),
				ChromosomeConfigFileIdentifierArgument.class, ConstantArgumentSerializer.of(ChromosomeConfigFileIdentifierArgument::config));

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
		return LIBGENETICS_CONFIG.devConfig.hasDevTests;
	}

	private static List<EntrypointContainer<LibGeneticsEntrypointApi>> loadEntrypoints() {
		return FabricLoader.getInstance().getEntrypointContainers(ENTRYPOINT, LibGeneticsEntrypointApi.class);
	}

	public static class LibGeneticsConfig implements ChromosomeConfigFile {
		@Override
		public String getFilePathFromConfigDirectory() {
			return ".diamonddev/libgenetics.json";
		}

		@SerializedName("libGeneticsCommand")
		public LibGeneticsCommandConfig libGeneticsCommandConfig = new LibGeneticsCommandConfig();

		@SerializedName("development")
		public DevConfig devConfig = new DevConfig();

		public static class DevConfig {
			@SerializedName("unlockDevTests")
			public boolean hasDevTests = FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment();
		}
		public static class LibGeneticsCommandConfig {
			@SerializedName("commandPermissionLevel")
			public int permissionLevel = 4;

			@SerializedName("allowCustomBranches")
			public boolean allowCustomBranches = true;
		}
	}
}
