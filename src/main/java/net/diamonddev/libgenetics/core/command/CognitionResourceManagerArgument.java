package net.diamonddev.libgenetics.core.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataListener;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.diamonddev.libgenetics.common.api.v1.util.GeneralUtil;
import net.diamonddev.libgenetics.core.command.abstraction.StringArrayListArgType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collection;

public class CognitionResourceManagerArgument extends StringArrayListArgType {

    private static final DynamicCommandExceptionType INVALID_EXCEPTION =
            new DynamicCommandExceptionType((id) -> Text.literal("Listener " + id + " was not found in register!"));

    private CognitionResourceManagerArgument() {}
    public static CognitionResourceManagerArgument resourceManager() {return new CognitionResourceManagerArgument();}

    public static CognitionResourceManager getManager(CommandContext<ServerCommandSource> context, String argumentName) throws CommandSyntaxException {
        ArrayList<CognitionDataListener> listeners = CognitionRegistry.listeners;
        String name = context.getArgument(argumentName, String.class);
        CognitionResourceManager mgr = null;
        for (CognitionDataListener listener : listeners) {
            if (listener.getFabricId().toString().matches(name)) {
                mgr = listener.getManager();
            }
        }

        if (mgr == null) {
            throw INVALID_EXCEPTION.create(name);
        } else return mgr;
    }
    @Override
    public ArrayList<String> getArray() {
        return GeneralUtil.remapArrayList(CognitionRegistry.listeners, (listener) -> listener.getFabricId().toString());
    }
}
