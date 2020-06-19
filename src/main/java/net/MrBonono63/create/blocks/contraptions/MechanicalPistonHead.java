package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;

public class MechanicalPistonHead extends FacingBlock {
    private static final BooleanProperty STICKY = BooleanProperty.of("sticky");

    public MechanicalPistonHead(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.UP).with(STICKY, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(STICKY);
    }
}
