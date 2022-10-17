package net.diamonddev.libgenetics.common.api.v1.enchantment.target;

import net.diamonddev.libgenetics.core.mixin.EnchantmentTargetMixin;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;

public class ShieldEnchantmentTarget extends EnchantmentTargetMixin {
    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof ShieldItem;
    }
}
