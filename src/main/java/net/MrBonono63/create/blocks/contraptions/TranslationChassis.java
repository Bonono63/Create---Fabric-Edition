package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;

public class TranslationChassis extends PillarBlock {
    private static final BooleanProperty STICKY_TOP = BooleanProperty.of("sticky_top");
    private static final BooleanProperty STICKY_BOTTOM = BooleanProperty.of("sticky_bottom");
    public TranslationChassis(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y).with(STICKY_BOTTOM, false).with(STICKY_TOP, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
        builder.add(STICKY_BOTTOM);
        builder.add(STICKY_TOP);
    }
}
