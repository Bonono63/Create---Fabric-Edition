package com.mrbonono63.create.content.logistics.block.depot;

import java.util.function.Supplier;

import com.mrbonono63.create.foundation.networking.SimplePacketBase;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class EjectorElytraPacket extends SimplePacketBase {

	private BlockPos pos;

	public EjectorElytraPacket(BlockPos pos) {
		this.pos = pos;
	}

	public EjectorElytraPacket(PacketBuffer buffer) {
		pos = buffer.readBlockPos();
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeBlockPos(pos);
	}

	@Override
	public void handle(Supplier<Context> context) {
		context.get()
			.enqueueWork(() -> {
				ServerPlayerEntity player = context.get()
					.getSender();
				if (player == null)
					return;
				World world = player.world;
				if (world == null || !world.isBlockPresent(pos))
					return;
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity instanceof EjectorTileEntity)
					((EjectorTileEntity) tileEntity).deployElytra(player);
			});
		context.get()
			.setPacketHandled(true);

	}

}
