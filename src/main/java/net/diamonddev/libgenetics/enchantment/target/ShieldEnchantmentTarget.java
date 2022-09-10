package net.diamonddev.libgenetics.enchantment.target;

import net.diamonddev.libgenetics.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;

public class ShieldEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof ShieldItem;
    }
}
