package com.oierbravo.createsifter.foundation.data.recipe;


import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ModProcessingRecipeGen extends ProcessingRecipeGen {
    protected static final List<ModProcessingRecipeGen> GENERATORS = new ArrayList<>();
    public ModProcessingRecipeGen(DataGenerator generator) {
        super(generator);
    }
    public static void registerAll(DataGenerator generator) {
        GENERATORS.add(new SiftingRecipeGen(generator));

        generator.addProvider(true, new DataProvider() {
            @Override
            public void run(CachedOutput dc) throws IOException {
                GENERATORS.forEach(g -> {
                    try {
                        g.run(dc);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public @NotNull String getName() {
                return "Create: Sifter's Processing Recipes";
            }
        });
    }


}