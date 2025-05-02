package com.yeetdot.memoria.block.entity.custom;

import com.yeetdot.memoria.block.entity.ModBlockEntities;
import com.yeetdot.memoria.recipe.MnemonicInfuserRecipe;
import com.yeetdot.memoria.recipe.MnemonicInfuserRecipeInput;
import com.yeetdot.memoria.recipe.ModRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.*;

public class MnemonicInfuserBlockEntity extends PedestalBlockEntity{
    private int progress = 0;
    private int maxProgress = 100;
    private List<ItemStack> nearbyPedestalContents = new ArrayList<>();

    public MnemonicInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MNEMONIC_INFUSER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putInt("mnemonic_infuser.progress", progress);
        nbt.putInt("mnemonic_infuser.max_progress", progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.readNbt(nbt, inventory, registries);
        progress = nbt.getInt("mnemonic_infuser.progress").get();
        maxProgress = nbt.getInt("mnemonic_infuser.max_progress").get();
        super.readNbt(nbt, registries);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (hasRecipe(world)) {
            increaseCraftingProgress();
            markDirty(world, pos, state);

            if (hasFinishedCrafting()) {
                craftItem(world);
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem(World world) {
        Optional<RecipeEntry<MnemonicInfuserRecipe>> recipe = getCurrentRecipe(world);
        this.maxProgress = recipe.get().value().duration();
        ItemStack result = recipe.get().value().output();
        this.removeStack(0);
        this.setStack(0, new ItemStack(result.getItem()));
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasFinishedCrafting() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean hasRecipe(World world) {
        Optional<RecipeEntry<MnemonicInfuserRecipe>> recipe = getCurrentRecipe(world);
        return recipe.isPresent();
    }

    public String test () {
        return getItems().getFirst().toString();
    }

    public List<ItemStack> getNearbyPedestalContents(World world) {
            BlockPos pos = this.getPos();
            List<Vec3i> poses = List.of(
                    new Vec3i(3, 0, 0),
                    new Vec3i(2, 0, 2),
                    new Vec3i(0, 0, 3),
                    new Vec3i(-2, 0, 2),
                    new Vec3i(-3, 0, 0),
                    new Vec3i(-2, 0, -2),
                    new Vec3i(0, 0, -3),
                    new Vec3i(2, 0, -2));

            for (int i = 0; i < 8; i++) {
                BlockEntity entity = world.getBlockEntity(pos.add(poses.get(i)));
                if (entity instanceof PedestalBlockEntity pedestal) {
                    nearbyPedestalContents.add(pedestal.getItems().getFirst());
                }
            }
//          nearbyPedestalContents.sort(Comparator.comparing((itemStack -> itemStack.getItem().toString())));


        return nearbyPedestalContents;
    }

    private Optional<RecipeEntry<MnemonicInfuserRecipe>> getCurrentRecipe(World world) {
        return this.getWorld().getServer().getRecipeManager().getFirstMatch(ModRecipes.MNEMONIC_INFUSER_TYPE, new MnemonicInfuserRecipeInput(inventory.getFirst(), getNearbyPedestalContents(world)), this.getWorld());
    }
}
