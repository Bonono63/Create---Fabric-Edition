package net.MrBonono63.create.blocks.logistics;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class FlexPulsePeater extends AbstractRedstoneGateBlock {
    private static final BooleanProperty POWERED = Properties.POWERED;
    private static final BooleanProperty POWERING = BooleanProperty.of("powering");

    public FlexPulsePeater(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(POWERING, false));

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        builder.add(POWERING);
        builder.add(FACING);
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 0;
    }
}
