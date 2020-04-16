package net.MrBonono63.create.blocks.palettes;

import net.minecraft.block.AbstractGlassBlock;

public class CreateGlassBlock extends AbstractGlassBlock {

    public CreateGlassBlock(Settings settings) {
        super(settings.nonOpaque());
    }
}
