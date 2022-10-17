package net.diamonddev.libgenetics.common.api.v1.enchantment.target;

import net.diamonddev.libgenetics.core.mixin.EnchantmentTargetMixin;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;

public class ElytraEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof ElytraItem;
    }
}
