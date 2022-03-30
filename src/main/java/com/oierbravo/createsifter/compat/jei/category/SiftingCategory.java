package com.oierbravo.createsifter.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.compat.jei.category.animations.AnimatedSifter;
import com.oierbravo.createsifter.register.ModBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;

import java.util.Arrays;
import java.util.List;

public class SiftingCategory extends CreateRecipeCategory<AbstractCrushingRecipe> {
    private AnimatedSifter sifter = new AnimatedSifter();
    public SiftingCategory() {
        super(doubleItemIcon(ModBlocks.SIFTER.get(), AllItems.WHEAT_FLOUR.get()), emptyBackground(177, 53));
    }
    public void setCategoryId(String name) {
        this.uid = CreateSifter.asResource(name);
        this.name = name;
    }
    @Override
    public Class<? extends AbstractCrushingRecipe> getRecipeClass() {
        return AbstractCrushingRecipe.class;
    }

    @Override
    public void setIngredients(AbstractCrushingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getRollableResultsAsItemStacks());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AbstractCrushingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 14, 8);
        itemStacks.set(0, Arrays.asList(recipe.getIngredients()
                .get(0)
                .getItems()));

        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        for (int outputIndex = 0; outputIndex < results.size(); outputIndex++) {
            int xOffset = outputIndex % 2 == 0 ? 0 : 19;
            int yOffset = (outputIndex / 2) * -19;

            itemStacks.init(outputIndex + 1, false, single ? 139 : 133 + xOffset, 27 + yOffset);
            itemStacks.set(outputIndex + 1, results.get(outputIndex)
                    .getStack());
        }

        addStochasticTooltip(itemStacks, results);
    }

    @Override
    public void draw(AbstractCrushingRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        int size = recipe.getRollableResultsAsItemStacks()
                .size();

        AllGuiTextures.JEI_SLOT.render(matrixStack, 14, 8);
        AllGuiTextures.JEI_ARROW.render(matrixStack, 85, 32);
        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 43, 4);
        sifter.draw(matrixStack, 48, 27);

        if (size == 1) {
            getRenderedSlot(recipe, 0).render(matrixStack, 139, 27);
            return;
        }

        for (int i = 0; i < size; i++) {
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2) * -19;
            getRenderedSlot(recipe, i).render(matrixStack, 133 + xOffset, 27 + yOffset);
        }

    }
}
