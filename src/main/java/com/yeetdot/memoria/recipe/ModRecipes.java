package com.yeetdot.memoria.recipe;

import com.yeetdot.memoria.Memoria;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<MnemonicInfuserRecipe> MNEMONIC_INFUSER_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(Memoria.MOD_ID, "mnemonic_infuser"),
            new MnemonicInfuserRecipe.Serializer());

    public static final RecipeType<MnemonicInfuserRecipe> MNEMONIC_INFUSER_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(Memoria.MOD_ID, "mnemonic_infuser"),
            new RecipeType<MnemonicInfuserRecipe>() {
                @Override
                public String toString() {
                    return "mnemonic_infuser";
                }
            }
    );

    public static void registerRecipes() {}
}
