package com.mrbonono63.create.content.contraptions.base;

import com.mrbonono63.create.content.contraptions.components.actors.ActorData;
import com.mrbonono63.create.content.contraptions.relays.belt.BeltData;
import com.mrbonono63.create.content.logistics.block.FlapData;
import com.mrbonono63.create.foundation.render.backend.MaterialType;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;

public class KineticRenderMaterials {
    public static final MaterialType<InstancedModel<RotatingData>> ROTATING = new MaterialType<>();
    public static final MaterialType<InstancedModel<BeltData>> BELTS = new MaterialType<>();

    public static final MaterialType<InstancedModel<ActorData>> ACTORS = new MaterialType<>();

    public static final MaterialType<InstancedModel<FlapData>> FLAPS = new MaterialType<>();
}
