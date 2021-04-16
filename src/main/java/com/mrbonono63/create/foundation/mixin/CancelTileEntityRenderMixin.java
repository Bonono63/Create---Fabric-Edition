package com.mrbonono63.create.foundation.mixin;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mrbonono63.create.foundation.render.backend.FastRenderDispatcher;
import com.mrbonono63.create.foundation.render.backend.instancing.IInstanceRendered;

@Environment(EnvType.CLIENT)
@Mixin(ChunkBuilder.ChunkData.class)
public class CancelTileEntityRenderMixin {

	/**
	 * JUSTIFICATION: when instanced rendering is enabled, many tile entities no longer need
	 * to be processed by the normal game renderer. This method is only called to retrieve the
	 * list of tile entities to render. By filtering the output here, we prevent the game from
	 * doing unnecessary light lookups and frustum checks.
	 */
	@Inject(at = @At("RETURN"), method = "getTileEntities", cancellable = true)
	private void noRenderInstancedTiles(CallbackInfoReturnable<List<BlockEntity>> cir) {
		if (FastRenderDispatcher.available()) {
			List<BlockEntity> tiles = cir.getReturnValue();

			tiles.removeIf(tile -> tile instanceof IInstanceRendered && !((IInstanceRendered) tile).shouldRenderAsTE());
		}
	}
}
