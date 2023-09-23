package com.oierbravo.createsifter;

import com.oierbravo.createsifter.foundation.data.recipe.ModProcessingRecipeGen;
import com.oierbravo.createsifter.groups.ModGroup;
import com.oierbravo.createsifter.register.*;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public CreateSifter() {
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);

        new ModGroup("main");
        ModConfigs.register();

        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();

        ModRecipeTypes.register(modEventBus);
        modEventBus.addListener(this::doClientStuff);

        generateLangEntries();

        modEventBus.addListener(EventPriority.LOWEST, CreateSifter::gatherData);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> ModPartials::init);
    }
    private void generateLangEntries(){

        registrate().addRawLang("createsifter.recipe.sifting", "Sifting recipe");
        registrate().addRawLang("create.recipe.sifting", "Sifting recipe");
        registrate().addRawLang("create.createsifter.recipe.sifting.minimumspeed", "%1$s RPM");
        registrate().addRawLang("createsifter.recipe.sifting.waterlogged", "Waterlogged");
        registrate().addRawLang("itemGroup.createsifter:main", "Create sifting");
        //Ponder
        registrate().addRawLang("createsifter.ponder.sifter.header", "Block sifting");
        registrate().addRawLang("createsifter.ponder.sifter.text_1", "Sifter process items by sifting them");
        registrate().addRawLang("createsifter.ponder.sifter.text_2", "They can be powered from the side using cogwheels");
        registrate().addRawLang("createsifter.ponder.sifter.text_3", "Throw or Insert items at the top");
        registrate().addRawLang("createsifter.ponder.sifter.text_4", "After some time, the result can be obtained via Right-click");
        registrate().addRawLang("createsifter.ponder.sifter.text_5", "The outputs can also be extracted by automation");

        registrate().addRawLang("createsifter.recipe.sifting.brass_required", "Brass sifter required");

    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {

        }
        if (event.includeServer()) {
            ModProcessingRecipeGen.registerAll(gen);
        }

    }
    private void doClientStuff(final FMLClientSetupEvent event) {
       event.enqueueWork(ModPonders::register);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }

}
