package com.oierbravo.createsifter.compat.jei.category;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.ModLang;
import com.oierbravo.createsifter.compat.jei.category.animations.AbstractAnimatedSifter;
import com.oierbravo.createsifter.compat.jei.category.animations.AnimatedBrassSifter;
import com.oierbravo.createsifter.compat.jei.category.animations.AnimatedSifter;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeManager;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.mechanicals.compat.jei.RecipeRequirementRenderer;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;

import java.util.Iterator;
import java.util.List;

public class SiftingCategory extends CreateRecipeCategory<SiftingRecipe> {

    public final static ResourceLocation UID = ModConstants.asResource(SiftingRecipe.Type.ID);
    public final static RecipeType<SiftingRecipe> TYPE = new mezz.jei.api.recipe.RecipeType<>(UID, SiftingRecipe.class);

    public final static CreateRecipeCategory.Info<SiftingRecipe> INFO = new CreateRecipeCategory.Info<>(
            TYPE,
            ModLang.translate("recipe." + SiftingRecipe.Type.ID).component(),
            new EmptyBackground(177, 120),
            new ItemIcon(() -> new ItemStack(ModBlocks.SIFTER.asItem())),
            SiftingRecipeManager::getAllHolders,
            List.of(
                    ModBlocks.SIFTER::asStack,
                    ModBlocks.BRASS_SIFTER::asStack
            )
    );

    public SiftingCategory(CreateRecipeCategory.Info<SiftingRecipe> info) {
        super(info);
    }


    public void setRecipe(IRecipeLayoutBuilder builder, SiftingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 3, 3).setBackground(getRenderedSlot(), -1, -1).addIngredients(recipe.getInput());

        if(!recipe.getMesh().isEmpty())
            builder.addSlot(RecipeIngredientRole.CATALYST, 3, 18).setBackground(getRenderedSlot(), -1, -1).addIngredients(Ingredient.of(recipe.getMesh()));

        if(recipe.isWaterlogged()){
            builder.addSlot(RecipeIngredientRole.CATALYST, 3, 36).setBackground(getRenderedSlot(), -1, -1).addFluidStack(Fluids.WATER.getSource(),1000);

        }

        List<ProcessingOutput> results = recipe.getRollableResults();
        boolean single = results.size() == 1;
        int i = 0;

        for(Iterator var7 = results.iterator(); var7.hasNext(); ++i) {
            ProcessingOutput output = (ProcessingOutput)var7.next();
            int xOffset = i % 9 * 19;
            int yOffset = i / 9 * 19;
            (builder.addSlot(RecipeIngredientRole.OUTPUT, single ? 45 : 4 + xOffset, 60 + yOffset)
                    .setBackground(getRenderedSlot(output), -1, -1)
                    .addItemStack(output.getStack()))
                    .addRichTooltipCallback(addStochasticTooltip(output));
        }
    }


    public void draw(SiftingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
       List<ProcessingOutput> results = recipe.getRollableResults();
       boolean single = results.size() == 1;
       AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 20, 2); // Input arrow
       AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 50, 32); //Output arrow

       drawSifter(graphics, recipe.requiresAdvancedSifter(), recipe.isWaterlogged());
       //drawRequirements(recipe, graphics, 67, 4);
       RecipeRequirementRenderer.drawRequirements(recipe,graphics, 67,4);

    }

    protected void drawSifter(GuiGraphics guiGraphics, boolean pAdvanced, boolean waterlogged){
        int x = 25;
        int y = 27;
        AbstractAnimatedSifter<?> sifter = (pAdvanced) ? new AnimatedBrassSifter() : new AnimatedSifter();
        sifter.waterlogged(waterlogged);
        sifter.draw(guiGraphics, x, y);
    }
}
