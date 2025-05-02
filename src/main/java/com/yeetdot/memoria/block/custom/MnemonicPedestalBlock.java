package com.yeetdot.memoria.block.custom;

import com.mojang.serialization.MapCodec;
import com.yeetdot.memoria.block.entity.custom.PedestalBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MnemonicPedestalBlock extends AbstractPedestalBlock{
    private static final MapCodec<MnemonicPedestalBlock> CODEC = createCodec(MnemonicPedestalBlock::new);

    public MnemonicPedestalBlock(Settings settings) {
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
}
