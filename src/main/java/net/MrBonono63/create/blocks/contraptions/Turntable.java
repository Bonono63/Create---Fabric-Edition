package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class Turntable extends Block {
    public static final VoxelShape OUTLINE;
    public Turntable(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return OUTLINE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return OUTLINE;
    }

    static
    {
        VoxelShape voxelShape1 = Block.createCuboidShape(6.0,0.0,6.0,10.0,4.0,10.0);
        VoxelShape voxelShape2 = Block.createCuboidShape(1.0,4,1.0,15.0,8,15.0);
        OUTLINE = VoxelShapes.union(voxelShape1, voxelShape2);
    }
}
