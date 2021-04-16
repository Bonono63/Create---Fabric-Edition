package com.mrbonono63.create.content.curiosities.zapper.terrainzapper;

import java.util.List;
import java.util.stream.Collectors;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;
import com.mrbonono63.create.foundation.utility.NBTHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;

public class WorldshaperRenderHandler {

	private static List<BlockPos> renderedShape;
	private static BlockPos renderedPosition;

	public static void tick() {
		gatherSelectedBlocks();
		if (renderedPosition == null)
			return;

		CreateClient.outliner.showCluster("terrainZapper", renderedShape.stream()
			.map(pos -> pos.add(renderedPosition))
			.collect(Collectors.toList()))
			.colored(0xbfbfbf)
			.lineWidth(1 / 32f)
			.withFaceTexture(AllSpecialTextures.CHECKERED);
	}

	protected static void gatherSelectedBlocks() {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		ItemStack heldMain = player.getHeldItemMainhand();
		ItemStack heldOff = player.getHeldItemOffhand();
		boolean zapperInMain = AllItems.WORLDSHAPER.isIn(heldMain);
		boolean zapperInOff = AllItems.WORLDSHAPER.isIn(heldOff);

		if (zapperInMain) {
			CompoundNBT tag = heldMain.getOrCreateTag();
			if (!tag.contains("_Swap") || !zapperInOff) {
				createBrushOutline(tag, player, heldMain);
				return;
			}
		}

		if (zapperInOff) {
			CompoundNBT tag = heldOff.getOrCreateTag();
			createBrushOutline(tag, player, heldOff);
			return;
		}

		renderedPosition = null;
	}

	public static void createBrushOutline(CompoundNBT tag, ClientPlayerEntity player, ItemStack zapper) {
		if (!tag.contains("BrushParams")) {
			renderedPosition = null;
			return;
		}

		Brush brush = NBTHelper.readEnum(tag, "Brush", TerrainBrushes.class)
			.get();
		PlacementOptions placement = NBTHelper.readEnum(tag, "Placement", PlacementOptions.class);
		BlockPos params = NBTUtil.readBlockPos(tag.getCompound("BrushParams"));
		brush.set(params.getX(), params.getY(), params.getZ());
		renderedShape = brush.getIncludedPositions();

		Vector3d start = player.getPositionVec()
			.add(0, player.getEyeHeight(), 0);
		Vector3d range = player.getLookVec()
			.scale(128);
		BlockRayTraceResult raytrace = player.world
			.rayTraceBlocks(new RayTraceContext(start, start.add(range), BlockMode.OUTLINE, FluidMode.NONE, player));
		if (raytrace == null || raytrace.getType() == Type.MISS) {
			renderedPosition = null;
			return;
		}

		BlockPos pos = raytrace.getPos();
		renderedPosition = pos.add(brush.getOffset(player.getLookVec(), raytrace.getFace(), placement));
	}

}
