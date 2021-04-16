package com.mrbonono63.create.foundation.ponder.instructions;

import com.mrbonono63.create.foundation.ponder.elements.ParrotElement;

import net.minecraft.util.Direction;

public class CreateParrotInstruction extends FadeIntoSceneInstruction<ParrotElement> {

	public CreateParrotInstruction(int fadeInTicks, Direction fadeInFrom, ParrotElement element) {
		super(fadeInTicks, fadeInFrom, element);
	}
	
	@Override
	protected Class<ParrotElement> getElementClass() {
		return ParrotElement.class;
	}

}
