package com.mrbonono63.create.content.schematics;

import net.minecraft.block.BlockState;

public interface ISpecialBlockItemRequirement {

	default ItemRequirement getRequiredItems(BlockState state) {
		return ItemRequirement.INVALID;
	}
	
}
