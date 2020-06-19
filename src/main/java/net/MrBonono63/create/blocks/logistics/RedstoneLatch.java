package net.MrBonono63.create.blocks.logistics;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class RedstoneLatch extends AbstractRedstoneGateBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,2,16);
    private static final BooleanProperty POWERING = BooleanProperty.of("powering");
    private static final BooleanProperty POWERED = Properties.POWERED;
    private static final BooleanProperty POWERED_SIDE = BooleanProperty.of("powered_side");

    public RedstoneLatch(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERING, false).with(POWERED, false).with(POWERED_SIDE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(POWERING);
        builder.add(POWERED);
        builder.add(POWERED_SIDE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 0;
    }
}
