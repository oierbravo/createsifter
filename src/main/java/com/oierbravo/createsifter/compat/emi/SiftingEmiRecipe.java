package com.oierbravo.createsifter.compat.emi;

import com.oierbravo.createsifter.compat.emi.animations.AnimatedSifter;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeWithRequirements;
import com.oierbravo.mechanicals.utility.LibLang;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.createmod.catnip.data.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SiftingEmiRecipe  implements EmiRecipe {
    private static AllGuiTextures arrowTexture = AllGuiTextures.JEI_DOWN_ARROW;
    private static AllGuiTextures shadow = AllGuiTextures.JEI_SHADOW;

    ResourceLocation id;
    SiftingRecipe recipe;

    private List<EmiIngredient> inputs;
    private List<EmiStack> outputs;

    public SiftingEmiRecipe(SiftingRecipe recipe, ResourceLocation id){
        this.recipe = recipe;
        this.id = id.withPrefix("/");
        inputs = List.of(EmiIngredient.of(recipe.getInput()),EmiStack.of(recipe.getMesh()));

        List<EmiStack> outputs = new ArrayList<>();
        for(ProcessingOutput output : recipe.getRollableResults()){
            outputs.add(EmiStack.of(output.getStack()).setChance(output.getChance()));
        }
        this.outputs = outputs;
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CreteSifterEMI.SIFTING_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 177;
    }

    @Override
    public int getDisplayHeight() {
        return 78 + ((outputs.size() / 10) * 19);
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(shadow.location, 11, 42, shadow.getWidth(), shadow.getHeight(), shadow.getStartX(), shadow.getStartY());

        widgets.addTexture(arrowTexture.location, 21, 3, arrowTexture.getWidth(), arrowTexture.getHeight(), arrowTexture.getStartX(), arrowTexture.getStartY());
        widgets.addTexture(arrowTexture.location, 52, 33, arrowTexture.getWidth(), arrowTexture.getHeight(), arrowTexture.getStartX(), arrowTexture.getStartY());

        widgets.addSlot(this.inputs.get(0), 3, 3);
        widgets.addSlot(this.inputs.get(1), 3, 22);

        boolean single = outputs.size() == 1;
        int i = 0;

        for(Iterator var7 = outputs.iterator(); var7.hasNext(); ++i) {
            EmiStack output = (EmiStack)var7.next();
            int xOffset = i % 9 * 19;
            int yOffset = i / 9 * 19;
            widgets.addSlot(output,single ? 45 : 4 + xOffset, 60 + yOffset ).recipeContext(this);
        }
        AnimatedSifter.sifter(widgets, 27, 47, recipe.isWaterlogged(), recipe.requiresAdvancedSifter());

        drawRequirements(recipe, widgets,67, 4);


        /*builder.addSlot(RecipeIngredientRole.INPUT, 3, 3).setBackground(getRenderedSlot(), -1, -1).addIngredients(recipe.getInput());

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
        }*/
    }
    private void drawRequirements(IRecipeWithRequirements recipe, WidgetHolder widgets, int x, int y){
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int index = 0;
        int distance = 9;
        int offsetX = 5;
        int offsetY = 14;
        widgets.addText((Component) LibLang.translate("ui.recipe.requirements.title").component().withStyle(new ChatFormatting[0]), x, y, -1 , true);
        //guiGraphics.drawString(fontRenderer, );
        List<Pair<Component, Component>> jeiRequirementTooltips = recipe.getJeiRequirementsTooltips();
        if (jeiRequirementTooltips.isEmpty()) {
            widgets.addText((Component) LibLang.translate("ui.recipe_requirement.none.tooltip").component().withStyle(new ChatFormatting[0]), x + offsetX, y + offsetY,-8355712 , false);

        } else {
            for(Pair<Component, Component> pair : jeiRequirementTooltips) {
                int oneLinerLenght = ((Component)pair.getSecond()).getString().length() + ((Component)pair.getSecond()).getString().length();
                if (oneLinerLenght < 19) {
                    widgets.addText(((Component)pair.getFirst()).plainCopy().append(" ").append((Component)pair.getSecond()), x + offsetX, y + offsetY + distance * index,-8355712 , false);
                    ++index;
                } else {
                    widgets.addText((Component)pair.getFirst(), x + offsetX, y + offsetY + distance * index,-8355712 , false);
                    ++index;
                    widgets.addText((Component)pair.getSecond(), x + offsetX, y + offsetY + distance * index,-8355712 , false);
                    ++index;
                }
            }

        }
    }
}
