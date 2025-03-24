package com.oierbravo.createsifter.foundation.data.recipe;

import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.mechanicals.foundation.data.AbstractMechanicalRecipeGenerator;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class SiftingRecipeGen extends AbstractMechanicalRecipeGenerator<SiftingRecipeBuilder> {

    public SiftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,
                MODID,
                SiftingRecipe.Type.ID,
                SiftingRecipeBuilder::new,
                "Sifting recipes"
                );
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        createAndesite("gravel", Blocks.GRAVEL)
                .output(.05f, AllItems.COPPER_NUGGET::get,1)
                .output(.01f, AllItems.ZINC_NUGGET::get,1)
                .output(.01f, Items.IRON_NUGGET,1)
                .output(.05f, Items.GOLD_NUGGET,1)
                .output(.10f, Items.COAL,1)
                .output(.1f, Items.FLINT, 1)
                .save(recipeOutput);


        createBrass("gravel", Blocks.GRAVEL)
                .output(.1f, AllItems.CRUSHED_COPPER::get,1)
                .output(.1f, AllItems.CRUSHED_ZINC::get,1)
                .output(.05f, AllItems.CRUSHED_GOLD::get,1)
                .output(.10f, AllItems.CRUSHED_IRON::get,1)
                .output(.10f, Items.LAPIS_LAZULI,1)
                .output(.15f, Items.COAL,1)
                .output(.1f, Items.FLINT, 1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAdvancedBrass("gravel", Blocks.GRAVEL)
                .output(.10f, AllItems.CRUSHED_COPPER::get,1)
                .output(.10f, AllItems.CRUSHED_ZINC::get,1)
                .output(.05f, AllItems.CRUSHED_GOLD::get,1)
                .output(.15f, AllItems.CRUSHED_IRON::get,1)
                .output(.10f, Items.LAPIS_LAZULI,1)
                .output(.05f, Items.DIAMOND,1)
                .output(.02f, Items.EMERALD,1)
                .output(.1f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        /*createString("sand_string_mesh")
                .require(Blocks.SAND)
                .requiredMesh(ModItems.STRING_MESH)
                .output(.05f, Items.REDSTONE,1)
                .output(.4f, Items.BONE_MEAL,1)
                .save(recipeOutput);*/

        createAndesite("sand", Blocks.SAND)
                .output(.1f, Items.REDSTONE,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createBrass("sand",Blocks.SAND)
                .output(.25f, Items.REDSTONE,2)
                .output(.10f, Items.GLOWSTONE_DUST,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.05f, Items.BLAZE_POWDER,1)
                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createBrass("soul_sand", Blocks.SOUL_SAND)
                .output(.10f, Items.QUARTZ,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAdvancedBrass("soul_sand", Blocks.SOUL_SAND)
                .output(.45f, Items.QUARTZ,1)
                .output(.15f, Items.QUARTZ,1)
                .output(.05f, Items.GHAST_TEAR,1)
                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);


        createString("moss", Blocks.MOSS_BLOCK)
                .output(.1f, Items.OAK_SAPLING)
                .output(.1f, Items.SPRUCE_SAPLING)
                .output(.1f, Items.BIRCH_SAPLING)
                .output(.1f, Items.JUNGLE_SAPLING)
                .output(.1f, Items.ACACIA_SAPLING)
                .output(.1f, Items.DARK_OAK_SAPLING)
                .output(.1f, Items.CHERRY_SAPLING)
                .save(recipeOutput);

        createAndesite("moss", Blocks.MOSS_BLOCK)
                .output(.10f, Items.OAK_SAPLING)
                .output(.10f, Items.SPRUCE_SAPLING)
                .output(.10f, Items.BIRCH_SAPLING)
                .output(.10f, Items.JUNGLE_SAPLING)
                .output(.10f, Items.ACACIA_SAPLING)
                .output(.10f, Items.DARK_OAK_SAPLING)
                .output(.10f, Items.CHERRY_SAPLING)
                .save(recipeOutput);

        createString("dirt", Blocks.DIRT)
                .output(.20f, Items.WHEAT_SEEDS)
                .output(.10f, Items.BEETROOT_SEEDS)
                .output(.05f, Items.MELON_SEEDS)
                .output(.10f, Items.SHORT_GRASS)
                .output(.02f, Items.POTATO)
                .output(.02f, Items.CARROT)
                .save(recipeOutput);

        createAndesite("dirt", Blocks.DIRT, true)
                .output(.20f, Items.KELP)
                .output(.30f, Items.SEAGRASS)
                .output(.05f, Items.TUBE_CORAL)
                .output(.05f, Items.BRAIN_CORAL)
                .output(.05f, Items.BUBBLE_CORAL)
                .output(.05f, Items.FIRE_CORAL)
                .output(.05f, Items.HORN_CORAL)
                .save(recipeOutput);

        //Compat recipes
        createString("enderio_moss", Blocks.MOSS_BLOCK)
                .output(.01f, ResourceLocation.fromNamespaceAndPath("enderio","grains_of_infinity"),1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);

        createString("arch_moss", Blocks.MOSS_BLOCK)
                .output(.01f, Mods.ARS_N,"blue_archwood_sappling",1)
                .output(.01f, Mods.ARS_N,"red_archwood_sappling",1)
                .output(.01f, Mods.ARS_N,"green_archwood_sappling",1)
                .output(.01f, Mods.ARS_N,"purple_archwood_sappling",1)
                .withCondition(new ModLoadedCondition("ars_nouveau"))
                .saveCompat(recipeOutput);

        createAndesite("arch_moss", Blocks.MOSS_BLOCK)
                .output(.1f, Mods.ARS_N,"blue_archiwood_sappling",1)
                .output(.1f, Mods.ARS_N,"red_archiwood_sappling",1)
                .output(.1f, Mods.ARS_N,"green_archiwood_sappling",1)
                .output(.1f, Mods.ARS_N,"purple_archiwood_sappling",1)
                .withCondition(new ModLoadedCondition("ars_nouveau"))
                .saveCompat(recipeOutput);

    }
    public SiftingRecipeBuilder createString(String name, ItemLike input){
        return createString(name, input, false, false);
    }
    public SiftingRecipeBuilder createString(String name, ItemLike input, boolean waterlogged){
        return createString(name, input, waterlogged, false);
    }
    public SiftingRecipeBuilder createString(String name, ResourceLocation resourceLocation, boolean waterlogged, boolean handOnly){
        return createString(name, BuiltInRegistries.ITEM.get(resourceLocation).asItem(), waterlogged, handOnly);
    }
    public SiftingRecipeBuilder createString(String name, ItemLike input, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "string", waterlogged, handOnly))
                .requiredMesh(ModItems.STRING_MESH);
        if(waterlogged)
            return builder.isWaterlogged();
        return builder;

    }

    public SiftingRecipeBuilder createAndesite(String name, ItemLike input){
        return createAndesite(name, input, false, false);
    }
    public SiftingRecipeBuilder createAndesite(String name, ItemLike input, boolean waterlogged){
        return createAndesite(name, input, waterlogged, false);
    }
    public SiftingRecipeBuilder createAndesite(String name, ItemLike input, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "andesite", waterlogged, handOnly))
                .requiredMesh(ModItems.ANDESITE_MESH)
                .require(input);
        if(waterlogged)
            return builder.isWaterlogged();
        /*if(handOnly)
            return builder.isWaterlogged();*/
        return builder;

    }

    public SiftingRecipeBuilder createBrass(String name, ItemLike input){
        return createBrass(name, input, false, false);
    }
    public SiftingRecipeBuilder createBrass(String name, ItemLike input, boolean waterlogged){
        return createBrass(name, input, waterlogged, false);
    }
    public SiftingRecipeBuilder createBrass(String name, ItemLike input, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "brass", waterlogged, handOnly))
                .requiredMesh(ModItems.BRASS_MESH)
                .require(input);
        if(waterlogged)
            builder.isWaterlogged();
        /*if(handOnly)
            return builder.isWaterlogged();*/
        return builder;

    }

    public SiftingRecipeBuilder createSturdy(String name, ItemLike input){
        return createAdvancedBrass(name, input, false);
    }
    public SiftingRecipeBuilder createSturdy(String name, ItemLike input, boolean waterlogged){
        return createBrass(name, input, waterlogged, false);
    }
    public SiftingRecipeBuilder createSturdy(String name, ItemLike input, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "sturdy", waterlogged, false))
                .requiredMesh(ModItems.STURDY_MESH)
                .require(input);
        if(waterlogged)
            builder.isWaterlogged();
        /*if(handOnly)
            return builder.isWaterlogged();*/
        return builder;
    }
    public SiftingRecipeBuilder createAdvancedBrass(String name, ItemLike input){
        return createAdvancedBrass(name, input, false);
    }
    public SiftingRecipeBuilder createAdvancedBrass(String name, ItemLike input, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "advance_brass", waterlogged, false))
                .requiredMesh(ModItems.ADVANCED_BRASS_MESH)
                .require(input);
        if(waterlogged)
            builder.isWaterlogged();
        return builder;
    }

    public SiftingRecipeBuilder createAdvancedSturdy(String name, ItemLike input){
        return createAdvancedBrass(name, input, false);
    }
    public SiftingRecipeBuilder createAdvancedSturdy(String name, ItemLike input, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "advance_sturdy", waterlogged, false))
                .requiredMesh(ModItems.ADVANCED_STURDY_MESH)
                .require(input);
        if(waterlogged)
            builder.isWaterlogged();
        return builder;
    }
    private String generateRecipeName(String baseName, String mesh, boolean waterlogged, boolean handOnly){
        String id = baseName + "_" + mesh;
        if(waterlogged)
            id += "_waterlogged";
        if(handOnly)
            id += "_handonly";
        return id;
    }
}
