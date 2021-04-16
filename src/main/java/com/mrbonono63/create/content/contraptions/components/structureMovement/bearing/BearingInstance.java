package com.mrbonono63.create.content.contraptions.components.structureMovement.bearing;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.BackHalfShaftInstance;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.foundation.render.backend.core.OrientedData;
import com.mrbonono63.create.foundation.render.backend.instancing.IDynamicInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.utility.AngleHelper;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;

import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class BearingInstance<B extends KineticTileEntity & IBearingTileEntity> extends BackHalfShaftInstance implements IDynamicInstance {
	final B bearing;

	final OrientedData topInstance;

	final Vector3f rotationAxis;
	final Quaternion blockOrientation;

	public BearingInstance(InstancedTileRenderer<?> modelManager, B tile) {
		super(modelManager, tile);
		this.bearing = tile;

		Direction facing = blockState.get(BlockStateProperties.FACING);
		rotationAxis = Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis).getUnitVector();

		blockOrientation = getBlockStateOrientation(facing);

		AllBlockPartials top =
				bearing.isWoodenTop() ? AllBlockPartials.BEARING_TOP_WOODEN : AllBlockPartials.BEARING_TOP;

		topInstance = getOrientedMaterial().getModel(top, blockState).createInstance();

		topInstance.setPosition(getInstancePosition()).setRotation(blockOrientation);
	}

	@Override
	public void beginFrame() {

		float interpolatedAngle = bearing.getInterpolatedAngle(AnimationTickHolder.getPartialTicks() - 1);
		Quaternion rot = rotationAxis.getDegreesQuaternion(interpolatedAngle);

		rot.multiply(blockOrientation);

		topInstance.setRotation(rot);
	}

	@Override
	public void updateLight() {
		super.updateLight();
		relight(pos, topInstance);
	}

	@Override
	public void remove() {
		super.remove();
		topInstance.delete();
	}

	static Quaternion getBlockStateOrientation(Direction facing) {
		Quaternion orientation;

		if (facing.getAxis().isHorizontal()) {
			orientation = Vector3f.POSITIVE_Y.getDegreesQuaternion(AngleHelper.horizontalAngle(facing.getOpposite()));
		} else {
			orientation = Quaternion.IDENTITY.copy();
		}

		orientation.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90 - AngleHelper.verticalAngle(facing)));
		return orientation;
	}
}
