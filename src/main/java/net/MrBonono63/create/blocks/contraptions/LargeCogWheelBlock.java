package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class LargeCogWheelBlock extends PillarBlock {
    private static final VoxelShape YSHAPE = Block.createCuboidShape(5,0,5,11,16,11);
    private static final VoxelShape YSHAPE1 = Block.createCuboidShape(0,6,0,16,10,16);
    private static final VoxelShape XSHAPE = Block.createCuboidShape(0,5,5,16,11,11);
    private static final VoxelShape XSHAPE1 = Block.createCuboidShape(6,0,0,10,16,16);
    private static final VoxelShape ZSHAPE = Block.createCuboidShape(5,5,0,11,11,16);
    private static final VoxelShape ZSHAPE1 = Block.createCuboidShape(0,0,6,16,16,10);
    private static final VoxelShape YSET = VoxelShapes.union(YSHAPE, YSHAPE1);
    private static final VoxelShape XSET = VoxelShapes.union(XSHAPE, XSHAPE1);
    private static final VoxelShape ZSET = VoxelShapes.union(ZSHAPE, ZSHAPE1);


    public LargeCogWheelBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
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
