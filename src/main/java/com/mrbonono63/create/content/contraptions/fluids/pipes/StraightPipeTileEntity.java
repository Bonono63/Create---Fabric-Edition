package com.mrbonono63.create.content.contraptions.fluids.pipes;

import java.util.List;

import com.mrbonono63.create.content.contraptions.fluids.FluidTransportBehaviour;
import com.mrbonono63.create.content.contraptions.relays.elementary.BracketedTileEntityBehaviour;
import com.mrbonono63.create.foundation.tileEntity.SmartTileEntity;
import com.mrbonono63.create.foundation.tileEntity.TileEntityBehaviour;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class StraightPipeTileEntity extends SmartTileEntity {

	public StraightPipeTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
		behaviours.add(new StraightPipeFluidTransportBehaviour(this));
		behaviours.add(new BracketedTileEntityBehaviour(this));
	}

	static class StraightPipeFluidTransportBehaviour extends FluidTransportBehaviour {

		public StraightPipeFluidTransportBehaviour(SmartTileEntity te) {
			super(te);
		}

		@Override
		public boolean canHaveFlowToward(BlockState state, Direction direction) {
			return state.contains(AxisPipeBlock.AXIS) && state.get(AxisPipeBlock.AXIS) == direction.getAxis();
		}

		@Override
		public AttachmentTypes getRenderedRimAttachment(IBlockDisplayReader world, BlockPos pos, BlockState state,
			Direction direction) {
			AttachmentTypes attachment = super.getRenderedRimAttachment(world, pos, state, direction);
			BlockState otherState = world.getBlockState(pos.offset(direction));

			Axis axis = IAxisPipe.getAxisOf(state);
			Axis otherAxis = IAxisPipe.getAxisOf(otherState);

			if (axis == otherAxis && axis != null)
				if (state.getBlock() == otherState.getBlock() || direction.getAxisDirection() == AxisDirection.POSITIVE)
					return AttachmentTypes.NONE;

			if (otherState.getBlock() instanceof FluidValveBlock
				&& FluidValveBlock.getPipeAxis(otherState) == direction.getAxis())
				return AttachmentTypes.NONE;

			return attachment;
		}

	}

}
