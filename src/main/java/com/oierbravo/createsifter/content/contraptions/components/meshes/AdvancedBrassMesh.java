package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class AdvancedBrassMesh extends AdvancedBaseMesh {
    public AdvancedBrassMesh(Properties pProperties) {
        super(pProperties);
        this.mesh = MeshTypes.ADVANCED_BRASS;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return super.getAttributeModifiers(slot, stack);
    }
}
