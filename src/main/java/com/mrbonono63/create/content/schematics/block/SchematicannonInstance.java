package com.mrbonono63.create.content.schematics.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.foundation.render.backend.core.ModelData;
import com.mrbonono63.create.foundation.render.backend.instancing.IDynamicInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.render.backend.instancing.RenderMaterial;
import com.mrbonono63.create.foundation.render.backend.instancing.TileEntityInstance;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;
import com.mrbonono63.create.foundation.utility.MatrixStacker;

import net.minecraft.util.Direction;

public class SchematicannonInstance extends TileEntityInstance<SchematicannonTileEntity> implements IDynamicInstance {

    private final ModelData connector;
    private final ModelData pipe;

    public SchematicannonInstance(InstancedTileRenderer<?> modelManager, SchematicannonTileEntity tile) {
        super(modelManager, tile);

        RenderMaterial<?, InstancedModel<ModelData>> mat = getTransformMaterial();

        connector = mat.getModel(AllBlockPartials.SCHEMATICANNON_CONNECTOR, blockState).createInstance();
        pipe = mat.getModel(AllBlockPartials.SCHEMATICANNON_PIPE, blockState).createInstance();

        beginFrame();
    }

    @Override
    public void beginFrame() {
        float partialTicks = AnimationTickHolder.getPartialTicks();

        double[] cannonAngles = SchematicannonRenderer.getCannonAngles(tile, pos, partialTicks);

        double pitch = cannonAngles[0];
        double yaw = cannonAngles[1];

        double recoil = SchematicannonRenderer.getRecoil(tile, partialTicks);

        MatrixStack ms = new MatrixStack();
        MatrixStacker msr = MatrixStacker.of(ms);

        msr.translate(getInstancePosition());

        ms.push();
        msr.centre();
        msr.rotate(Direction.UP, (float) ((yaw + 90) / 180 * Math.PI));
        msr.unCentre();
        connector.setTransform(ms);
        ms.pop();

        msr.translate(.5f, 15 / 16f, .5f);
        msr.rotate(Direction.UP, (float) ((yaw + 90) / 180 * Math.PI));
        msr.rotate(Direction.SOUTH, (float) (pitch / 180 * Math.PI));
        msr.translate(-.5f, -15 / 16f, -.5f);
        msr.translate(0, -recoil / 100, 0);

        pipe.setTransform(ms);
    }

    @Override
    public void remove() {
        connector.delete();
        pipe.delete();
    }

    @Override
    public void updateLight() {
        relight(pos, connector, pipe);
    }
}
