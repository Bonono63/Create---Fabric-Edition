package com.mrbonono63.create.content.contraptions.processing;

import com.mrbonono63.create.foundation.data.AssetLookup;
import com.mrbonono63.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class BasinGenerator extends SpecialBlockStateGen {

	@Override
	protected int getXRotation(BlockState state) {
		return 0;
	}

	@Override
	protected int getYRotation(BlockState state) {
		return horizontalAngle(state.get(BasinBlock.FACING));
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
		BlockState state) {
		if (state.get(BasinBlock.FACING).getAxis().isVertical())
			return AssetLookup.partialBaseModel(ctx, prov);
		return AssetLookup.partialBaseModel(ctx, prov, "directional");
	}

}
