package com.mrbonono63.create.content.contraptions.fluids.actors;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.components.structureMovement.pulley.AbstractPulleyRenderer;
import com.mrbonono63.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction.Axis;

public class HosePulleyRenderer extends AbstractPulleyRenderer {

	public HosePulleyRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher, AllBlockPartials.HOSE_HALF, AllBlockPartials.HOSE_HALF_MAGNET);
	}

	@Override
	protected Axis getShaftAxis(KineticTileEntity te) {
		return te.getBlockState()
			.get(HosePulleyBlock.HORIZONTAL_FACING)
			.rotateY()
			.getAxis();
	}

	@Override
	protected AllBlockPartials getCoil() {
		return AllBlockPartials.HOSE_COIL;
	}

	@Override
	protected SuperByteBuffer renderRope(KineticTileEntity te) {
		return AllBlockPartials.HOSE.renderOn(te.getBlockState());
	}

	@Override
	protected SuperByteBuffer renderMagnet(KineticTileEntity te) {
		return AllBlockPartials.HOSE_MAGNET.renderOn(te.getBlockState());
	}

	@Override
	protected float getOffset(KineticTileEntity te, float partialTicks) {
		return ((HosePulleyTileEntity) te).getInterpolatedOffset(partialTicks);
	}

	@Override
	protected boolean isRunning(KineticTileEntity te) {
		return true;
	}

}
