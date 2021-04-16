package com.mrbonono63.create.foundation.block.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mrbonono63.create.foundation.block.IBlockVertexColor;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;

public class ColoredVertexModel extends BakedModelWrapper<IBakedModel> {

	private IBlockVertexColor color;
	private static ModelProperty<BlockPos> POSITION_PROPERTY = new ModelProperty<>();

	public ColoredVertexModel(IBakedModel originalModel, IBlockVertexColor color) {
		super(originalModel);
		this.color = color;
	}

	@Override
	public IModelData getModelData(IBlockDisplayReader world, BlockPos pos, BlockState state, IModelData tileData) {
		return new ModelDataMap.Builder().withInitial(POSITION_PROPERTY, pos).build();
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
		List<BakedQuad> quads = new ArrayList<>(super.getQuads(state, side, rand, extraData));
		if (!extraData.hasProperty(POSITION_PROPERTY))
			return quads;
		if (quads.isEmpty())
			return quads;
		
		// Optifine might've rejigged vertex data
		VertexFormat format = DefaultVertexFormats.BLOCK;
		int colorIndex = 0;
		for (int j = 0; j < format.getElements().size(); j++) {
			VertexFormatElement e = format.getElements().get(j);
			if (e.getUsage() == VertexFormatElement.Usage.COLOR)
				colorIndex = j;
		}
		int colorOffset = format.getOffset(colorIndex) / 4; 
		BlockPos data = extraData.getData(POSITION_PROPERTY);
		
		for (int i = 0; i < quads.size(); i++) {
			BakedQuad quad = quads.get(i);
			BakedQuad newQuad = new BakedQuad(Arrays.copyOf(quad.getVertexData(), quad.getVertexData().length),
					quad.getTintIndex(), quad.getFace(), quad.getSprite(), quad.hasShade());
			int[] vertexData = newQuad.getVertexData();

			for (int vertex = 0; vertex < vertexData.length; vertex += format.getIntegerSize()) {
				float x = Float.intBitsToFloat(vertexData[vertex]);
				float y = Float.intBitsToFloat(vertexData[vertex + 1]);
				float z = Float.intBitsToFloat(vertexData[vertex + 2]);
				int color = this.color.getColor(x + data.getX(), y + data.getY(), z + data.getZ());
				vertexData[vertex + colorOffset] = color;
			}

			quads.set(i, newQuad);
		}
		return quads;
	}

}
