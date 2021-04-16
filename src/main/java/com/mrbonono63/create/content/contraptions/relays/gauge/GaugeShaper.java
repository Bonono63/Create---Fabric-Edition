package com.mrbonono63.create.content.contraptions.relays.gauge;

import java.util.Arrays;

import com.simibubi.create.AllShapes;
import com.mrbonono63.create.foundation.utility.VoxelShaper;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;

public class GaugeShaper extends VoxelShaper {

	private VoxelShaper axisFalse, axisTrue;

	static GaugeShaper make(){
		GaugeShaper shaper = new GaugeShaper();
		shaper.axisFalse = forDirectional(AllShapes.GAUGE_SHAPE_UP, Direction.UP);
		shaper.axisTrue = forDirectional(rotatedCopy(AllShapes.GAUGE_SHAPE_UP, new Vector3d(0, 90, 0)), Direction.UP);
		//shapes for X axis need to be swapped
		Arrays.asList(Direction.EAST, Direction.WEST).forEach(direction -> {
			VoxelShape mem = shaper.axisFalse.get(direction);
			shaper.axisFalse.withShape(shaper.axisTrue.get(direction), direction);
			shaper.axisTrue.withShape(mem, direction);
		});
		return shaper;
	}

	public VoxelShape get(Direction direction, boolean axisAlong) {
		return (axisAlong ? axisTrue : axisFalse).get(direction);
	}
}