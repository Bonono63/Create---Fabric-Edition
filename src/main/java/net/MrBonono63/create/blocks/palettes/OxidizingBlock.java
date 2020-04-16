package net.MrBonono63.create.blocks.palettes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.Random;

public class OxidizingBlock extends Block {
    public static final IntProperty OXIDIZATION;
    private float chance;

    public OxidizingBlock(Settings settings, float chance) {
        super(settings);
        this.chance = chance;
        this.setDefaultState(this.stateManager.getDefaultState().with(OXIDIZATION, 0));
    }

    public static Integer getOxidization(BlockState state)
    {
        return state.get(OXIDIZATION);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(OXIDIZATION));
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getRandom().nextFloat() <= chance)
        {
            int currentState = state.get(OXIDIZATION);
            boolean canIncrease = false;
            LinkedList<Integer> neighbors = new LinkedList<>();
            for (Direction facing : Direction.values())
            {
                BlockPos neighborPos = pos.offset(facing);
                if (world.isAir(neighborPos))
                    continue;
                BlockState neighborState = world.getBlockState(neighborPos);
                if (neighborState.get(OXIDIZATION) != 0)
                {
                    neighbors.add(neighborState.get(OXIDIZATION));
                }
                if (Block.isSideSolidFullSquare(neighborState,world,neighborPos,facing.getOpposite()))
                    continue;
                canIncrease = true;
            }
            if (canIncrease)
            {
                OptionalDouble average = neighbors.stream().mapToInt(v -> v).average();
                if (average.orElse(7d) >= currentState)
                    world.setBlockState(pos, state.with(OXIDIZATION, Math.min(currentState + 1, 7)));
            }
        }
    }

    public float setHardness(BlockState state)
    {
        float hardness = 3f;
        if (getOxidization(state) < 8)
        {
            hardness = hardness - 0.2f * state.get(OXIDIZATION);
        }
        return hardness;
    }

    @Override
    public float getHardness(BlockState state, BlockView world, BlockPos pos) {
        return setHardness(state);
    }

    static
    {
        OXIDIZATION = IntProperty.of("oxidization",0,7);
    }
}
