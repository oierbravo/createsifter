package com.oierbravo.createsifter;

import com.oierbravo.createsifter.foundation.data.recipe.ModProcessingRecipes;
import com.oierbravo.createsifter.register.*;
import com.oierbravo.createsifter.register.config.ModConfigs;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.LangMerger;
import com.simibubi.create.repack.registrate.util.NonNullLazyValue;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(CreateSifter.MODID)
public class CreateSifter {
    public static final String MODID = "createsifter";
    public static final String DISPLAY_NAME = "Create Sifter";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger(MODID);
    public static IEventBus modEventBus;

    public static final NonNullLazyValue<CreateRegistrate> registrate = CreateRegistrate.lazy(MODID);

    public CreateSifter() {
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CreateRegistrate r = registrate.get();
        ModItems.register(r);
        ModBlocks.register();
        ModEntities.register(r);
        ModTiles.register();
        if (DatagenModLoader.isRunningDataGen()) {
            modEventBus.addListener((GatherDataEvent g) -> ModPonder.generateLang(r, g));
        }
        modEventBus.addListener((FMLClientSetupEvent e) -> ModPonder.register());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> ModPartials::load);
        modEventBus.addListener(ModConfigs::onLoad);
        modEventBus.addListener(ModConfigs::onReload);
        modEventBus.addListener(EventPriority.LOWEST, CreateSifter::gatherData);
        modEventBus.addGenericListener(RecipeSerializer.class, ModRecipeTypes::register);
        ModConfigs.register();
    }
    public static CreateRegistrate registrate() {
        return registrate.get();
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        //gen.addProvider(new LangMerger(gen));
        ModProcessingRecipes.registerAllProcessingProviders(gen);

        //ProcessingRecipeGen.registerAll(gen);
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
