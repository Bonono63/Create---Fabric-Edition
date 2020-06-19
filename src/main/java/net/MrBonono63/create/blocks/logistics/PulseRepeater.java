package net.MrBonono63.create.blocks.logistics;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class PulseRepeater extends AbstractRedstoneGateBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,2,16);
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty PULSING = BooleanProperty.of("pulsing");

    public PulseRepeater(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(PULSING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        builder.add(PULSING);
        builder.add(FACING);
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
