package net.MrBonono63.create.blocks.schematics;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CreativeCrateBlock extends Block {
    public static final VoxelShape OUTLINE;
    public CreativeCrateBlock(Settings settings) {
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
        VoxelShape voxelShape = Block.createCuboidShape(1,0,1,15,14,15);
        OUTLINE = VoxelShapes.union(voxelShape);
    }
}
