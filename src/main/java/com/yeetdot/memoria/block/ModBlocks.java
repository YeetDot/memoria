package com.yeetdot.memoria.block;

import com.yeetdot.memoria.Memoria;
import com.yeetdot.memoria.block.custom.MnemonicInfuserBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static final Block MNEMONIC_INFUSER = register("mnemonic_infuser", MnemonicInfuserBlock::new, AbstractBlock.Settings.copy(Blocks.ENCHANTING_TABLE).nonOpaque());
    public static final Block MNEMONIC_PEDESTAL = register("mnemonic_pedestal", Block::new, AbstractBlock.Settings.create());

    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean createItem) {
        Block block = factory.apply(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Memoria.MOD_ID, name))));
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Memoria.MOD_ID, name));
        if (createItem) {
            registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return register(name, factory, settings, true);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Memoria.MOD_ID, name)), new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Memoria.MOD_ID, name)))));
    }

    public static void registerBlocks() {}
}
