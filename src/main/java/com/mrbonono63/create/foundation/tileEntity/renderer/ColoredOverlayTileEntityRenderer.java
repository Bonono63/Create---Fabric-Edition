package com.mrbonono63.create.foundation.tileEntity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.foundation.render.SuperByteBuffer;
import com.mrbonono63.create.foundation.render.backend.FastRenderDispatcher;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;

public abstract class ColoredOverlayTileEntityRenderer<T extends TileEntity> extends SafeTileEntityRenderer<T> {

	public ColoredOverlayTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected void renderSafe(T te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer,
			int light, int overlay) {

		if (FastRenderDispatcher.available(te.getWorld())) return;

		SuperByteBuffer render = render(getOverlayBuffer(te), getColor(te, partialTicks), light);
		render.renderInto(ms, buffer.getBuffer(RenderType.getSolid()));
	}

	protected abstract int getColor(T te, float partialTicks);

	protected abstract SuperByteBuffer getOverlayBuffer(T te);

	public static SuperByteBuffer render(SuperByteBuffer buffer, int color, int light) {
		return buffer.color(color).light(light);
	}

}
