package com.oierbravo.createsifter.infrastucture.data;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.infrastucture.data.recipe.CraftingRecipeGen;
import com.oierbravo.createsifter.infrastucture.data.recipe.MillingAndCrushingRecipeGen;
import com.oierbravo.createsifter.infrastucture.data.recipe.SiftingCompatRecipeGen;
import com.oierbravo.createsifter.infrastucture.data.recipe.SiftingRecipeGen;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

import static com.oierbravo.createsifter.ModConstants.MODID;

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
        }
        event.getGenerator().addProvider(true, CreateSifter.registrate().setDataProvider(new RegistrateDataProvider(CreateSifter.registrate(), MODID, event)));

    }
}