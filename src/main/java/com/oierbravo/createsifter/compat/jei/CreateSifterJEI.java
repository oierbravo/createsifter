package com.oierbravo.createsifter.compat.jei;

import com.google.common.collect.ArrayListMultimap;
import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.compat.jei.category.SiftingCategory;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeManager;
import com.oierbravo.createsifter.register.ModRecipes;
import com.oierbravo.mechanicals.utility.MechanicalItemStackUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class CreateSifterJEI implements IModPlugin {

    private static final ResourceLocation ID = ModConstants.asResource("jei_plugin");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(SiftingCategory.INFO);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        SiftingCategory.INFO.registerRecipes(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        SiftingCategory.INFO.registerCatalysts(registration);
    }


}
