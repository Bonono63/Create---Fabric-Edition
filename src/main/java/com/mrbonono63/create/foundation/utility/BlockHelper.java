package com.mrbonono63.create.foundation.utility;

import java.util.function.Consumer;

import com.mrbonono63.create.AllBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.apache.commons.lang3.mutable.MutableInt;

import com.mrbonono63.create.AllBlocks;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.components.actors.SeatBlock;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlimeBlock;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import org.jetbrains.annotations.Nullable;

public class BlockHelper {

	@Environment(EnvType.CLIENT)
	public static void addReducedDestroyEffects(BlockState state, World worldIn, BlockPos pos,
		ParticleManager manager) {
		if (!(worldIn instanceof ClientWorld))
			return;
		ClientWorld world = (ClientWorld) worldIn;
		VoxelShape voxelshape = state.getShape(world, pos);
		MutableInt amtBoxes = new MutableInt(0);
		voxelshape.forEachBox((x1, y1, z1, x2, y2, z2) -> amtBoxes.increment());
		double chance = 1d / amtBoxes.getValue();

		voxelshape.forEachBox((x1, y1, z1, x2, y2, z2) -> {
			double d1 = Math.min(1.0D, x2 - x1);
			double d2 = Math.min(1.0D, y2 - y1);
			double d3 = Math.min(1.0D, z2 - z1);
			int i = Math.max(2, MathHelper.ceil(d1 / 0.25D));
			int j = Math.max(2, MathHelper.ceil(d2 / 0.25D));
			int k = Math.max(2, MathHelper.ceil(d3 / 0.25D));

			for (int l = 0; l < i; ++l) {
				for (int i1 = 0; i1 < j; ++i1) {
					for (int j1 = 0; j1 < k; ++j1) {
						if (world.random.nextDouble() > chance)
							continue;

						double d4 = ((double) l + 0.5D) / (double) i;
						double d5 = ((double) i1 + 0.5D) / (double) j;
						double d6 = ((double) j1 + 0.5D) / (double) k;
						double d7 = d4 * d1 + x1;
						double d8 = d5 * d2 + y1;
						double d9 = d6 * d3 + z1;
						manager
							.addParticle((new BlockDustParticle(world, (double) pos.getX() + d7, (double) pos.getY() + d8,
								(double) pos.getZ() + d9, d4 - 0.5D, d5 - 0.5D, d6 - 0.5D, state)).setBlockPos(pos));
					}
				}
			}

		});
	}

	public static BlockState setZeroAge(BlockState blockState) {
		if (blockState.contains(Properties.AGE_1))
			return blockState.with(Properties.AGE_1, 0);
		if (blockState.contains(Properties.AGE_2))
			return blockState.with(Properties.AGE_2, 0);
		if (blockState.contains(Properties.AGE_3))
			return blockState.with(Properties.AGE_3, 0);
		if (blockState.contains(Properties.AGE_5))
			return blockState.with(Properties.AGE_5, 0);
		if (blockState.contains(Properties.AGE_7))
			return blockState.with(Properties.AGE_7, 0);
		if (blockState.contains(Properties.AGE_15))
			return blockState.with(Properties.AGE_15, 0);
		if (blockState.contains(Properties.AGE_25))
			return blockState.with(Properties.AGE_25, 0);
		if (blockState.contains(Properties.HONEY_LEVEL))
			return blockState.with(Properties.HONEY_LEVEL, 0);
		if (blockState.contains(Properties.HATCH))
			return blockState.with(Properties.HATCH, 0);
		if (blockState.contains(Properties.STAGE))
			return blockState.with(Properties.STAGE, 0);
		if (blockState.contains(Properties.LEVEL_3))
			return blockState.with(Properties.LEVEL_3, 0);
		if (blockState.contains(Properties.LEVEL_8))
			return blockState.with(Properties.LEVEL_8, 0);
		if (blockState.contains(Properties.EXTENDED))
			return blockState.with(Properties.EXTENDED, false);
		return blockState;
	}

	public static int findAndRemoveInInventory(BlockState block, PlayerEntity player, int amount) {
		int amountFound = 0;
		Item required = getRequiredItem(block).getItem();

		boolean needsTwo = block.contains(Properties.SLAB_TYPE)
			&& block.get(Properties.SLAB_TYPE) == SlabType.DOUBLE;

		if (needsTwo)
			amount *= 2;

		if (block.contains(Properties.EGGS))
			amount *= block.get(Properties.EGGS);

		if (block.contains(Properties.PICKLES))
			amount *= block.get(Properties.PICKLES);

		{
			// Try held Item first
			int preferredSlot = player.inventory.selectedSlot;
			ItemStack itemstack = player.inventory.getStack(preferredSlot);
			int count = itemstack.getCount();
			if (itemstack.getItem() == required && count > 0) {
				int taken = Math.min(count, amount - amountFound);
				player.inventory.setStack(preferredSlot,
					new ItemStack(itemstack.getItem(), count - taken));
				amountFound += taken;
			}
		}

		// Search inventory
		for (int i = 0; i < player.inventory.size(); ++i) {
			if (amountFound == amount)
				break;

			ItemStack itemstack = player.inventory.getStack(i);
			int count = itemstack.getCount();
			if (itemstack.getItem() == required && count > 0) {
				int taken = Math.min(count, amount - amountFound);
				player.inventory.setStack(i, new ItemStack(itemstack.getItem(), count - taken));
				amountFound += taken;
			}
		}

		if (needsTwo) {
			// Give back 1 if uneven amount was removed
			if (amountFound % 2 != 0)
				player.inventory.insertStack(new ItemStack(required));
			amountFound /= 2;
		}

		return amountFound;
	}

