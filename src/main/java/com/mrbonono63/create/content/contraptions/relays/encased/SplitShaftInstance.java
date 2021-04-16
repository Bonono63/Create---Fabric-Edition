package com.mrbonono63.create.content.contraptions.relays.encased;

import java.util.ArrayList;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.IRotate;
import com.mrbonono63.create.content.contraptions.base.KineticTileInstance;
import com.mrbonono63.create.content.contraptions.base.RotatingData;
import com.mrbonono63.create.foundation.render.backend.instancing.InstanceData;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.render.backend.instancing.RenderMaterial;
import com.mrbonono63.create.foundation.utility.Iterate;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class SplitShaftInstance extends KineticTileInstance<SplitShaftTileEntity> {

    protected final ArrayList<RotatingData> keys;

    public SplitShaftInstance(InstancedTileRenderer<?> modelManager, SplitShaftTileEntity tile) {
        super(modelManager, tile);

        keys = new ArrayList<>(2);

        float speed = tile.getSpeed();

        RenderMaterial<?, InstancedModel<RotatingData>> rotatingMaterial = getRotatingMaterial();

        for (Direction dir : Iterate.directionsInAxis(getRotationAxis())) {

            InstancedModel<RotatingData> half = AllBlockPartials.SHAFT_HALF.getModel(rotatingMaterial, blockState, dir);

            float splitSpeed = speed * tile.getRotationSpeedModifier(dir);

            keys.add(setup(half.createInstance(), splitSpeed));
        }
    }

    @Override
    public void update() {
        Block block = blockState.getBlock();
        final Direction.Axis boxAxis = ((IRotate) block).getRotationAxis(blockState);

        Direction[] directions = Iterate.directionsInAxis(boxAxis);

        for (int i : Iterate.zeroAndOne) {
            updateRotation(keys.get(i), tile.getSpeed() * tile.getRotationSpeedModifier(directions[i]));
        }
    }

    @Override
    public void updateLight() {
        relight(pos, keys.stream());
    }

    @Override
    public void remove() {
        keys.forEach(InstanceData::delete);
        keys.clear();
    }

}
