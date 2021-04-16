package com.mrbonono63.create.foundation.render.backend.core;

import java.nio.ByteBuffer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrbonono63.create.foundation.render.backend.RenderUtil;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;

public class ModelData extends BasicData {
    private static final float[] empty = new float[25];

    private float[] matrices = empty;

    public ModelData(InstancedModel<?> owner) {
        super(owner);
    }

    public ModelData setTransform(MatrixStack stack) {
        matrices = RenderUtil.writeMatrixStack(stack);
        markDirty();
        return this;
    }

    @Override
    public void write(ByteBuffer buf) {
        super.write(buf);
        buf.asFloatBuffer().put(matrices);
        buf.position(buf.position() + matrices.length * 4);
    }
}
