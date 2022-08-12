package com.oierbravo.createsifter.foundation.data.recipe;


import com.oierbravo.createsifter.ModRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class SiftingRecipeGen extends ModProcessingRecipes {
    //GeneratedRecipe GRAVEL = create("gravel", b -> b.duration(100).require(Blocks.GRAVEL).output(Items.FLINT));
    /* GeneratedRecipe COBBLE_STONE = create("cobblestone", b ->
            b.duration(100).require(Blocks.DIRT)
            .output(Blocks.COBBLESTONE)
    );
   /* GeneratedRecipe STONE = create(ResourceLocation.tryParse("dirt"), b -> b.duration(100)
            .require(ItemTags.)
            .output(Items.STRING));*/





    public SiftingRecipeGen(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected ModRecipeTypes getRecipeType() {
        return ModRecipeTypes.SIFTING;
    }

}
