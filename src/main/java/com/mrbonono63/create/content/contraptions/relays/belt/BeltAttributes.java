package com.mrbonono63.create.content.contraptions.relays.belt;

import com.mrbonono63.create.foundation.render.backend.gl.attrib.CommonAttributes;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.IAttribSpec;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.IVertexAttrib;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.VertexAttribSpec;

public enum BeltAttributes implements IVertexAttrib {
    INSTANCE_ROTATION("aInstanceRot", CommonAttributes.QUATERNION),
    SOURCE_TEX("aSourceTexture", CommonAttributes.UV),
    SCROLL_TEX("aScrollTexture", CommonAttributes.VEC4),
    SCROLL_MULT("aScrollMult", CommonAttributes.NORMALIZED_BYTE),
    ;

    private final String name;
    private final VertexAttribSpec spec;

    BeltAttributes(String name, VertexAttribSpec spec) {
        this.name = name;
        this.spec = spec;
    }

    @Override
    public String attribName() {
        return name;
    }

    @Override
    public IAttribSpec attribSpec() {
        return spec;
    }

    @Override
    public int getDivisor() {
        return 1;
    }

    @Override
    public int getBufferIndex() {
        return 1;
    }
}
