package com.mrbonono63.create.foundation.ponder.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.foundation.ponder.PonderScene;
import com.mrbonono63.create.foundation.ponder.PonderUI;
import com.mrbonono63.create.foundation.utility.animation.LerpedFloat;

public abstract class AnimatedOverlayElement extends PonderOverlayElement {

	protected LerpedFloat fade;
	
	public AnimatedOverlayElement() {
		fade = LerpedFloat.linear()
			.startWithValue(0);
	}

	public void setFade(float fade) {
		this.fade.setValue(fade);
	}
	
	@Override
	public final void render(PonderScene scene, PonderUI screen, MatrixStack ms, float partialTicks) {
		float currentFade = fade.getValue(partialTicks);
		render(scene, screen, ms, partialTicks, currentFade);
	}

	protected abstract void render(PonderScene scene, PonderUI screen, MatrixStack ms, float partialTicks, float fade);

}
