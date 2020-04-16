package net.MrBonono63.create.blocks.contraptions;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public interface IWrenchable {

    public default ActionResult onWrenched(BlockState state, ItemUsageContext context) {
        return ActionResult.PASS;
    }
}
