package com.oierbravo.createsifter.content.contraptions.components.meshes;

import net.minecraft.world.item.ItemStack;

public interface IMesh {

    default boolean isAdvanced(){
        return false;
    }
    /*default boolean test(ItemStack itemStack){
        return itemStack.getItem() instanceof IMesh;
    }*/
}
