package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CogwheelBlock extends PillarBlock {
    private static final VoxelShape YSHAPE = Block.createCuboidShape(5,0,5,11,16,11);
    private static final VoxelShape YSHAPE1 = Block.createCuboidShape(2,6,2,14,10,14);
    private static final VoxelShape XSHAPE = Block.createCuboidShape(0,5,5,16,11,11);
    private static final VoxelShape XSHAPE1 = Block.createCuboidShape(6,2,2,10,14,14);
    private static final VoxelShape ZSHAPE = Block.createCuboidShape(5,5,0,11,11,16);
    private static final VoxelShape ZSHAPE1 = Block.createCuboidShape(2,2,6,14,14,10);
    private static final VoxelShape YSET = VoxelShapes.union(YSHAPE, YSHAPE1);
    private static final VoxelShape XSET = VoxelShapes.union(XSHAPE, XSHAPE1);
    private static final VoxelShape ZSET = VoxelShapes.union(ZSHAPE, ZSHAPE1);


    public CogwheelBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(state.get(AXIS))
        {
            case Z:
            default:
                return ZSET;
            case Y:
                return YSET;
            case X:
                return XSET;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
