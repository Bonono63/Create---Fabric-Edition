package com.mrbonono63.create.content.logistics.block.inventories;

import java.util.List;

import com.mrbonono63.create.foundation.tileEntity.SmartTileEntity;
import com.mrbonono63.create.foundation.tileEntity.TileEntityBehaviour;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.AxisDirection;

public abstract class CrateTileEntity extends SmartTileEntity {

	public CrateTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {}

	public boolean isDoubleCrate() {
		return getBlockState().get(AdjustableCrateBlock.DOUBLE);
	}

	public boolean isSecondaryCrate() {
		if (!hasWorld())
			return false;
		if (!(getBlockState().getBlock() instanceof CrateBlock))
			return false;
		return isDoubleCrate() && getFacing().getAxisDirection() == AxisDirection.NEGATIVE;
	}
	
	public Direction getFacing() {
		return getBlockState().get(AdjustableCrateBlock.FACING);
	}

}
