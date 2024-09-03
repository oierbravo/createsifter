package com.oierbravo.createsifter.compat.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipe;
import com.oierbravo.createsifter.foundation.data.recipe.SiftingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;
import java.util.stream.Collectors;

@IRecipeHandler.For(SiftingRecipe.class)
public class SiftingRecipeHandler implements IRecipeHandler<SiftingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super SiftingRecipe> manager, SiftingRecipe recipe) {
        return manager.getCommandString() + recipe.toString() + recipe.getRollableResults() + "[" + recipe.getIngredients() + "]";
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super SiftingRecipe> manager, SiftingRecipe firstRecipe, U secondRecipe) {
        if (!this.isGoodRecipe(secondRecipe)) {
            return false;
        } else {
            SiftingRecipe second = (SiftingRecipe)secondRecipe;
            if (firstRecipe.getIngredients().size() == second.getIngredients().size() ) {
                if (IngredientUtil.doIngredientsConflict(firstRecipe.getIngredients(), second.getIngredients())) {
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super SiftingRecipe> manager, SiftingRecipe recipe) {
        return Optional.of(
                IDecomposedRecipe.builder()
                        .with(BuiltinRecipeComponents.Input.INGREDIENTS, recipe.getIngredients()
                                .stream()
                                .map(IIngredient::fromIngredient)
                                .toList())
                        .with(BuiltinRecipeComponents.Output.CHANCED_ITEMS, recipe.getRollableResults().stream().map(SiftingRecipeHandler::mapProcessingResult).toList())
                        .with(BuiltinRecipeComponents.Processing.TIME, recipe.getProcessingDuration())
                        .with(RecipeComponents.Input.BOOLEAN, recipe.isWaterlogged())
                        .with(RecipeComponents.Input.FLOAT, recipe.getMinimumSpeed())
                        .build());
    }

    @Override
    public Optional<SiftingRecipe> recompose(IRecipeManager<? super SiftingRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        SiftingRecipeBuilder builder = new SiftingRecipeBuilder(this.factory(), name);
        builder.withItemIngredients((NonNullList)recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS).stream().map(IIngredient::asVanillaIngredient).collect(Collectors.toCollection(NonNullList::create)));
        builder.withItemOutputs((NonNullList)recipe.getOrThrow(BuiltinRecipeComponents.Output.CHANCED_ITEMS).stream().map(SiftingRecipeHandler::mapPercentagedToProcessingOutput).collect(Collectors.toCollection(NonNullList::create)));
        builder.duration((Integer)recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME));
        builder.waterlogged((boolean)recipe.getOrThrowSingle(RecipeComponents.Input.BOOLEAN));
        builder.minimumSpeed((float)recipe.getOrThrowSingle(RecipeComponents.Input.FLOAT));
        return Optional.of(builder.build());
    }


    public SiftingRecipeBuilder.SiftingRecipeFactory factory() {
        return SiftingRecipe::new;
    }
    public boolean isGoodRecipe(Recipe<?> recipe) {
        return recipe instanceof SiftingRecipe;
    }
    public static ProcessingOutput mapPercentagedToProcessingOutput(Percentaged<IItemStack> stack) {

        return new ProcessingOutput(stack.getData().getInternal(), (float) stack.getPercentage());
    }
    public static Percentaged<IItemStack> mapProcessingResult(ProcessingOutput result) {

        return IItemStack.of(result.getStack()).percent(result.getChance() * 100);
    }
}
