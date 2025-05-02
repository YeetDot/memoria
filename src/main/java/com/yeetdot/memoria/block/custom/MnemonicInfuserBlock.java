package com.yeetdot.memoria.block.custom;

import com.mojang.serialization.MapCodec;
import com.yeetdot.memoria.block.entity.ModBlockEntities;
import com.yeetdot.memoria.block.entity.custom.MnemonicInfuserBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (createBlockEntity(pos, state) instanceof MnemonicInfuserBlockEntity entity) {
            player.sendMessage(Text.of(entity.getNearbyPedestalContents(world).toString()), false);
            player.sendMessage(Text.of(entity.test()), false);
            if (stack.isOf(Items.ECHO_SHARD)) {
                player.sendMessage(Text.of("hi"), false);
                player.sendMessage(Text.of(entity.test()), false);
                return ActionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MnemonicInfuserBlockEntity(pos, state);
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
