package com.oierbravo.createsifter.foundation.data.recipe;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshTypes;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.mechanicals.foundation.data.AbstractMechanicalRecipeGenerator;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class SiftingRecipeGen extends AbstractMechanicalRecipeGenerator<SiftingRecipeBuilder> {

    public SiftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,
                MODID,
                SiftingRecipe.Type.ID,
                () -> new SiftingRecipeBuilder(),
                "Sifting recipes"
                );
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        /*
        GeneratedRecipe GRAVEL_ANDESITE = create(CreateSifter.asResource("gravel_andesite_mesh"), b ->  b.duration(500)

            .require(MeshTypes.ANDESITE.getItem())
            .require(Blocks.GRAVEL)
            .output(.05f, AllItems.COPPER_NUGGET::get,1)
            .output(.01f, AllItems.ZINC_NUGGET::get,1)
            .output(.01f, Items.IRON_NUGGET,1)
            .output(.05f, Items.GOLD_NUGGET,1)
            .output(.10f, Items.COAL,1)
            .output(.1f, Items.FLINT, 1));
         */
        create("gravel_andesite")
                .require(Blocks.GRAVEL)
                .requiredMesh(MeshTypes.ANDESITE.getItem())
                .processingTime(500)
                .output(.05f, AllItems.COPPER_NUGGET::get,1)
                .output(.01f, AllItems.ZINC_NUGGET::get,1)
                .output(.01f, Items.IRON_NUGGET,1)
                .output(.05f, Items.GOLD_NUGGET,1)
                .output(.10f, Items.COAL,1)
                .output(.1f, Items.FLINT, 1)
                .output(.1f, Mods.ARS_N, "test",1)
                .save(recipeOutput);
        create("gravel_andesite_extra")
                .require(Blocks.GRAVEL)
                .requiredMesh(MeshTypes.ANDESITE.getItem())
                .processingTime(500)
                .output(.05f, AllItems.BLAZE_CAKE::get,1)
                .output(.10f, Items.GHAST_TEAR,1)
                .output(.1f, Items.INFESTED_STONE, 1)
                .save(recipeOutput);

        create("gravel_zinc_mesh")
                .requiredMesh(MeshTypes.ZINC.getItem())
                .require(Blocks.GRAVEL)
                .processingTime(500)
                .output(.05f, AllItems.COPPER_NUGGET::get,1)
                .output(.02f, AllItems.ZINC_NUGGET::get,1)
                .output(.05f, Items.IRON_NUGGET,1)
                .output(.10f, Items.GOLD_NUGGET,1)
                .output(.10f, Items.COAL,1)
                .output(.05f, Items.LAPIS_LAZULI,1)
                .output(.1f, Items.FLINT, 1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        create("gravel_brass_mesh")
                .require(Blocks.GRAVEL)
                .requiredMesh(MeshTypes.BRASS.getItem())
                .processingTime(500)
                .output(.1f, AllItems.CRUSHED_COPPER::get,1)
                .output(.1f, AllItems.CRUSHED_ZINC::get,1)
                .output(.05f, AllItems.CRUSHED_GOLD::get,1)
                .output(.10f, AllItems.CRUSHED_IRON::get,1)
                .output(.10f, Items.LAPIS_LAZULI,1)
                .output(.15f, Items.COAL,1)
                .output(.1f, Items.FLINT, 1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        create("gravel_advanced_brass_mesh")
                .require(Blocks.GRAVEL)
                .requiredMesh(MeshTypes.ADVANCED_BRASS.getItem())
                .processingTime(500)
                .output(.10f, AllItems.CRUSHED_COPPER::get,1)
                .output(.10f, AllItems.CRUSHED_ZINC::get,1)
                .output(.05f, AllItems.CRUSHED_GOLD::get,1)
                .output(.15f, AllItems.CRUSHED_IRON::get,1)
                .output(.10f, Items.LAPIS_LAZULI,1)
                .output(.05f, Items.DIAMOND,1)
                .output(.02f, Items.EMERALD,1)
                .output(.1f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);
        create("gravel_advanced_brass_mesh_extra")
                .require(Blocks.GRAVEL)
                .requiredMesh(MeshTypes.ADVANCED_BRASS.getItem())
                .processingTime(500)
                .output(1f, AllItems.WRENCH::get,1)
                .output(.50f, AllItems.BLAZE_CAKE.get(), 1)
                .save(recipeOutput);

        create("sand_string_mesh")
                .require(Blocks.SAND)
                .requiredMesh(MeshTypes.STRING.getItem())
                .processingTime(500)
                .output(.05f, Items.REDSTONE,1)
                .output(.4f, Items.BONE_MEAL,1)
                .save(recipeOutput);

        create("sand_andesite_mesh")
                .require(Blocks.SAND)
                .requiredMesh(MeshTypes.ANDESITE.getItem())
                .processingTime(500)
                .output(.1f, Items.REDSTONE,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        create("sand_zinc_mesh")
                .require(Blocks.SAND)
                .requiredMesh(MeshTypes.ZINC.getItem())
                .processingTime(500)
                .output(.15f, Items.REDSTONE,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        create("sand_brass_mesh")
                .require(Blocks.SAND)
                .requiredMesh(MeshTypes.BRASS.getItem())
                .processingTime(500)
                .output(.25f, Items.REDSTONE,2)
                .output(.10f, Items.GLOWSTONE_DUST,1)
                .output(.4f, Items.BONE_MEAL,1)
                .output(.05f, Items.BLAZE_POWDER,1)
                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        create("soul_sand_brass_mesh")
                .require(Blocks.SOUL_SAND)
                .requiredMesh(MeshTypes.BRASS.getItem())
                .processingTime(500)
                .output(.10f, Items.QUARTZ,1)
                .output(.10f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);

        create("soul_sand_advanced_brass_mesh")
                .require(Blocks.SOUL_SAND)
                .requiredMesh(MeshTypes.ADVANCED_BRASS.getItem())
                .processingTime(500)
                .output(.45f, Items.QUARTZ,1)
                .output(.15f, Items.QUARTZ,1)
                .output(.05f, Items.GHAST_TEAR,1)
                .output(.20f, AllItems.EXP_NUGGET.get(), 1)
                .save(recipeOutput);



        create("dirt_string_mesh_waterlogged")
                .require(Blocks.DIRT)
                .requiredMesh(MeshTypes.STRING.getItem())
                .processingTime(500)
                .output(.20f, Items.KELP)
                .output(.30f, Items.SEAGRASS)
                .output(.05f, Items.TUBE_CORAL)
                .output(.05f, Items.BRAIN_CORAL)
                .output(.05f, Items.BUBBLE_CORAL)
                .output(.05f, Items.FIRE_CORAL)
                .output(.05f, Items.HORN_CORAL)
                .isWaterlogged()
                .save(recipeOutput);
    }
}
