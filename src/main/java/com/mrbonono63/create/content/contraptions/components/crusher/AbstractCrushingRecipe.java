package com.mrbonono63.create.content.contraptions.components.crusher;

import com.simibubi.create.AllRecipeTypes;
import com.mrbonono63.create.content.contraptions.processing.ProcessingRecipe;
import com.mrbonono63.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeParams;

import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class AbstractCrushingRecipe extends ProcessingRecipe<RecipeWrapper> {

	public AbstractCrushingRecipe(AllRecipeTypes recipeType, ProcessingRecipeParams params) {
		super(recipeType, params);
	}

	@Override
	protected int getMaxInputCount() {
		return 1;
	}
}
