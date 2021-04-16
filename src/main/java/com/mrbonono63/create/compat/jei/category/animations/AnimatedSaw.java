package com.mrbonono63.create.compat.jei.category.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.animations.AnimatedKinetics;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllBlocks;
import com.mrbonono63.create.content.contraptions.components.saw.SawBlock;
import com.mrbonono63.create.foundation.gui.AllGuiTextures;
import com.mrbonono63.create.foundation.gui.GuiGameElement;

import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.vector.Vector3f;

public class AnimatedSaw extends AnimatedKinetics {

	@Override
	public void draw(MatrixStack matrixStack, int xOffset, int yOffset) {
		matrixStack.push();
		matrixStack.translate(xOffset, yOffset, 0);
		AllGuiTextures.JEI_SHADOW.draw(matrixStack, -16, 13);

		matrixStack.translate(0, 0, 200);
		matrixStack.translate(29, 17, 0);
		matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-22.5f));
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90 - 225f));
		int scale = 25;

		GuiGameElement.of(shaft(Axis.X))
			.rotateBlock(-getCurrentAngle(), 0, 0)
			.scale(scale)
			.render(matrixStack);

		GuiGameElement.of(AllBlocks.MECHANICAL_SAW.getDefaultState()
			.with(SawBlock.FACING, Direction.UP))
			.rotateBlock(0, 0, 0)
			.scale(scale)
			.render(matrixStack);

		GuiGameElement.of(AllBlockPartials.SAW_BLADE_VERTICAL_ACTIVE)
			.rotateBlock(0, -90, -90)
			.scale(scale)
			.render(matrixStack);

		matrixStack.pop();
	}

}
