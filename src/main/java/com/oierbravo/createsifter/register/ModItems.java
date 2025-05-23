package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.meshes.AdvancedMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.Mesh;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.recipe.CompatMetals;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import static com.oierbravo.createsifter.CreateSifter.REGISTRATE;

public class ModItems {

    public static final ItemEntry<Mesh> STRING_MESH =
            REGISTRATE.item("string_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(8))
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                            .define('C', Items.STRING)
                            .define('S', Items.STICK)
                            .pattern("SSS")
                            .pattern("SCS")
                            .pattern("SSS")
                            .unlockedBy("has_string", RegistrateRecipeProvider.has(Items.STRING))
                            .unlockedBy("has_stick", RegistrateRecipeProvider.has(Items.STICK))
                            .save(p, ModConstants.asResource("crafting/" + c.getName())))
                    .register();
    public static final ItemEntry<Mesh> ANDESITE_MESH =
            REGISTRATE.item("andesite_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(16))
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                            .define('C', AllItems.ANDESITE_ALLOY)
                            .define('S', Items.STICK)
                            .pattern("SSS")
                            .pattern("SCS")
                            .pattern("SSS")
                            .unlockedBy("has_andesite_alloy", RegistrateRecipeProvider.has(AllItems.ANDESITE_ALLOY))
                            .unlockedBy("has_stick", RegistrateRecipeProvider.has(Items.STICK))
                            .save(p, ModConstants.asResource("crafting/" + c.getName())))
                    .register();
    public static final ItemEntry<Mesh> ZINC_MESH =
            REGISTRATE.item("zinc_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .register();
    public static final ItemEntry<Mesh> BRASS_MESH =
            REGISTRATE.item("brass_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(64))
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                            .define('C', AllItems.BRASS_INGOT)
                            .define('S', Items.STICK)
                            .pattern("SSS")
                            .pattern("SCS")
                            .pattern("SSS")
                            .unlockedBy("has_brass_ingot", RegistrateRecipeProvider.has(AllItems.BRASS_INGOT))
                            .unlockedBy("has_stick", RegistrateRecipeProvider.has(Items.STICK))
                            .save(p, ModConstants.asResource("crafting/" + c.getName())))
                    .register();
    public static final ItemEntry<Mesh> STURDY_MESH =
            REGISTRATE.item("sturdy_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(64))
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                            .define('C', AllItems.STURDY_SHEET)
                            .define('S', Items.STICK)
                            .pattern("SSS")
                            .pattern("SCS")
                            .pattern("SSS")
                            .unlockedBy("has_sturdy_sheet", RegistrateRecipeProvider.has(AllItems.STURDY_SHEET))
                            .save(p, ModConstants.asResource("crafting/" + c.getName())))
                    .register();

    public static final ItemEntry<Mesh> CUSTOM_MESH =
            REGISTRATE.item("custom_mesh", Mesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(64))
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .register();

    public static final ItemEntry<AdvancedMesh> ADVANCED_BRASS_MESH =
            REGISTRATE.item("advanced_brass_mesh", AdvancedMesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(256))
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                            .define('S', AllItems.BRASS_SHEET)
                            .define('M', ModItems.BRASS_MESH)
                            .pattern(" S ")
                            .pattern("SMS")
                            .pattern(" S ")
                            .unlockedBy("has_sifter", RegistrateRecipeProvider.has(ModBlocks.SIFTER))
                            .save(p, ModConstants.asResource("crafting/" + c.getName())))
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .register();

