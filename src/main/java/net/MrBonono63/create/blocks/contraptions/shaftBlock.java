package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class shaftBlock extends PillarBlock {
    private static final VoxelShape YSHAPE = Block.createCuboidShape(5,0,5,11,16,11);
    private static final VoxelShape XSHAPE = Block.createCuboidShape(0,5,5,16,11,11);
    private static final VoxelShape ZSHAPE = Block.createCuboidShape(5,5,0,11,11,16);


    public shaftBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        switch(state.get(AXIS))
        {
            case Z:
            default:
                return ZSHAPE;
            case Y:
                return YSHAPE;
            case X:
                return XSHAPE;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
