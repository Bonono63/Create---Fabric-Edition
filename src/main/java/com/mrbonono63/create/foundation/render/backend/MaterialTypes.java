package com.mrbonono63.create.foundation.render.backend;

import com.mrbonono63.create.foundation.render.backend.core.ModelData;
import com.mrbonono63.create.foundation.render.backend.core.OrientedData;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;

public class MaterialTypes {
    public static final MaterialType<InstancedModel<ModelData>> TRANSFORMED = new MaterialType<>();
    public static final MaterialType<InstancedModel<OrientedData>> ORIENTED = new MaterialType<>();
}
