package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MagnetPulleyBlock extends Block {
    public static final VoxelShape OUTLINE;
    public MagnetPulleyBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE;
    }

    static
    {
        VoxelShape voxelShape = Block.createCuboidShape(3.0,0.0,3.0,13.0,2.0,13.0);
        VoxelShape voxelShape1 = Block.createCuboidShape(6.0,2.0,6.0,10,16,10);
        OUTLINE = VoxelShapes.union(voxelShape, voxelShape1);
    }
}
