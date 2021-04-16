package com.mrbonono63.create.foundation.render.backend.instancing;

public interface IInstanceRendered {
    default boolean shouldRenderAsTE() {
        return false;
    }
}
