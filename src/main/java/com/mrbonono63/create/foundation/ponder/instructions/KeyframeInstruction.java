package com.mrbonono63.create.foundation.ponder.instructions;

import com.mrbonono63.create.foundation.ponder.PonderInstruction;
import com.mrbonono63.create.foundation.ponder.PonderScene;

public class KeyframeInstruction extends PonderInstruction {

    public static final KeyframeInstruction IMMEDIATE = new KeyframeInstruction(false);
    public static final KeyframeInstruction DELAYED = new KeyframeInstruction(true);
    
	private boolean delayed;

    private KeyframeInstruction(boolean delayed) {
		this.delayed = delayed; 
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public void tick(PonderScene scene) { }

    @Override
    public void onScheduled(PonderScene scene) {
        scene.markKeyframe(delayed ? 6 : 0);
    }
}
