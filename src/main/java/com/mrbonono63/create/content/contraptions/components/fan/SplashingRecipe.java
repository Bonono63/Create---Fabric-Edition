package com.mrbonono63.create.content.contraptions.components.fan;

import javax.annotation.ParametersAreNonnullByDefault;

import com.simibubi.create.AllRecipeTypes;
import com.mrbonono63.create.content.contraptions.processing.ProcessingRecipe;
import com.mrbonono63.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.mrbonono63.create.content.logistics.InWorldProcessing;
import com.mrbonono63.create.content.logistics.InWorldProcessing.SplashingInv;

import net.minecraft.world.World;

@ParametersAreNonnullByDefault
public class SplashingRecipe extends ProcessingRecipe<SplashingInv> {

	public SplashingRecipe(ProcessingRecipeParams params) {
		super(AllRecipeTypes.SPLASHING, params);
	}

	@Override
	public boolean matches(SplashingInv inv, World worldIn) {
		if (inv.isEmpty())
			return false;
		return ingredients.get(0)
			.test(inv.getStackInSlot(0));
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}

	@Override
	protected int getMaxOutputCount() {
		return 12;
	}

}
