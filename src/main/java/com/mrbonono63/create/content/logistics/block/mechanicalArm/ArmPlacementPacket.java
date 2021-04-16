package com.mrbonono63.create.content.logistics.block.mechanicalArm;

import java.util.Collection;
import java.util.function.Supplier;

import com.mrbonono63.create.foundation.networking.SimplePacketBase;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ArmPlacementPacket extends SimplePacketBase {

	private Collection<ArmInteractionPoint> points;
	private ListNBT receivedTag;
	private BlockPos pos;

	public ArmPlacementPacket(Collection<ArmInteractionPoint> points, BlockPos pos) {
		this.points = points;
		this.pos = pos;
	}

	public ArmPlacementPacket(PacketBuffer buffer) {
		CompoundNBT nbt = buffer.readCompoundTag();
		receivedTag = nbt.getList("Points", NBT.TAG_COMPOUND);
		pos = buffer.readBlockPos();
	}

	@Override
	public void write(PacketBuffer buffer) {
		CompoundNBT nbt = new CompoundNBT();
		ListNBT pointsNBT = new ListNBT();
		points.stream()
			.map(aip -> aip.serialize(pos))
			.forEach(pointsNBT::add);
		nbt.put("Points", pointsNBT);
		buffer.writeCompoundTag(nbt);
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
				if (!(tileEntity instanceof ArmTileEntity))
					return;

				ArmTileEntity arm = (ArmTileEntity) tileEntity;
				arm.interactionPointTag = receivedTag;
			});
		context.get()
			.setPacketHandled(true);

	}

}
