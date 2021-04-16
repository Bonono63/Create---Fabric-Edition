package com.mrbonono63.create.content.contraptions.components.structureMovement.pulley;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.fluids.actors.HosePulleyTileEntity;
import com.mrbonono63.create.foundation.render.backend.core.OrientedData;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;

public class HosePulleyInstance extends AbstractPulleyInstance {
	final HosePulleyTileEntity tile = (HosePulleyTileEntity) super.tile;

	public HosePulleyInstance(InstancedTileRenderer<?> dispatcher, HosePulleyTileEntity tile) {
		super(dispatcher, tile);
		beginFrame();
	}

	protected InstancedModel<OrientedData> getRopeModel() {
		return getOrientedMaterial().getModel(AllBlockPartials.HOSE, blockState);
	}

	protected InstancedModel<OrientedData> getMagnetModel() {
		return getOrientedMaterial().getModel(AllBlockPartials.HOSE_MAGNET, blockState);
	}

	protected InstancedModel<OrientedData> getHalfMagnetModel() {
		return getOrientedMaterial().getModel(AllBlockPartials.HOSE_HALF_MAGNET, blockState);
	}

	protected InstancedModel<OrientedData> getCoilModel() {
		return AllBlockPartials.HOSE_COIL.getModel(getOrientedMaterial(), blockState, rotatingAbout);
	}

	protected InstancedModel<OrientedData> getHalfRopeModel() {
		return getOrientedMaterial().getModel(AllBlockPartials.HOSE_HALF, blockState);
	}

	protected float getOffset() {
		return tile.getInterpolatedOffset(AnimationTickHolder.getPartialTicks());
	}

	protected boolean isRunning() {
		return true;
	}
}
