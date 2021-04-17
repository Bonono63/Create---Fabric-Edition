package com.mrbonono63.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.mrbonono63.create.content.contraptions.base.KineticTileEntityRenderer;
import com.mrbonono63.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import com.mrbonono63.create.content.contraptions.relays.encased.CasingConnectivity;
import com.mrbonono63.create.content.schematics.ClientSchematicLoader;
import com.mrbonono63.create.content.schematics.client.SchematicAndQuillHandler;
import com.mrbonono63.create.content.schematics.client.SchematicHandler;
import com.mrbonono63.create.foundation.ResourceReloadHandler;
import com.mrbonono63.create.foundation.block.render.CustomBlockModels;
import com.mrbonono63.create.foundation.block.render.SpriteShifter;
import com.mrbonono63.create.foundation.config.AllConfigs;
import com.mrbonono63.create.foundation.gui.UIRenderHelper;
import com.mrbonono63.create.foundation.item.CustomItemModels;
import com.mrbonono63.create.foundation.item.CustomRenderedItems;
import com.mrbonono63.create.foundation.ponder.content.PonderIndex;
import com.mrbonono63.create.foundation.ponder.elements.WorldSectionElement;
import com.mrbonono63.create.foundation.render.AllProgramSpecs;
import com.mrbonono63.create.foundation.render.KineticRenderer;
import com.mrbonono63.create.foundation.render.SuperByteBufferCache;
import com.mrbonono63.create.foundation.render.backend.Backend;
import com.mrbonono63.create.foundation.render.backend.OptifineHandler;
import com.mrbonono63.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.mrbonono63.create.foundation.utility.WorldAttached;
import com.mrbonono63.create.foundation.utility.ghost.GhostBlocks;
import com.mrbonono63.create.foundation.utility.outliner.Outliner;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GraphicsMode;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.network.MessageType;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldAccess;
/*
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
*/
import org.jetbrains.annotations.Nullable;

public class CreateClient {

	public static ClientSchematicLoader schematicSender;
	public static SchematicHandler schematicHandler;
	public static SchematicAndQuillHandler schematicAndQuillHandler;
	public static SuperByteBufferCache bufferCache;
	public static WorldAttached<KineticRenderer> kineticRenderer;
	public static final Outliner outliner = new Outliner();
	public static GhostBlocks ghostBlocks;

	private static CustomBlockModels customBlockModels;
	private static CustomItemModels customItemModels;
	private static CustomRenderedItems customRenderedItems;
	private static AllColorHandlers colorHandlers;
	private static CasingConnectivity casingConnectivity;

	//
	public static void addClientListeners(IEventBus modEventBus) {
		modEventBus.addListener(CreateClient::clientInit);
		modEventBus.addListener(CreateClient::onModelBake);
		modEventBus.addListener(CreateClient::onModelRegistry);
		modEventBus.addListener(CreateClient::onTextureStitch);
		modEventBus.addListener(AllParticleTypes::registerFactories);

		Backend.init();
		OptifineHandler.init();
	}

	public static void clientInit() {
		AllProgramSpecs.init();
		kineticRenderer = new WorldAttached<>(KineticRenderer::new);

		schematicSender = new ClientSchematicLoader();
		schematicHandler = new SchematicHandler();
		schematicAndQuillHandler = new SchematicAndQuillHandler();

		bufferCache = new SuperByteBufferCache();
		bufferCache.registerCompartment(KineticTileEntityRenderer.KINETIC_TILE);
		bufferCache.registerCompartment(ContraptionRenderDispatcher.CONTRAPTION, 20);
		bufferCache.registerCompartment(WorldSectionElement.DOC_WORLD_SECTION, 20);

		ghostBlocks = new GhostBlocks();

		com.mrbonono63.create.AllKeys.register();
		com.mrbonono63.create.AllContainerTypes.registerScreenFactories();
		// AllTileEntities.registerRenderers();
		com.mrbonono63.create.AllEntityTypes.registerRenderers();
		getColorHandler().init();
		com.mrbonono63.create.AllFluids.assignRenderLayers();
		PonderIndex.register();
		PonderIndex.registerTags();

		UIRenderHelper.init();


	}
	//TODO model loading and texture loading
/*
	IResourceManager resourceManager = Minecraft.getInstance()
			.getResourceManager();
		if (resourceManager instanceof IReloadableResourceManager)
			((IReloadableResourceManager) resourceManager).addReloadListener(new ResourceReloadHandler());

	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		if (!event.getMap()
			.getId()
			.equals(PlayerContainer.BLOCK_ATLAS_TEXTURE))
			return;
		SpriteShifter.getAllTargetSprites()
			.forEach(event::addSprite);
	}

	public static void onModelBake(ModelBakeEvent event) {
		Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
		AllBlockPartials.onModelBake(event);

		getCustomBlockModels()
			.foreach((block, modelFunc) -> swapModels(modelRegistry, getAllBlockStateModelLocations(block), modelFunc));
		getCustomItemModels()
			.foreach((item, modelFunc) -> swapModels(modelRegistry, getItemModelLocation(item), modelFunc));
		getCustomRenderedItems().foreach((item, modelFunc) -> {
			swapModels(modelRegistry, getItemModelLocation(item), m -> modelFunc.apply(m)
				.loadPartials(event));
		});
	}

	public static void onModelRegistry(ModelRegistryEvent event) {
		AllBlockPartials.onModelRegistry(event);

		getCustomRenderedItems().foreach((item, modelFunc) -> modelFunc.apply(null)
			.getModelLocations()
			.forEach(BakedModelManager::addSpecialModel));
	}
*/
	protected static ModelIdentifier getItemModelLocation(Item item) {
		return new ModelIdentifier(item.toString(), "inventory");
	}

