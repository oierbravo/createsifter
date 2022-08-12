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
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;
import java.util.List;

public class SiftingCategory extends CreateRecipeCategory<AbstractCrushingRecipe> {
    private AnimatedSifter sifter = new AnimatedSifter();
    public SiftingCategory() {
        super(doubleItemIcon(ModBlocks.SIFTER.get(), AllItems.WHEAT_FLOUR.get()), emptyBackground(177, 53));
    }
    /*public void setCategoryId(String name) {
        this.uid = CreateSifter.asResource(name);
        this.name = name;
    }*/
    @Override
    public void setCategoryId(String name) {
        this.name = name;
        this.type = RecipeType.create(CreateSifter.MODID, name, this.getRecipeClass());
    }
    @Override
    public Class<? extends AbstractCrushingRecipe> getRecipeClass() {
        return AbstractCrushingRecipe.class;
    }
    @Override
    public Component getTitle() {
        return new TranslatableComponent( CreateSifter.MODID + ".recipe." + name);
    }

    

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AbstractCrushingRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, 15, 9)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.getIngredients().get(0));

        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        int i = 0;
        for (ProcessingOutput output : results) {
            int xOffset = i % 2 == 0 ? 0 : 19;
            int yOffset = (i / 2) * -19;

            builder
                    .addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 133 + xOffset, 27 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack())
                    .addTooltipCallback(addStochasticTooltip(output));

            i++;
        }
    }

    @Override
    public void draw(AbstractCrushingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_ARROW.render(matrixStack, 85, 32);
        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 43, 4);
        sifter.draw(matrixStack, 48, 27);
    }
}
