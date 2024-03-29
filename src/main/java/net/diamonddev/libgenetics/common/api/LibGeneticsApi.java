package net.diamonddev.libgenetics.common.api;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.diamonddev.libgenetics.core.command.LibGeneticsCommand;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;


public interface LibGeneticsApi {

    /**
     * Used to add new branches to the LibGenetics command.
     * @see LibGeneticsCommand
     */
    default void addLibGeneticsCommandBranches(LiteralArgumentBuilder<ServerCommandSource> root, ArrayList<ArgumentBuilder<ServerCommandSource, ?>> branches) {

    }
}
