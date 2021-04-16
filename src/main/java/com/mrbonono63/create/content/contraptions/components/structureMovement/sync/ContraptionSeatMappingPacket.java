package com.mrbonono63.create.content.contraptions.components.structureMovement.sync;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.mrbonono63.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.mrbonono63.create.foundation.networking.SimplePacketBase;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ContraptionSeatMappingPacket extends SimplePacketBase {

	private Map<UUID, Integer> mapping;
	private int entityID;

	public ContraptionSeatMappingPacket(int entityID, Map<UUID, Integer> mapping) {
		this.entityID = entityID;
		this.mapping = mapping;
	}

	public ContraptionSeatMappingPacket(PacketBuffer buffer) {
		entityID = buffer.readInt();
		mapping = new HashMap<>();
		short size = buffer.readShort();
		for (int i = 0; i < size; i++)
			mapping.put(buffer.readUniqueId(), (int) buffer.readShort());
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(entityID);
		buffer.writeShort(mapping.size());
		mapping.forEach((k, v) -> {
			buffer.writeUniqueId(k);
			buffer.writeShort(v);
		});
	}

	@Override
	public void handle(Supplier<Context> context) {
		context.get()
			.enqueueWork(() -> {
				Entity entityByID = Minecraft.getInstance().world.getEntityByID(entityID);
				if (!(entityByID instanceof AbstractContraptionEntity))
					return;
				AbstractContraptionEntity contraptionEntity = (AbstractContraptionEntity) entityByID;
				contraptionEntity.getContraption()
					.setSeatMapping(mapping);
			});
		context.get()
			.setPacketHandled(true);
	}

}
