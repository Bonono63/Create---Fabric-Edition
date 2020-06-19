package net.MrBonono63.create.blocks.foundation;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;

public class OmniDirectionalFacingBlock extends WallMountedBlock {
    protected OmniDirectionalFacingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(FACE, WallMountLocation.WALL));
    }
    //adds bock that face in any direction without rotation.
}
