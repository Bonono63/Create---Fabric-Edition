package com.mrbonono63.create.foundation.worldgen;

import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.Random;

import com.mrbonono63.create.content.curiosities.tools.SandPaperItem;
import com.mrbonono63.create.foundation.utility.BlockHelper;
import com.mrbonono63.create.foundation.utility.Iterate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class OxidizingBlock extends Block {

	public static final IntProperty OXIDIZATION = IntProperty.of("oxidization", 0, 7);
	private float chance;

	public OxidizingBlock(Settings properties, float chance) {
		super(properties);
		this.chance = chance;
		setDefaultState(getDefaultState().with(OXIDIZATION, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.add(OXIDIZATION));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return super.hasRandomTicks(state) || state.get(OXIDIZATION) < 7;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld server, BlockPos pos, Random random) {
		if (server.getRandom()
			.nextFloat() <= chance) {
			int currentState = state.get(OXIDIZATION);
			boolean canIncrease = false;
			LinkedList<Integer> neighbors = new LinkedList<>();
			for (Direction facing : Iterate.directions) {
				BlockPos neighbourPos = pos.offset(facing);
				BlockPos asd = pos.add(0,0,0);
				if (!server.isRegionLoaded(neighbourPos, asd))
					continue;
				if (!server.canSetBlock(neighbourPos))
					continue;
				BlockState neighborState = server.getBlockState(neighbourPos);
				if (neighborState.contains(OXIDIZATION) && neighborState.get(OXIDIZATION) != 0) {
					neighbors.add(neighborState.get(OXIDIZATION));
				}
				if (BlockHelper.hasBlockSolidSide(neighborState, server, neighbourPos, facing.getOpposite())) {
					continue;
				}
				canIncrease = true;
			}
			if (canIncrease) {
				OptionalDouble average = neighbors.stream()
					.mapToInt(v -> v)
					.average();
				if (average.orElse(7d) >= currentState)
					server.setBlockState(pos, state.with(OXIDIZATION, Math.min(currentState + 1, 7)));
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
		BlockHitResult blockRayTraceResult) {
		if (state.get(OXIDIZATION) > 0 && player.getStackInHand(hand)
			.getItem() instanceof SandPaperItem) {
			if (!player.isCreative())
				player.getStackInHand(hand)
					.damage(1, player, p -> p.sendToolBreakStatus(p.getActiveHand()));
			world.setBlockState(pos, state.with(OXIDIZATION, 0));
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
}
