package com.mrbonono63.create.content.contraptions.components.structureMovement;

import static net.minecraft.block.HorizontalFaceBlock.FACE;
import static net.minecraft.state.properties.BlockStateProperties.AXIS;
import static net.minecraft.state.properties.BlockStateProperties.FACING;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

import com.simibubi.create.AllBlocks;
import com.mrbonono63.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.mrbonono63.create.content.contraptions.components.structureMovement.chassis.AbstractChassisBlock;
import com.mrbonono63.create.content.contraptions.relays.belt.BeltBlock;
import com.mrbonono63.create.content.contraptions.relays.belt.BeltSlope;
import com.mrbonono63.create.foundation.utility.DirectionHelper;
import com.mrbonono63.create.foundation.utility.Iterate;
import com.mrbonono63.create.foundation.utility.VecHelper;

import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BellAttachment;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class StructureTransform {

	// Assuming structures cannot be rotated around multiple axes at once
	Rotation rotation;
	int angle;
	Axis rotationAxis;
	BlockPos offset;

	private StructureTransform(BlockPos offset, int angle, Axis axis, Rotation rotation) {
		this.offset = offset;
		this.angle = angle;
		rotationAxis = axis;
		this.rotation = rotation;
	}

	public StructureTransform(BlockPos offset, float xRotation, float yRotation, float zRotation) {
		this.offset = offset;
		if (xRotation != 0) {
			rotationAxis = Axis.X;
			angle = (int) (Math.round(xRotation / 90) * 90);
		}
		if (yRotation != 0) {
			rotationAxis = Axis.Y;
			angle = (int) (Math.round(yRotation / 90) * 90);
		}
		if (zRotation != 0) {
			rotationAxis = Axis.Z;
			angle = (int) (Math.round(zRotation / 90) * 90);
		}

		angle %= 360;
		if (angle < -90)
			angle += 360;

		this.rotation = Rotation.NONE;
		if (angle == -90 || angle == 270)
			this.rotation = Rotation.CLOCKWISE_90;
		if (angle == 90)
			this.rotation = Rotation.COUNTERCLOCKWISE_90;
		if (angle == 180)
			this.rotation = Rotation.CLOCKWISE_180;

	}

	public Vector3d apply(Vector3d localVec) {
		Vector3d vec = localVec;
		if (rotationAxis != null)
			vec = VecHelper.rotateCentered(vec, angle, rotationAxis);
		vec = vec.add(Vector3d.of(offset));
		return vec;
	}

	public BlockPos apply(BlockPos localPos) {
		Vector3d vec = VecHelper.getCenterOf(localPos);
		if (rotationAxis != null)
			vec = VecHelper.rotateCentered(vec, angle, rotationAxis);
		localPos = new BlockPos(vec);
		return localPos.add(offset);
	}

	/**
	 * Minecraft does not support blockstate rotation around axes other than y. Add
	 * specific cases here for blockstates, that should react to rotations around
	 * horizontal axes
	 */
	public BlockState apply(BlockState state) {
		Block block = state.getBlock();

		if (rotationAxis == Axis.Y) {
			if (block instanceof BellBlock) {
				if (state.get(BlockStateProperties.BELL_ATTACHMENT) == BellAttachment.DOUBLE_WALL) {
					state = state.with(BlockStateProperties.BELL_ATTACHMENT, BellAttachment.SINGLE_WALL);
				}
				return state.with(HorizontalFaceBlock.HORIZONTAL_FACING,
					rotation.rotate(state.get(HorizontalFaceBlock.HORIZONTAL_FACING)));
			}
			return state.rotate(rotation);
		}

		if (block instanceof AbstractChassisBlock)
			return rotateChassis(state);

		if (block instanceof HorizontalFaceBlock) {
			Direction stateFacing = state.get(HorizontalFaceBlock.HORIZONTAL_FACING);
			AttachFace stateFace = state.get(FACE);
			Direction forcedAxis = rotationAxis == Axis.Z ? Direction.EAST : Direction.SOUTH;

			if (stateFacing.getAxis() == rotationAxis && stateFace == AttachFace.WALL)
				return state;

			for (int i = 0; i < rotation.ordinal(); i++) {
				stateFace = state.get(FACE);
				stateFacing = state.get(HorizontalFaceBlock.HORIZONTAL_FACING);

				boolean b = state.get(FACE) == AttachFace.CEILING;
				state = state.with(HORIZONTAL_FACING, b ? forcedAxis : forcedAxis.getOpposite());

				if (stateFace != AttachFace.WALL) {
					state = state.with(FACE, AttachFace.WALL);
					continue;
				}

				if (stateFacing.getAxisDirection() == AxisDirection.POSITIVE) {
					state = state.with(FACE, AttachFace.FLOOR);
					continue;
				}
				state = state.with(FACE, AttachFace.CEILING);
			}

			return state;
		}

		boolean halfTurn = rotation == Rotation.CLOCKWISE_180;
		if (block instanceof StairsBlock) {
			state = transformStairs(state, halfTurn);
			return state;
		}

		if (AllBlocks.BELT.has(state)) {
			state = transformBelt(state, halfTurn);
			return state;
		}

		if (state.contains(FACING)) {
			Direction newFacing = transformFacing(state.get(FACING));
			if (state.contains(DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE)) {
				if (rotationAxis == newFacing.getAxis() && rotation.ordinal() % 2 == 1)
					state = state.cycle(DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE);
			}
			state = state.with(FACING, newFacing);

		} else if (state.contains(AXIS)) {
			state = state.with(AXIS, transformAxis(state.get(AXIS)));

		} else if (halfTurn) {

			if (state.contains(FACING)) {
				Direction stateFacing = state.get(FACING);
				if (stateFacing.getAxis() == rotationAxis)
					return state;
			}

			if (state.contains(HORIZONTAL_FACING)) {
				Direction stateFacing = state.get(HORIZONTAL_FACING);
				if (stateFacing.getAxis() == rotationAxis)
					return state;
			}

			state = state.rotate(rotation);
			if (state.contains(SlabBlock.TYPE) && state.get(SlabBlock.TYPE) != SlabType.DOUBLE)
				state = state.with(SlabBlock.TYPE,
					state.get(SlabBlock.TYPE) == SlabType.BOTTOM ? SlabType.TOP : SlabType.BOTTOM);
		}

		return state;
	}

	protected BlockState transformStairs(BlockState state, boolean halfTurn) {
		if (state.get(StairsBlock.FACING)
			.getAxis() != rotationAxis) {
			for (int i = 0; i < rotation.ordinal(); i++) {
				Direction direction = state.get(StairsBlock.FACING);
				Half half = state.get(StairsBlock.HALF);
				if (direction.getAxisDirection() == AxisDirection.POSITIVE ^ half == Half.BOTTOM
					^ direction.getAxis() == Axis.Z)
					state = state.cycle(StairsBlock.HALF);
				else
					state = state.with(StairsBlock.FACING, direction.getOpposite());
			}
		} else {
			if (halfTurn) {
				state = state.cycle(StairsBlock.HALF);
			}
		}
		return state;
	}

	protected BlockState transformBelt(BlockState state, boolean halfTurn) {
		Direction initialDirection = state.get(BeltBlock.HORIZONTAL_FACING);
		boolean diagonal =
			state.get(BeltBlock.SLOPE) == BeltSlope.DOWNWARD || state.get(BeltBlock.SLOPE) == BeltSlope.UPWARD;

		if (!diagonal) {
			for (int i = 0; i < rotation.ordinal(); i++) {
				Direction direction = state.get(BeltBlock.HORIZONTAL_FACING);
				BeltSlope slope = state.get(BeltBlock.SLOPE);
				boolean vertical = slope == BeltSlope.VERTICAL;
				boolean horizontal = slope == BeltSlope.HORIZONTAL;
				boolean sideways = slope == BeltSlope.SIDEWAYS;

				Direction newDirection = direction.getOpposite();
				BeltSlope newSlope = BeltSlope.VERTICAL;

				if (vertical) {
					if (direction.getAxis() == rotationAxis) {
						newDirection = direction.rotateYCCW();
						newSlope = BeltSlope.SIDEWAYS;
					} else {
						newSlope = BeltSlope.HORIZONTAL;
						newDirection = direction;
						if (direction.getAxis() == Axis.Z)
							newDirection = direction.getOpposite();
					}
				}

				if (sideways) {
					newDirection = direction;
					if (direction.getAxis() == rotationAxis)
						newSlope = BeltSlope.HORIZONTAL;
					else
						newDirection = direction.rotateYCCW();
				}

				if (horizontal) {
					newDirection = direction;
					if (direction.getAxis() == rotationAxis)
						newSlope = BeltSlope.SIDEWAYS;
					else if (direction.getAxis() != Axis.Z)
						newDirection = direction.getOpposite();
				}

				state = state.with(BeltBlock.HORIZONTAL_FACING, newDirection);
				state = state.with(BeltBlock.SLOPE, newSlope);
			}

		} else if (initialDirection.getAxis() != rotationAxis) {
			for (int i = 0; i < rotation.ordinal(); i++) {
				Direction direction = state.get(BeltBlock.HORIZONTAL_FACING);
				Direction newDirection = direction.getOpposite();
				BeltSlope slope = state.get(BeltBlock.SLOPE);
				boolean upward = slope == BeltSlope.UPWARD;
				boolean downward = slope == BeltSlope.DOWNWARD;

				// Rotate diagonal
				if (direction.getAxisDirection() == AxisDirection.POSITIVE ^ downward ^ direction.getAxis() == Axis.Z) {
					state = state.with(BeltBlock.SLOPE, upward ? BeltSlope.DOWNWARD : BeltSlope.UPWARD);
				} else {
					state = state.with(BeltBlock.HORIZONTAL_FACING, newDirection);
				}
			}

		} else if (halfTurn) {
			Direction direction = state.get(BeltBlock.HORIZONTAL_FACING);
			Direction newDirection = direction.getOpposite();
			BeltSlope slope = state.get(BeltBlock.SLOPE);
			boolean vertical = slope == BeltSlope.VERTICAL;

			if (diagonal) {
				state = state.with(BeltBlock.SLOPE, slope == BeltSlope.UPWARD ? BeltSlope.DOWNWARD
					: slope == BeltSlope.DOWNWARD ? BeltSlope.UPWARD : slope);
			} else if (vertical) {
				state = state.with(BeltBlock.HORIZONTAL_FACING, newDirection);
			}
		}
		return state;
	}

	public Axis transformAxis(Axis axisIn) {
		Direction facing = Direction.getFacingFromAxis(AxisDirection.POSITIVE, axisIn);
		facing = transformFacing(facing);
		Axis axis = facing.getAxis();
		return axis;
	}

	public Direction transformFacing(Direction facing) {
		for (int i = 0; i < rotation.ordinal(); i++)
			facing = DirectionHelper.rotateAround(facing, rotationAxis);
		return facing;
	}

	private BlockState rotateChassis(BlockState state) {
		if (rotation == Rotation.NONE)
			return state;

		BlockState rotated = state.with(AXIS, transformAxis(state.get(AXIS)));
		AbstractChassisBlock block = (AbstractChassisBlock) state.getBlock();

		for (Direction face : Iterate.directions) {
			BooleanProperty glueableSide = block.getGlueableSide(rotated, face);
			if (glueableSide != null)
				rotated = rotated.with(glueableSide, false);
		}

		for (Direction face : Iterate.directions) {
			BooleanProperty glueableSide = block.getGlueableSide(state, face);
			if (glueableSide == null || !state.get(glueableSide))
				continue;
			Direction rotatedFacing = transformFacing(face);
			BooleanProperty rotatedGlueableSide = block.getGlueableSide(rotated, rotatedFacing);
			if (rotatedGlueableSide != null)
				rotated = rotated.with(rotatedGlueableSide, true);
		}

		return rotated;
	}

	public static StructureTransform fromBuffer(PacketBuffer buffer) {
		BlockPos readBlockPos = buffer.readBlockPos();
		int readAngle = buffer.readInt();
		int axisIndex = buffer.readVarInt();
		int rotationIndex = buffer.readVarInt();
		return new StructureTransform(readBlockPos, readAngle, axisIndex == -1 ? null : Axis.values()[axisIndex],
			rotationIndex == -1 ? null : Rotation.values()[rotationIndex]);
	}

	public void writeToBuffer(PacketBuffer buffer) {
		buffer.writeBlockPos(offset);
		buffer.writeInt(angle);
		buffer.writeVarInt(rotationAxis == null ? -1 : rotationAxis.ordinal());
		buffer.writeVarInt(rotation == null ? -1 : rotation.ordinal());
	}

}