    public static final ItemEntry<AdvancedMesh> ADVANCED_STURDY_MESH =
            REGISTRATE.item("advanced_sturdy_mesh", AdvancedMesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(256))
                    .tag(AllTags.commonItemTag("meshes"))
                    .tag(Tags.Items.ENCHANTABLES, ItemTags.DURABILITY_ENCHANTABLE)
                    .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                            .define('S', AllItems.STURDY_SHEET)
                            .define('M', ModItems.STURDY_MESH)
                            .pattern(" S ")
                            .pattern("SMS")
                            .pattern(" S ")
                            .unlockedBy("has_sifter", RegistrateRecipeProvider.has(ModBlocks.SIFTER))
                            .save(p, ModConstants.asResource("crafting/" + c.getName())))
                    .register();

    public static final ItemEntry<AdvancedMesh> ADVANCED_CUSTOM_MESH =
            REGISTRATE.item("advanced_custom_mesh", AdvancedMesh::new)
                    .model(AssetLookup.existingItemModel())
                    .properties(properties -> properties.durability(256))
                    .tag(AllTags.commonItemTag("meshes"))
                    .register();


    public static final ItemEntry<Item>
            PEBBLE_ANDESITE = pebble("andesite", Blocks.ANDESITE),
            PEBBLE_BASALT = pebble("basalt", Blocks.BASALT),
            PEBBLE_BLACKSTONE = pebble("blackstone", Blocks.BLACKSTONE),
            PEBBLE_CALCITE = pebble("calcite", Blocks.CALCITE),
            PEBBLE_DEEPSLATE = pebble("deepslate", Blocks.COBBLED_DEEPSLATE),
            PEBBLE_DIORITE = pebble("diorite", Blocks.DIORITE),
            PEBBLE_GRANITE = pebble("granite", Blocks.GRANITE),
            PEBBLE_STONE = pebble("stone", Blocks.COBBLESTONE),
            PEBBLE_TUFF = pebble("tuff", Blocks.TUFF);



    //public static final ItemEntry<Item> GOLD = metalPiece()
    public static final ItemEntry<Item>
            PIECE_GOLD = metalPiece("gold", AllItems.CRUSHED_GOLD),
            PIECE_IRON = metalPiece("iron", AllItems.CRUSHED_IRON),
            PIECE_ZINC = metalPiece("zinc", AllItems.CRUSHED_ZINC);

    public static final ItemEntry<TagDependentIngredientItem>
            PIECE_OSMIUM = compatMetalPiece(CompatMetals.OSMIUM, AllItems.CRUSHED_OSMIUM),
            PIECE_PLATINUM = compatMetalPiece(CompatMetals.PLATINUM, AllItems.CRUSHED_PLATINUM),
            PIECE_SILVER = compatMetalPiece(CompatMetals.SILVER, AllItems.CRUSHED_SILVER),
            PIECE_TIN = compatMetalPiece(CompatMetals.TIN, AllItems.CRUSHED_TIN),
            PIECE_LEAD = compatMetalPiece(CompatMetals.LEAD, AllItems.CRUSHED_LEAD),
            PIECE_QUICKSILVER = compatMetalPiece(CompatMetals.QUICKSILVER, AllItems.CRUSHED_QUICKSILVER),
            PIECE_BAUXITE = compatMetalPiece(CompatMetals.ALUMINUM, AllItems.CRUSHED_BAUXITE),
            PIECE_URANIUM = compatMetalPiece(CompatMetals.URANIUM, AllItems.CRUSHED_URANIUM),
            PIECE_NICKEL = compatMetalPiece(CompatMetals.NICKEL, AllItems.CRUSHED_NICKEL);

    private static ItemEntry<Item> pebble(String name, Block fullBlock) {
        return REGISTRATE
                .item( name + "_pebble",
                        Item::new)
                .tag(AllTags.commonItemTag("pebbles"))
                .recipe((itemItemDataGenContext, registrateRecipeProvider) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,fullBlock,1).requires(itemItemDataGenContext.get(),4)
                                .unlockedBy("has_sifter", RegistrateRecipeProvider.has(ModBlocks.SIFTER))
                                .save(registrateRecipeProvider, ModConstants.asResource("shapeless/" + itemItemDataGenContext.getName())))
                .register();
    }
    private static ItemEntry<Item> metalPiece(String metalName, ItemEntry<Item> fullItem) {
        return REGISTRATE
                .item( "raw_" + metalName + "_piece",
                        Item::new)
                .tag(AllTags.commonItemTag("pieces"))
                .recipe((itemItemDataGenContext, registrateRecipeProvider) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,fullItem.get() ,1).requires(itemItemDataGenContext.get(),4)
                                .unlockedBy("has_sifter", RegistrateRecipeProvider.has(ModBlocks.SIFTER))
                                .save(registrateRecipeProvider, ModConstants.asResource("shapeless/" + itemItemDataGenContext.getName())))
                .register();
    }

    private static ItemEntry<TagDependentIngredientItem> compatMetalPiece(CompatMetals metal, ItemEntry<TagDependentIngredientItem> fullItem) {
        String metalName = metal.getName();
        return compatMetalPiece(metalName, fullItem);
    }

    private static ItemEntry<TagDependentIngredientItem> compatMetalPiece(String metalName, ItemEntry<TagDependentIngredientItem> fullItem) {
        return REGISTRATE
                .item( "raw_" + metalName + "_piece",
                        props -> new TagDependentIngredientItem(props, AllTags.commonItemTag("ores/" + metalName)))
                .tag(AllTags.commonItemTag("pieces"))
                .recipe((itemTagDependentIngredientItemDataGenContext, registrateRecipeProvider) ->
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,fullItem.get() ,1).requires(itemTagDependentIngredientItemDataGenContext.get(),4)
                            .unlockedBy("has_sifter", RegistrateRecipeProvider.has(ModBlocks.SIFTER))
                            .save(registrateRecipeProvider, ModConstants.asResource("shapeless/" + itemTagDependentIngredientItemDataGenContext.getName())))
                .register();
    }


    public static void register() {}


}
