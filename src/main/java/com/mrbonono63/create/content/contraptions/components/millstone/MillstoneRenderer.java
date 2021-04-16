package com.mrbonono63.create.content.contraptions.components.millstone;

import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.CreateClient;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntityRenderer;
import com.mrbonono63.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class MillstoneRenderer extends KineticTileEntityRenderer {

	public MillstoneRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected SuperByteBuffer getRotatedModel(KineticTileEntity te) {
		return CreateClient.bufferCache.renderPartial(AllBlockPartials.MILLSTONE_COG, te.getBlockState());
	}
	
}
