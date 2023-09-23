package com.oierbravo.createsifter.content.contraptions.components.meshes;

import net.minecraft.world.item.Item;

public abstract class AdvancedBaseMesh extends Item implements IMesh {
    protected MeshTypes mesh;
    public AdvancedBaseMesh(Properties pProperties) {
        super(pProperties);

    }
}
