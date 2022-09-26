package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.foundation.item.render.CreateCustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class MeshItemRenderer extends CustomRenderedItemModelRenderer<MeshItemRenderer.MeshModel> {

	@Override
	protected void render(ItemStack stack, MeshModel model, PartialItemModelRenderer renderer,
		TransformType transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		LocalPlayer player = Minecraft.getInstance().player;
		float partialTicks = AnimationTickHolder.getPartialTicks();

		boolean leftHand = transformType == TransformType.FIRST_PERSON_LEFT_HAND;
		boolean firstPerson = leftHand || transformType == TransformType.FIRST_PERSON_RIGHT_HAND;

		CompoundTag tag = stack.getOrCreateTag();
		boolean jeiMode = tag.contains("JEI");

		ms.pushPose();

		if (tag.contains("Sifting")) {
			ms.pushPose();

			if (transformType == TransformType.GUI) {
				ms.translate(0.0F, .2f, 1.0F);
				ms.scale(.75f, .75f, .75f);
			} else {
				int modifier = leftHand ? -1 : 1;
				//ms.mulPose(Vector3f.YP.rotationDegrees(modifier * 20));
				//ms.mulPose(Vector3f.ZP.rotationDegrees(modifier * 20));
				ms.mulPose(Vector3f.YP.rotationDegrees(modifier * 40));
			}

			// Reverse bobbing
			float time = (float) (!jeiMode ? player.getUseItemRemainingTicks()
					: (-AnimationTickHolder.getTicks()) % stack.getUseDuration()) - partialTicks + 1.0F;
			if (time / (float) stack.getUseDuration() < 0.8F) {
				float bobbing = -Mth.abs(Mth.cos(time / 4.0F * (float) Math.PI) * 0.1F);

				if (transformType == TransformType.GUI)
					ms.translate(bobbing, bobbing, 0.0F);
				else
					ms.translate(0.0f, bobbing, 0.0F);
			}

			ItemStack toSift = ItemStack.of(tag.getCompound("Sifting"));
			itemRenderer.renderStatic(toSift, TransformType.NONE, light, overlay, ms, buffer, 0);

			ms.popPose();
		}

		if (firstPerson) {
			int itemInUseCount = player.getUseItemRemainingTicks();
			if (itemInUseCount > 0) {
				int modifier = leftHand ? -1 : 1;
				//int modifier = 0;
				ms.translate(0, 0, 0);
				//ms.translate(0, 0, 0);
				//ms.translate(modifier * .5f, 0, -.25f);
				//ms.mulPose(Vector3f.ZP.rotationDegrees(modifier * 40));
				//ms.mulPose(Vector3f.XP.rotationDegrees(modifier * 10));
				//ms.mulPose(Vector3f.YP.rotationDegrees(modifier * 90));
				ms.mulPose(Vector3f.ZP.rotationDegrees(modifier * 1));
				ms.mulPose(Vector3f.XP.rotationDegrees(modifier * 1));
				ms.mulPose(Vector3f.YP.rotationDegrees(modifier * 1));
			}
		}

		itemRenderer.render(stack, TransformType.NONE, false, ms, buffer, light, overlay, model.getOriginalModel());

		ms.popPose();
	}

	@Override
	public MeshModel createModel(BakedModel originalModel) {
		return new MeshModel(originalModel);
	}

	public static class MeshModel extends CreateCustomRenderedItemModel {

		public MeshModel(BakedModel template) {
			super(template, "");
		}

	}

}
