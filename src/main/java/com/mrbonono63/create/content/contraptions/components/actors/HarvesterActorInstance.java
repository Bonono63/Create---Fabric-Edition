package com.mrbonono63.create.content.contraptions.components.actors;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.content.contraptions.components.structureMovement.MovementContext;
import com.mrbonono63.create.content.contraptions.components.structureMovement.render.ActorInstance;
import com.mrbonono63.create.content.contraptions.components.structureMovement.render.ContraptionKineticRenderer;
import com.mrbonono63.create.foundation.render.backend.core.ModelData;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;
import com.mrbonono63.create.foundation.render.backend.instancing.RenderMaterial;
import com.mrbonono63.create.foundation.utility.AngleHelper;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;
import com.mrbonono63.create.foundation.utility.MatrixStacker;
import com.mrbonono63.create.foundation.utility.VecHelper;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

public class HarvesterActorInstance extends ActorInstance {
    static double oneOverRadius = 16.0 / 6.5;
    static float originOffset = 1 / 16f;
    static Vector3d rotOffset = new Vector3d(0.5f, -2 * originOffset + 0.5f, originOffset + 0.5f);


    ModelData harvester;
    private Direction facing;

    private float horizontalAngle;

    private double rotation;
    private double previousRotation;

    public HarvesterActorInstance(ContraptionKineticRenderer modelManager, MovementContext context) {
        super(modelManager, context);

        RenderMaterial<?, InstancedModel<ModelData>> renderMaterial = modelManager.getTransformMaterial();

        BlockState state = context.state;

        facing = state.get(HORIZONTAL_FACING);

        harvester = renderMaterial.getModel(AllBlockPartials.HARVESTER_BLADE, state).createInstance();

        horizontalAngle = facing.getHorizontalAngle() + ((facing.getAxis() == Direction.Axis.X) ? 180 : 0);

        harvester.setBlockLight(localBlockLight());
    }

    @Override
    public void tick() {
        super.tick();

        previousRotation = rotation;

        if (context.contraption.stalled || VecHelper.isVecPointingTowards(context.relativeMotion, facing.getOpposite()))
            return;

        double arcLength = context.motion.length();

        double radians = arcLength * oneOverRadius;

        float deg = AngleHelper.deg(radians);

        deg = (float) (((int) (deg * 3000)) / 3000);

        rotation += deg * 1.25;

        rotation %= 360;
    }

    @Override
    public void beginFrame() {
        MatrixStack ms = new MatrixStack();
        MatrixStacker msr = MatrixStacker.of(ms);

        msr.translate(context.localPos)
           .centre()
           .rotateY(horizontalAngle)
           .unCentre()
           .translate(rotOffset)
           .rotateX(getRotation())
           .translateBack(rotOffset);

        harvester.setTransform(ms);
    }

    private double getRotation() {
        return AngleHelper.angleLerp(AnimationTickHolder.getPartialTicks(), previousRotation, rotation);
    }
}
