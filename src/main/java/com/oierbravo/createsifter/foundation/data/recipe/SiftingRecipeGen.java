package com.oierbravo.createsifter.foundation.data.recipe;


import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshTypes;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class SiftingRecipeGen extends ModProcessingRecipes {
    GeneratedRecipe DIRT_STRING = create("dirt_string_mesh", b ->  b.duration(200)
            .require(Blocks.DIRT)
            .require(MeshTypes.STRING.getItem())
            .output(1, Blocks.ANDESITE,1));
    GeneratedRecipe GRAVEL_ANDESITE = create("gravel_andesite_mesh", b ->  b.duration(500)

            .require(MeshTypes.ANDESITE.getItem())
            .require(Blocks.GRAVEL)
            .output(.1f, AllItems.COPPER_NUGGET::get,1)
            .output(.1f, AllItems.ZINC_NUGGET::get,1)
            .output(.05f, Items.IRON_NUGGET,1)
            .output(.15f, Items.GOLD_NUGGET,1)
            .output(.10f, Items.COAL,1)
            .output(.1f, Items.FLINT, 1));

    GeneratedRecipe GRAVEL_ZINC = create("gravel_zinc_mesh", b ->  b.duration(500)

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

    GeneratedRecipe GRAVEL_BRASS = create("gravel_brass_mesh", b ->  b.duration(500)

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

    GeneratedRecipe SAND_STRING = create("sand_string_mesh", b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.ANDESITE.getItem())
            .output(.05f, Items.REDSTONE,1)
            .output(.4f, Items.BONE_MEAL,1));

    GeneratedRecipe SAND_ANDESITE = create("sand_andesite_mesh", b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.ANDESITE.getItem())
            .output(.1f, Items.REDSTONE,1)
            .output(.4f, Items.BONE_MEAL,1)
            .output(.10f, AllItems.EXP_NUGGET.get(), 1));

    GeneratedRecipe SAND_ZINC = create("sand_andesite_mesh", b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.ANDESITE.getItem())
            .output(.15f, Items.REDSTONE,1)
            .output(.4f, Items.BONE_MEAL,1)
            .output(.10f, AllItems.EXP_NUGGET.get(), 1));

    GeneratedRecipe SAND_BRASS = create("sand_andesite_mesh", b -> b.duration(500)
            .require(Blocks.SAND)
            .require(MeshTypes.ANDESITE.getItem())
            .output(.25f, Items.REDSTONE,2)
            .output(.10f, Items.GLOWSTONE_DUST,1)
            .output(.4f, Items.BONE_MEAL,1)
            .output(.20f, AllItems.EXP_NUGGET.get(), 1));
    /* GeneratedRecipe COBBLE_STONE = create("cobblestone", b ->
            b.duration(100).require(Blocks.DIRT)
            .output(Blocks.COBBLESTONE)
    );
   /* GeneratedRecipe STONE = create(ResourceLocation.tryParse("dirt"), b -> b.duration(100)
            .require(ItemTags.)
            .output(Items.STRING));*/





    public SiftingRecipeGen(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected ModRecipeTypes getRecipeType() {
        return ModRecipeTypes.SIFTING;
    }

}
