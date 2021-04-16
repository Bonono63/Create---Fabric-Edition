package com.mrbonono63.create;

import java.util.Random;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mrbonono63.create.content.CreateItemGroup;
import com.mrbonono63.create.content.contraptions.TorquePropagator;
import com.mrbonono63.create.content.contraptions.components.structureMovement.train.capability.CapabilityMinecartController;
import com.mrbonono63.create.content.logistics.RedstoneLinkNetworkHandler;
import com.mrbonono63.create.content.palettes.AllPaletteBlocks;
import com.mrbonono63.create.content.palettes.PalettesItemGroup;
import com.mrbonono63.create.content.schematics.SchematicProcessor;
import com.mrbonono63.create.content.schematics.ServerSchematicLoader;
import com.mrbonono63.create.content.schematics.filtering.SchematicInstances;
import com.mrbonono63.create.foundation.advancement.AllAdvancements;
import com.mrbonono63.create.foundation.advancement.AllTriggers;
import com.mrbonono63.create.foundation.command.ChunkUtil;
import com.mrbonono63.create.foundation.command.ServerLagger;
import com.mrbonono63.create.foundation.config.AllConfigs;
import com.mrbonono63.create.foundation.data.CreateRegistrate;
import com.mrbonono63.create.foundation.data.LangMerger;
import com.mrbonono63.create.foundation.data.recipe.MechanicalCraftingRecipeGen;
import com.mrbonono63.create.foundation.data.recipe.ProcessingRecipeGen;
import com.mrbonono63.create.foundation.data.recipe.StandardRecipeGen;
import com.mrbonono63.create.foundation.networking.AllPackets;
import com.mrbonono63.create.foundation.worldgen.AllWorldFeatures;
import com.tterrag.registrate.util.NonNullLazyValue;

import net.minecraft.data.DataGenerator;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Create.ID)
public class Create implements ClientModInitializer {

	public static final String ID = "create";
	public static final String NAME = "Create";
	public static final String VERSION = "0.3.1c";

	public static Logger logger = LogManager.getLogger();
	public static ItemGroup baseCreativeTab = new CreateItemGroup();
	public static ItemGroup palettesCreativeTab = new PalettesItemGroup();

	public static Gson GSON = new GsonBuilder().setPrettyPrinting()
		.disableHtmlEscaping()
		.create();

	public static ServerSchematicLoader schematicReceiver;
	public static RedstoneLinkNetworkHandler redstoneLinkNetworkHandler;
	public static TorquePropagator torquePropagator;
	public static ServerLagger lagger;
	public static ChunkUtil chunkUtil;
	public static Random random;

	private static final NonNullLazyValue<CreateRegistrate> registrate = CreateRegistrate.lazy(ID);

	public Create() {

		com.simibubi.create.AllBlocks.register();
		com.simibubi.create.AllItems.register();
		com.simibubi.create.AllFluids.register();
		com.simibubi.create.AllTags.register();
		AllPaletteBlocks.register();
		com.simibubi.create.AllEntityTypes.register();
		com.simibubi.create.AllTileEntities.register();
		com.simibubi.create.AllMovementBehaviours.register();
		AllWorldFeatures.register();

		Create.init();
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, Create::onBiomeLoad);
		modEventBus.addGenericListener(Feature.class, AllWorldFeatures::registerOreFeatures);
		modEventBus.addGenericListener(Placement.class, AllWorldFeatures::registerDecoratorFeatures);
		modEventBus.addGenericListener(IRecipeSerializer.class, AllRecipeTypes::register);
		modEventBus.addGenericListener(ContainerType.class, AllContainerTypes::register);
		modEventBus.addGenericListener(ParticleType.class, AllParticleTypes::register);
		modEventBus.addGenericListener(SoundEvent.class, AllSoundEvents::register);
		modEventBus.addListener(AllConfigs::onLoad);
		modEventBus.addListener(AllConfigs::onReload);
		modEventBus.addListener(EventPriority.LOWEST, this::gatherData);

		AllConfigs.register();
		random = new Random();

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> com.simibubi.create.CreateClient.addClientListeners(modEventBus));
	}

	public static void init() {
		CapabilityMinecartController.register();
		SchematicInstances.register();
		schematicReceiver = new ServerSchematicLoader();
		redstoneLinkNetworkHandler = new RedstoneLinkNetworkHandler();
		torquePropagator = new TorquePropagator();
		lagger = new ServerLagger();

		chunkUtil = new ChunkUtil();
		chunkUtil.init();
		MinecraftForge.EVENT_BUS.register(chunkUtil);

		AllPackets.registerPackets();
		AllTriggers.register();

		event.enqueueWork(() -> {
			SchematicProcessor.register();
			AllWorldFeatures.registerFeatures();
		});
	}

	public static void onBiomeLoad(BiomeLoadingEvent event) {
		AllWorldFeatures.reload(event);
	}

	public static CreateRegistrate registrate() {
		return registrate.get();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}

	public void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		gen.addProvider(new AllAdvancements(gen));
		gen.addProvider(new LangMerger(gen));
		gen.addProvider(AllSoundEvents.provider(gen));
		gen.addProvider(new StandardRecipeGen(gen));
		gen.addProvider(new MechanicalCraftingRecipeGen(gen));
		ProcessingRecipeGen.registerAll(gen);
	}

}
