package com.oierbravo.createsifter.compat.jei.category;

import com.oierbravo.createsifter.compat.jei.category.animations.AnimatedBrassSifter;
import com.oierbravo.createsifter.compat.jei.category.animations.AnimatedSifter;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipe;
import com.oierbravo.createsifter.foundation.gui.ModGUITextures;

import com.oierbravo.createsifter.foundation.util.ModLang;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Lang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.material.Fluids;

import java.util.Iterator;
import java.util.List;

public class SiftingCategory extends CreateRecipeCategory<SiftingRecipe> {

    public SiftingCategory(CreateRecipeCategory.Info<SiftingRecipe> info) {
        super(info);

    }


    public void setRecipe(IRecipeLayoutBuilder builder, SiftingRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 15, 9).setBackground(getRenderedSlot(), -1, -1).addIngredients(recipe.getSiftableIngredient());

        if(!recipe.getMeshIngredient().isEmpty())
            builder.addSlot(RecipeIngredientRole.CATALYST, 15, 24).setBackground(getRenderedSlot(), -1, -1).addIngredients(recipe.getMeshIngredient());

        if(recipe.isWaterlogged()){
            builder.addSlot(RecipeIngredientRole.CATALYST, 15, 42).setBackground(getRenderedSlot(), -1, -1).addFluidStack(Fluids.WATER.getSource(),1000);

        }

        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        int i = 0;

        for(Iterator var7 = results.iterator(); var7.hasNext(); ++i) {
            ProcessingOutput output = (ProcessingOutput)var7.next();
            int xOffset = i % 4 * 19;
            int yOffset = i / 4 * 19;
            (builder.addSlot(RecipeIngredientRole.OUTPUT, single ? 139 : 100 + xOffset, 2 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack()))
                    .addTooltipCallback(addStochasticTooltip(output));
        }
    }


    public void draw(SiftingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {


        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        if(single){
            AllGuiTextures.JEI_ARROW.render(graphics, 85, 32); //Output arrow
        } else {
            ModGUITextures.JEI_SHORT_ARROW.render(graphics, 75, 32); //Output arrow
        }

        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 43, 2);

        drawSifter(graphics, recipe.requiresAdvancedMesh(), recipe.isWaterlogged());


        NonNullList<MutableComponent> requirements = NonNullList.create();
        if(recipe.isWaterlogged())
            requirements.add(ModLang.translate("recipe.sifting.waterlogged").component());
        if(recipe.hasSpeedRequeriment())
            requirements.add(Lang.translate("createsifter.recipe.sifting.minimumspeed",recipe.getSpeedRequeriment()).component());
        if(recipe.requiresAdvancedMesh())
            requirements.add(ModLang.translate("recipe.sifting.brass_required").component());

        drawRequirements(graphics,requirements);

    }
    protected void drawSifter(GuiGraphics guiGraphics, boolean pAdvanced, boolean waterlogged){
        int x = 48;
        int y = 27;
        if(pAdvanced){
            AnimatedBrassSifter brassSifter = new AnimatedBrassSifter();
            brassSifter.waterlogged(waterlogged);
            brassSifter.draw(guiGraphics, x, y);
            return;
        }
        AnimatedSifter sifter = new AnimatedSifter();
        sifter.waterlogged(waterlogged);
        sifter.draw(guiGraphics, x, y);
    }
    protected void drawRequirements(GuiGraphics guiGraphics, NonNullList<MutableComponent> requirements){
        int startX = 41;
        int startY = 56;

        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;

        for(int index = 0; index < requirements.size();index++){
            guiGraphics.drawString(fontRenderer, requirements.get(index), startX, startY + 15 * index, 0xFF808080,false);
        }
    }
}
