package com.mrbonono63.create.content.curiosities.zapper.blockzapper;

import javax.annotation.Nullable;

import com.mrbonono63.create.content.curiosities.zapper.blockzapper.BlockzapperItem.ComponentTier;
import com.mrbonono63.create.foundation.block.render.CustomRenderedItemModel;
import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;

public class BlockzapperModel extends CustomRenderedItemModel {

	public BlockzapperModel(IBakedModel template) {
		super(template, "handheld_blockzapper");
		addPartials("core", "core_glow", "body", "amplifier_core", "amplifier_core_glow", "accelerator", "gold_body",
			"gold_scope", "gold_amplifier", "gold_retriever", "gold_accelerator", "chorus_body", "chorus_scope",
			"chorus_amplifier", "chorus_retriever", "chorus_accelerator");
	}

	@Override
	public ItemStackTileEntityRenderer createRenderer() {
		return new BlockzapperItemRenderer();
	}

	@Nullable
	IBakedModel getComponentPartial(ComponentTier tier, BlockzapperItem.Components component) {
		String prefix = tier == ComponentTier.Chromatic ? "chorus_" : tier == ComponentTier.Brass ? "gold_" : "";
		return getPartial(prefix + Lang.asId(component.name()));
	}

}
