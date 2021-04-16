package com.mrbonono63.create.content.curiosities.tools;

import com.mrbonono63.create.foundation.block.render.CustomRenderedItemModel;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

public class ExtendoGripModel extends CustomRenderedItemModel {

	public ExtendoGripModel(IBakedModel template) {
		super(template, "extendo_grip");
		addPartials("cog", "thin_short", "wide_short", "thin_long", "wide_long");
	}

	@Override
	public ItemStackTileEntityRenderer createRenderer() {
		return new ExtendoGripItemRenderer();
	}

}
