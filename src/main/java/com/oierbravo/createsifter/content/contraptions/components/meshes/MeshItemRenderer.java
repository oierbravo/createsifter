package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MeshItemRenderer extends CustomRenderedItemModelRenderer {
	@Override
	protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer,
						  ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		LocalPlayer player = Minecraft.getInstance().player;
		float partialTicks = AnimationTickHolder.getPartialTicks();

		boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
		boolean firstPerson = leftHand || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;

		CompoundTag tag = stack.getOrCreateTag();
		boolean jeiMode = tag.contains("JEI");

		ms.pushPose();

		if (tag.contains("Sifting")) {
			ms.pushPose();

			if (transformType == ItemDisplayContext.GUI) {
				ms.translate(0.0F, .2f, 1.0F);
				ms.scale(.75f, .75f, .75f);
			} else {
				int modifier = leftHand ? -1 : 1;
				ms.mulPose(Axis.YP.rotationDegrees(modifier * 40));
			}

			// Reverse bobbing
			float time = (float) (!jeiMode ? player.getUseItemRemainingTicks()
					: (-AnimationTickHolder.getTicks()) % stack.getUseDuration()) - partialTicks + 1.0F;
			if (time / (float) stack.getUseDuration() < 0.8F) {
				float bobbing = -Mth.abs(Mth.cos(time / 4.0F * (float) Math.PI) * 0.1F);

				if (transformType == ItemDisplayContext.GUI)
					ms.translate(bobbing, bobbing, 0.0F);
				else
					ms.translate(0.0f, bobbing, 0.0F);
			}

			ItemStack toSift = ItemStack.of(tag.getCompound("Sifting"));
			itemRenderer.renderStatic(toSift, ItemDisplayContext.NONE, light, overlay, ms, buffer, player.level(), 0);


			ms.popPose();
		}

		if (firstPerson) {
			int itemInUseCount = player.getUseItemRemainingTicks();
			if (itemInUseCount > 0) {
				int modifier = leftHand ? -1 : 1;
				ms.translate(0, 0, 0);
				ms.mulPose(Axis.ZP.rotationDegrees(modifier * 1));
				ms.mulPose(Axis.XP.rotationDegrees(modifier * 1));
				ms.mulPose(Axis.YP.rotationDegrees(modifier * 1));
			}
		}


		itemRenderer.render(stack, ItemDisplayContext.NONE, false, ms, buffer, light, overlay, model.getOriginalModel());

		ms.popPose();
	}
	public static class CreateSifterCustomRenderedItemModel extends CustomRenderedItemModel {

		public CreateSifterCustomRenderedItemModel(BakedModel template, String basePath) {
			super(template);
		}

	}
}
