package net.MrBonono63.create.blocks.logistics;

import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class FlexPeater extends AbstractRedstoneGateBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,2,16);
    private static final BooleanProperty POWERED = Properties.POWERED;
    private static final BooleanProperty POWERING = BooleanProperty.of("powering");

    public FlexPeater(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(POWERING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(POWERED);
        builder.add(POWERING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return SHAPE;
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 0;
    }
}
