package com.mrbonono63.create.foundation.block.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.simibubi.create.Create;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;

@SuppressWarnings("deprecation")
public abstract class CustomRenderedItemModel extends WrappedBakedModel {

	protected String basePath;
	protected Map<String, IBakedModel> partials = new HashMap<>();
	protected TransformType currentPerspective;
	protected ItemStackTileEntityRenderer renderer;

	public CustomRenderedItemModel(IBakedModel template, String basePath) {
		super(template);
		this.basePath = basePath;
		this.renderer = createRenderer();
	}

	public final List<ResourceLocation> getModelLocations() {
		return partials.keySet().stream().map(this::getPartialModelLocation).collect(Collectors.toList());
	}
	
	public ItemStackTileEntityRenderer getRenderer() {
		return renderer;
	}

	public abstract ItemStackTileEntityRenderer createRenderer();

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public IBakedModel handlePerspective(TransformType cameraTransformType, MatrixStack mat) {
		currentPerspective = cameraTransformType;
		return super.handlePerspective(cameraTransformType, mat);
	}

	protected void addPartials(String... partials) {
		this.partials.clear();
		for (String name : partials)
			this.partials.put(name, null);
	}

	public CustomRenderedItemModel loadPartials(ModelBakeEvent event) {
		for (String name : partials.keySet())
			partials.put(name, loadModel(event, name));
		return this;
	}

	private IBakedModel loadModel(ModelBakeEvent event, String name) {
		return event.getModelLoader().func_217845_a(getPartialModelLocation(name), ModelRotation.X0_Y0);
	}

	private ResourceLocation getPartialModelLocation(String name) {
		return new ResourceLocation(Create.ID, "item/" + basePath + "/" + name);
	}

	public TransformType getCurrentPerspective() {
		return currentPerspective;
	}
	
	public IBakedModel getPartial(String name) {
		return partials.get(name);
	}

}
