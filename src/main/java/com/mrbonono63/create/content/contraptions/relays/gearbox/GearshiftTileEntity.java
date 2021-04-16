package com.mrbonono63.create.content.contraptions.relays.gearbox;

import com.mrbonono63.create.content.contraptions.relays.encased.SplitShaftTileEntity;

import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;

public class GearshiftTileEntity extends SplitShaftTileEntity {

	public GearshiftTileEntity(TileEntityType<? extends GearshiftTileEntity> type) {
		super(type);
	}

	@Override
	public float getRotationSpeedModifier(Direction face) {
		if (hasSource()) {
			if (face != getSourceFacing() && getBlockState().get(BlockStateProperties.POWERED))
				return -1;
		}
		return 1;
	}
	
}
