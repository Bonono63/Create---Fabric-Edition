package com.mrbonono63.create.content.contraptions.components.fan;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntityRenderer;
import com.mrbonono63.create.foundation.render.SuperByteBuffer;
import com.mrbonono63.create.foundation.render.backend.FastRenderDispatcher;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;

public class EncasedFanRenderer extends KineticTileEntityRenderer {

	public EncasedFanRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected void renderSafe(KineticTileEntity te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer,
		int light, int overlay) {
		if (FastRenderDispatcher.available(te.getWorld())) return;

		Direction direction = te.getBlockState()
			.get(FACING);
		IVertexBuilder vb = buffer.getBuffer(RenderType.getCutoutMipped());

		int lightBehind = WorldRenderer.getLightmapCoordinates(te.getWorld(), te.getPos().offset(direction.getOpposite()));
		int lightInFront = WorldRenderer.getLightmapCoordinates(te.getWorld(), te.getPos().offset(direction));
		
		SuperByteBuffer shaftHalf =
			AllBlockPartials.SHAFT_HALF.renderOnDirectionalSouth(te.getBlockState(), direction.getOpposite());
		SuperByteBuffer fanInner =
			AllBlockPartials.ENCASED_FAN_INNER.renderOnDirectionalSouth(te.getBlockState(), direction.getOpposite());
		
		float time = AnimationTickHolder.getRenderTime(te.getWorld());
		float speed = te.getSpeed() * 5;
		if (speed > 0)
			speed = MathHelper.clamp(speed, 80, 64 * 20);
		if (speed < 0)
			speed = MathHelper.clamp(speed, -64 * 20, -80);
		float angle = (time * speed * 3 / 10f) % 360;
		angle = angle / 180f * (float) Math.PI;

		standardKineticRotationTransform(shaftHalf, te, lightBehind).renderInto(ms, vb);
		kineticRotationTransform(fanInner, te, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
	}

}
