package com.oierbravo.createsifter.compat.emi;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.register.ModBlocks;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

import static com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeManager.getRecipesMerged;

@EmiEntrypoint
public class CreteSifterEMI implements EmiPlugin {
    public static final EmiStack SIFTER = EmiStack.of(ModBlocks.SIFTER);
    public static final EmiStack BRASS_SIFTER = EmiStack.of(ModBlocks.BRASS_SIFTER);
    public static final EmiRecipeCategory SIFTING_CATEGORY
            = new EmiRecipeCategory(ModConstants.asResource("sifting"), SIFTER);

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(SIFTING_CATEGORY);

        registry.addWorkstation(SIFTING_CATEGORY, SIFTER);
        registry.addWorkstation(SIFTING_CATEGORY, BRASS_SIFTER);
        List<RecipeHolder<SiftingRecipe>> mergedRecipes = getRecipesMerged();
        for(RecipeHolder<SiftingRecipe> recipeHolder: mergedRecipes){
            registry.addRecipe(new SiftingEmiRecipe(recipeHolder.value(), recipeHolder.id()));
        }
    }
}
