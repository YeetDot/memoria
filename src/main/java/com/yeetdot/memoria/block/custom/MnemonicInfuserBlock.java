package com.yeetdot.memoria.block.custom;

import com.mojang.serialization.MapCodec;
import com.yeetdot.memoria.block.entity.ModBlockEntities;
import com.yeetdot.memoria.block.entity.custom.PedestalBlockEntity;
import com.yeetdot.memoria.recipe.MnemonicInfuserRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MnemonicInfuserBlock extends AbstractPedestalBlock {
    private static final MapCodec<MnemonicInfuserBlock> CODEC = createCodec(MnemonicInfuserBlock::new);

    public MnemonicInfuserBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }

        return validateTicker(type, ModBlockEntities.MNEMONIC_INFUSER_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
