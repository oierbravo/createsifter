package com.oierbravo.createsifter.foundation.data.recipe;


import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshTypes;
import com.simibubi.create.AllItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class SiftingRecipeGen extends ModProcessingRecipeGen {
    GeneratedRecipe GRAVEL_ANDESITE = create(CreateSifter.asResource("gravel_andesite_mesh"), b ->  b.duration(500)

            .require(MeshTypes.ANDESITE.getItem())
            .require(Blocks.GRAVEL)
            .output(.1f, AllItems.COPPER_NUGGET::get,1)
            .output(.1f, AllItems.ZINC_NUGGET::get,1)
            .output(.05f, Items.IRON_NUGGET,1)
            .output(.15f, Items.GOLD_NUGGET,1)
            .output(.10f, Items.COAL,1)
            .output(.1f, Items.FLINT, 1));

    GeneratedRecipe GRAVEL_ZINC = create(CreateSifter.asResource("gravel_zinc_mesh"), b ->  b.duration(500)

            .require(MeshTypes.ZINC.getItem())
            .require(Blocks.GRAVEL)
            .output(.15f, AllItems.COPPER_NUGGET::get,1)
            .output(.15f, AllItems.ZINC_NUGGET::get,1)
            .output(.1f, Items.IRON_NUGGET,1)
            .output(.20f, Items.GOLD_NUGGET,1)
            .output(.20f, Items.COAL,1)
            .output(.10f, Items.LAPIS_LAZULI,1)
            .output(.1f, Items.FLINT, 1)
            .output(.10f, AllItems.EXP_NUGGET.get(), 1));

    GeneratedRecipe GRAVEL_BRASS = create(CreateSifter.asResource("gravel_brass_mesh"), b ->  b.duration(500)

            .require(MeshTypes.BRASS.getItem())
            .require(Blocks.GRAVEL)
            .output(.20f, AllItems.CRUSHED_COPPER::get,1)
            .output(.20f, AllItems.CRUSHED_ZINC::get,1)
            .output(.15f, AllItems.CRUSHED_GOLD::get,1)
            .output(.25f, AllItems.CRUSHED_IRON::get,1)
            .output(.20f, Items.LAPIS_LAZULI,1)
            .output(.25f, Items.COAL,1)
            .output(.1f, Items.FLINT, 1)
            .output(.15f, AllItems.EXP_NUGGET.get(), 1));

    GeneratedRecipe SAND_STRING = create(CreateSifter.asResource("sand_string_mesh"), b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.STRING.getItem())
            .output(.05f, Items.REDSTONE,1)
            .output(.4f, Items.BONE_MEAL,1));

    GeneratedRecipe SAND_ANDESITE = create(CreateSifter.asResource("sand_andesite_mesh"), b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.ANDESITE.getItem())
            .output(.1f, Items.REDSTONE,1)
            .output(.4f, Items.BONE_MEAL,1)
            .output(.10f, AllItems.EXP_NUGGET.get(), 1));

    GeneratedRecipe SAND_ZINC = create(CreateSifter.asResource("sand_zinc_mesh"), b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.ZINC.getItem())
            .output(.15f, Items.REDSTONE,1)
            .output(.4f, Items.BONE_MEAL,1)
            .output(.10f, AllItems.EXP_NUGGET.get(), 1));

    GeneratedRecipe SAND_BRASS = create(CreateSifter.asResource("sand_brass_mesh"), b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.BRASS.getItem())
            .output(.25f, Items.REDSTONE,2)
            .output(.10f, Items.GLOWSTONE_DUST,1)
            .output(.4f, Items.BONE_MEAL,1)
            .output(.05f, Items.BLAZE_POWDER,1)
            .output(.20f, AllItems.EXP_NUGGET.get(), 1));


    GeneratedRecipe DIRT_STRING_MESH_WATERLOGGED = create(CreateSifter.asResource("dirt_string_mesh_waterlogged"), b -> b.duration(500)
            .require(Blocks.DIRT)
            .require(MeshTypes.STRING.getItem())
            .output(.20f, Items.KELP)
            .output(.30f, Items.SEAGRASS)
            .output(.05f, Items.TUBE_CORAL)
            .output(.05f, Items.BRAIN_CORAL)
            .output(.05f, Items.BUBBLE_CORAL)
            .output(.05f, Items.FIRE_CORAL)
            .output(.05f, Items.HORN_CORAL)
            .isWaterlogged()
    )
            ;

    public SiftingRecipeGen(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected ModRecipeTypes getRecipeType() {
        return ModRecipeTypes.SIFTING;
    }

}
