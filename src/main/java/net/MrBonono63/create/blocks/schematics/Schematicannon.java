package net.MrBonono63.create.blocks.schematics;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class Schematicannon extends Block {
    public static final VoxelShape OUTLINE;
    public Schematicannon(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE;
    }

    static
    {
        VoxelShape voxelShape = Block.createCuboidShape(1.0,0.0,1.0,15.0,8.0,15.0);
        VoxelShape voxelShape1 = Block.createCuboidShape(0.0,8.0,0.0,16.0,11.0,16.0);
        OUTLINE = VoxelShapes.union(voxelShape, voxelShape1);
    }
}
