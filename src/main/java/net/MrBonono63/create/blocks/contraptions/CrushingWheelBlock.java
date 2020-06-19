package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class CrushingWheelBlock extends PillarBlock {
    public CrushingWheelBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
