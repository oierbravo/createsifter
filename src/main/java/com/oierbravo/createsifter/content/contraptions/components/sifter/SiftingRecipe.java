package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.oierbravo.createsifter.ModRecipeTypes;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SiftingRecipe  extends AbstractCrushingRecipe {
    public SiftingRecipe(ProcessingRecipeParams params) {
        super(ModRecipeTypes.SIFTING, params);
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0)
                .test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 16;
    }

    @Override
    protected int getMaxOutputCount() {
        return 16;
    }
}
