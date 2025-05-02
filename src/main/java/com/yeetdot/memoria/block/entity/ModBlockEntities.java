package com.yeetdot.memoria.block.entity;

import com.yeetdot.memoria.Memoria;
import com.yeetdot.memoria.block.ModBlocks;
import com.yeetdot.memoria.block.entity.custom.MnemonicInfuserBlockEntity;
import com.yeetdot.memoria.block.entity.custom.PedestalBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<MnemonicInfuserBlockEntity> MNEMONIC_INFUSER_BLOCK_ENTITY = create("mnemonic_infuser_block_entity", MnemonicInfuserBlockEntity::new, ModBlocks.MNEMONIC_INFUSER);
    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL_BLOCK_ENTITY = create("pedestal_block_entity", PedestalBlockEntity::new, ModBlocks.MNEMONIC_PEDESTAL);


    private static <T extends BlockEntity> BlockEntityType<T> create(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Memoria.MOD_ID, id), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void registerBlockEntityTypes() {}
}