package com.mrbonono63.create.content.logistics.item.filter;

import java.util.Arrays;
import java.util.List;

import com.mrbonono63.create.content.logistics.item.filter.FilterScreenPacket.Option;
import com.mrbonono63.create.foundation.gui.AllGuiTextures;
import com.mrbonono63.create.foundation.gui.AllIcons;
import com.mrbonono63.create.foundation.gui.widgets.IconButton;
import com.mrbonono63.create.foundation.gui.widgets.Indicator;
import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class FilterScreen extends AbstractFilterScreen<FilterContainer> {

	private static final String PREFIX = "gui.filter.";

	private ITextComponent allowN = Lang.translate(PREFIX + "allow_list");
	private ITextComponent allowDESC = Lang.translate(PREFIX + "allow_list.description");
	private ITextComponent denyN = Lang.translate(PREFIX + "deny_list");
	private ITextComponent denyDESC = Lang.translate(PREFIX + "deny_list.description");

	private ITextComponent respectDataN = Lang.translate(PREFIX + "respect_data");
	private ITextComponent respectDataDESC = Lang.translate(PREFIX + "respect_data.description");
	private ITextComponent ignoreDataN = Lang.translate(PREFIX + "ignore_data");
	private ITextComponent ignoreDataDESC = Lang.translate(PREFIX + "ignore_data.description");

	private IconButton whitelist, blacklist;
	private IconButton respectNBT, ignoreNBT;
	private Indicator whitelistIndicator, blacklistIndicator;
	private Indicator respectNBTIndicator, ignoreNBTIndicator;

	public FilterScreen(FilterContainer container, PlayerInventory inv, ITextComponent title) {
		super(container, inv, title, AllGuiTextures.FILTER);
	}

	@Override
	protected void init() {
		super.init();
		int x = guiLeft;
		int y = guiTop;

		blacklist = new IconButton(x + 18, y + 73, AllIcons.I_BLACKLIST);
		blacklist.setToolTip(denyN);
		whitelist = new IconButton(x + 36, y + 73, AllIcons.I_WHITELIST);
		whitelist.setToolTip(allowN);
		blacklistIndicator = new Indicator(x + 18, y + 67, StringTextComponent.EMPTY);
		whitelistIndicator = new Indicator(x + 36, y + 67, StringTextComponent.EMPTY);
		widgets.addAll(Arrays.asList(blacklist, whitelist, blacklistIndicator, whitelistIndicator));

		respectNBT = new IconButton(x + 60, y + 73, AllIcons.I_RESPECT_NBT);
		respectNBT.setToolTip(respectDataN);
		ignoreNBT = new IconButton(x + 78, y + 73, AllIcons.I_IGNORE_NBT);
		ignoreNBT.setToolTip(ignoreDataN);
		respectNBTIndicator = new Indicator(x + 60, y + 67, StringTextComponent.EMPTY);
		ignoreNBTIndicator = new Indicator(x + 78, y + 67, StringTextComponent.EMPTY);
		widgets.addAll(Arrays.asList(respectNBT, ignoreNBT, respectNBTIndicator, ignoreNBTIndicator));
		handleIndicators();
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		boolean mouseClicked = super.mouseClicked(x, y, button);

		if (button != 0)
			return mouseClicked;

		if (blacklist.isHovered()) {
			container.blacklist = true;
			sendOptionUpdate(Option.BLACKLIST);
			return true;
		}

		if (whitelist.isHovered()) {
			container.blacklist = false;
			sendOptionUpdate(Option.WHITELIST);
			return true;
		}

		if (respectNBT.isHovered()) {
			container.respectNBT = true;
			sendOptionUpdate(Option.RESPECT_DATA);
			return true;
		}

		if (ignoreNBT.isHovered()) {
			container.respectNBT = false;
			sendOptionUpdate(Option.IGNORE_DATA);
			return true;
		}

		return mouseClicked;
	}

	@Override
	protected List<IconButton> getTooltipButtons() {
		return Arrays.asList(blacklist, whitelist, respectNBT, ignoreNBT);
	}

	@Override
	protected List<IFormattableTextComponent> getTooltipDescriptions() {
		return Arrays.asList(denyDESC.copy(), allowDESC.copy(), respectDataDESC.copy(), ignoreDataDESC.copy());
	}

	@Override
	protected boolean isButtonEnabled(IconButton button) {
		if (button == blacklist)
			return !container.blacklist;
		if (button == whitelist)
			return container.blacklist;
		if (button == respectNBT)
			return !container.respectNBT;
		if (button == ignoreNBT)
			return container.respectNBT;
		return true;
	}

	@Override
	protected boolean isIndicatorOn(Indicator indicator) {
		if (indicator == blacklistIndicator)
			return container.blacklist;
		if (indicator == whitelistIndicator)
			return !container.blacklist;
		if (indicator == respectNBTIndicator)
			return container.respectNBT;
		if (indicator == ignoreNBTIndicator)
			return !container.respectNBT;
		return false;
	}

}
