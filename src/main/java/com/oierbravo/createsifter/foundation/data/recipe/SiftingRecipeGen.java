package com.oierbravo.createsifter.foundation.data.recipe;

import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.mechanicals.foundation.data.AbstractMechanicalRecipeGenerator;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
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

        createAndesite("gravel").require(Blocks.GRAVEL)
                .output(.05f, AllItems.COPPER_NUGGET::get,1)
                .output(.01f, AllItems.ZINC_NUGGET::get,1)
                .output(.01f, Items.IRON_NUGGET,1)
                .output(.05f, Items.GOLD_NUGGET,1)
                .output(.10f, Items.COAL,1)
                .output(.1f, Items.FLINT, 1)
                .save(recipeOutput);


        createBrass("gravel").require(Blocks.GRAVEL)
                .output(.1f, AllItems.CRUSHED_COPPER::get,1)
                .output(.1f, AllItems.CRUSHED_ZINC::get,1)
                .output(.05f, AllItems.CRUSHED_GOLD::get,1)
                .output(.10f, AllItems.CRUSHED_IRON::get,1)
                .output(.10f, Items.LAPIS_LAZULI,1)
                .output(.15f, Items.COAL,1)
                .output(.1f, Items.FLINT, 1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAdvancedBrass("gravel").require(Blocks.GRAVEL)
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

        createAndesite("sand").require(Blocks.SAND)
                .output(.1f, Items.REDSTONE,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createBrass("sand").require(Blocks.SAND)
                .output(.25f, Items.REDSTONE,2)
                .output(.10f, Items.GLOWSTONE_DUST,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.05f, Items.BLAZE_POWDER,1)
                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createBrass("soul_sand").require(Blocks.SOUL_SAND)
                .output(.10f, Items.QUARTZ,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAdvancedBrass("soul_sand").require(Blocks.SOUL_SAND)
                .output(.45f, Items.QUARTZ,1)
                .output(.15f, Items.QUARTZ,1)
                .output(.05f, Items.GHAST_TEAR,1)
                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);


        createString("leaves").require(ItemTags.LEAVES)
                .output(.1f, Items.OAK_SAPLING)
                .output(.1f, Items.SPRUCE_SAPLING)
                .output(.1f, Items.BIRCH_SAPLING)
                .output(.1f, Items.JUNGLE_SAPLING)
                .output(.1f, Items.ACACIA_SAPLING)
                .output(.1f, Items.DARK_OAK_SAPLING)
                .output(.1f, Items.CHERRY_SAPLING)
                .save(recipeOutput);

        createAndesite("leaves").require(ItemTags.LEAVES)
                .output(.10f, Items.OAK_SAPLING)
                .output(.10f, Items.SPRUCE_SAPLING)
                .output(.10f, Items.BIRCH_SAPLING)
                .output(.10f, Items.JUNGLE_SAPLING)
                .output(.10f, Items.ACACIA_SAPLING)
                .output(.10f, Items.DARK_OAK_SAPLING)
                .output(.10f, Items.CHERRY_SAPLING)
                .save(recipeOutput);

        createString("dirt").require(Blocks.DIRT)
                .output(.20f, Items.WHEAT_SEEDS)
                .output(.10f, Items.BEETROOT_SEEDS)
                .output(.05f, Items.MELON_SEEDS)
                .output(.10f, Items.SHORT_GRASS)
                .output(.02f, Items.POTATO)
                .output(.02f, Items.CARROT)
                .save(recipeOutput);

        createAndesite("dirt",true).require(Blocks.DIRT)
                .output(.20f, Items.KELP)
                .output(.30f, Items.SEAGRASS)
                .output(.05f, Items.TUBE_CORAL)
                .output(.05f, Items.BRAIN_CORAL)
                .output(.05f, Items.BUBBLE_CORAL)
                .output(.05f, Items.FIRE_CORAL)
                .output(.05f, Items.HORN_CORAL)
                .save(recipeOutput);

        //Compat recipes
        createAndesite("enderio_dust").require(ModBlocks.DUST)
                .output(.01f, ResourceLocation.fromNamespaceAndPath("enderio","grains_of_infinity"),1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);
        createBrass("enderio_dust").require(ModBlocks.DUST)
                .output(.05f, ResourceLocation.fromNamespaceAndPath("enderio","grains_of_infinity"),1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);

        createAndesite("ae2_dust").require(ModBlocks.DUST)
                .output(.01f, Mods.AE2,"sky_dust",1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);
        createBrass("ae2_dust").require(ModBlocks.DUST)
                .output(.05f, Mods.AE2,"sky_dust",1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);

        createAndesite("ae2_sand").require(ModBlocks.DUST)
                .output(.05f, Mods.AE2,"certus_quartz_crystal",1)
                .withCondition(new ModLoadedCondition(Mods.AE2.getId()))
                .saveCompat(recipeOutput);
        createBrass("ae2_sand").require(ModBlocks.DUST)
                .output(.1f, Mods.AE2,"certus_quartz_crystal",1)
                .withCondition(new ModLoadedCondition(Mods.AE2.getId()))
                .saveCompat(recipeOutput);
        createAdvancedBrass("ae2_sand").require(ModBlocks.DUST)
                .output(.1f, Mods.AE2,"certus_quartz_crystal",1)
                .output(.005f, Mods.AE2,"charged_certus_quartz_crystal",1)
                .withCondition(new ModLoadedCondition(Mods.AE2.getId()))
                .saveCompat(recipeOutput);

        createString("arch_leaves").require(ItemTags.LEAVES)
                .output(.01f, Mods.ARS_N,"blue_archwood_sapling",1)
                .output(.01f, Mods.ARS_N,"red_archwood_sapling",1)
                .output(.01f, Mods.ARS_N,"green_archwood_sapling",1)
                .output(.01f, Mods.ARS_N,"purple_archwood_sapling",1)
                .withCondition(new ModLoadedCondition(Mods.ARS_N.getId()))
                .saveCompat(recipeOutput);

        createAndesite("arch_leaves").require(ItemTags.LEAVES)
                .output(.1f, Mods.ARS_N,"blue_archwood_sapling",1)
                .output(.1f, Mods.ARS_N,"red_archwood_sapling",1)
                .output(.1f, Mods.ARS_N,"green_archwood_sapling",1)
                .output(.1f, Mods.ARS_N,"purple_archwood_sapling",1)
                .withCondition(new ModLoadedCondition(Mods.ARS_N.getId()))
                .saveCompat(recipeOutput);

    }
    public SiftingRecipeBuilder createString(String name){
        return createString(name, false, false);
    }
    public SiftingRecipeBuilder createString(String name, boolean waterlogged){
        return createString(name, waterlogged, false);
    }

    public SiftingRecipeBuilder createString(String name, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "string", waterlogged, handOnly))
                .requiredMesh(ModItems.STRING_MESH);
        if(waterlogged)
            return builder.isWaterlogged();
        return builder;

    }

    public SiftingRecipeBuilder createAndesite(String name){
        return createAndesite(name, false, false);
    }
    public SiftingRecipeBuilder createAndesite(String name, boolean waterlogged){
        return createAndesite(name, waterlogged, false);
    }
    public SiftingRecipeBuilder createAndesite(String name, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "andesite", waterlogged, handOnly))
                .requiredMesh(ModItems.ANDESITE_MESH);
        if(waterlogged)
            return builder.isWaterlogged();
        /*if(handOnly)
            return builder.isWaterlogged();*/
        return builder;

    }

    public SiftingRecipeBuilder createBrass(String name){
        return createBrass(name, false, false);
    }
    public SiftingRecipeBuilder createBrass(String name, boolean waterlogged){
        return createBrass(name, waterlogged, false);
    }
    public SiftingRecipeBuilder createBrass(String name, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "brass", waterlogged, handOnly))
                .requiredMesh(ModItems.BRASS_MESH);
        if(waterlogged)
            builder.isWaterlogged();
        /*if(handOnly)
            return builder.isWaterlogged();*/
        return builder;

    }

    public SiftingRecipeBuilder createSturdy(String name){
        return createAdvancedBrass(name, false);
    }
    public SiftingRecipeBuilder createSturdy(String name, boolean waterlogged){
        return createBrass(name, waterlogged, false);
    }
    public SiftingRecipeBuilder createSturdy(String name, boolean waterlogged, boolean handOnly){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "sturdy", waterlogged, false))
                .requiredMesh(ModItems.STURDY_MESH);
        if(waterlogged)
            builder.isWaterlogged();
        /*if(handOnly)
            return builder.isWaterlogged();*/
        return builder;
    }
    public SiftingRecipeBuilder createAdvancedBrass(String name){
        return createAdvancedBrass(name, false);
    }
    public SiftingRecipeBuilder createAdvancedBrass(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "advance_brass", waterlogged, false))
                .requiredMesh(ModItems.ADVANCED_BRASS_MESH);
        if(waterlogged)
            builder.isWaterlogged();
        return builder;
    }

    public SiftingRecipeBuilder createAdvancedSturdy(String name){
        return createAdvancedBrass(name, false);
    }
    public SiftingRecipeBuilder createAdvancedSturdy(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "advance_sturdy", waterlogged, false))
                .requiredMesh(ModItems.ADVANCED_STURDY_MESH);
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
