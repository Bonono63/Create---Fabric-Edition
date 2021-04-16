package com.mrbonono63.create.content.contraptions.components.actors;

import com.mrbonono63.create.foundation.render.backend.gl.attrib.VertexFormat;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;

import net.minecraft.client.renderer.BufferBuilder;

public class ActorModel extends InstancedModel<ActorData> {
    public static VertexFormat FORMAT = VertexFormat.builder()
            .addAttributes(ActorVertexAttributes.class)
            .build();

    public ActorModel(InstancedTileRenderer<?> renderer, BufferBuilder buf) {
        super(renderer, buf);
    }

    @Override
    protected VertexFormat getInstanceFormat() {
        return FORMAT;
    }

    @Override
    protected ActorData newInstance() {
        return new ActorData(this);
    }
}
