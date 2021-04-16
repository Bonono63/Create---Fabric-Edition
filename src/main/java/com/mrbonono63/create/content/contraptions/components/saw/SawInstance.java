package com.mrbonono63.create.content.contraptions.components.saw;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.base.RotatingData;
import com.mrbonono63.create.content.contraptions.base.SingleRotatingInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;

import net.minecraft.block.BlockState;
import net.minecraft.util.Rotation;

public class SawInstance extends SingleRotatingInstance {

    public SawInstance(InstancedTileRenderer<?> modelManager, KineticTileEntity tile) {
        super(modelManager, tile);
    }

    @Override
    protected InstancedModel<RotatingData> getModel() {
        if (blockState.get(FACING).getAxis().isHorizontal()) {
			BlockState referenceState = blockState.rotate(tile.getWorld(), tile.getPos(), Rotation.CLOCKWISE_180);
			return AllBlockPartials.SHAFT_HALF.getModel(getRotatingMaterial(), referenceState, referenceState.get(FACING));
		} else {
			return getRotatingMaterial().getModel(shaft());
		}
    }
}
