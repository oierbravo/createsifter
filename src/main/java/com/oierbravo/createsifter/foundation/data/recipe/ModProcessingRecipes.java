package com.oierbravo.createsifter.foundation.data.recipe;


import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class ModProcessingRecipes extends CreateRecipeProvider {


    protected static final List<ModProcessingRecipes> GENERATORS = new ArrayList<>();


    public static void registerAllProcessingProviders(DataGenerator gen, PackOutput output) {
        GENERATORS.add(new SiftingRecipeGen(output));
        gen.addProvider(true, new DataProvider() {

            @Override
            public String getName() {
                return "Create's Processing Recipes";
            }

            @Override
            public CompletableFuture<?> run(CachedOutput dc) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(dc))
                        .toArray(CompletableFuture[]::new));
            }
        });

    }


    public ModProcessingRecipes(PackOutput output) {
        super(output);
    }


    /* Functions from Create's ProcessingRecipeGen.java */

    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(String name,
                                                                     UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(CreateSifter.asResource(name), transform);
    }

    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(ResourceLocation name,
                                                                     UnaryOperator<ProcessingRecipeBuilder<T>> transform) {

        return createWithDeferredId(() -> name, transform);
    }

    protected <T extends ProcessingRecipe<?>> GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name,
                                                                                   UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        GeneratedRecipe generatedRecipe =
                c -> transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name.get()))
                //c -> transform.apply(new ModProcessingRecipeBuilder<>(serializer.getFactory(), name.get()))
                        .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected <T extends ProcessingRecipe<?>> ProcessingRecipeSerializer<T> getSerializer() {
        return getRecipeType().getSerializer();
    }

    protected abstract IRecipeTypeInfo getRecipeType();

}