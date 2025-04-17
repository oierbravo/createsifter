package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.ModLang;
import com.oierbravo.mechanicals.utility.MechanicalLangIdGenerator;
import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class ModCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(ModLang.translate(MechanicalLangIdGenerator.creativeTabId("main")).component())
                    .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getId())
                    /*.displayItems((itemDisplayParameters, output) -> {
                        List<ItemEntry<TagDependentIngredientItem>> tagDependentExclusions = List.of(
                                AllItems.CRUSHED_OSMIUM,
                                AllItems.CRUSHED_PLATINUM,
                                AllItems.CRUSHED_SILVER,
                                AllItems.CRUSHED_TIN,
                                AllItems.CRUSHED_LEAD,
                                AllItems.CRUSHED_QUICKSILVER,
                                AllItems.CRUSHED_BAUXITE,
                                AllItems.CRUSHED_URANIUM,
                                AllItems.CRUSHED_NICKEL
                        );
                        for (RegistryEntry<Item, Item> entry : CreateSifter.registrate().getAll(Registries.ITEM)) {
                            if (!(entry.get() instanceof TagDependentIngredientItem))
                                output.accept(entry.get());

                            if (entry.get() instanceof TagDependentIngredientItem && !((TagDependentIngredientItem) entry.get()).shouldHide()) {
                                output.accept(entry.get());
                            }
                        }

                        })*/
                    .icon(ModBlocks.SIFTER::asStack)
                    .build());

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}
