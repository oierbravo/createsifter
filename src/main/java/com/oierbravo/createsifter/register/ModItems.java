package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItems {
    public static CreativeModeTab itemGroup = new CreativeModeTab(CreateSifter.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(AllItems.WRENCH.get());
        }
    };

    public static void register(CreateRegistrate registrate) {
        registrate.creativeModeTab(()->itemGroup, CreateSifter.DISPLAY_NAME);
    }
}
