package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.requirements;

import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanicals.foundation.recipe.RecipeRequirementType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record WaterloggedRecipeRequirement(boolean waterlogged) implements IRecipeRequirement {

    @Override
    public boolean test(Level level, BlockEntity blockEntity) {
        return waterlogged;
    }

    @Override
    public String getIdString() {
        return "waterlogged";
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return null;
    }
}
