package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class BasinBlock extends Block {

    private static final VoxelShape NORMAL_OUTLINE_SHAPE;

    public BasinBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, net.minecraft.world.BlockView view, BlockPos pos, net.minecraft.entity.EntityContext context) {
        return NORMAL_OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return NORMAL_OUTLINE_SHAPE;
    }

    static {
        VoxelShape voxelShape = Block.createCuboidShape(16.0D,16.0D,16.0D,16.0D,16.0D,16.0D);
        VoxelShape voxelShape1Bottom = Block.createCuboidShape(2.0D,0.0D,2.0D,14.0D,2.0D,14.0D);
        VoxelShape voxelShapeSide1 = Block.createCuboidShape(0.0D, 2.0D, 2.0D, 16.0D, 13.0D, 0.0D);
        VoxelShape voxelShapeSide3 = Block.createCuboidShape(0.0d,2.0d,14.0d,16d,13.0d,16.0d);
        VoxelShape voxelShapeSide2 = Block.createCuboidShape(0.0D,2.0D,2.0D,2.0D,13.0D,16.0D);
        VoxelShape voxelShapeSide4 = Block.createCuboidShape(14.0D,2.0D,2.0D,16.0D,13.0D,16.0D);
        NORMAL_OUTLINE_SHAPE = VoxelShapes.union(voxelShape, voxelShape1Bottom, voxelShapeSide1, voxelShapeSide2, voxelShapeSide3, voxelShapeSide4);
    }
}
