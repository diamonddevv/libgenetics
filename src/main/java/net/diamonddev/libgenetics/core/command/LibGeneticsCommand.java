package net.diamonddev.libgenetics.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class LibGeneticsCommand {

    private static final String RESOURCE_LISTENER_ID = "resource_listener_id";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("libgenetics").requires(scs -> scs.hasPermissionLevel(2))
                        .then(literal("getcache")
                                .then(argument(RESOURCE_LISTENER_ID, DataLoaderResourceManagerArgument.resourceManager())
                                        .executes(LibGeneticsCommand::exeGetResourceCache)
                                )
                        )
        );
    }

    private static int exeGetResourceCache(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        DataLoaderResourceManager manager = DataLoaderResourceManagerArgument.getManager(context, RESOURCE_LISTENER_ID);
        context.getSource().sendFeedback(Text.literal(manager.CACHE.toString()), true);
        return 1;
    }
}
