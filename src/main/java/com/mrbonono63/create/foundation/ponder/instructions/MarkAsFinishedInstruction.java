package com.mrbonono63.create.foundation.ponder.instructions;

import com.mrbonono63.create.foundation.ponder.PonderInstruction;
import com.mrbonono63.create.foundation.ponder.PonderScene;

public class MarkAsFinishedInstruction extends PonderInstruction {

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public void tick(PonderScene scene) {
		scene.setFinished(true);
	}
	
	@Override
	public void onScheduled(PonderScene scene) {
		scene.stopCounting();
	}

}
