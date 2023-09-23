package com.oierbravo.createsifter.compat.crafttweaker;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.createtweaker.managers.base.IProcessingRecipeManager;
import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipeSerializer;
import com.oierbravo.createsifter.foundation.data.recipe.SiftingRecipeBuilder;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @docParam this <recipetype:create:milling>
 */
@ZenRegister
@ZenCodeType.Name("mods.createsifter.SiftingManager")
//@Document("mods/createsifter/SiftingManager")
public class SiftingRecipeManager implements IProcessingRecipeManager<SiftingRecipe> {
    public SiftingRecipeSerializer getSifterSerializer() {
        return ModRecipeTypes.SIFTING.getSerializer();
    }


    /**
     * Adds a milling recipe.
     *
     * @param name     The name of the recipe.
     * @param outputs  The outputs of the recipe
     * @param itemInputs    The input of the recipe.
     * @param duration The duration of the recipe (default 100 ticks).
     * @param waterlogged Is a waterlogged recipe? (default false).
     * @param duration Minimum required speed (default 1.0 ticks).
     *
     * @docParam name "milled"
     * @docParam outputs [<item:minecraft:diamond> % 50, <item:minecraft:apple>, (<item:minecraft:dirt> * 2) % 12]
     * @docParam input <item:minecraft:dirt>
     * @docParam duration 200
     * @docParam waterlogged false
     * @docParam minimumSpeed 1
     */
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredient[] itemInputs, @ZenCodeType.OptionalInt(100) int duration, @ZenCodeType.OptionalBoolean(false) boolean waterlogged, @ZenCodeType.OptionalInt(1) int minimumSpeed) {

        name = fixRecipeName(name);
        ResourceLocation resourceLocation = new ResourceLocation("crafttweaker", name);
        SiftingRecipeBuilder builder = new SiftingRecipeBuilder(getSifterSerializer().getFactory(), resourceLocation);
        builder.withItemOutputs(Arrays.stream(outputs)
                .map(mcWeightedItemStack -> new ProcessingOutput(mcWeightedItemStack.getData()
                        .getInternal(), (float) mcWeightedItemStack.getPercentage()))
                .toArray(ProcessingOutput[]::new));

        List<Ingredient> ingredients = new ArrayList();
        Arrays.stream(itemInputs).forEach((iIngredient) -> {
            ingredients.add(iIngredient.asVanillaIngredient());
        });
        builder.withItemIngredients((Ingredient[])ingredients.toArray(new Ingredient[0]));

        builder.duration(duration);
        builder.waterlogged(waterlogged);
        builder.minimumSpeed(minimumSpeed);
        SiftingRecipe recipe = builder.build();
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, recipe));
    }


    @Override
    public AllRecipeTypes getCreateRecipeType() {
        return null;
    }

    @Override
    public RecipeType<SiftingRecipe> getRecipeType() {
       return ModRecipeTypes.SIFTING.getType();
    }
}
