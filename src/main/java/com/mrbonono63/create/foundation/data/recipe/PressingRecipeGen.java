package com.mrbonono63.create.foundation.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;

import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class PressingRecipeGen extends ProcessingRecipeGen {

	GeneratedRecipe

	SUGAR_CANE = create(() -> Items.SUGAR_CANE, b -> b.output(Items.PAPER)),

		PATH = create("path", b -> b.require(Ingredient.fromItems(Items.GRASS_BLOCK, Items.DIRT, Items.PODZOL))
			.output(Items.GRASS_PATH)),

		IRON = create("iron_ingot", b -> b.require(I.iron())
			.output(AllItems.IRON_SHEET.get())),
		GOLD = create("gold_ingot", b -> b.require(I.gold())
			.output(AllItems.GOLDEN_SHEET.get())),
		COPPER = create("copper_ingot", b -> b.require(I.copper())
			.output(AllItems.COPPER_SHEET.get())),
		LAPIS = create("lapis_block", b -> b.require(Blocks.LAPIS_BLOCK)
			.output(AllItems.LAPIS_SHEET.get())),
		BRASS = create("brass_ingot", b -> b.require(I.brass())
			.output(AllItems.BRASS_SHEET.get()))

	;

	public PressingRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_);
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.PRESSING;
	}

}
