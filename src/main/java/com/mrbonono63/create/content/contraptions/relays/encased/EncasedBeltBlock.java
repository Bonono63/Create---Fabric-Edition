package com.mrbonono63.create.content.contraptions.relays.encased;

import com.simibubi.create.AllTileEntities;
import com.mrbonono63.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.mrbonono63.create.foundation.utility.Iterate;
import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class EncasedBeltBlock extends RotatedPillarKineticBlock {

	public static final Property<Part> PART = EnumProperty.create("part", Part.class);
	public static final BooleanProperty CONNECTED_ALONG_FIRST_COORDINATE =
		DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;

	public EncasedBeltBlock(Properties properties) {
		super(properties);
		setDefaultState(getDefaultState().with(PART, Part.NONE));
	}

	@Override
	public boolean shouldCheckWeakPower(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
		return false;
	}

	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.NORMAL;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder.add(PART, CONNECTED_ALONG_FIRST_COORDINATE));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Axis placedAxis = context.getNearestLookingDirection()
			.getAxis();
		Axis axis = context.getPlayer() != null && context.getPlayer()
			.isSneaking() ? placedAxis : getPreferredAxis(context);
		if (axis == null)
			axis = placedAxis;

		BlockState state = getDefaultState().with(AXIS, axis);
		for (Direction facing : Iterate.directions) {
			if (facing.getAxis() == axis)
				continue;
			BlockPos pos = context.getPos();
			BlockPos offset = pos.offset(facing);
			state = updatePostPlacement(state, facing, context.getWorld()
				.getBlockState(offset), context.getWorld(), pos, offset);
		}
		return state;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction face, BlockState neighbour, IWorld worldIn,
		BlockPos currentPos, BlockPos facingPos) {
		Part part = stateIn.get(PART);
		Axis axis = stateIn.get(AXIS);
		boolean connectionAlongFirst = stateIn.get(CONNECTED_ALONG_FIRST_COORDINATE);
		Axis connectionAxis =
			connectionAlongFirst ? (axis == Axis.X ? Axis.Y : Axis.X) : (axis == Axis.Z ? Axis.Y : Axis.Z);

		Axis faceAxis = face.getAxis();
		boolean facingAlongFirst = axis == Axis.X ? faceAxis.isVertical() : faceAxis == Axis.X;
		boolean positive = face.getAxisDirection() == AxisDirection.POSITIVE;

		if (axis == faceAxis)
			return stateIn;

		if (!(neighbour.getBlock() instanceof EncasedBeltBlock)) {
			if (facingAlongFirst != connectionAlongFirst || part == Part.NONE)
				return stateIn;
			if (part == Part.MIDDLE)
				return stateIn.with(PART, positive ? Part.END : Part.START);
			if ((part == Part.START) == positive)
				return stateIn.with(PART, Part.NONE);
			return stateIn;
		}

		Part otherPart = neighbour.get(PART);
		Axis otherAxis = neighbour.get(AXIS);
		boolean otherConnection = neighbour.get(CONNECTED_ALONG_FIRST_COORDINATE);
		Axis otherConnectionAxis =
			otherConnection ? (otherAxis == Axis.X ? Axis.Y : Axis.X) : (otherAxis == Axis.Z ? Axis.Y : Axis.Z);

		if (neighbour.get(AXIS) == faceAxis)
			return stateIn;
		if (otherPart != Part.NONE && otherConnectionAxis != faceAxis)
			return stateIn;

		if (part == Part.NONE) {
			part = positive ? Part.START : Part.END;
			connectionAlongFirst = axis == Axis.X ? faceAxis.isVertical() : faceAxis == Axis.X;
		} else if (connectionAxis != faceAxis) {
			return stateIn;
		}

		if ((part == Part.START) != positive)
			part = Part.MIDDLE;

		return stateIn.with(PART, part)
			.with(CONNECTED_ALONG_FIRST_COORDINATE, connectionAlongFirst);
	}

	@Override
	public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
		if (originalState.get(PART) == Part.NONE)
			return super.getRotatedBlockState(originalState, targetedFace);
		return super.getRotatedBlockState(originalState,
			Direction.getFacingFromAxis(AxisDirection.POSITIVE, getConnectionAxis(originalState)));
	}

	@Override
	public BlockState updateAfterWrenched(BlockState newState, ItemUseContext context) {
//		Blocks.AIR.getDefaultState()
//			.updateNeighbors(context.getWorld(), context.getPos(), 1);
		Axis axis = newState.get(AXIS);
		newState = getDefaultState().with(AXIS, axis);
		if (newState.contains(BlockStateProperties.POWERED))
			newState = newState.with(BlockStateProperties.POWERED, context.getWorld()
				.isBlockPowered(context.getPos()));
		for (Direction facing : Iterate.directions) {
			if (facing.getAxis() == axis)
				continue;
			BlockPos pos = context.getPos();
			BlockPos offset = pos.offset(facing);
			newState = updatePostPlacement(newState, facing, context.getWorld()
				.getBlockState(offset), context.getWorld(), pos, offset);
		}
//		newState.updateNeighbors(context.getWorld(), context.getPos(), 1 | 2);
		return newState;
	}

	@Override
	public boolean hasShaftTowards(IWorldReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == state.get(AXIS);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.get(AXIS);
	}

	public static boolean areBlocksConnected(BlockState state, BlockState other, Direction facing) {
		Part part = state.get(PART);
		Axis connectionAxis = getConnectionAxis(state);
		Axis otherConnectionAxis = getConnectionAxis(other);

		if (otherConnectionAxis != connectionAxis)
			return false;
		if (facing.getAxis() != connectionAxis)
			return false;
		if (facing.getAxisDirection() == AxisDirection.POSITIVE && (part == Part.MIDDLE || part == Part.START))
			return true;
		if (facing.getAxisDirection() == AxisDirection.NEGATIVE && (part == Part.MIDDLE || part == Part.END))
			return true;

		return false;
	}

	protected static Axis getConnectionAxis(BlockState state) {
		Axis axis = state.get(AXIS);
		boolean connectionAlongFirst = state.get(CONNECTED_ALONG_FIRST_COORDINATE);
		Axis connectionAxis =
			connectionAlongFirst ? (axis == Axis.X ? Axis.Y : Axis.X) : (axis == Axis.Z ? Axis.Y : Axis.Z);
		return connectionAxis;
	}

	public static float getRotationSpeedModifier(KineticTileEntity from, KineticTileEntity to) {
		float fromMod = 1;
		float toMod = 1;
		if (from instanceof AdjustablePulleyTileEntity)
			fromMod = ((AdjustablePulleyTileEntity) from).getModifier();
		if (to instanceof AdjustablePulleyTileEntity)
			toMod = ((AdjustablePulleyTileEntity) to).getModifier();
		return fromMod / toMod;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return AllTileEntities.ENCASED_SHAFT.create();
	}

	public enum Part implements IStringSerializable {
		START, MIDDLE, END, NONE;

		@Override
		public String getString() {
			return Lang.asId(name());
		}
	}

}
