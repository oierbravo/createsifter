package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshUtils;
import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record SiftingRecipeInput(ItemStack input, ItemStack mesh) implements RecipeInput {


    @Override
    public @NotNull ItemStack getItem(int slot) {
        if (slot == 0) return input;
        if (slot == 1) return mesh;
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 2;
    }
    public static <S extends AbstractSifterBlockEntity> SiftingRecipeInput fromSifter(S sifter){
        return new SiftingRecipeInput(sifter.getInputItemStack(), sifter.getMeshItemStack());
    }
    public static SiftingRecipeInput fromStacks(ItemStack itemStack, ItemStack secondItemStack){
        if(MeshUtils.isMeshItem(itemStack))
            return new SiftingRecipeInput(secondItemStack,itemStack);
         else
            return new SiftingRecipeInput(itemStack,secondItemStack);
    }
}
