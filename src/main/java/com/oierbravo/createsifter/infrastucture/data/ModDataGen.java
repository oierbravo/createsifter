package com.oierbravo.createsifter.infrastucture.data;

import com.oierbravo.createsifter.infrastucture.data.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDataGen {
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        if (event.includeServer()) {
            generator.addProvider(true, new SiftingRecipeGen(output, lookupProvider));
            generator.addProvider(true, new SiftingCompatRecipeGen(output, lookupProvider));
            generator.addProvider(true, new MillingAndCrushingRecipeGen(output, lookupProvider));
            generator.addProvider(true, new CraftingRecipeGen(output, lookupProvider));
            generator.addProvider(true, new SmeltingRecipeGen(output, lookupProvider));
        }
    }
}