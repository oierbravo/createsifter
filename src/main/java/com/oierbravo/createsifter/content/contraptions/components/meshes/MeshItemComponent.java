package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.equipment.sandPaper.SandPaperItemComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record MeshItemComponent(ItemStack item) {
    public static final Codec<MeshItemComponent> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(ItemStack.OPTIONAL_CODEC.fieldOf("item")
                    .forGetter(i -> i.item))
            .apply(instance, MeshItemComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MeshItemComponent> STREAM_CODEC =
            StreamCodec.composite(ItemStack.OPTIONAL_STREAM_CODEC, i -> i.item, MeshItemComponent::new);

    @Override
    public boolean equals(Object arg0) {
        return arg0 instanceof ItemStack otherItem && ItemStack.isSameItemSameComponents(otherItem, item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item.getItem(), item.getCount(), item.getComponents());
    }
}
