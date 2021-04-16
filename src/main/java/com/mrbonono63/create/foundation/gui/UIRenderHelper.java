package com.mrbonono63.create.foundation.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mrbonono63.create.foundation.utility.ColorHelper;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class UIRenderHelper {

	public static Framebuffer framebuffer;

	public static void init() {
		RenderSystem.recordRenderCall(() -> {
			MainWindow mainWindow = Minecraft.getInstance()
				.getWindow();
			framebuffer = new Framebuffer(mainWindow.getFramebufferWidth(), mainWindow.getFramebufferHeight(), true,
				Minecraft.IS_RUNNING_ON_MAC);
			framebuffer.setFramebufferColor(0, 0, 0, 0);
//			framebuffer.deleteFramebuffer();
		});
	}

	public static void prepFramebufferSize() {
		MainWindow window = Minecraft.getInstance()
			.getWindow();
		if (framebuffer.framebufferWidth != window.getFramebufferWidth()
			|| framebuffer.framebufferHeight != window.getFramebufferHeight()) {
			framebuffer.func_216491_a(window.getFramebufferWidth(), window.getFramebufferHeight(),
				Minecraft.IS_RUNNING_ON_MAC);
		}
	}

	public static void drawFramebuffer(float alpha) {
		MainWindow window = Minecraft.getInstance()
			.getWindow();

		float vx = (float) window.getScaledWidth();
		float vy = (float) window.getScaledHeight();
		float tx = (float) framebuffer.framebufferWidth / (float) framebuffer.framebufferTextureWidth;
		float ty = (float) framebuffer.framebufferHeight / (float) framebuffer.framebufferTextureHeight;

		RenderSystem.enableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableLighting();
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();

		framebuffer.bindFramebufferTexture();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEXTURE);

		bufferbuilder.vertex(0, vy, 0)
			.color(1, 1, 1, alpha)
			.texture(0, 0)
			.endVertex();
		bufferbuilder.vertex(vx, vy, 0)
			.color(1, 1, 1, alpha)
			.texture(tx, 0)
			.endVertex();
		bufferbuilder.vertex(vx, 0, 0)
			.color(1, 1, 1, alpha)
			.texture(tx, ty)
			.endVertex();
		bufferbuilder.vertex(0, 0, 0)
			.color(1, 1, 1, alpha)
			.texture(0, ty)
			.endVertex();

		tessellator.draw();
		framebuffer.unbindFramebufferTexture();
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
	}

	// angle in degrees; 0° -> fading to the right
	// x and y specify the middle point of the starting edge
	// width is the total width of the streak
	public static void streak(MatrixStack ms, float angle, int x, int y, int width, int length, int color) {
		int a1 = 0xa0 << 24;
		int a2 = 0x80 << 24;
		int a3 = 0x10 << 24;
		int a4 = 0x00 << 24;

		color = color & 0x00FFFFFF;
		int c1 = a1 | color;
		int c2 = a2 | color;
		int c3 = a3 | color;
		int c4 = a4 | color;

		ms.push();
		ms.translate(x, y, 0);
		ms.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(angle - 90));

		streak(ms, width / 2, length, c1, c2, c3, c4);

		ms.pop();
	}

	private static void streak(MatrixStack ms, int width, int height, int c1, int c2, int c3, int c4) {
		double split1 = .5;
		double split2 = .75;
		Matrix4f model = ms.peek()
			.getModel();
		GuiUtils.drawGradientRect(model, 0, -width, 0, width, (int) (split1 * height), c1, c2);
		GuiUtils.drawGradientRect(model, 0, -width, (int) (split1 * height), width, (int) (split2 * height), c2, c3);
		GuiUtils.drawGradientRect(model, 0, -width, (int) (split2 * height), width, height, c3, c4);
	}

	// draws a wide chevron-style breadcrumb arrow pointing left
	public static void breadcrumbArrow(MatrixStack matrixStack, int x, int y, int z, int width, int height, int indent,
		int startColor, int endColor) {
		matrixStack.push();
		matrixStack.translate(x - indent, y, z);

		breadcrumbArrow(matrixStack, width, height, indent, startColor, endColor);

		matrixStack.pop();
	}

	private static void breadcrumbArrow(MatrixStack ms, int width, int height, int indent, int c1, int c2) {

		/*
		 * 0,0 x1,y1 ********************* x4,y4 ***** x7,y7
		 * **** ****
		 * **** ****
		 * x0,y0 x2,y2 x5,y5
		 * **** ****
		 * **** ****
		 * x3,y3 ********************* x6,y6 ***** x8,y8
		 *
		 */

		float x0 = 0, y0 = height / 2f;
		float x1 = indent, y1 = 0;
		float x2 = indent, y2 = height / 2f;
		float x3 = indent, y3 = height;
		float x4 = width, y4 = 0;
		float x5 = width, y5 = height / 2f;
		float x6 = width, y6 = height;
		float x7 = indent + width, y7 = 0;
		float x8 = indent + width, y8 = height;

		indent = Math.abs(indent);
		width = Math.abs(width);
		int fc1 = ColorHelper.mixAlphaColors(c1, c2, 0);
		int fc2 = ColorHelper.mixAlphaColors(c1, c2, (indent) / (width + 2f * indent));
		int fc3 = ColorHelper.mixAlphaColors(c1, c2, (indent + width) / (width + 2f * indent));
		int fc4 = ColorHelper.mixAlphaColors(c1, c2, 1);

		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableCull();
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.shadeModel(GL11.GL_SMOOTH);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		Matrix4f model = ms.peek()
			.getModel();
		bufferbuilder.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);

		bufferbuilder.vertex(model, x0, y0, 0)
			.color(fc1 >> 16 & 0xFF, fc1 >> 8 & 0xFF, fc1 & 0xFF, fc1 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x1, y1, 0)
			.color(fc2 >> 16 & 0xFF, fc2 >> 8 & 0xFF, fc2 & 0xFF, fc2 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x2, y2, 0)
			.color(fc2 >> 16 & 0xFF, fc2 >> 8 & 0xFF, fc2 & 0xFF, fc2 >> 24 & 0xFF)
			.endVertex();

		bufferbuilder.vertex(model, x0, y0, 0)
			.color(fc1 >> 16 & 0xFF, fc1 >> 8 & 0xFF, fc1 & 0xFF, fc1 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x2, y2, 0)
			.color(fc2 >> 16 & 0xFF, fc2 >> 8 & 0xFF, fc2 & 0xFF, fc2 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x3, y3, 0)
			.color(fc2 >> 16 & 0xFF, fc2 >> 8 & 0xFF, fc2 & 0xFF, fc2 >> 24 & 0xFF)
			.endVertex();

		bufferbuilder.vertex(model, x3, y3, 0)
			.color(fc2 >> 16 & 0xFF, fc2 >> 8 & 0xFF, fc2 & 0xFF, fc2 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x1, y1, 0)
			.color(fc2 >> 16 & 0xFF, fc2 >> 8 & 0xFF, fc2 & 0xFF, fc2 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x4, y4, 0)
			.color(fc3 >> 16 & 0xFF, fc3 >> 8 & 0xFF, fc3 & 0xFF, fc3 >> 24 & 0xFF)
			.endVertex();

		bufferbuilder.vertex(model, x3, y3, 0)
			.color(fc2 >> 16 & 0xFF, fc2 >> 8 & 0xFF, fc2 & 0xFF, fc2 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x4, y4, 0)
			.color(fc3 >> 16 & 0xFF, fc3 >> 8 & 0xFF, fc3 & 0xFF, fc3 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x6, y6, 0)
			.color(fc3 >> 16 & 0xFF, fc3 >> 8 & 0xFF, fc3 & 0xFF, fc3 >> 24 & 0xFF)
			.endVertex();

		bufferbuilder.vertex(model, x5, y5, 0)
			.color(fc3 >> 16 & 0xFF, fc3 >> 8 & 0xFF, fc3 & 0xFF, fc3 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x4, y4, 0)
			.color(fc3 >> 16 & 0xFF, fc3 >> 8 & 0xFF, fc3 & 0xFF, fc3 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x7, y7, 0)
			.color(fc4 >> 16 & 0xFF, fc4 >> 8 & 0xFF, fc4 & 0xFF, fc4 >> 24 & 0xFF)
			.endVertex();

		bufferbuilder.vertex(model, x6, y6, 0)
			.color(fc3 >> 16 & 0xFF, fc3 >> 8 & 0xFF, fc3 & 0xFF, fc3 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x5, y5, 0)
			.color(fc3 >> 16 & 0xFF, fc3 >> 8 & 0xFF, fc3 & 0xFF, fc3 >> 24 & 0xFF)
			.endVertex();
		bufferbuilder.vertex(model, x8, y8, 0)
			.color(fc4 >> 16 & 0xFF, fc4 >> 8 & 0xFF, fc4 & 0xFF, fc4 >> 24 & 0xFF)
			.endVertex();

		tessellator.draw();
		RenderSystem.shadeModel(GL11.GL_FLAT);
		RenderSystem.disableBlend();
		RenderSystem.enableCull();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}
}
