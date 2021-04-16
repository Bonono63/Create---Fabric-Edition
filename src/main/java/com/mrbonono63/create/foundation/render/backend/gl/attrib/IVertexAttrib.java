package com.mrbonono63.create.foundation.render.backend.gl.attrib;

public interface IVertexAttrib {

    String attribName();

    IAttribSpec attribSpec();

    int getDivisor();

    int getBufferIndex();
}
