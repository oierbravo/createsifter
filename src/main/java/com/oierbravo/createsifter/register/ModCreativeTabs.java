package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateSifter.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.createsifter:main"))
                    .withTabsBefore(AllCreativeModeTabs.BUILDING_BLOCKS_TAB.getId())
                    .icon(ModBlocks.SIFTER::asStack)
                    .displayItems((pParameters, pOutput) -> {
                        for (RegistryEntry<Block> entry : CreateSifter.REGISTRATE.getAll(Registries.BLOCK)) {
                            pOutput.accept(entry.get());
                        }
                        for (RegistryEntry<Item> entry : CreateSifter.REGISTRATE.getAll(Registries.ITEM)) {
                            pOutput.accept(entry.get());
                        }
                    })
                    .build());

    public static CreativeModeTab getBaseTab() {
        return MAIN_TAB.get();
    }

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}
