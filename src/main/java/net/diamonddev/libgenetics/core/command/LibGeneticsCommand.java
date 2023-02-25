package net.diamonddev.libgenetics.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.diamonddev.libgenetics.common.api.v1.config.JsonConfigFileWrapper;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class LibGeneticsCommand {

    private static final String RESOURCE_LISTENER_ID = "resource_listener_id";

    private static final String CONFIG_ID = "config_id";
    private static final String CONFIG_KEY = "config_key";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("libgenetics").requires(scs -> scs.hasPermissionLevel(2))
                        .then(literal("getcache")
                                .then(argument(RESOURCE_LISTENER_ID, DataLoaderResourceManagerArgument.resourceManager())
                                        .executes(LibGeneticsCommand::exeGetResourceCache)
                                )
                        ).then(literal("getconfig")
                                .then(argument(CONFIG_ID, JsonConfigFileIdentifierArgument.config())
                                        .executes(LibGeneticsCommand::exeGetConfigFileJson)
                                )
                        )
        );
    }

    private static int exeGetConfigFileJson(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        JsonConfigFileWrapper config = JsonConfigFileIdentifierArgument.getConfigJson(context, CONFIG_ID);
        context.getSource().sendFeedback(Text.literal(config.read().toString()), false);
        return 1;
    }

    private static int exeGetResourceCache(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        DataLoaderResourceManager manager = DataLoaderResourceManagerArgument.getManager(context, RESOURCE_LISTENER_ID);
        context.getSource().sendFeedback(Text.literal(manager.CACHE.toString()), true);
        return 1;
    }
}
