package net.diamonddev.libgenetics.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.diamonddev.libgenetics.common.api.v1.config.JsonConfigFileWrapper;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceManager;
import net.diamonddev.libgenetics.core.GeneticsMod;
import net.diamonddev.libgentest.GeneticsTest;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LibGeneticsCommand {

    private static final String RESOURCE_LISTENER_ID = "resource_listener_id";

    private static final String CONFIG_ID = "config_id";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("libgenetics").requires(scs -> scs.hasPermissionLevel(GeneticsMod.LIBGENETICS_CONFIG.libgeneticsCommandPermissionLevel))
                        .then(literal("getcache")

                                .then(argument(RESOURCE_LISTENER_ID, DataLoaderResourceManagerArgument.resourceManager())
                                        .executes(LibGeneticsCommand::exeGetResourceCache)
                                )

                        ).then(literal("getconfig")

                                .then(argument(CONFIG_ID, JsonConfigFileIdentifierArgument.config())
                                        .executes(LibGeneticsCommand::exeGetConfigFileJson)

                                        .then(literal("getpath")
                                                .executes(LibGeneticsCommand::exeGetConfigFilePath)
                                        )
                                )

                        ).then(literal("devtools").requires(src -> GeneticsMod.hasDevTools())
                                .then(literal("printouttest")
                                        .executes(LibGeneticsCommand::exeDevHardcodedPrintoutTest)
                                )
                        )
        );
    }

    private static int exeDevHardcodedPrintoutTest(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        GeneticsTest.listener.getManager().forEachRecipe(GeneticsTest.type, res -> {
            GeneticsTest.SerializationPojo pojo = res.getAsClass(GeneticsTest.SerializationPojo.class);
            System.out.println(pojo.yes);
            System.out.println(pojo.no);
        });

        return 1;
    }

    private static int exeGetConfigFilePath(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        JsonConfigFileWrapper config = JsonConfigFileIdentifierArgument.getConfigJson(context, CONFIG_ID);
        context.getSource().sendFeedback(Text.literal(config.getPath()), false);
        return 1;
    }

    private static int exeGetConfigFileJson(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        JsonConfigFileWrapper config = JsonConfigFileIdentifierArgument.getConfigJson(context, CONFIG_ID);
        context.getSource().sendFeedback(Text.literal(config.readNoFileManagement().toString()), false);
        return 1;
    }

    private static int exeGetResourceCache(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        DataLoaderResourceManager manager = DataLoaderResourceManagerArgument.getManager(context, RESOURCE_LISTENER_ID);
        context.getSource().sendFeedback(Text.literal(manager.CACHE.toString()), true);
        return 1;
    }
}
