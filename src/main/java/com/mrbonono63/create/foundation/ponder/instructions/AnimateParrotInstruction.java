package com.mrbonono63.create.foundation.ponder.instructions;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.mrbonono63.create.foundation.ponder.ElementLink;
import com.mrbonono63.create.foundation.ponder.elements.ParrotElement;

import net.minecraft.util.math.vector.Vector3d;

public class AnimateParrotInstruction extends AnimateElementInstruction<ParrotElement> {

	public static AnimateParrotInstruction rotate(ElementLink<ParrotElement> link, Vector3d rotation, int ticks) {
		return new AnimateParrotInstruction(link, rotation, ticks, (wse, v) -> wse.setRotation(v, ticks == 0),
			ParrotElement::getRotation);
	}

	public static AnimateParrotInstruction move(ElementLink<ParrotElement> link, Vector3d offset, int ticks) {
		return new AnimateParrotInstruction(link, offset, ticks, (wse, v) -> wse.setPositionOffset(v, ticks == 0),
			ParrotElement::getPositionOffset);
	}

	protected AnimateParrotInstruction(ElementLink<ParrotElement> link, Vector3d totalDelta, int ticks,
		BiConsumer<ParrotElement, Vector3d> setter, Function<ParrotElement, Vector3d> getter) {
		super(link, totalDelta, ticks, setter, getter);
	}

}
