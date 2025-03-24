package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.content.contraptions.components.meshes.*;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.recipe.CompatMetals;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.oierbravo.createsifter.CreateSifter.REGISTRATE;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.inventoryTrigger;

public class ModItems {

    public static final ItemEntry<Mesh> STRING_MESH =
            REGISTRATE.item("string_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(8))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();
    public static final ItemEntry<Mesh> ANDESITE_MESH =
            REGISTRATE.item("andesite_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(16))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();
    public static final ItemEntry<Mesh> ZINC_MESH =
            REGISTRATE.item("zinc_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();
    public static final ItemEntry<Mesh> BRASS_MESH =
            REGISTRATE.item("brass_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(64))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();
    public static final ItemEntry<Mesh> STURDY_MESH =
            REGISTRATE.item("sturdy_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(64))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();

    public static final ItemEntry<Mesh> CUSTOM_MESH =
            REGISTRATE.item("custom_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(64))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();

    public static final ItemEntry<AdvancedMesh> ADVANCED_BRASS_MESH =
            REGISTRATE.item("advanced_brass_mesh", AdvancedMesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(256))

                    .tag(AllTags.commonItemTag("meshes"))
                    .register();

    public static final ItemEntry<AdvancedMesh> ADVANCED_STURDY_MESH =
            REGISTRATE.item("advanced_sturdy_mesh", AdvancedMesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(256))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();

    public static final ItemEntry<AdvancedMesh> ADVANCED_CUSTOM_MESH =
            REGISTRATE.item("advanced_custom_mesh", AdvancedMesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(256))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();

    //public static final ItemEntry<Item> GOLD = metalPiece()
    public static final ItemEntry<Item>
            GOLD = metalPiece("gold"),
            IRON = metalPiece("iron"),
            ZINC = metalPiece("zinc");

    public static final ItemEntry<TagDependentIngredientItem>
            OSMIUM = compatMetalPiece(CompatMetals.OSMIUM),
            PLATINUM = compatMetalPiece(CompatMetals.PLATINUM),
            SILVER = compatMetalPiece(CompatMetals.SILVER),
            TIN = compatMetalPiece(CompatMetals.TIN),
            LEAD = compatMetalPiece(CompatMetals.LEAD),
            QUICKSILVER = compatMetalPiece(CompatMetals.QUICKSILVER),
            BAUXITE = compatMetalPiece(CompatMetals.ALUMINUM),
            URANIUM = compatMetalPiece(CompatMetals.URANIUM),
            NICKEL = compatMetalPiece(CompatMetals.NICKEL);


    private static ItemEntry<Item> metalPiece(String metalName) {
        return REGISTRATE
                .item( "raw_" + metalName + "_piece",
                        Item::new)
                .tag(AllTags.commonItemTag("pieces"))
                .register();
    }

    private static ItemEntry<TagDependentIngredientItem> compatMetalPiece(CompatMetals metal) {
        String metalName = metal.getName();
        return compatMetalPiece(metalName);
    }

    private static ItemEntry<TagDependentIngredientItem> compatMetalPiece(String metalName) {
        return REGISTRATE
                .item( "raw_" + metalName + "_piece",
                        props -> new TagDependentIngredientItem(props, AllTags.commonItemTag("ores/" + metalName)))
                .tag(AllTags.commonItemTag("pieces"))
                .register();
    }


    public static void register() {}


}
