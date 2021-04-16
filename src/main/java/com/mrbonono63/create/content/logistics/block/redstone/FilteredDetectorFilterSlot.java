package com.mrbonono63.create.content.logistics.block.redstone;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.foundation.tileEntity.behaviour.ValueBoxTransform;
import com.mrbonono63.create.foundation.utility.AngleHelper;
import com.mrbonono63.create.foundation.utility.MatrixStacker;
import com.mrbonono63.create.foundation.utility.VecHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.math.vector.Vector3d;

public class FilteredDetectorFilterSlot extends ValueBoxTransform {
	Vector3d position = VecHelper.voxelSpace(8f, 15.5f, 11f);

	@Override
	protected Vector3d getLocalOffset(BlockState state) {
		return rotateHorizontally(state, position);
	}

	@Override
	protected void rotate(BlockState state, MatrixStack ms) {
		float yRot = AngleHelper.horizontalAngle(state.get(HorizontalBlock.HORIZONTAL_FACING)) + 180;
		MatrixStacker.of(ms)
			.rotateY(yRot)
			.rotateX(90);
	}

}
