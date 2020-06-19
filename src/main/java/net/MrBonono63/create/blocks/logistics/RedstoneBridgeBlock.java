package net.MrBonono63.create.blocks.logistics;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class RedstoneBridgeBlock extends FacingBlock {
    public static final BooleanProperty RECEIVER = BooleanProperty.of("receiver");
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0,1.0,3.0,3.0,15.0,13.0);;
    public static final VoxelShape WEST_SHAPE = Block.createCuboidShape(13,1.0,3,16.0,15.0,13.0);;
    public static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(3,1.0,2,13.0,15.0,3.0);;
    public static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3,1.0,13,13.0,15.0,16.0);;
    public static final VoxelShape UP_SHAPE = Block.createCuboidShape(2,0.0,2,14.0,3.0,14.0);;
    public static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(2,13.0,2,14.0,16.0,14.0);;

    public RedstoneBridgeBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(RECEIVER, true));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(RECEIVER);
        builder.add(POWERED);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case UP:
            default:
                return UP_SHAPE;
            case DOWN:
                return DOWN_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
        }
    }
}
