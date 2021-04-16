package com.mrbonono63.create.content.contraptions.base;

import com.mrbonono63.create.foundation.render.backend.core.BasicAttributes;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.VertexFormat;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;

import net.minecraft.client.renderer.BufferBuilder;

public class RotatingModel extends InstancedModel<RotatingData> {
    public static VertexFormat FORMAT = VertexFormat.builder()
            .addAttributes(BasicAttributes.class)
            .addAttributes(KineticAttributes.class)
            .addAttributes(RotatingAttributes.class)
            .build();

    public RotatingModel(InstancedTileRenderer<?> renderer, BufferBuilder buf) {
        super(renderer, buf);
    }

    @Override
    protected RotatingData newInstance() {
        return new RotatingData(this);
    }

    @Override
    protected VertexFormat getInstanceFormat() {
        return FORMAT;
    }

}
