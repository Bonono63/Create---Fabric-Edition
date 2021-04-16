package com.mrbonono63.create.content.schematics;

public interface ISpecialEntityItemRequirement {

	default ItemRequirement getRequiredItems() {
		return ItemRequirement.INVALID;
	}
	
}
