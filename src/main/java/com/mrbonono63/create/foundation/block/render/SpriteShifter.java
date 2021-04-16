package com.mrbonono63.create.foundation.block.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.simibubi.create.Create;

import net.minecraft.util.ResourceLocation;

public class SpriteShifter {

	protected static Map<String, SpriteShiftEntry> textures = new HashMap<>();

	public static SpriteShiftEntry get(String originalLocation, String targetLocation) {
		String key = originalLocation + "->" + targetLocation;
		if (textures.containsKey(key))
			return textures.get(key);

		SpriteShiftEntry entry = new SpriteShiftEntry();
		entry.originalTextureLocation = new ResourceLocation(Create.ID, originalLocation);
		entry.targetTextureLocation = new ResourceLocation(Create.ID, targetLocation);
		textures.put(key, entry);
		return entry;
	}

	public static void reloadUVs() {
		textures.values().forEach(SpriteShiftEntry::loadTextures);
	}

	public static List<ResourceLocation> getAllTargetSprites() {
		return textures.values().stream().map(SpriteShiftEntry::getTargetResourceLocation).collect(Collectors.toList());
	}

}
