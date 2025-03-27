package com.oierbravo.createsifter.content.contraptions.components.meshes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IMesh {

    default boolean isAdvanced(){
        return false;
    }

    static ItemStack asStack(Item item) {
        return new ItemStack(item);
    }
    /*default boolean test(ItemStack itemStack){
        return itemStack.getItem() instanceof IMesh;
    }*/
}
