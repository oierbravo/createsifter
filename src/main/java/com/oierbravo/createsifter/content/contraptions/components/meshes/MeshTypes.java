package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.register.ModItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.checkerframework.checker.units.qual.K;

public enum MeshTypes implements StringRepresentable {
    STRING(0,ModItems.STRING_MESH),
    ANDESITE(1,ModItems.ANDESITE_MESH),
    ZINC(2, ModItems.ZINC_MESH),
    BRASS(3, ModItems.BRASS_MESH);

    private final int tier;
    private final ItemEntry item;

    MeshTypes(int tier, ItemEntry item) {
        this.tier = tier;
        this.item = item;
    }
    public String getName() {
        return this.name().toLowerCase() + "_mesh";
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

    public ItemLike getItem() {
       return (ItemLike) this.item.get();

    }
}
