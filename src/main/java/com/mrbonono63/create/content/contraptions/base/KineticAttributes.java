package com.mrbonono63.create.content.contraptions.base;

import com.mrbonono63.create.foundation.render.backend.gl.attrib.CommonAttributes;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.IAttribSpec;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.IVertexAttrib;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.VertexAttribSpec;

public enum KineticAttributes implements IVertexAttrib {
    INSTANCE_POSITION("aInstancePos", CommonAttributes.VEC3),
    SPEED("aSpeed", CommonAttributes.FLOAT),
    OFFSET("aOffset", CommonAttributes.FLOAT),
    ;

    private final String name;
    private final VertexAttribSpec spec;

    KineticAttributes(String name, VertexAttribSpec spec) {
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
