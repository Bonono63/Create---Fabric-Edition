package com.mrbonono63.create.content.contraptions.components.structureMovement.bearing;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.mrbonono63.create.content.contraptions.components.structureMovement.ControlledContraptionEntity;
import com.mrbonono63.create.content.contraptions.components.structureMovement.MovementBehaviour;
import com.mrbonono63.create.content.contraptions.components.structureMovement.MovementContext;
import com.mrbonono63.create.content.contraptions.components.structureMovement.OrientedContraptionEntity;
import com.mrbonono63.create.content.contraptions.components.structureMovement.render.ActorInstance;
import com.mrbonono63.create.content.contraptions.components.structureMovement.render.ContraptionKineticRenderer;
import com.mrbonono63.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import com.mrbonono63.create.foundation.render.SuperByteBuffer;
import com.mrbonono63.create.foundation.render.backend.FastRenderDispatcher;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StabilizedBearingMovementBehaviour extends MovementBehaviour {

	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderInContraption(MovementContext context, MatrixStack ms, MatrixStack msLocal,
		IRenderTypeBuffer buffer) {
		if (FastRenderDispatcher.available()) return;

		Direction facing = context.state.get(BlockStateProperties.FACING);
		AllBlockPartials top = AllBlockPartials.BEARING_TOP;
		SuperByteBuffer superBuffer = top.renderOn(context.state);
		float renderPartialTicks = AnimationTickHolder.getPartialTicks();

		// rotate to match blockstate
		Quaternion orientation = BearingInstance.getBlockStateOrientation(facing);

		// rotate against parent
		float angle = getCounterRotationAngle(context, facing, renderPartialTicks) * facing.getAxisDirection().getOffset();

		Quaternion rotation = facing.getUnitVector().getDegreesQuaternion(angle);

		rotation.multiply(orientation);

		orientation = rotation;

		superBuffer.rotateCentered(orientation);

		// render
		superBuffer.light(msLocal.peek()
			.getModel(), ContraptionRenderDispatcher.getLightOnContraption(context));
		superBuffer.renderInto(ms, buffer.getBuffer(RenderType.getSolid()));
	}

	@Override
	public boolean hasSpecialInstancedRendering() {
		return true;
	}

	@Nullable
	@Override
	public ActorInstance createInstance(ContraptionKineticRenderer kr, MovementContext context) {
		return new StabilizedBearingInstance(kr, context);
	}

	static float getCounterRotationAngle(MovementContext context, Direction facing, float renderPartialTicks) {
		float offset = 0;

		Axis axis = facing.getAxis();

		AbstractContraptionEntity entity = context.contraption.entity;
		if (entity instanceof ControlledContraptionEntity) {
			ControlledContraptionEntity controlledCE = (ControlledContraptionEntity) entity;
			if (context.contraption.canBeStabilized(facing, context.localPos))
				offset = -controlledCE.getAngle(renderPartialTicks);

		} else if (entity instanceof OrientedContraptionEntity) {
			OrientedContraptionEntity orientedCE = (OrientedContraptionEntity) entity;
			if (axis.isVertical())
				offset = -orientedCE.getYaw(renderPartialTicks);
			else {
				if (orientedCE.isInitialOrientationPresent() && orientedCE.getInitialOrientation()
						.getAxis() == axis)
					offset = -orientedCE.getPitch(renderPartialTicks);
			}
		}
		return offset;
	}


}
