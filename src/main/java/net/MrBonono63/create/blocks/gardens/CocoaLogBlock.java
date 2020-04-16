package net.MrBonono63.create.blocks.gardens;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.MrBonono63.create.config.CreateConfig;
import net.minecraft.block.*;
import net.minecraft.state.property.Properties;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.*;

public class CocoaLogBlock extends PillarBlock implements Fertilizable {
    //local cocoa age property min 0 max 2
    public static IntProperty AGE;

    CreateConfig config = AutoConfig.getConfigHolder(CreateConfig.class).getConfig();

    public CocoaLogBlock(Settings settings) {
        super(settings);
        //sets the default age property
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        //tells the game it has a age property
        super.appendProperties(builder.add(AGE));
    }

    protected int getAge(BlockState state)
    {
        return state.get(this.getAgeProperty());
    }

    public int getMaxAge()
    {
        return 2;
    }

    public IntProperty getAgeProperty()
    {
        return AGE;
    }

    public boolean isMature(BlockState state)
    {
        return getAge(state) >= this.getMaxAge();
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    //don't override otherwise the game won't tick it
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt() > config.cocoaLogGrowthSpeed / 100d && !isMature(state))
        {
            world.setBlockState(pos, state.with(AGE, getAge(state) + 1), 2);
        }
        else
        {
            grow(world, random, pos, state);
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        //checks age
        if (!isMature(state))
        {
            world.setBlockState(pos, (BlockState)state.with(AGE, getAge(state) + 1), 2);
        }
        else // doesn't grow unless its mature
        {
            List<Direction> shuffledDirections = Arrays.asList(Direction.values());
            Collections.shuffle(shuffledDirections);

            for (Direction facing : shuffledDirections) {
                if (facing.getAxis().isVertical())
                    continue;
                if (facing.getAxis() == state.get(Properties.AXIS))
                    continue;
                if (!world.getBlockState(pos.offset(facing)).getMaterial().isReplaceable())
                    continue;

                world.setBlockState(pos.offset(facing),
                        Blocks.COCOA.getDefaultState().with(Properties.HORIZONTAL_FACING, facing.getOpposite())
                                .with(Properties.AGE_2, 0));

                break;
            }
        }
    }

    static {
        //gives the age property a boundary (in vanilla, like crops and plants)
        AGE = Properties.AGE_2;
    }
}
