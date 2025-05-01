package com.yeetdot.memoria.block.custom;

import com.mojang.serialization.MapCodec;
import com.yeetdot.memoria.block.entity.custom.MnemonicInfuserBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MnemonicInfuserBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2,0,2,14,13,14);
    public static final MapCodec<MnemonicInfuserBlock> CODEC = MnemonicInfuserBlock.createCodec(MnemonicInfuserBlock::new);

    public MnemonicInfuserBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MnemonicInfuserBlockEntity(pos, state);
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof MnemonicInfuserBlockEntity) {
            ItemScatterer.spawn(world, pos, ((MnemonicInfuserBlockEntity) blockEntity));
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, moved);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof MnemonicInfuserBlockEntity blockEntity) {
            if (blockEntity.isEmpty() && !stack.isEmpty()) {
                blockEntity.setStack(0, stack.copyWithCount(1));
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                stack.decrement(1);

                blockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            } else if (!blockEntity.isEmpty() && !player.isSneaking()) {
                ItemStack stack1 = blockEntity.getStack(0);
                if (stack.isEmpty()) {
                    player.setStackInHand(hand, stack1);
                } else {
                    dropStack(world, pos, Direction.UP, stack1);
                }
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                blockEntity.clear();

                blockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            }
        }

        return ActionResult.SUCCESS;
    }
}
