package com.mrbonono63.create.content.contraptions.relays.belt;

import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.util.IStringSerializable;

public enum BeltSlope implements IStringSerializable {
	HORIZONTAL, UPWARD, DOWNWARD, VERTICAL, SIDEWAYS;

	@Override
	public String getString() {
		return Lang.asId(name());
	}

	public boolean isDiagonal() {
		return this == UPWARD || this == DOWNWARD;
	}
}