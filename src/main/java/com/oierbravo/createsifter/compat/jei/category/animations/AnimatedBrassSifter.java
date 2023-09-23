package com.oierbravo.createsifter.compat.jei.category.animations;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlock;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class AnimatedBrassSifter extends BaseAnimatedSifter<BrassSifterBlock> {

    @Override
    PartialModel getMeshModel() {
        return ModPartials.BRASS_SIFTER_MESH;
    }

    @Override
    PartialModel getCogModel() {
        return ModPartials.BRASS_SIFTER_COG;
    }

    @Override
    BlockEntry<BrassSifterBlock> getSifterBlock() {
        return ModBlocks.BRASS_SIFTER;
    }
}
