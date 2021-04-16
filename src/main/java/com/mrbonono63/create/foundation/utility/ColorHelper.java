package com.mrbonono63.create.foundation.utility;

import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class ColorHelper {

	public static int rainbowColor(int timeStep) {
		int localTimeStep = timeStep % 1536;
		int timeStepInPhase = localTimeStep % 256;
		int phaseBlue = localTimeStep / 256;
		int red = colorInPhase(phaseBlue + 4, timeStepInPhase);
		int green = colorInPhase(phaseBlue + 2, timeStepInPhase);
		int blue = colorInPhase(phaseBlue, timeStepInPhase);
		return (red << 16) + (green << 8) + (blue);
	}

	private static int colorInPhase(int phase, int progress) {
		phase = phase % 6;
		if (phase <= 1)
			return 0;
		if (phase == 2)
			return progress;
		if (phase <= 4)
			return 255;
		else
			return 255 - progress;
	}

	public static int applyAlpha(int color, float alpha) {
		int prevAlphaChannel = (color >> 24) & 0xFF;
		if (prevAlphaChannel > 0)
			alpha *= prevAlphaChannel / 256f;
		int alphaChannel = (int) (0xFF * MathHelper.clamp(alpha, 0, 1));
		return (color & 0xFFFFFF) | alphaChannel << 24;
	}

	public static int mixColors(int color1, int color2, float w) {
		int r1 = (color1 >> 16);
		int g1 = (color1 >> 8) & 0xFF;
		int b1 = color1 & 0xFF;
		int r2 = (color2 >> 16);
		int g2 = (color2 >> 8) & 0xFF;
		int b2 = color2 & 0xFF;

		int color = ((int) (r1 + (r2 - r1) * w) << 16) + ((int) (g1 + (g2 - g1) * w) << 8) + (int) (b1 + (b2 - b1) * w);

		return color;
	}

	public static int mixAlphaColors(int color1, int color2, float w) {
		int a1 = (color1 >> 24);
		int r1 = (color1 >> 16) & 0xFF;
		int g1 = (color1 >> 8) & 0xFF;
		int b1 = color1 & 0xFF;
		int a2 = (color2 >> 24);
		int r2 = (color2 >> 16) & 0xFF;
		int g2 = (color2 >> 8) & 0xFF;
		int b2 = color2 & 0xFF;

		int color = ((int) (a1 + (a2 - a1) * w) << 24) + ((int) (r1 + (r2 - r1) * w) << 16)
			+ ((int) (g1 + (g2 - g1) * w) << 8) + (int) (b1 + (b2 - b1) * w);

		return color;
	}

	public static void glColor(int color) {
		color = mixColors(color, 0xFFFFFF, .5f);
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;

		RenderSystem.color4f(r / 256f, g / 256f, b / 256f, 1);
	}

	public static void glResetColor() {
		RenderSystem.color4f(1, 1, 1, 1);
	}

	public static Vector3d getRGB(int color) {
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = color & 0xFF;
		return new Vector3d(r, g, b).scale(1 / 256d);
	}

	public static int colorFromUUID(UUID uuid) {
		if (uuid == null)
			return 0x333333;
		return colorFromLong(uuid.getLeastSignificantBits());
	}

	public static int colorFromLong(long l) {
		int rainbowColor = ColorHelper.rainbowColor(String.valueOf(l)
			.hashCode());
		return ColorHelper.mixColors(rainbowColor, 0xFFFFFF, .5f);
	}

}