	public static ItemStack getRequiredItem(BlockState state) {
		ItemStack itemStack = new ItemStack(state.getBlock());
		if (itemStack.getItem() == Items.FARMLAND)
			itemStack = new ItemStack(Items.DIRT);
		else if (itemStack.getItem() == Items.GRASS_PATH)
			itemStack = new ItemStack(Items.GRASS_BLOCK);
		return itemStack;
	}

	public static void destroyBlock(World world, BlockPos pos, float effectChance) {
		destroyBlock(world, pos, effectChance, stack -> Block.dropStack(world, pos, stack));
	}

	public static void destroyBlock(World world, BlockPos pos, float effectChance,
		Consumer<ItemStack> droppedItemCallback) {
		destroyBlockAs(world, pos, null, ItemStack.EMPTY, effectChance, droppedItemCallback);
	}

	public static void destroyBlockAs(World world, BlockPos pos, @Nullable PlayerEntity player, ItemStack usedTool,
		float effectChance, Consumer<ItemStack> droppedItemCallback) {
		FluidState fluidState = world.getFluidState(pos);
		BlockState state = world.getBlockState(pos);
		if (world.random.nextFloat() < effectChance)
			world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
		BlockEntity blockEntity = state.hasTileEntity() ? world.getBlockEntity(pos) : null;
		if (player != null) {
			BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, player);
			MinecraftForge.EVENT_BUS.post(event);
			if (event.isCanceled())
				return;

			if (event.getExpToDrop() > 0 && world instanceof ServerWorld)
				state.getBlock()
					.dropXpOnBlockBreak((ServerWorld) world, pos, event.getExpToDrop());

			usedTool.onBlockDestroyed(world, state, pos, player);
			player.addStat(Stats.MINED.getOrCreateStat(state.getBlock()));
		}

		if (world instanceof ServerWorld && world.getGameRules()
			.getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots
			&& (player == null || !player.isCreative())) {
			for (ItemStack itemStack : Block.getDrops(state, (ServerWorld) world, pos, blockEntity, player, usedTool))
				droppedItemCallback.accept(itemStack);
			state.spawnAdditionalDrops((ServerWorld) world, pos, ItemStack.EMPTY);
		}

		world.setBlockState(pos, fluidState.getBlockState());
	}

	public static boolean isSolidWall(BlockView reader, BlockPos fromPos, Direction toDirection) {
		return hasBlockSolidSide(reader.getBlockState(fromPos.offset(toDirection)), reader, fromPos.offset(toDirection),
			toDirection.getOpposite());
	}

	public static boolean noCollisionInSpace(BlockView reader, BlockPos pos) {
		return reader.getBlockState(pos)
			.getCollisionShape(reader, pos)
			.isEmpty();
	}

	public static void placeSchematicBlock(World world, BlockState state, BlockPos target, ItemStack stack,
		@Nullable CompoundTag data) {
		// Piston
		if (state.contains(Properties.EXTENDED))
			state = state.with(Properties.EXTENDED, Boolean.FALSE);
		if (state.contains(Properties.WATERLOGGED))
			state = state.with(Properties.WATERLOGGED, Boolean.FALSE);

		if (AllBlocks.BELT.has(state)) {
			world.setBlockState(target, state, 2);
			return;
		} else if (state.getBlock() == Blocks.COMPOSTER)
			state = Blocks.COMPOSTER.getDefaultState();
		else if (state.getBlock() != Blocks.SEA_PICKLE && state.getBlock() instanceof IPlantable)
			state = ((IPlantable) state.getBlock()).getPlant(world, target);

		if (world.getDimension()
			.isUltrawarm()
			&& state.getFluidState()
				.getFluid()
				.isIn(FluidTags.WATER)) {
			int i = target.getX();
			int j = target.getY();
			int k = target.getZ();
			world.playSound(null, target, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F,
				2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

			for (int l = 0; l < 8; ++l) {
				world.addParticle(ParticleTypes.LARGE_SMOKE, i + Math.random(), j + Math.random(), k + Math.random(),
					0.0D, 0.0D, 0.0D);
			}
			Block.dropStacks(state, world, target);
			return;
		}
		world.setBlockState(target, state, 18);
		if (data != null) {
			BlockEntity tile = world.getBlockEntity(target);
			if (tile != null) {
				data.putInt("x", target.getX());
				data.putInt("y", target.getY());
				data.putInt("z", target.getZ());
				if (tile instanceof KineticTileEntity)
					((KineticTileEntity) tile).warnOfMovement();
				tile.fromTag(tile.getBlockState(), data);
			}
		}

		try {
			state.getBlock()
				.onPlaced(world, target, state, null, stack);
		} catch (Exception e) {
		}
	}

	public static double getBounceMultiplier(Block block) {
		if (block instanceof SlimeBlock)
			return 0.8D;
		if (block instanceof BedBlock || block instanceof SeatBlock)
			return 0.66 * 0.8D;
		return 0;
	}

	public static boolean hasBlockSolidSide(BlockState p_220056_0_, BlockView p_220056_1_, BlockPos p_220056_2_,
		Direction p_220056_3_) {
		return !p_220056_0_.isIn(BlockTags.LEAVES)
			&& Block.isFaceFullSquare(p_220056_0_.getCollisionShape(p_220056_1_, p_220056_2_), p_220056_3_);
	}

	public static boolean extinguishFire(World world, @Nullable PlayerEntity p_175719_1_, BlockPos p_175719_2_,
		Direction p_175719_3_) {
		p_175719_2_ = p_175719_2_.offset(p_175719_3_);
		if (world.getBlockState(p_175719_2_)
			.getBlock() == Blocks.FIRE) {
			world.syncWorldEvent(p_175719_1_, 1009, p_175719_2_, 0);
			world.removeBlock(p_175719_2_, false);
			return true;
		} else {
			return false;
		}
	}
}
