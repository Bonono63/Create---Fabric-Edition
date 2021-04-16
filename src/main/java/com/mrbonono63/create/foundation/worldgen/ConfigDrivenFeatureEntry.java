package com.mrbonono63.create.foundation.worldgen;

import java.util.Optional;

import com.mrbonono63.create.foundation.config.ConfigBase;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigDrivenFeatureEntry extends ConfigBase {

	public final String id;
	public final NonNullSupplier<? extends Block> block;

	protected ConfigInt clusterSize;
	protected ConfigInt minHeight;
	protected ConfigInt maxHeight;
	protected ConfigFloat frequency;

	Optional<ConfiguredFeature<?, ?>> feature = Optional.empty();

	public ConfigDrivenFeatureEntry(String id, NonNullSupplier<? extends Block> block, int clusterSize,
		float frequency) {
		this.id = id;
		this.block = block;
		this.clusterSize = i(clusterSize, 0, "clusterSize");
		this.minHeight = i(0, 0, "minHeight");
		this.maxHeight = i(256, 0, "maxHeight");
		this.frequency = f(frequency, 0, 512, "frequency", "Amount of clusters generated per Chunk.",
			"  >1 to spawn multiple.", "  <1 to make it a chance.", "  0 to disable.");
	}

	public ConfigDrivenFeatureEntry between(int minHeight, int maxHeight) {
		allValues.remove(this.minHeight);
		allValues.remove(this.maxHeight);
		this.minHeight = i(minHeight, 0, "minHeight");
		this.maxHeight = i(maxHeight, 0, "maxHeight");
		return this;
	}

	public ConfiguredFeature<?, ?> getFeature() {
		if (!feature.isPresent())
			feature = Optional.of(createFeature());
		return feature.get();
	}

	private ConfiguredFeature<?, ?> createFeature() {
		ConfigDrivenOreFeatureConfig config =
			new ConfigDrivenOreFeatureConfig(FillerBlockType.BASE_STONE_OVERWORLD, block.get()
				.getDefaultState(), id);

		return ConfigDrivenOreFeature.INSTANCE.configure(config)
			.decorate(ConfigDrivenDecorator.INSTANCE.configure(config));
	}

	public void addToConfig(ForgeConfigSpec.Builder builder) {
		registerAll(builder);
	}

	@Override
	public String getName() {
		return id;
	}

}
