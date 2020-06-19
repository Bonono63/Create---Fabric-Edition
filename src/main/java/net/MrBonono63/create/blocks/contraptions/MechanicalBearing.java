package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class MechanicalBearing extends FacingBlock {
    public MechanicalBearing(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.UP));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
