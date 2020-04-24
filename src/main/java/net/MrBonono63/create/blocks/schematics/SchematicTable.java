package net.MrBonono63.create.blocks.schematics;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SchematicTable extends HorizontalFacingBlock {
    public static final DirectionProperty FACING;
    public static final VoxelShape COLLISION_SHAPE_TOP;
    public static final VoxelShape COLLISION_SHAPE;
    public static final VoxelShape BOTTOM_SHAPE;
    public static final VoxelShape BASE_SHAPE;
    public static final VoxelShape MIDDLE_SHAPE;
    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape WEST_SHAPE;


    public SchematicTable(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        switch ((Direction)state.get(FACING))
        {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return BASE_SHAPE;
        }
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView view, BlockPos pos) {
        return BASE_SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    @Override
    public boolean canPlaceAtSide(BlockState world, BlockView view, BlockPos pos, BlockPlacementEnvironment env) {
        return false;
    }

    static
    {
        BOTTOM_SHAPE = Block.createCuboidShape(4.0,0.0,4.0,12.0,2.0,12.0);
        MIDDLE_SHAPE = Block.createCuboidShape(5.0,2.0,5.0,11.0,14,11.0);
        BASE_SHAPE = VoxelShapes.union(BOTTOM_SHAPE,MIDDLE_SHAPE);
        COLLISION_SHAPE_TOP = Block.createCuboidShape(0.0,15.0,0.0,16.0,15.0,16.0);
        COLLISION_SHAPE = VoxelShapes.union(BASE_SHAPE, COLLISION_SHAPE_TOP);
        WEST_SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 10.0D, 0.0D, 5D, 14.0D, 16.0D),new VoxelShape[]{Block.createCuboidShape(5D, 12.0D, 0.0D, 10D, 16.0D, 16.0D), Block.createCuboidShape(10D, 14.0D, 0.0D, 15.0D, 18.0D, 16.0D), BASE_SHAPE});
        NORTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 10.0D, 0.0D, 16.0D, 14.0D, 5D), new VoxelShape[]{Block.createCuboidShape(0.0D, 12.0D, 5D, 16.0D, 16.0D, 10D), Block.createCuboidShape(0.0D, 14.0D, 10D, 16.0D, 18.0D, 15.0D), BASE_SHAPE});
        EAST_SHAPE = VoxelShapes.union(Block.createCuboidShape(16.0D, 10.0D, 0.0D, 11D, 14.0D, 16.0D), new VoxelShape[]{Block.createCuboidShape(11D, 12.0D, 0.0D, 6D, 16.0D, 16.0D), Block.createCuboidShape(6D, 14.0D, 0.0D, 1.0D, 18.0D, 16.0D), BASE_SHAPE});
        SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 10.0D, 16.0D, 16.0D, 14.0D, 11D), new VoxelShape[]{Block.createCuboidShape(0.0D, 12.0D, 11D, 16.0D, 16.0D, 6D), Block.createCuboidShape(0.0D, 14.0D, 6D, 16.0D, 18.0D, 1.0D), BASE_SHAPE});
        FACING = HorizontalFacingBlock.FACING;
    }
}
