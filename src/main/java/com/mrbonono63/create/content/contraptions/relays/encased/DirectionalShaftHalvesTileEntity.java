package com.mrbonono63.create.content.contraptions.relays.encased;

import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class DirectionalShaftHalvesTileEntity extends KineticTileEntity {

	public DirectionalShaftHalvesTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
	}

	public Direction getSourceFacing() {
		BlockPos localSource = source.subtract(getPos());
		return Direction.getFacingFromVector(localSource.getX(), localSource.getY(), localSource.getZ());
	}

}
