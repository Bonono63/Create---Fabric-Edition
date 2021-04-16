package com.mrbonono63.create.foundation.ponder.ui;

import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.foundation.gui.UIRenderHelper;
import com.mrbonono63.create.foundation.gui.widgets.AbstractSimiWidget;
import com.mrbonono63.create.foundation.ponder.content.PonderChapter;
import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.client.Minecraft;

public class ChapterLabel extends AbstractSimiWidget {

	private final PonderChapter chapter;
	private final PonderButton button;

	public ChapterLabel(PonderChapter chapter, int x, int y, BiConsumer<Integer, Integer> onClick) {
		super(x, y, 175, 38);

		this.button = new PonderButton(x + 4, y + 4, onClick, 30, 30).showing(chapter);
		this.button.fade(1);

		this.chapter = chapter;
	}

	@Override
	public void render(@Nonnull MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		UIRenderHelper.streak(ms, 0, x, y + height / 2, height - 2, width, 0x101010);
		Minecraft.getInstance().fontRenderer.draw(ms, Lang.translate("ponder.chapter." + chapter.getId()), x + 50,
			y + 20, 0xffddeeff);

		button.renderButton(ms, mouseX, mouseY, partialTicks);
		super.render(ms, mouseX, mouseY, partialTicks);
	}

	@Override
	public void onClick(double x, double y) {
		if (!button.isMouseOver(x, y))
			return;

		button.runCallback(x, y);
	}
}
