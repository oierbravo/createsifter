package com.oierbravo.createsifter;

import com.oierbravo.createsifter.foundation.data.recipe.ModProcessingRecipes;
import com.oierbravo.createsifter.groups.ModGroup;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.createsifter.register.ModPartials;
import com.oierbravo.createsifter.register.ModBlockEntities;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CreateSifter.MODID)
public class CreateSifter {
    public static final String MODID = "createsifter";
    public static final String DISPLAY_NAME = "Create Sifter";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger(MODID);
    public static IEventBus modEventBus;

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public CreateSifter() {
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);


        new ModGroup("main");

        //ModPartials.init();

        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();





        //modEventBus.addGenericListener(RecipeSerializer.class, ModRecipeTypes::register);
        ModRecipeTypes.register(modEventBus);

        generateLangEntries();

        modEventBus.addListener(EventPriority.LOWEST, CreateSifter::gatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> ModPartials::load);
       // DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateSifterClient.onCtorClient(modEventBus, forgeEventBus));

    }
    private void generateLangEntries(){

        registrate().addRawLang("createsifter.recipe.sifting", "Sifting recipe");
        registrate().addRawLang("create.recipe.sifting", "Sifting recipe");
        registrate().addRawLang("itemGroup.createsifter:main", "Create sifting");
    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    @SubscribeEvent
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
