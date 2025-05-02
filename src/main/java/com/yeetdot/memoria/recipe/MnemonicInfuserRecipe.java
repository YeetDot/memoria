package com.yeetdot.memoria.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.*;

public record MnemonicInfuserRecipe(Ingredient mainInput, List<Ingredient> ingredients, int duration,
                                    ItemStack output) implements Recipe<MnemonicInfuserRecipeInput> {

    @Override
    public boolean matches(MnemonicInfuserRecipeInput input, World world) {
        if (!mainInput.test(input.mainInput())) return false;
        if (input.subInputs().size() != ingredients.size()) return false;
        List<Ingredient> requiredSubs = new ArrayList<>(ingredients);
        for (ItemStack stack : input.subInputs()) {
            boolean matched = false;

            Iterator<Ingredient> iterator = requiredSubs.iterator();
            while (iterator.hasNext()) {
                Ingredient ingredient = iterator.next();
                if (ingredient.test(stack)) {
                    iterator.remove();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        return requiredSubs.isEmpty();
    }

    @Override
    public ItemStack craft(MnemonicInfuserRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MnemonicInfuserRecipeInput>> getSerializer() {
        return ModRecipes.MNEMONIC_INFUSER_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<MnemonicInfuserRecipeInput>> getType() {
        return ModRecipes.MNEMONIC_INFUSER_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return null;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<MnemonicInfuserRecipe> {
        private static final MapCodec<MnemonicInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("mainInput").forGetter(recipe -> recipe.mainInput),
                Ingredient.CODEC.listOf(1, 8).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                Codec.INT.fieldOf("duration").forGetter(recipe -> recipe.duration),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
        ).apply(instance, MnemonicInfuserRecipe::new));

        private static final PacketCodec<RegistryByteBuf, MnemonicInfuserRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, recipe -> recipe.mainInput,
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), recipe -> recipe.ingredients,
                PacketCodecs.INTEGER, recipe -> recipe.duration,
                ItemStack.PACKET_CODEC, recipe -> recipe.output,
                MnemonicInfuserRecipe::new
        );

        @Override
        public MapCodec<MnemonicInfuserRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MnemonicInfuserRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
