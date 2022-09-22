package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipe;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.curiosities.tools.SandPaperItemRenderer;
import com.simibubi.create.content.curiosities.tools.SandPaperPolishingRecipe;
import com.simibubi.create.foundation.item.CustomUseEffectsItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.mixin.accessor.LivingEntityAccessor;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class AndesiteMesh extends BaseMesh{
    public AndesiteMesh(Properties pProperties) {
        super(pProperties);
        this.mesh = MeshTypes.ANDESITE;
    }
}
