package com.mrbonono63.create.content.logistics.item.filter;

import static com.mrbonono63.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;
import static net.minecraft.util.text.TextFormatting.GRAY;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.content.logistics.item.filter.FilterScreenPacket.Option;
import com.mrbonono63.create.foundation.gui.AbstractSimiContainerScreen;
import com.mrbonono63.create.foundation.gui.AllGuiTextures;
import com.mrbonono63.create.foundation.gui.AllIcons;
import com.mrbonono63.create.foundation.gui.GuiGameElement;
import com.mrbonono63.create.foundation.gui.widgets.IconButton;
import com.mrbonono63.create.foundation.gui.widgets.Indicator;
import com.mrbonono63.create.foundation.gui.widgets.Indicator.State;
import com.mrbonono63.create.foundation.item.ItemDescription.Palette;
import com.mrbonono63.create.foundation.item.TooltipHelper;
import com.mrbonono63.create.foundation.networking.AllPackets;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractFilterScreen<F extends AbstractFilterContainer> extends AbstractSimiContainerScreen<F> {

	protected AllGuiTextures background;
    private List<Rectangle2d> extraAreas = Collections.EMPTY_LIST;

	private IconButton resetButton;
	private IconButton confirmButton;

	protected AbstractFilterScreen(F container, PlayerInventory inv, ITextComponent title, AllGuiTextures background) {
		super(container, inv, title);
		this.background = background;
	}

	@Override
	protected void init() {
		setWindowSize(PLAYER_INVENTORY.width, background.height + PLAYER_INVENTORY.height + 20);
		super.init();
		widgets.clear();
		int x = guiLeft - 50;
		int offset = guiTop < 30 ? 30 - guiTop : 0;
		extraAreas = ImmutableList.of(new Rectangle2d(x, guiTop + offset, background.width + 70, background.height - offset));

		resetButton = new IconButton(x + background.width - 62, guiTop + background.height - 24, AllIcons.I_TRASH);
		confirmButton = new IconButton(x + background.width - 33, guiTop + background.height - 24, AllIcons.I_CONFIRM);

		widgets.add(resetButton);
		widgets.add(confirmButton);
	}

	@Override
	protected void renderWindow(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		int x = guiLeft - 50;
		int y = guiTop;
		background.draw(ms, this, x, y);

		int invX = guiLeft;
		int invY = y + background.height + 10;
		PLAYER_INVENTORY.draw(ms, this, invX, invY);
		textRenderer.draw(ms, playerInventory.getDisplayName(), invX + 7, invY + 6, 0x666666);
		textRenderer.draw(ms, I18n.format(container.filterItem.getTranslationKey()), x + 15, y + 3, 0xdedede);

		GuiGameElement.of(container.filterItem)
				.at(x + background.width, guiTop + background.height - 60)
				.scale(5)
				.render(ms);

	}

	@Override
	public void tick() {
		handleTooltips();
		super.tick();
		handleIndicators();

		if (!container.player.getHeldItemMainhand()
			.equals(container.filterItem, false))
			client.player.closeScreen();
	}

	public void handleIndicators() {
		List<IconButton> tooltipButtons = getTooltipButtons();
		for (IconButton button : tooltipButtons)
			button.active = isButtonEnabled(button);
		for (Widget w : widgets)
			if (w instanceof Indicator)
				((Indicator) w).state = isIndicatorOn((Indicator) w) ? State.ON : State.OFF;
	}

	protected abstract boolean isButtonEnabled(IconButton button);

	protected abstract boolean isIndicatorOn(Indicator indicator);

	protected void handleTooltips() {
		List<IconButton> tooltipButtons = getTooltipButtons();

		for (IconButton button : tooltipButtons) {
			if (!button.getToolTip()
				.isEmpty()) {
				button.setToolTip(button.getToolTip()
					.get(0));
				button.getToolTip()
					.add(TooltipHelper.holdShift(Palette.Yellow, hasShiftDown()));
			}
		}

		if (hasShiftDown()) {
			List<IFormattableTextComponent> tooltipDescriptions = getTooltipDescriptions();
			for (int i = 0; i < tooltipButtons.size(); i++)
				fillToolTip(tooltipButtons.get(i), tooltipDescriptions.get(i));
		}
	}

	protected List<IconButton> getTooltipButtons() {
		return Collections.emptyList();
	}

	protected List<IFormattableTextComponent> getTooltipDescriptions() {
		return Collections.emptyList();
	}

	private void fillToolTip(IconButton button, ITextComponent tooltip) {
		if (!button.isHovered())
			return;
		List<ITextComponent> tip = button.getToolTip();
		tip.addAll(TooltipHelper.cutTextComponent(tooltip, GRAY, GRAY));
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		boolean mouseClicked = super.mouseClicked(x, y, button);

		if (button == 0) {
			if (confirmButton.isHovered()) {
				client.player.closeScreen();
				return true;
			}
			if (resetButton.isHovered()) {
				container.clearContents();
				contentsCleared();
				sendOptionUpdate(Option.CLEAR);
				return true;
			}
		}

		return mouseClicked;
	}

	protected void contentsCleared() {}

	protected void sendOptionUpdate(Option option) {
		AllPackets.channel.sendToServer(new FilterScreenPacket(option));
	}

	@Override
	public List<Rectangle2d> getExtraAreas() {
		return extraAreas;
	}
}
