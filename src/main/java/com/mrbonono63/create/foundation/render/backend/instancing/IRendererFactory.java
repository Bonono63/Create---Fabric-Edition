package com.mrbonono63.create.foundation.render.backend.instancing;

import net.minecraft.tileentity.TileEntity;

@FunctionalInterface
public interface IRendererFactory<T extends TileEntity> {
    TileEntityInstance<? super T> create(InstancedTileRenderer<?> manager, T te);
}
