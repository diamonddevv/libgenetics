package net.diamonddev.libgentest;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.diamonddev.libgenetics.common.api.LibGeneticsApi;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class Api implements LibGeneticsApi {
    @Override
    public void addLibGeneticsCommandBranches(LiteralArgumentBuilder<ServerCommandSource> root, ArrayList<ArgumentBuilder<ServerCommandSource, ?>> branches) {
        branches.add(root.then(CommandManager.literal("test").executes(ctx -> {ctx.getSource().sendFeedback(Text.literal("passed"),false); return 1;})));
    }
}
