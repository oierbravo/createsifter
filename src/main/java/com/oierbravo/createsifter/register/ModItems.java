package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.meshes.*;
import com.oierbravo.createsifter.groups.ModGroup;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

public class ModItems {
    private static final CreateRegistrate REGISTRATE = CreateSifter.registrate()
            .creativeModeTab(() -> ModGroup.MAIN);


    public static void register() {}

    public static final ItemEntry<StringMesh> STRING_MESH =
            REGISTRATE.item("string_mesh", StringMesh::new)
                    .model(AssetLookup.existingItemModel())

                    //      .model(AssetLookup.customGenericItemModel("meshes", "string_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    public static final ItemEntry<AndesiteMesh> ANDESITE_MESH =
            REGISTRATE.item("andesite_mesh", AndesiteMesh::new)
                    //.model()
                    .model(AssetLookup.existingItemModel())
                   // .model(AssetLookup.customGenericItemModel("meshes", "andesite_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    public static final ItemEntry<ZincMesh> ZINC_MESH =
            REGISTRATE.item("zinc_mesh", ZincMesh::new)
          //          .model(AssetLookup.customGenericItemModel("meshes", "zinc_mesh"))
                    .model(AssetLookup.existingItemModel())

                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    public static final ItemEntry<BrassMesh> BRASS_MESH =
            REGISTRATE.item("brass_mesh", BrassMesh::new)
                    .model(AssetLookup.existingItemModel())

                    //     .model(AssetLookup.customGenericItemModel("meshes", "brass_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    public static final ItemEntry<CustomMesh> CUSTOM_MESH =
            REGISTRATE.item("custom_mesh", CustomMesh::new)
                    .model(AssetLookup.existingItemModel())

                    //            .model(AssetLookup.customGenericItemModel("meshes", "custom_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    
}
