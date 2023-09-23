package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.meshes.CustomMesh;
import com.oierbravo.createsifter.register.ModCreativeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MeshItemBuilder extends ItemBuilder {
    private static final CreateRegistrate REGISTRATE = CreateSifter.registrate()
            .setCreativeTab(ModCreativeTabs.MAIN_TAB);

    private ResourceLocation resourceLocation;
    public MeshItemBuilder(ResourceLocation resourceLocation) {
        super(resourceLocation);
        this.resourceLocation = resourceLocation;
    }

    @Override
    public Item createObject() {
        return new CustomMesh(createItemProperties()) ;
    }
}
