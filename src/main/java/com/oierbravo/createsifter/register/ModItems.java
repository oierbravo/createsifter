package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.meshes.AndesiteMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BrassMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.StringMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.ZincMesh;
import com.oierbravo.createsifter.groups.ModGroup;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItems {
    private static final CreateRegistrate REGISTRATE = CreateSifter.registrate()
            .creativeModeTab(() -> ModGroup.MAIN);

    public static void register() {}

    public static final ItemEntry<StringMesh> STRING_MESH =
            REGISTRATE.item("string_mesh", StringMesh::new)
                    .model(AssetLookup.customGenericItemModel("meshes", "string_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    public static final ItemEntry<AndesiteMesh> ANDESITE_MESH =
            REGISTRATE.item("andesite_mesh", AndesiteMesh::new)
                    .model(AssetLookup.customGenericItemModel("meshes", "andesite_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    public static final ItemEntry<ZincMesh> ZINC_MESH =
            REGISTRATE.item("zinc_mesh", ZincMesh::new)
                    .model(AssetLookup.customGenericItemModel("meshes", "zinc_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    public static final ItemEntry<BrassMesh> BRASS_MESH =
            REGISTRATE.item("brass_mesh", BrassMesh::new)
                    .model(AssetLookup.customGenericItemModel("meshes", "brass_mesh"))
                    .tag(ModTags.ModItemTags.MESHES.tag)
                    .register();
    
}
