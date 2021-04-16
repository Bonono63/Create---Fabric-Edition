package com.mrbonono63.create.content.logistics.packet;

import com.mrbonono63.create.content.logistics.block.inventories.AdjustableCrateTileEntity;
import com.mrbonono63.create.foundation.networking.TileEntityConfigurationPacket;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class ConfigureFlexcratePacket extends TileEntityConfigurationPacket<AdjustableCrateTileEntity> {

	private int maxItems;
	
	public ConfigureFlexcratePacket(PacketBuffer buffer) {
		super(buffer);
	}
	
	public ConfigureFlexcratePacket(BlockPos pos, int newMaxItems) {
		super(pos);
		this.maxItems = newMaxItems;
	}

	@Override
	protected void writeSettings(PacketBuffer buffer) {
		buffer.writeInt(maxItems);
	}

	@Override
	protected void readSettings(PacketBuffer buffer) {
		maxItems = buffer.readInt();
	}

	@Override
	protected void applySettings(AdjustableCrateTileEntity te) {
		te.allowedAmount = maxItems;
	}

}
