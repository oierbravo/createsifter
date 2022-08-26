package com.oierbravo.createsifter.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.createsifter.compat.jei.category.animations.AnimatedSifter;
import com.oierbravo.createsifter.foundation.gui.ModGUITextures;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SiftingCategory extends CreateRecipeCategory<AbstractCrushingRecipe> {
    private AnimatedSifter sifter = new AnimatedSifter();

    public SiftingCategory(CreateRecipeCategory.Info<AbstractCrushingRecipe> info) {
        super(info);
    }


    public void setRecipe(IRecipeLayoutBuilder builder, AbstractCrushingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 9).setBackground(getRenderedSlot(), -1, -1).addIngredients((Ingredient)recipe.getIngredients().get(0));
        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        int i = 0;

        for(Iterator var7 = results.iterator(); var7.hasNext(); ++i) {
            ProcessingOutput output = (ProcessingOutput)var7.next();
            //int xOffset = i % 2 == 0 ? 0 : 19;
            //int yOffset = i / 2 * -19;
            //int xOffset = i * 19 + 2;
            //int yOffset = 27;
            //int xOffset = i % 4 == 0 ? 0 : 19 + i % 4 + 1;
            //int yOffset = i / 4 * -19;
            int xOffset = i % 4 * 19;
            int yOffset = i / 4 * 19;
            /*((IRecipeSlotBuilder)builder
                    .addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 15 + xOffset,  single ? 19 :27 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack()))
                    .addTooltipCallback(addStochasticTooltip(output));*/
            ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 100 + xOffset, 2 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack()))
                    .addTooltipCallback(addStochasticTooltip(output));
        }

    }


    public void draw(AbstractCrushingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        if(single){
            AllGuiTextures.JEI_ARROW.render(matrixStack, 85, 32); //Output arrow
        } else {
            ModGUITextures.JEI_SHORT_ARROW.render(matrixStack, 75, 32); //Output arrow
        }

        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 43, 4);
        sifter.draw(matrixStack, 48, 27);
    }
}
