package com.mrbonono63.create.content.contraptions.components.press;

import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.relays.encased.ShaftInstance;
import com.mrbonono63.create.foundation.render.backend.core.OrientedData;
import com.mrbonono63.create.foundation.render.backend.instancing.IDynamicInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.utility.AngleHelper;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;

import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class PressInstance extends ShaftInstance implements IDynamicInstance {

    private final OrientedData pressHead;
    private final MechanicalPressTileEntity press;

    public PressInstance(InstancedTileRenderer<?> dispatcher, MechanicalPressTileEntity tile) {
        super(dispatcher, tile);
        press = tile;

        pressHead = dispatcher.getOrientedMaterial()
                .getModel(AllBlockPartials.MECHANICAL_PRESS_HEAD, blockState)
                .createInstance();

        Quaternion q = Vector3f.POSITIVE_Y.getDegreesQuaternion(AngleHelper.horizontalAngle(blockState.get(MechanicalPressBlock.HORIZONTAL_FACING)));

        pressHead.setRotation(q);

        transformModels();
    }

    @Override
    public void beginFrame() {
        if (!press.running)
            return;

        transformModels();
    }

    private void transformModels() {
        float renderedHeadOffset = getRenderedHeadOffset(press);

        pressHead.setPosition(getInstancePosition())
                .nudge(0, -renderedHeadOffset, 0);
    }

    private float getRenderedHeadOffset(MechanicalPressTileEntity press) {
        return press.getRenderedHeadOffset(AnimationTickHolder.getPartialTicks());
    }

    @Override
    public void updateLight() {
        super.updateLight();

        relight(pos, pressHead);
    }

    @Override
    public void remove() {
        super.remove();
        pressHead.delete();
    }
}
