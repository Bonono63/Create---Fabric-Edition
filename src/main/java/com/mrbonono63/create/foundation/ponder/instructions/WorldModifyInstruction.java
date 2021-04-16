package com.mrbonono63.create.foundation.ponder.instructions;

import com.mrbonono63.create.foundation.ponder.PonderInstruction;
import com.mrbonono63.create.foundation.ponder.PonderScene;
import com.mrbonono63.create.foundation.ponder.Selection;
import com.mrbonono63.create.foundation.ponder.elements.WorldSectionElement;

public abstract class WorldModifyInstruction extends PonderInstruction {

	private Selection selection;

	public WorldModifyInstruction(Selection selection) {
		this.selection = selection;
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void tick(PonderScene scene) {
		runModification(selection, scene);
		if (needsRedraw()) 
			scene.forEach(WorldSectionElement.class, WorldSectionElement::queueRedraw);
	}

	protected abstract void runModification(Selection selection, PonderScene scene);

	protected abstract boolean needsRedraw();

}
