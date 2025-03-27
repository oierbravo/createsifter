package com.oierbravo.createsifter;

import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterBlockEntity;
import com.oierbravo.createsifter.foundation.data.ModDataGen;
import com.oierbravo.createsifter.infrastucture.config.MConfigs;
import com.oierbravo.createsifter.ponders.ModPonderPlugin;
import com.oierbravo.createsifter.register.*;
import com.oierbravo.mechanicals.register.MechanicalCreativeModeTabs;
import com.oierbravo.mechanicals.utility.RegistrateLangBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.oierbravo.createsifter.ModConstants.MODID;


@Mod(MODID)
public class CreateSifter {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger(MODID);
    public static IEventBus modEventBus;

    public static final CreateRegistrate REGISTRATE =
            CreateRegistrate.create(MODID).defaultCreativeTab(ModCreativeTabs.MAIN_TAB.getKey());

    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }
    public CreateSifter(IEventBus modEventBus, ModContainer modContainer) {

        REGISTRATE.registerEventListeners(modEventBus);
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        ModCreativeTabs.register(modEventBus);


        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        ModItemComponents.register(modEventBus);
        MConfigs.register(modLoadingContext,modContainer);

        modEventBus.addListener(ModDataGen::gatherData);

        ModRecipes.register(modEventBus);

        //modEventBus.addListener(ModDataGen::gatherData);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::doClientStuff);

        generateLangEntries();
    }
    private void generateLangEntries(){
        new RegistrateLangBuilder(MODID, registrate())
            .addRaw("config.jade.plugin_createsifter.sifter_data", "Create Sifter")
            .add("itemGroup.createsifter:main", "Create sifting")
            .add("recipe.sifting", "Sifting recipe")
            .add("recipe.sifting.minimumspeed", "%1$s RPM")
            .add("recipe.sifting.waterlogged", "Waterlogged")
            .add("recipe.sifting.brass_required", "Brass sifter")
            .add("tooltip.mesh", "Mesh: %s")
            //Ponder
            .add("ponder.sifter.header", "Block sifting")
            .add("ponder.sifter.text_1", "Sifter process items by sifting them")
            .add("ponder.sifter.text_2", "They can be powered from the side using cogwheels")
            .add("ponder.sifter.text_3", "Throw or Insert items at the top")
            .add("ponder.sifter.text_4", "After some time, the result can be obtained via Right-click")
            .add("ponder.sifter.text_5", "The outputs can also be extracted by automation");

    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    @net.neoforged.bus.api.SubscribeEvent
    public void registerCapabilities(net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent event) {
        SifterBlockEntity.registerCapabilities(event);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ModPartials.init();
        PonderIndex.addPlugin(new ModPonderPlugin());
    }


    public static Logger getLogger(){
        return LOGGER;
    }

}
