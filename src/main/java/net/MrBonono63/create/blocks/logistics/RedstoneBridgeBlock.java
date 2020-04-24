package net.MrBonono63.create.blocks.logistics;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class RedstoneBridgeBlock extends WallMountedBlock {
    public static final BooleanProperty RECEIVER;
    public static final BooleanProperty POWERED;
    public static final VoxelShape DOWN_SHAPE;
    public static final VoxelShape UP_SHAPE;
    public static final VoxelShape WEST_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;

    public RedstoneBridgeBlock(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.stateManager.getDefaultState().with(FACE, Direction.DOWN).with(POWERED, false).with(RECEIVER, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(RECEIVER);
        builder.add(POWERED);
        builder.add(FACE);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        switch (state.get(FACE))
        {
            case UP:
                return UP_SHAPE;

            case EAST:
                return EAST_SHAPE;

            case NORTH:
                return NORTH_SHAPE;

            case DOWN:
                return DOWN_SHAPE;

            case WEST:
                return WEST_SHAPE;

            case SOUTH:
                return SOUTH_SHAPE;

            default:
                return DOWN_SHAPE;
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        switch (state.get(FACING))
        {
            case EAST:
                return EAST_SHAPE;

            case NORTH:
                return NORTH_SHAPE;

            case SOUTH:
                return SOUTH_SHAPE;

            case WEST:
                return WEST_SHAPE;

            case DOWN:
                return DOWN_SHAPE;

            case UP:
                return UP_SHAPE;
            default:
                return DOWN_SHAPE;
        }
    }

    static
    {
        RECEIVER = BooleanProperty.of("receiver");
        POWERED = BooleanProperty.of("powered");
        FACING = FacingBlock.FACING;
        DOWN_SHAPE = Block.createCuboidShape(2.0,0.0,2.0,14.0,2.0,14.0);
        UP_SHAPE = Block.createCuboidShape(2,14.0,2,14.0,16.0,14.0);
        NORTH_SHAPE = Block.createCuboidShape(2,14.0,2,14.0,16.0,14.0);
        WEST_SHAPE = Block.createCuboidShape(2,14.0,2,14.0,16.0,14.0);
        EAST_SHAPE = Block.createCuboidShape(2,14.0,2,14.0,16.0,14.0);
        SOUTH_SHAPE = Block.createCuboidShape(2,14.0,2,14.0,16.0,14.0);
    }
}
