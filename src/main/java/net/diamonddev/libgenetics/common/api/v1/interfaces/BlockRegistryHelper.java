package net.diamonddev.libgenetics.common.api.v1.interfaces;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public interface BlockRegistryHelper {
    HashMap<Block, BlockItem> hashedBlockItems = new HashMap<>();

    default void registerBlockAndItem(Block block, Identifier identifier, Item.Settings settings) {
        BlockItem bi = new BlockItem(block, settings);

        Registry.register(Registries.BLOCK, identifier, block);
        Registry.register(Registries.ITEM, identifier, bi); //todo update mcver

        hashedBlockItems.put(block, bi);
    }

    static BlockItem getBlockItem(Block block) {
        return hashedBlockItems.get(block);
    }
}
