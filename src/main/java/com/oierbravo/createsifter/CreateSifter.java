package com.oierbravo.createsifter;

import com.oierbravo.createsifter.foundation.data.recipe.ModProcessingRecipes;
import com.oierbravo.createsifter.groups.ModGroup;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.createsifter.register.ModPartials;
import com.oierbravo.createsifter.register.ModTiles;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CreateSifter.MODID)
public class CreateSifter {
    public static final String MODID = "createsifter";
    public static final String DISPLAY_NAME = "Create Sifter";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger(MODID);
    public static IEventBus modEventBus;

    public static final NonNullSupplier<CreateRegistrate> registrate = CreateRegistrate.lazy(MODID);

    public CreateSifter() {
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CreateRegistrate r = registrate.get();

        new ModGroup("main");

        //ModPartials.init();

        ModBlocks.register();
        ModItems.register();
        ModTiles.register();


        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> ModPartials::load);
        modEventBus.addListener(EventPriority.LOWEST, CreateSifter::gatherData);


        //modEventBus.addGenericListener(RecipeSerializer.class, ModRecipeTypes::register);
        ModRecipeTypes.register(modEventBus);

        generateLangEntries();

    }
    private void generateLangEntries(){

        registrate().addRawLang("createsifter.recipe.sifting", "Sifting recipe");
        registrate().addRawLang("create.recipe.sifting", "Sifting recipe");
        registrate().addRawLang("itemGroup.createsifter:main", "Create sifting");
    }
    public static CreateRegistrate registrate() {
        return registrate.get();
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            //gen.addProvider(new LangMerger(gen));
            //gen.addProvider(AllSoundEvents.provider(gen));
        }
        if (event.includeServer()) {
            ModProcessingRecipes.registerAllProcessingProviders(gen);
        }

    }


    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
