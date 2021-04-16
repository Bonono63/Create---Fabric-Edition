package com.mrbonono63.create.content.contraptions.components.millstone;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.base.RotatingData;
import com.mrbonono63.create.content.contraptions.base.SingleRotatingInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;

public class MillStoneCogInstance extends SingleRotatingInstance {

    public MillStoneCogInstance(InstancedTileRenderer<?> modelManager, KineticTileEntity tile) {
        super(modelManager, tile);
    }

    @Override
    protected InstancedModel<RotatingData> getModel() {
        return getRotatingMaterial().getModel(AllBlockPartials.MILLSTONE_COG, tile.getBlockState());
    }
}
