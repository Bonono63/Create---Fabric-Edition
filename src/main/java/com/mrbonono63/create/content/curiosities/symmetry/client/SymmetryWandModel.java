package com.mrbonono63.create.content.curiosities.symmetry.client;

import com.mrbonono63.create.foundation.block.render.CustomRenderedItemModel;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

public class SymmetryWandModel extends CustomRenderedItemModel {

	public SymmetryWandModel(IBakedModel template) {
		super(template, "wand_of_symmetry");
		addPartials("bits", "core", "core_glow");
	}

	@Override
	public ItemStackTileEntityRenderer createRenderer() {
		return new SymmetryWandItemRenderer();
	}
	
}