	protected static List<ModelIdentifier> getAllBlockStateModelLocations(Block block) {
		List<ModelIdentifier> models = new ArrayList<>();
		block.getStateManager()
			.getStates()
			.forEach(state -> {
				models.add(getBlockModelLocation(block, BlockModels.propertyMapToString(state.getEntries())));
			});
		return models;
	}

	protected static ModelIdentifier getBlockModelLocation(Block block, String suffix) {
		return new ModelIdentifier(block.getLootTableId(), suffix);
	}

	protected static <T extends BakedModel> void swapModels(Map<Identifier, BakedModel> modelRegistry,
		List<ModelIdentifier> locations, Function<BakedModel, T> factory) {
		locations.forEach(location -> {
			swapModels(modelRegistry, location, factory);
		});
	}

	protected static <T extends BakedModel> void swapModels(Map<Identifier, BakedModel> modelRegistry,
		ModelIdentifier location, Function<BakedModel, T> factory) {
		modelRegistry.put(location, factory.apply(modelRegistry.get(location)));
	}

	public static CustomItemModels getCustomItemModels() {
		if (customItemModels == null)
			customItemModels = new CustomItemModels();
		return customItemModels;
	}

	public static CustomRenderedItems getCustomRenderedItems() {
		if (customRenderedItems == null)
			customRenderedItems = new CustomRenderedItems();
		return customRenderedItems;
	}

	public static CustomBlockModels getCustomBlockModels() {
		if (customBlockModels == null)
			customBlockModels = new CustomBlockModels();
		return customBlockModels;
	}

	public static com.mrbonono63.create.AllColorHandlers getColorHandler() {
		if (colorHandlers == null)
			colorHandlers = new com.mrbonono63.create.AllColorHandlers();
		return colorHandlers;
	}

	public static CasingConnectivity getCasingConnectivity() {
		if (casingConnectivity == null)
			casingConnectivity = new CasingConnectivity();
		return casingConnectivity;
	}

	public static void invalidateRenderers() {
		invalidateRenderers(null);
	}

	public static void invalidateRenderers(@Nullable WorldAccess world) {
		bufferCache.invalidate();

		if (world != null) {
			kineticRenderer.get(world)
				.invalidate();
		} else {
			kineticRenderer.forEach(InstancedTileRenderer::invalidate);
		}

		ContraptionRenderDispatcher.invalidateAll();
	}

	public static void checkGraphicsFanciness() {
		MinecraftClient mc = MinecraftClient.getInstance();
		if (mc.player == null)
			return;

		if (mc.options.graphicsMode != GraphicsMode.FABULOUS)
			return;

		if (AllConfigs.CLIENT.ignoreFabulousWarning.get())
			return;

		MutableText text = Texts.bracketed(new LiteralText("WARN"))
			.formatted(Formatting.GOLD)
			.append(new LiteralText(
				" Some of Create's visual features will not be available while Fabulous graphics are enabled!"))
			.styled(style -> style
				.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/create dismissFabulousWarning"))
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					new LiteralText("Click here to disable this warning"))));

		mc.inGameHud.addChatMessage(MessageType.CHAT, text, mc.player.getUuid());
	}
}
