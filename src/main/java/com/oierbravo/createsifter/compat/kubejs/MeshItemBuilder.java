package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.CustomMesh;
import com.oierbravo.createsifter.groups.ModGroup;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.createsifter.register.ModTags;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MeshItemBuilder extends ItemBuilder {
    private static final CreateRegistrate REGISTRATE = CreateSifter.registrate()
            .creativeModeTab(() -> ModGroup.MAIN);

    private ResourceLocation resourceLocation;
    public MeshItemBuilder(ResourceLocation resourceLocation) {
        super(resourceLocation);
        this.resourceLocation = resourceLocation;
    }

    @Override
    public Item createObject() {
        /*return
        REGISTRATE.item(resourceLocation.getPath(), CustomMesh::new)
                .model(AssetLookup.customGenericItemModel("meshes", "custom_mesh"))
                .tag(ModTags.ModItemTags.MESHES.tag)
                .register()
                .get();*/
        //return ModItems.CUSTOM_MESH.get();
        return new CustomMesh(createItemProperties()) ;
    }
}
