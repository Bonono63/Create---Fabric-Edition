package com.mrbonono63.create.content.contraptions.relays.belt;

import com.mrbonono63.create.content.contraptions.base.KineticAttributes;
import com.mrbonono63.create.foundation.render.backend.core.BasicAttributes;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.VertexFormat;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;

import net.minecraft.client.renderer.BufferBuilder;

public class BeltInstancedModel extends InstancedModel<BeltData> {
    public static VertexFormat FORMAT = VertexFormat.builder()
			.addAttributes(BasicAttributes.class)
            .addAttributes(KineticAttributes.class)
            .addAttributes(BeltAttributes.class)
            .build();

	public BeltInstancedModel(InstancedTileRenderer<?> renderer, BufferBuilder buf) {
        super(renderer, buf);
    }

	@Override
	protected BeltData newInstance() {
        return new BeltData(this);
    }

    @Override
	protected VertexFormat getInstanceFormat() {
        return FORMAT;
    }

}
