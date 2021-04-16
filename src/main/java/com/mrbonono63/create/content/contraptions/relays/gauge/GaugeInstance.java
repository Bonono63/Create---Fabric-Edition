package com.mrbonono63.create.content.contraptions.relays.gauge;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.base.KineticTileEntity;
import com.mrbonono63.create.content.contraptions.relays.encased.ShaftInstance;
import com.mrbonono63.create.foundation.render.backend.core.ModelData;
import com.mrbonono63.create.foundation.render.backend.instancing.IDynamicInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;
import com.mrbonono63.create.foundation.utility.Couple;
import com.mrbonono63.create.foundation.utility.Iterate;
import com.mrbonono63.create.foundation.utility.MatrixStacker;

import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;

public abstract class GaugeInstance extends ShaftInstance implements IDynamicInstance {

    protected final ArrayList<DialFace> faces;

    protected MatrixStack ms;

    protected GaugeInstance(InstancedTileRenderer<?> dispatcher, KineticTileEntity tile) {
        super(dispatcher, tile);

        faces = new ArrayList<>(2);

        GaugeTileEntity gaugeTile = (GaugeTileEntity) tile;
        GaugeBlock gaugeBlock = (GaugeBlock) blockState.getBlock();

        InstancedModel<ModelData> dialModel = getTransformMaterial().getModel(AllBlockPartials.GAUGE_DIAL, blockState);
        InstancedModel<ModelData> headModel = getHeadModel();

        ms = new MatrixStack();
        MatrixStacker msr = MatrixStacker.of(ms);
        msr.translate(getInstancePosition());

        float progress = MathHelper.lerp(AnimationTickHolder.getPartialTicks(), gaugeTile.prevDialState, gaugeTile.dialState);

        for (Direction facing : Iterate.directions) {
            if (!gaugeBlock.shouldRenderHeadOnFace(world, pos, blockState, facing))
                continue;

            DialFace face = makeFace(facing, dialModel, headModel);

            faces.add(face);

            face.setupTransform(msr, progress);
        }
    }

    private DialFace makeFace(Direction face, InstancedModel<ModelData> dialModel, InstancedModel<ModelData> headModel) {
        return new DialFace(face, dialModel.createInstance(), headModel.createInstance());
    }

    @Override
    public void beginFrame() {
        GaugeTileEntity gaugeTile = (GaugeTileEntity) tile;

        if (MathHelper.epsilonEquals(gaugeTile.prevDialState, gaugeTile.dialState))
            return;

        float progress = MathHelper.lerp(AnimationTickHolder.getPartialTicks(), gaugeTile.prevDialState, gaugeTile.dialState);

        MatrixStacker msr = MatrixStacker.of(ms);

        for (DialFace faceEntry : faces) {
            faceEntry.updateTransform(msr, progress);
        }
    }

    @Override
    public void updateLight() {
        super.updateLight();

        relight(pos, faces.stream()
                          .flatMap(Couple::stream));
    }

    @Override
    public void remove() {
        super.remove();

        faces.forEach(DialFace::delete);
    }

    protected abstract InstancedModel<ModelData> getHeadModel();

    private class DialFace extends Couple<ModelData> {

        Direction face;

        public DialFace(Direction face, ModelData first, ModelData second) {
            super(first, second);
            this.face = face;
        }

        private void setupTransform(MatrixStacker msr, float progress) {
            float dialPivot = 5.75f / 16;

            ms.push();
            rotateToFace(msr);

            getSecond().setTransform(ms);

            msr.translate(0, dialPivot, dialPivot)
               .rotate(Direction.EAST, (float) (Math.PI / 2 * -progress))
               .translate(0, -dialPivot, -dialPivot);

            getFirst().setTransform(ms);

            ms.pop();
        }

        private void updateTransform(MatrixStacker msr, float progress) {
            float dialPivot = 5.75f / 16;

            ms.push();

            rotateToFace(msr)
                    .translate(0, dialPivot, dialPivot)
                    .rotate(Direction.EAST, (float) (Math.PI / 2 * -progress))
                    .translate(0, -dialPivot, -dialPivot);

            getFirst().setTransform(ms);

            ms.pop();
        }

        protected MatrixStacker rotateToFace(MatrixStacker msr) {
            return msr.centre()
                      .rotate(Direction.UP, (float) ((-face.getHorizontalAngle() - 90) / 180 * Math.PI))
                      .unCentre();
        }

        private void delete() {
            getFirst().delete();
            getSecond().delete();
        }
    }

    public static class Speed extends GaugeInstance {
        public Speed(InstancedTileRenderer<?> dispatcher, KineticTileEntity tile) {
            super(dispatcher, tile);
        }

        @Override
        protected InstancedModel<ModelData> getHeadModel() {
            return getTransformMaterial().getModel(AllBlockPartials.GAUGE_HEAD_SPEED, blockState);
        }
    }

    public static class Stress extends GaugeInstance {
        public Stress(InstancedTileRenderer<?> dispatcher, KineticTileEntity tile) {
            super(dispatcher, tile);
        }

        @Override
        protected InstancedModel<ModelData> getHeadModel() {
            return getTransformMaterial().getModel(AllBlockPartials.GAUGE_HEAD_STRESS, blockState);
        }
    }
}
