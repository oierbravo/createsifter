package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.oierbravo.createsifter.register.ModItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ItemLike;

public enum MeshTypes implements StringRepresentable {
    STRING(0,ModItems.STRING_MESH),
    ANDESITE(1,ModItems.ANDESITE_MESH),
    ZINC(2, ModItems.ZINC_MESH),
    BRASS(3, ModItems.BRASS_MESH),
    CUSTOM(4,ModItems.CUSTOM_MESH);

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
