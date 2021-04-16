package com.mrbonono63.create.content.logistics.block;

import com.mrbonono63.create.foundation.render.backend.gl.attrib.VertexFormat;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;

import net.minecraft.client.renderer.BufferBuilder;

public class FlapModel extends InstancedModel<FlapData> {
	public static VertexFormat FORMAT = VertexFormat.builder()
			.addAttributes(FlapAttributes.class)
			.build();

	public FlapModel(InstancedTileRenderer<?> renderer, BufferBuilder buf) {
		super(renderer, buf);
	}

	@Override
	protected FlapData newInstance() {
		return new FlapData(this);
	}

	@Override
	protected VertexFormat getInstanceFormat() {
		return FORMAT;
	}
}
