package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.CustomMesh;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MeshItemBuilder extends ItemBuilder {
    public MeshItemBuilder(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public Item createObject() {
        return new CustomMesh(createItemProperties());
    }
}
