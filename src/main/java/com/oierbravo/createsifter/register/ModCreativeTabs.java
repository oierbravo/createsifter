package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModLang;
import com.oierbravo.mechanicals.utility.LangIdGenerator;
import com.oierbravo.mechanicals.utility.MechanicalRegistrateDisplayItemsGenerator;
import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class ModCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(ModLang.translate(LangIdGenerator.creativeTab("main")).component())
                    .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getId())
                    .displayItems(
                            MechanicalRegistrateDisplayItemsGenerator.create(true)
                                    .withItems(CreateSifter.registrate().getAll(Registries.ITEM))
                                    .withBlocks(CreateSifter.registrate().getAll(Registries.BLOCK))
                                    .withTagDependentExclusions(List.of(
                                            ModItems.PIECE_BAUXITE,
                                            ModItems.PIECE_LEAD,
                                            ModItems.PIECE_NICKEL,
                                            ModItems.PIECE_OSMIUM,
                                            ModItems.PIECE_PLATINUM,
                                            ModItems.PIECE_SILVER,
                                            ModItems.PIECE_QUICKSILVER,
                                            ModItems.PIECE_PLATINUM,
                                            ModItems.PIECE_TIN,
                                            ModItems.PIECE_URANIUM
                                    ))
                    )
                    .icon(ModBlocks.SIFTER::asStack)
                    .build());

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}
