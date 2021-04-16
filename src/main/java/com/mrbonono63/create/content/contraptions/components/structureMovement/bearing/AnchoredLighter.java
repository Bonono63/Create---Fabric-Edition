package com.mrbonono63.create.content.contraptions.components.structureMovement.bearing;

import com.mrbonono63.create.content.contraptions.components.structureMovement.Contraption;
import com.mrbonono63.create.content.contraptions.components.structureMovement.ContraptionLighter;
import com.mrbonono63.create.foundation.render.backend.light.GridAlignedBB;

public class AnchoredLighter extends ContraptionLighter<Contraption> {

    public AnchoredLighter(Contraption contraption) {
        super(contraption);
    }

    @Override
    public GridAlignedBB getContraptionBounds() {
        GridAlignedBB bb = GridAlignedBB.from(contraption.bounds);
        bb.translate(contraption.anchor);
        return bb;
    }
}
