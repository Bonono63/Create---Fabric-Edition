package com.mrbonono63.create.foundation.ponder.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.foundation.ponder.PonderElement;
import com.mrbonono63.create.foundation.ponder.PonderScene;
import com.mrbonono63.create.foundation.ponder.PonderUI;

public abstract class PonderOverlayElement extends PonderElement {

	public void tick(PonderScene scene) {}

	public abstract void render(PonderScene scene, PonderUI screen, MatrixStack ms, float partialTicks);

}
