package com.oierbravo.createsifter.foundation.data.recipe;


import com.oierbravo.createsifter.ModRecipeTypes;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class SiftingRecipeGen extends ModProcessingRecipes {
    GeneratedRecipe GRAVEL = create("gravel", b -> b.duration(500)
            .require(Blocks.GRAVEL)
            .output(.1f, AllItems.CRUSHED_COPPER::get,1)
            .output(.1f, AllItems.CRUSHED_ZINC::get,1)
            .output(.05f, AllItems.CRUSHED_GOLD::get,1)
            .output(.15f, AllItems.CRUSHED_IRON::get,1)
            .output(.1f, Items.FLINT, 1)
            .output(.25f, AllItems.EXP_NUGGET.get(), 1));
    GeneratedRecipe SAND = create("sand", b -> b.duration(500)
            .require(Blocks.SAND)
            .output(.1f, Items.REDSTONE,1)
            .output(.3f, Items.BONE_MEAL,1)
            .output(.25f, AllItems.EXP_NUGGET.get(), 1));
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
