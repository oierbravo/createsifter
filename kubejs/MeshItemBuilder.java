package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.content.contraptions.components.meshes.Mesh;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MeshItemBuilder extends ItemBuilder {

    private ResourceLocation resourceLocation;
    public MeshItemBuilder(ResourceLocation resourceLocation) {
        super(resourceLocation);
        this.resourceLocation = resourceLocation;
    }

    @Override
    public Item createObject() {
        return new Mesh(createItemProperties()) ;
    }
}
