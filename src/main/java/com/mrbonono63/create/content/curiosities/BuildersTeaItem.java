package com.mrbonono63.create.content.curiosities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BuildersTeaItem extends Item {

	public BuildersTeaItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}

	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
		PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		if (playerentity instanceof ServerPlayerEntity)
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerentity, stack);

		if (!world.isRemote) 
			entity.addPotionEffect(new EffectInstance(Effects.HASTE, 3 * 60 * 20, 0, false, false, false));

		if (playerentity != null) {
			playerentity.addStat(Stats.ITEM_USED.get(this));
			playerentity.getFoodStats().addStats(1, .6F);
			if (!playerentity.abilities.isCreativeMode)
				stack.shrink(1);
		}

		if (playerentity == null || !playerentity.abilities.isCreativeMode) {
			if (stack.isEmpty()) 
				return new ItemStack(Items.GLASS_BOTTLE);
			if (playerentity != null) 
				playerentity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
		}

		return stack;
	}

	public int getUseDuration(ItemStack p_77626_1_) {
		return 42;
	}

	public UseAction getUseAction(ItemStack p_77661_1_) {
		return UseAction.DRINK;
	}

	public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
		p_77659_2_.setActiveHand(p_77659_3_);
		return ActionResult.success(p_77659_2_.getHeldItem(p_77659_3_));
	}

}
