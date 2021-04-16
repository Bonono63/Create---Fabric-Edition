package com.mrbonono63.create.content.contraptions.components.turntable;

import com.simibubi.create.AllBlocks;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;
import com.mrbonono63.create.foundation.utility.VecHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class TurntableHandler {

	public static void gameRenderTick() {
		Minecraft mc = Minecraft.getInstance();
		BlockPos pos = mc.player.getBlockPos();

		if (!AllBlocks.TURNTABLE.has(mc.world.getBlockState(pos)))
			return;
		if (!mc.player.isOnGround())
			return;
		if (mc.isGamePaused())
			return;

		TileEntity tileEntity = mc.world.getTileEntity(pos);
		if (!(tileEntity instanceof TurntableTileEntity))
			return;
		
		TurntableTileEntity turnTable = (TurntableTileEntity) tileEntity;
		float speed = turnTable.getSpeed() * 3/10;

		if (speed == 0)
			return;
		
		Vector3d origin = VecHelper.getCenterOf(pos);
		Vector3d offset = mc.player.getPositionVec().subtract(origin);
		
		if (offset.length() > 1/4f)
			speed *= MathHelper.clamp((1/2f - offset.length()) * 2, 0, 1);

		mc.player.rotationYaw = mc.player.prevRotationYaw - speed * AnimationTickHolder.getPartialTicks();
		mc.player.renderYawOffset = mc.player.rotationYaw;
	}

}
