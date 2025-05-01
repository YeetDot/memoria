package com.yeetdot.memoria.datagen;

import com.yeetdot.memoria.block.ModBlocks;
import com.yeetdot.memoria.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                offerStonecuttingRecipe(RecipeCategory.MISC, ModItems.QUARTZ_BALL, Items.QUARTZ);
                ShapedRecipeJsonBuilder.create(wrapperLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModBlocks.MNEMONIC_INFUSER)
                        .pattern("EAE")
                        .pattern("QMQ")
                        .pattern("ODO")
                        .input('E', Items.ECHO_SHARD)
                        .input('A', Items.AMETHYST_SHARD)
                        .input('Q', Blocks.QUARTZ_BLOCK)
                        .input('M', ModItems.MEMORY_SHARD)
                        .input('O', Blocks.OBSIDIAN)
                        .input('D', Blocks.DIAMOND_BLOCK)
                        .criterion(hasItem(Items.ECHO_SHARD),conditionsFromItem(Items.ECHO_SHARD))
                        .offerTo(recipeExporter);

            }
        };
    }

    @Override
    public String getName() {
        return "recipe_provider";
    }
}
