package net.diamonddev.libgenetics.asm;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");

        ClassTinkerers.enumBuilder(enchantTarget)
                .addEnumSubclass("ELYTRA", "net.diamonddev.libgenetics.enchantment.target.ElytraEnchantmentTarget").build();
        ClassTinkerers.enumBuilder(enchantTarget)
                .addEnumSubclass("SHIELD", "net.diamonddev.libgenetics.enchantment.target.ShieldEnchantmentTarget").build();

    }
}