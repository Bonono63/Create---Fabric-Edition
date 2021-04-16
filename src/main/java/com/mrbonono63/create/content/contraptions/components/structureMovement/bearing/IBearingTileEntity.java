package com.mrbonono63.create.content.contraptions.components.structureMovement.bearing;

import com.mrbonono63.create.content.contraptions.components.structureMovement.DirectionalExtenderScrollOptionSlot;
import com.mrbonono63.create.content.contraptions.components.structureMovement.IControlContraption;
import com.mrbonono63.create.foundation.tileEntity.behaviour.ValueBoxTransform;

import net.minecraft.util.Direction.Axis;

public interface IBearingTileEntity extends IControlContraption {

	float getInterpolatedAngle(float partialTicks);

	boolean isWoodenTop();

	default ValueBoxTransform getMovementModeSlot() {
		return new DirectionalExtenderScrollOptionSlot((state, d) -> {
			Axis axis = d.getAxis();
			Axis bearingAxis = state.get(BearingBlock.FACING)
				.getAxis();
			return bearingAxis != axis;
		});
	}
	
	void setAngle(float forcedAngle);

}
