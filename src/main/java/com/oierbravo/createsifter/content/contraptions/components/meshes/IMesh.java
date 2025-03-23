package com.oierbravo.createsifter.content.contraptions.components.meshes;

import net.minecraft.world.item.ItemStack;

public interface IMesh {
    static boolean isMeshItemStack(ItemStack itemStack){
        if(itemStack.getItem() instanceof IMesh)
            return true;
        return false;
    }
}
