package com.mrbonono63.create.content.curiosities.tools;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.foundation.block.render.CustomRenderedItemModelRenderer;
import com.mrbonono63.create.foundation.item.PartialItemModelRenderer;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class DeforesterItemRenderer extends CustomRenderedItemModelRenderer<DeforesterModel> {

	@Override
	protected void render(ItemStack stack, DeforesterModel model, PartialItemModelRenderer renderer,
		MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {
		int maxLight = 0xF000F0;
		float worldTime = AnimationTickHolder.getRenderTime();
		
		renderer.renderSolid(model.getBakedModel(), light);
		renderer.renderSolidGlowing(model.getPartial("core"), maxLight);
		renderer.renderGlowing(model.getPartial("core_glow"), maxLight);
		
		float angle = worldTime * -.5f % 360;
		ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(angle));
		renderer.renderSolid(model.getPartial("gear"), light);
	}
	

}
