package com.mrbonono63.create.foundation.config;

public class CClient extends ConfigBase {

	public ConfigGroup client = group(0, "client",
		"Client-only settings - If you're looking for general settings, look inside your worlds serverconfig folder!");
	public ConfigBool tooltips = b(true, "enableTooltips", "Show item descriptions on Shift and controls on Ctrl.");
	public ConfigBool enableOverstressedTooltip =
		b(true, "enableOverstressedTooltip", "Display a tooltip when looking at overstressed components.");
	public ConfigBool explainRenderErrors =
		b(false, "explainRenderErrors", "Log a stack-trace when rendering issues happen within a moving contraption.");
	public ConfigFloat fanParticleDensity = f(.5f, 0, 1, "fanParticleDensity");
	public ConfigBool rainbowDebug =
		b(true, "enableRainbowDebug", "Show colourful debug information while the F3-Menu is open.");
	public ConfigBool experimentalRendering =
		b(true, "experimentalRendering", "Use modern OpenGL features to drastically increase performance.");
	public ConfigInt overlayOffsetX = i(20, Integer.MIN_VALUE, Integer.MAX_VALUE, "overlayOffsetX",
		"Offset the overlay from goggle- and hover- information by this many pixels on the X axis; Use /create overlay");
	public ConfigInt overlayOffsetY = i(0, Integer.MIN_VALUE, Integer.MAX_VALUE, "overlayOffsetY",
		"Offset the overlay from goggle- and hover- information by this many pixels on the Y axis; Use /create overlay");

	public ConfigBool ignoreFabulousWarning = b(false, "ignoreFabulousWarning",
		"Setting this to true will prevent Create from sending you a warning when playing with Fabulous graphics enabled");

	public ConfigGroup placementAssist = group(1, "placementAssist", "Settings for the Placement Assist");
	public ConfigEnum<PlacementIndicatorSetting> placementIndicator = e(PlacementIndicatorSetting.TEXTURE,
		"indicatorType",
		"What indicator should be used when showing where the assisted placement ends up relative to your crosshair",
		"Choose 'NONE' to disable the Indicator altogether");
	public ConfigFloat indicatorScale =
		f(1.0f, 0f, "indicatorScale", "Change the size of the Indicator by this multiplier");

	public ConfigGroup ponder = group(1, "ponder", "Ponder settings");
	public ConfigBool comfyReading =
		b(false, "comfyReading", "Slow down a ponder scene whenever there is text on screen.");

	@Override
	public String getName() {
		return "client";
	}

	public enum PlacementIndicatorSetting {
		TEXTURE, TRIANGLE, NONE
	}
}
