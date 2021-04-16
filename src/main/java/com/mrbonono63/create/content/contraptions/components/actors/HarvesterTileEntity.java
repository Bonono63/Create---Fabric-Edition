package com.mrbonono63.create.content.contraptions.components.actors;

import com.mrbonono63.create.foundation.tileEntity.SyncedTileEntity;

import net.minecraft.tileentity.TileEntityType;

public class HarvesterTileEntity extends SyncedTileEntity {

	public HarvesterTileEntity(TileEntityType<? extends HarvesterTileEntity> type) {
		super(type);
	}

	// For simulations such as Ponder
	float manuallyAnimatedSpeed;

	public void setAnimatedSpeed(float speed) {
		manuallyAnimatedSpeed = speed;
	}

}
