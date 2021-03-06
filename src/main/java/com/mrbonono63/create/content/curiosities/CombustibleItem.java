package com.mrbonono63.create.content.curiosities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CombustibleItem extends Item {
	private int burnTime = -1;

	public CombustibleItem(Properties properties) {
		super(properties);
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime(ItemStack itemStack) {
		return this.burnTime;
	}
}