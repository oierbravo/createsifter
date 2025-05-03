package com.oierbravo.createsifter.content.contraptions.components.meshes;

import net.minecraft.world.item.ItemStack;

public class MeshUtils {
    public static boolean isMeshItem(ItemStack itemStack){
        return itemStack.getItem() instanceof IMesh;
    }
    public static boolean isAdvancedMesh(ItemStack mesh) {
        return mesh.getItem() instanceof IAdvancedMesh;
    }
}
