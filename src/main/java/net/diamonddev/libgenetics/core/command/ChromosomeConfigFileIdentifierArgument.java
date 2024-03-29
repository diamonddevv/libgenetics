package net.diamonddev.libgenetics.core.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileRegistry;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileWrapper;
import net.diamonddev.libgenetics.common.api.v1.util.GeneralUtil;
import net.diamonddev.libgenetics.core.command.abstraction.StringArrayListArgType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;

public class ChromosomeConfigFileIdentifierArgument extends StringArrayListArgType {
    private static final DynamicCommandExceptionType INVALID_EXCEPTION =
            new DynamicCommandExceptionType((id) -> Text.literal("JSON Config File " + id + " was not found in register!"));

    private ChromosomeConfigFileIdentifierArgument() {}
    public static ChromosomeConfigFileIdentifierArgument config() {return new ChromosomeConfigFileIdentifierArgument();}

    public static ChromosomeConfigFileWrapper getConfigJson(CommandContext<ServerCommandSource> context, String argumentName) throws CommandSyntaxException {
        Collection<Identifier> ids = ChromosomeConfigFileRegistry.HASH.keySet();
        String name = context.getArgument(argumentName, String.class);
        Identifier id = null;
        for (Identifier identifier : ids) {
            if (identifier.toString().matches(name)) {
                id = identifier;
            }
        }

        if (id == null) {
            throw INVALID_EXCEPTION.create(name);
        } else return ChromosomeConfigFileRegistry.HASH.get(id);
    }

    @Override
    public ArrayList<String> getArray() {
        return GeneralUtil.toArrList(GeneralUtil.remapCollection(ChromosomeConfigFileRegistry.HASH.keySet(), ChromosomeConfigFileIdentifierArgument::remapIdToString));
    }

    private static String remapIdToString(Identifier t) {
        return t.toString();
    }

}
