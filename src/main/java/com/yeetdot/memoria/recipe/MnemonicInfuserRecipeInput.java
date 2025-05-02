package com.yeetdot.memoria.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record MnemonicInfuserRecipeInput(ItemStack mainInput, List<ItemStack> subInputs) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot == 0 ? mainInput : subInputs.get(slot-1);
    }

    @Override
    public int size() {
        return 1;
    }
}
