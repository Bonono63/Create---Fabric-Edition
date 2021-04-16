package com.mrbonono63.create.foundation;

import com.simibubi.create.CreateClient;
import com.mrbonono63.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.mrbonono63.create.foundation.block.render.SpriteShifter;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;

public class ResourceReloadHandler extends ReloadListener<Object> {

	@Override
	protected Object prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
		return new Object();
	}

	@Override
	protected void apply(Object $, IResourceManager resourceManagerIn, IProfiler profilerIn) {
		SpriteShifter.reloadUVs();
		CreateClient.invalidateRenderers();
		IHaveGoggleInformation.numberFormat.update();
	}

}
