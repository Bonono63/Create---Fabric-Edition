package com.mrbonono63.create.content.contraptions.components.structureMovement.chassis;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.AllBlockPartials;
import com.mrbonono63.create.foundation.render.backend.core.ModelData;
import com.mrbonono63.create.foundation.render.backend.instancing.IDynamicInstance;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.render.backend.instancing.TileEntityInstance;
import com.mrbonono63.create.foundation.utility.AngleHelper;
import com.mrbonono63.create.foundation.utility.AnimationTickHolder;
import com.mrbonono63.create.foundation.utility.MatrixStacker;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;

public class StickerInstance extends TileEntityInstance<StickerTileEntity> implements IDynamicInstance {

    float lastOffset = Float.NaN;
    final Direction facing;
    final boolean fakeWorld;
    final int offset;

    private final ModelData head;

    public StickerInstance(InstancedTileRenderer<?> modelManager, StickerTileEntity tile) {
        super(modelManager, tile);

        head = getTransformMaterial().getModel(AllBlockPartials.STICKER_HEAD, blockState).createInstance();

        fakeWorld = tile.getWorld() != Minecraft.getInstance().world;
        facing = blockState.get(StickerBlock.FACING);
        offset = blockState.get(StickerBlock.EXTENDED) ? 1 : 0;

        animateHead(offset);
    }

    @Override
    public void beginFrame() {
        float offset = tile.piston.getValue(AnimationTickHolder.getPartialTicks());

        if (fakeWorld)
            offset = this.offset;

        if (MathHelper.epsilonEquals(offset, lastOffset))
            return;

        animateHead(offset);

        lastOffset = offset;
    }

    private void animateHead(float offset) {
        MatrixStack stack = new MatrixStack();
        MatrixStacker.of(stack)
                     .translate(getInstancePosition())
                     .nudge(tile.hashCode())
                     .centre()
                     .rotateY(AngleHelper.horizontalAngle(facing))
                     .rotateX(AngleHelper.verticalAngle(facing) + 90)
                     .unCentre()
                     .translate(0, (offset * offset) * 4 / 16f, 0);

        head.setTransform(stack);
    }

    @Override
    public void updateLight() {
        relight(pos, head);
    }

    @Override
    public void remove() {
        head.delete();
    }
}
