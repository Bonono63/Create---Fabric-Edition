package com.mrbonono63.create.content.contraptions.components.structureMovement.bearing;

import com.simibubi.create.AllTileEntities;
import com.mrbonono63.create.foundation.block.ITE;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MechanicalBearingBlock extends BearingBlock implements ITE<MechanicalBearingTileEntity> {

	public MechanicalBearingBlock(Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return AllTileEntities.MECHANICAL_BEARING.create();
	}

	@Override
	public ActionResultType onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
		BlockRayTraceResult hit) {
		if (!player.isAllowEdit())
			return ActionResultType.FAIL;
		if (player.isSneaking())
			return ActionResultType.FAIL;
		if (player.getHeldItem(handIn)
			.isEmpty()) {
			if (worldIn.isRemote)
				return ActionResultType.SUCCESS;
			withTileEntityDo(worldIn, pos, te -> {
				if (te.running) {
					te.disassemble();
					return;
				}
				te.assembleNextTick = true;
			});
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public Class<MechanicalBearingTileEntity> getTileEntityClass() {
		return MechanicalBearingTileEntity.class;
	}

}
