package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public class RopePulley extends PillarBlock {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public RopePulley(Settings settings) {
        super(settings.nonOpaque());
        this.setDefaultState(getDefaultState().with(AXIS, Direction.Axis.X));
    }

    public BlockState rotate(BlockState state, BlockRotation rotation)
    {
        switch (rotation)
        {
            case CLOCKWISE_90:
            case COUNTERCLOCKWISE_90:
                switch (state.get(AXIS))
                {
                    case X:
                        return state.with(AXIS, Direction.Axis.X);
                    case Z:
                        return state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        switch (ctx.getSide().getAxis())
        {
            case X:
            default:
                return getDefaultState().with(AXIS, Direction.Axis.X);
            case Z:
                return getDefaultState().with(AXIS, Direction.Axis.Z);
        }
    }
}
