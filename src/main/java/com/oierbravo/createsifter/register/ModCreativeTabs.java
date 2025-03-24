package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.foundation.util.ModLang;
import com.oierbravo.mechanicals.Mechanicals;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static com.oierbravo.createsifter.CreateSifter.modEventBus;
import static com.oierbravo.createsifter.ModConstants.MODID;

public class ModCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(ModLang.translate("itemGroup.createsifter:main").component())
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
