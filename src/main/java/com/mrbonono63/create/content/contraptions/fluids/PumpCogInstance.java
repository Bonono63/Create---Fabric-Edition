package com.mrbonono63.create.content.contraptions.fluids;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.base.RotatingData;
import com.mrbonono63.create.content.contraptions.base.SingleRotatingInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;

import net.minecraft.block.BlockState;

public class PumpCogInstance extends SingleRotatingInstance {

    public PumpCogInstance(InstancedTileRenderer<?> modelManager, KineticTileEntity tile) {
        super(modelManager, tile);
    }

    @Override
    protected InstancedModel<RotatingData> getModel() {
        BlockState referenceState = tile.getBlockState();
        return AllBlockPartials.MECHANICAL_PUMP_COG.getModel(getRotatingMaterial(), referenceState, referenceState.get(FACING));
    }
}
