package com.mrbonono63.create.content.contraptions.relays.belt;

import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.util.IStringSerializable;

public enum BeltPart implements IStringSerializable {
	START, MIDDLE, END, PULLEY;

	@Override
	public String getString() {
		return Lang.asId(name());
	}
}