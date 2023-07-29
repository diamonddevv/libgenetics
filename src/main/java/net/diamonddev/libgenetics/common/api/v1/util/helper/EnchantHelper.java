package net.diamonddev.libgenetics.common.api.v1.util.helper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.item.EnchantedBookItem.STORED_ENCHANTMENTS_KEY;

public class EnchantHelper {

    public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
        return EnchantmentHelper.getLevel(enchantment, stack) > 0;
    }

    public static boolean hasEnchantmentStored(ItemStack stack, Enchantment enchantment) {
        boolean bl = false;
        if (stack.getItem() instanceof EnchantedBookItem) {
            NbtList list = EnchantedBookItem.getEnchantmentNbt(stack);
            Map<Enchantment, Integer> mappedStoredEnchants = EnchantmentHelper.fromNbt(list);

            for (Enchantment e : mappedStoredEnchants.keySet()) {
                if (enchantment == e) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    public static void storeEnchantment(ItemStack stack, EnchantmentLevelEntry enchantmentLevelEntry) {
        NbtList nbtList = getStoredEnchantments(stack);
        boolean bl = true;
        Identifier identifier = EnchantmentHelper.getEnchantmentId(enchantmentLevelEntry.enchantment);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            Identifier identifier2 = EnchantmentHelper.getIdFromNbt(nbtCompound);
            if (identifier2 != null && identifier2.equals(identifier)) {
                if (EnchantmentHelper.getLevelFromNbt(nbtCompound) < enchantmentLevelEntry.level) {
                    EnchantmentHelper.writeLevelToNbt(nbtCompound, enchantmentLevelEntry.level);
                }

                bl = false;
                break;
            }
        }

        if (bl) {
            nbtList.add(EnchantmentHelper.createNbt(identifier, enchantmentLevelEntry.level));
        }

        stack.getOrCreateNbt().put("StoredEnchantments", nbtList);
    }

    public static void storeAllEnchantments(ItemStack stack, Map<Enchantment, Integer> mappedEnchantsToAdd) {
        mappedEnchantsToAdd.forEach((enchantment, integer) -> {
            if (hasEnchantmentStored(stack, enchantment)) {
                if (integer >= getStoredEnchantmentLevel(stack, enchantment)) {
                    upgradeStoredEnchantment(stack, enchantment);
                }
            } else {
                storeEnchantment(stack, new EnchantmentLevelEntry(enchantment, integer));
            }
        });
    }

    public static void addAllEnchantments(ItemStack stack, Map<Enchantment, Integer> mappedEnchantsToAdd) {
        mappedEnchantsToAdd.forEach((enchantment, integer) -> {
            if (hasEnchantment(enchantment, stack)) {
                if (integer >= getEnchantmentLevel(stack, enchantment)) {
                    upgradeExistingEnchantment(stack, enchantment);
                }
            } else {
                stack.addEnchantment(enchantment, integer);
            }
        });
    }

    public static NbtList getStoredEnchantments(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null ? nbt.getList(STORED_ENCHANTMENTS_KEY, 10) : new NbtList();
    }

    public static Map<Enchantment, Integer> getMappedStoredEnchantments(ItemStack stack) {
        return EnchantmentHelper.fromNbt(getStoredEnchantments(stack));
    }

    public static void upgradeStoredEnchantment(ItemStack stack, Enchantment enchantment) {
        Map<Enchantment, Integer> existingMap = getMappedStoredEnchantments(stack);
        int existingLevel = existingMap.get(enchantment);
        if (existingLevel < enchantment.getMaxLevel()) {
            existingMap.put(enchantment, existingLevel + 1);
        }
        setStoredEnchantsFromMap(existingMap, stack);
    }

    public static void upgradeExistingEnchantment(ItemStack stack, Enchantment enchantment) {
        Map<Enchantment, Integer> existingMap = EnchantmentHelper.get(stack);
        int existingLevel = existingMap.get(enchantment);
        if (existingLevel < enchantment.getMaxLevel()) {
            existingMap.put(enchantment, existingLevel + 1);
        }
        EnchantmentHelper.set(existingMap, stack);
    }

    public static int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        return EnchantmentHelper.getLevel(enchantment, stack);
    }

    public static int getStoredEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        return getMappedStoredEnchantments(stack).get(enchantment);
    }

    public static int getXpCostForAddingEnchants(ItemStack stack, Map<Enchantment, Integer> mappedEnchantsToAdd) {
        float cost = 0.0f;
        if (stack.getItem() instanceof ToolItem toolItem) {
            cost = cost + toolItem.getEnchantability() * 0.75f;
        }
        for (Enchantment e : mappedEnchantsToAdd.keySet()) {
            int thisLevel = mappedEnchantsToAdd.get(e);
            float f = 0;

            switch (e.getRarity()) {
                case COMMON -> f = 1.25f;
                case UNCOMMON -> f = 1.5f;
                case RARE -> f = 1.75f;
                case VERY_RARE -> f = 2.0f;
            }
            f = f * thisLevel;
            cost = cost + f;
        }
        return (int) Math.floor(cost);
    }

    public static boolean allCompatible(Map<Enchantment, Integer> map1, Map<Enchantment, Integer> map2) {
        boolean bl = true;
        for (Enchantment e : map1.keySet()) {
            for (Enchantment e2 : map2.keySet()) {
                bl = canMergeEnchants(e, e2);
                if (!bl) {
                    break;
                }
            }
            if (!bl) {
                break;
            }
        }
        return bl;
    }

    public static boolean canMergeEnchants(Enchantment ench1, Enchantment ench2) {
        return ench1.canCombine(ench2) || ench1 == ench2;
    }

    public static boolean allAcceptable(Map<Enchantment, Integer> map, ItemStack stack) {
        boolean bl = true;
        for (Enchantment e : map.keySet()) {
            bl = e.isAcceptableItem(stack);
            if (!bl) {
                break;
            }
        }
        return bl;
    }

    public static NbtList mappedEnchantmentsToNbtList(Map<Enchantment, Integer> enchantMap) {
        NbtList nbt = new NbtList();

        for (Map.Entry<Enchantment, Integer> mappedEntry : enchantMap.entrySet()) {
            Enchantment enchant = mappedEntry.getKey();
            if (enchant != null) {
                Integer level = mappedEntry.getValue();
                nbt.add(EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchant), level));
            }
        }

        return nbt;
    }

    public static void setStoredEnchantsFromMap(Map<Enchantment, Integer> enchantMap, ItemStack stack) {
        NbtList nbt = mappedEnchantmentsToNbtList(enchantMap);
        stack.getOrCreateNbt().put(STORED_ENCHANTMENTS_KEY, nbt);
    }

    public static Map<Enchantment, Integer> enchantmentLevelEntryArrayToMap(ArrayList<EnchantmentLevelEntry> eles) {
        Map<Enchantment, Integer> map = new HashMap<>();
        for (EnchantmentLevelEntry ele : eles) {
            map.put(ele.enchantment, ele.level);
        }
        return map;
    }

}
