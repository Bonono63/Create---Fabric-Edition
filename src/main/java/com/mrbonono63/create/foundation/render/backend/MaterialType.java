package com.mrbonono63.create.foundation.render.backend;

import com.mrbonono63.create.foundation.render.backend.instancing.InstancedModel;

public class MaterialType<M extends InstancedModel<?>> {

	@Override
	public int hashCode() {
		return super.hashCode() * 31 * 493286711;
	}
}
