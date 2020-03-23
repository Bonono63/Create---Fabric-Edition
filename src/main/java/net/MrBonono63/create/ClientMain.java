package net.MrBonono63.create;

import net.MrBonono63.create.registry.CreateBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                CreateBlocks.TILED_GLASS_PANE,
                CreateBlocks.VERTICAL_FRAMED_GLASS,
                CreateBlocks.HORIZONTAL_FRAMED_GLASS,
                CreateBlocks.TILED_GLASS,

                CreateBlocks.HORIZONTAL_FRAMED_GLASS_PANE
        );
    }
}
