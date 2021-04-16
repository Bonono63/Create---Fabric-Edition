package com.mrbonono63.create.content;

import java.util.EnumSet;

import com.simibubi.create.AllBlocks;
import com.mrbonono63.create.foundation.item.CreateItemGroupBase;

import net.minecraft.item.ItemStack;

public class CreateItemGroup extends CreateItemGroupBase {

	public CreateItemGroup() {
		super("base");
	}

	@Override
	protected EnumSet<AllSections> getSections() {
		return EnumSet.complementOf(EnumSet.of(AllSections.PALETTES));
	}

	@Override
	public ItemStack createIcon() {
		return AllBlocks.COGWHEEL.asStack();
	}

}
