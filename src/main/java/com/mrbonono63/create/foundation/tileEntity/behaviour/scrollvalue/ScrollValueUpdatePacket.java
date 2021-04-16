package com.mrbonono63.create.foundation.tileEntity.behaviour.scrollvalue;

import com.mrbonono63.create.foundation.networking.TileEntityConfigurationPacket;
import com.mrbonono63.create.foundation.tileEntity.SmartTileEntity;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class ScrollValueUpdatePacket extends TileEntityConfigurationPacket<SmartTileEntity> {

	int value;
	
	public ScrollValueUpdatePacket(PacketBuffer buffer) {
		super(buffer);
	}
	
	public ScrollValueUpdatePacket(BlockPos pos, int amount) {
		super(pos);
		this.value = amount;
	}

	@Override
	protected void writeSettings(PacketBuffer buffer) {
		buffer.writeInt(value);
	}

	@Override
	protected void readSettings(PacketBuffer buffer) {
		value = buffer.readInt();
	}

	@Override
	protected void applySettings(SmartTileEntity te) {
		ScrollValueBehaviour behaviour = te.getBehaviour(ScrollValueBehaviour.TYPE);
		if (behaviour == null)
			return;
		behaviour.setValue(value);
	}

}
