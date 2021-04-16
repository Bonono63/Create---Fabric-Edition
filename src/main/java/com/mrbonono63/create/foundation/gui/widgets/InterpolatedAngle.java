package com.mrbonono63.create.foundation.gui.widgets;

import com.mrbonono63.create.foundation.utility.AngleHelper;

public class InterpolatedAngle extends InterpolatedValue {
	
	public float get(float partialTicks) {
		return AngleHelper.angleLerp(partialTicks, lastValue, value);
	}

}
