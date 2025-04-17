package com.oierbravo.createsifter.infrastucture.data.recipe;

import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.mechanicals.foundation.data.AbstractMechanicalRecipeGenerator;
import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class SiftingRecipeGen extends AbstractMechanicalRecipeGenerator<SiftingRecipeBuilder> {

    public SiftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String namespace, String recipeTypeId, Supplier<SiftingRecipeBuilder> builderSupplier, String displayName) {
        super(output, registries, namespace, recipeTypeId, builderSupplier,displayName);
    }

    public SiftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this(output, registries,
                MODID,
                SiftingRecipe.Type.ID,
                SiftingRecipeBuilder::new,
                "Sifting recipes"
                );
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        createAndesite("gravel").require(Blocks.GRAVEL)
                .output(.3f, AllItems.COPPER_NUGGET::get,1)
                .output(.4f, AllItems.ZINC_NUGGET::get,1)
                .output(.4f, Items.IRON_NUGGET,1)
                .output(.2f, Items.GOLD_NUGGET,1)
                .output(.10f, Items.COAL,1)
                .output(.5f, Items.FLINT, 1)
                .save(recipeOutput);


        createBrass("gravel").require(Blocks.GRAVEL)
                .output(.1f, AllItems.CRUSHED_COPPER::get,1)
                .output(.1f, AllItems.CRUSHED_ZINC::get,1)
                .output(.05f, AllItems.CRUSHED_GOLD::get,1)
                .output(.10f, AllItems.CRUSHED_IRON::get,1)
                .output(.10f, Items.LAPIS_LAZULI,1)
                .output(.35f, Items.COAL,1)
                .output(.1f, Items.FLINT, 1)
                .output(.10f, Items.AMETHYST_SHARD,1)
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
                .output(.10f, Items.AMETHYST_SHARD,1)
                .output(.1f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAndesite("sand").require(Blocks.SAND)
                .output(.10f, Items.REDSTONE,2)
                .output(.15f, Items.GOLD_NUGGET,1)
                .output(.15f, Items.CACTUS,1)
                .output(.10f, Items.GUNPOWDER,1)
                .output(.15f, Items.BONE,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createBrass("sand").require(Blocks.SAND)
                .output(.15f, Items.REDSTONE,2)
                .output(.05f, Items.BLAZE_POWDER,1)
                .output(.25f, AllItems.CRUSHED_GOLD::get,1)
                .output(.25f, Items.CACTUS,1)
                .output(.15f, Items.GUNPOWDER,1)
                .output(.25f, Items.BONE,1)

                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAndesite("dust").require(ModBlocks.DUST)
                .output(.20f, Items.REDSTONE,2)
                .output(.10f, Items.GLOWSTONE_DUST,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.01f, Items.BLAZE_POWDER,1)
                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createBrass("dust").require(ModBlocks.DUST)
                .output(.35f, Items.REDSTONE,2)
                .output(.20f, Items.GLOWSTONE_DUST,1)
                .output(.6f, Items.BONE_MEAL,1)
                .output(.05f, Items.BLAZE_POWDER,1)
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
                .output(.50f, ModItems.PEBBLE_STONE)
                .output(.45f, ModItems.PEBBLE_ANDESITE)
                .output(.25f, ModItems.PEBBLE_GRANITE)
                .output(.25f, ModItems.PEBBLE_DIORITE)
                .output(.10f, Items.WHEAT_SEEDS)
                .output(.05f, Items.BEETROOT_SEEDS)
                .output(.05f, Items.SUGAR_CANE)
                .output(.05f, Items.BAMBOO)
                .output(.10f, Items.SHORT_GRASS)
                .output(.01f, Items.MELON_SEEDS)
                .output(.01f, Items.PUMPKIN_SEEDS)
                .output(.01f, Items.POTATO)
                .output(.01f, Items.CARROT)
                .output(.01f, Items.SWEET_BERRIES)
                .save(recipeOutput);


        createAndesite("dirt").require(Blocks.DIRT)
                .output(.80f, ModItems.PEBBLE_STONE)
                .output(.55f, ModItems.PEBBLE_ANDESITE)
                .output(.45f, ModItems.PEBBLE_GRANITE)
                .output(.45f, ModItems.PEBBLE_DIORITE)
                .output(.20f, Items.WHEAT_SEEDS)
                .output(.10f, Items.BEETROOT_SEEDS)
                .output(.15f, Items.SUGAR_CANE)
                .output(.15f, Items.BAMBOO)
                .output(.10f, Items.SHORT_GRASS)
                .output(.05f, Items.MELON_SEEDS)
                .output(.05f, Items.PUMPKIN_SEEDS)
                .output(.05f, Items.POTATO)
                .output(.05f, Items.CARROT)
                .output(.05f, Items.SWEET_BERRIES)
                .output(.05f, Items.BROWN_MUSHROOM)
                .output(.05f, Items.RED_MUSHROOM)
                .save(recipeOutput);

        createString("dirt",true).require(Blocks.DIRT)
                .output(.30f, Items.KELP)
                .output(.20f, Items.SEAGRASS)
                .output(.05f, Items.TUBE_CORAL)
                .output(.05f, Items.BRAIN_CORAL)
                .output(.05f, Items.BUBBLE_CORAL)
                .output(.05f, Items.FIRE_CORAL)
                .output(.05f, Items.HORN_CORAL)
                .save(recipeOutput);

        createBrass("dirt",true).require(Blocks.DIRT)
                .output(.40f, Items.KELP)
                .output(.30f, Items.SEAGRASS)
                .output(.1f, Items.TUBE_CORAL)
                .output(.1f, Items.BRAIN_CORAL)
                .output(.1f, Items.BUBBLE_CORAL)
                .output(.1f, Items.FIRE_CORAL)
                .output(.1f, Items.HORN_CORAL)
                .output(.20f, Items.PRISMARINE_SHARD)
                .output(.1f, Items.PRISMARINE_CRYSTALS)
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

        createBrass("crushed_netherrack").require(ModBlocks.CRUSHED_NETHERRACK)
                .output(.50f,ModItems.PEBBLE_BASALT)
                .output(.40f,ModItems.PEBBLE_BLACKSTONE)
                .output(.10f,Items.GOLD_NUGGET)
                .output(.05f,Items.QUARTZ)
                .output(.10f, Items.BLAZE_POWDER,1)
                .output(.01f, Items.NETHERITE_SCRAP,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAdvancedBrass("crushed_netherrack").require(ModBlocks.CRUSHED_NETHERRACK)
                .output(.80f,ModItems.PEBBLE_BASALT)
                .output(.60f,ModItems.PEBBLE_BLACKSTONE)
                .output(.30f,Items.GOLD_NUGGET)
                .output(.10f,Items.QUARTZ)
                .output(.20f, Items.BLAZE_POWDER,1)
                .output(.5f, Items.NETHERITE_SCRAP,1)
                .output(.40f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createSturdy("crushed_basalt").require(ModBlocks.CRUSHED_BASALT)
                .output(.02f, Items.ANCIENT_DEBRIS,1)
                .output(.01f, Items.NETHERITE_SCRAP,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAdvancedSturdy("crushed_basalt").require(ModBlocks.CRUSHED_BASALT)
                .output(.05f, Items.ANCIENT_DEBRIS,1)
                .output(.02f, Items.NETHERITE_SCRAP,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createSturdy("crushed_end_stone").require(ModBlocks.CRUSHED_END_STONE)
                .output(.05f,Items.ENDER_PEARL)
                .output(.10f,Items.CHORUS_FLOWER)
                .output(.5f,Items.CHORUS_FRUIT)
                .output(.05f,Items.SHULKER_SHELL)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        createAdvancedSturdy("crushed_end_stone").require(ModBlocks.CRUSHED_END_STONE)
                .output(.10f,Items.ENDER_PEARL)
                .output(.10f,Items.CHORUS_FLOWER)
                .output(.10f,Items.SHULKER_SHELL)
                .output(.3f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);



    }
    public SiftingRecipeBuilder createString(String name){
        return createString(name, false);
    }
    public SiftingRecipeBuilder createString(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "string", waterlogged))
                .requiredMesh(ModItems.STRING_MESH);
        if(waterlogged)
            return builder.isWaterlogged();
        return builder;

    }

    public SiftingRecipeBuilder createAndesite(String name){
        return createAndesite(name, false);
    }
    public SiftingRecipeBuilder createAndesite(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "andesite", waterlogged))
                .requiredMesh(ModItems.ANDESITE_MESH);
        if(waterlogged)
            return builder.isWaterlogged();
        return builder;

    }

    public SiftingRecipeBuilder createBrass(String name){
        return createBrass(name, false);
    }
    public SiftingRecipeBuilder createBrass(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "brass", waterlogged))
                .requiredMesh(ModItems.BRASS_MESH);
        if(waterlogged)
            builder.isWaterlogged();
        return builder;

    }

    public SiftingRecipeBuilder createSturdy(String name){
        return createSturdy(name, false);
    }
    public SiftingRecipeBuilder createSturdy(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "sturdy", waterlogged))
                .requiredMesh(ModItems.STURDY_MESH);
        if(waterlogged)
            builder.isWaterlogged();
        return builder;
    }
    public SiftingRecipeBuilder createAdvancedBrass(String name){
        return createAdvancedBrass(name, false);
    }
    public SiftingRecipeBuilder createAdvancedBrass(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "advanced_brass", waterlogged))
                .requiredMesh(ModItems.ADVANCED_BRASS_MESH);
        if(waterlogged)
            builder.isWaterlogged();
        return builder;
    }

    public SiftingRecipeBuilder createAdvancedSturdy(String name){
        return createAdvancedSturdy(name, false);
    }
    public SiftingRecipeBuilder createAdvancedSturdy(String name, boolean waterlogged){
        SiftingRecipeBuilder builder = create(generateRecipeName(name, "advance_sturdy", waterlogged))
                .requiredMesh(ModItems.ADVANCED_STURDY_MESH);
        if(waterlogged)
            builder.isWaterlogged();
        return builder;
    }
    private String generateRecipeName(String baseName, String mesh, boolean waterlogged){
        String id = baseName + "_" + mesh;
        if(waterlogged)
            id += "_waterlogged";
        return id;
    }
}
