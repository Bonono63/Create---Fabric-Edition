package com.mrbonono63.create.foundation.networking;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_SERVER;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.simibubi.create.Create;
import com.mrbonono63.create.content.contraptions.components.structureMovement.ContraptionDisassemblyPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.ContraptionStallPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.gantry.GantryContraptionUpdatePacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.glue.GlueEffectPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.sync.ClientMotionPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.sync.ContraptionFluidPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.sync.ContraptionInteractionPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.sync.ContraptionSeatMappingPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.sync.LimbSwingUpdatePacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.train.CouplingCreationPacket;
import com.mrbonono63.create.content.contraptions.components.structureMovement.train.capability.MinecartControllerUpdatePacket;
import com.mrbonono63.create.content.contraptions.fluids.actors.FluidSplashPacket;
import com.mrbonono63.create.content.contraptions.relays.advanced.sequencer.ConfigureSequencedGearshiftPacket;
import com.mrbonono63.create.content.curiosities.symmetry.SymmetryEffectPacket;
import com.mrbonono63.create.content.curiosities.tools.ExtendoGripInteractionPacket;
import com.mrbonono63.create.content.curiosities.zapper.ZapperBeamPacket;
import com.mrbonono63.create.content.logistics.block.depot.EjectorElytraPacket;
import com.mrbonono63.create.content.logistics.block.depot.EjectorPlacementPacket;
import com.mrbonono63.create.content.logistics.block.depot.EjectorTriggerPacket;
import com.mrbonono63.create.content.logistics.block.mechanicalArm.ArmPlacementPacket;
import com.mrbonono63.create.content.logistics.item.filter.FilterScreenPacket;
import com.mrbonono63.create.content.logistics.packet.ConfigureFlexcratePacket;
import com.mrbonono63.create.content.logistics.packet.ConfigureStockswitchPacket;
import com.mrbonono63.create.content.logistics.packet.FunnelFlapPacket;
import com.mrbonono63.create.content.logistics.packet.TunnelFlapPacket;
import com.mrbonono63.create.content.schematics.packet.ConfigureSchematicannonPacket;
import com.mrbonono63.create.content.schematics.packet.InstantSchematicPacket;
import com.mrbonono63.create.content.schematics.packet.SchematicPlacePacket;
import com.mrbonono63.create.content.schematics.packet.SchematicSyncPacket;
import com.mrbonono63.create.content.schematics.packet.SchematicUploadPacket;
import com.mrbonono63.create.foundation.command.ConfigureConfigPacket;
import com.mrbonono63.create.foundation.command.HighlightPacket;
import com.mrbonono63.create.foundation.tileEntity.behaviour.filtering.FilteringCountUpdatePacket;
import com.mrbonono63.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueUpdatePacket;
import com.mrbonono63.create.foundation.utility.ServerSpeedProvider;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.TargetPoint;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public enum AllPackets {

	// Client to Server
	NBT(NbtPacket.class, NbtPacket::new, PLAY_TO_SERVER),
	CONFIGURE_SCHEMATICANNON(ConfigureSchematicannonPacket.class, ConfigureSchematicannonPacket::new, PLAY_TO_SERVER),
	CONFIGURE_FLEXCRATE(ConfigureFlexcratePacket.class, ConfigureFlexcratePacket::new, PLAY_TO_SERVER),
	CONFIGURE_STOCKSWITCH(ConfigureStockswitchPacket.class, ConfigureStockswitchPacket::new, PLAY_TO_SERVER),
	CONFIGURE_SEQUENCER(ConfigureSequencedGearshiftPacket.class, ConfigureSequencedGearshiftPacket::new,
		PLAY_TO_SERVER),
	PLACE_SCHEMATIC(SchematicPlacePacket.class, SchematicPlacePacket::new, PLAY_TO_SERVER),
	UPLOAD_SCHEMATIC(SchematicUploadPacket.class, SchematicUploadPacket::new, PLAY_TO_SERVER),
	CONFIGURE_FILTER(FilterScreenPacket.class, FilterScreenPacket::new, PLAY_TO_SERVER),
	CONFIGURE_FILTERING_AMOUNT(FilteringCountUpdatePacket.class, FilteringCountUpdatePacket::new, PLAY_TO_SERVER),
	CONFIGURE_SCROLLABLE(ScrollValueUpdatePacket.class, ScrollValueUpdatePacket::new, PLAY_TO_SERVER),
	EXTENDO_INTERACT(ExtendoGripInteractionPacket.class, ExtendoGripInteractionPacket::new, PLAY_TO_SERVER),
	CONTRAPTION_INTERACT(ContraptionInteractionPacket.class, ContraptionInteractionPacket::new, PLAY_TO_SERVER),
	CLIENT_MOTION(ClientMotionPacket.class, ClientMotionPacket::new, PLAY_TO_SERVER),
	PLACE_ARM(ArmPlacementPacket.class, ArmPlacementPacket::new, PLAY_TO_SERVER),
	MINECART_COUPLING_CREATION(CouplingCreationPacket.class, CouplingCreationPacket::new, PLAY_TO_SERVER),
	INSTANT_SCHEMATIC(InstantSchematicPacket.class, InstantSchematicPacket::new, PLAY_TO_SERVER),
	SYNC_SCHEMATIC(SchematicSyncPacket.class, SchematicSyncPacket::new, PLAY_TO_SERVER),
	LEFT_CLICK(LeftClickPacket.class, LeftClickPacket::new, PLAY_TO_SERVER),
	PLACE_EJECTOR(EjectorPlacementPacket.class, EjectorPlacementPacket::new, PLAY_TO_SERVER),
	TRIGGER_EJECTOR(EjectorTriggerPacket.class, EjectorTriggerPacket::new, PLAY_TO_SERVER),
	EJECTOR_ELYTRA(EjectorElytraPacket.class, EjectorElytraPacket::new, PLAY_TO_SERVER),

	// Server to Client
	SYMMETRY_EFFECT(SymmetryEffectPacket.class, SymmetryEffectPacket::new, PLAY_TO_CLIENT),
	SERVER_SPEED(ServerSpeedProvider.Packet.class, ServerSpeedProvider.Packet::new, PLAY_TO_CLIENT),
	BEAM_EFFECT(ZapperBeamPacket.class, ZapperBeamPacket::new, PLAY_TO_CLIENT),
	CONFIGURE_CONFIG(ConfigureConfigPacket.class, ConfigureConfigPacket::new, PLAY_TO_CLIENT),
	CONTRAPTION_STALL(ContraptionStallPacket.class, ContraptionStallPacket::new, PLAY_TO_CLIENT),
	CONTRAPTION_DISASSEMBLE(ContraptionDisassemblyPacket.class, ContraptionDisassemblyPacket::new, PLAY_TO_CLIENT),
	GLUE_EFFECT(GlueEffectPacket.class, GlueEffectPacket::new, PLAY_TO_CLIENT),
	CONTRAPTION_SEAT_MAPPING(ContraptionSeatMappingPacket.class, ContraptionSeatMappingPacket::new, PLAY_TO_CLIENT),
	LIMBSWING_UPDATE(LimbSwingUpdatePacket.class, LimbSwingUpdatePacket::new, PLAY_TO_CLIENT),
	MINECART_CONTROLLER(MinecartControllerUpdatePacket.class, MinecartControllerUpdatePacket::new, PLAY_TO_CLIENT),
	FLUID_SPLASH(FluidSplashPacket.class, FluidSplashPacket::new, PLAY_TO_CLIENT),
	CONTRAPTION_FLUID(ContraptionFluidPacket.class, ContraptionFluidPacket::new, PLAY_TO_CLIENT),
	GANTRY_UPDATE(GantryContraptionUpdatePacket.class, GantryContraptionUpdatePacket::new, PLAY_TO_CLIENT),
	BLOCK_HIGHLIGHT(HighlightPacket.class, HighlightPacket::new, PLAY_TO_CLIENT),
	TUNNEL_FLAP(TunnelFlapPacket.class, TunnelFlapPacket::new, PLAY_TO_CLIENT),
	FUNNEL_FLAP(FunnelFlapPacket.class, FunnelFlapPacket::new, PLAY_TO_CLIENT),

	;

	public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(Create.ID, "network");
	public static final String NETWORK_VERSION = new ResourceLocation(Create.ID, "1").toString();
	public static SimpleChannel channel;

	private LoadedPacket<?> packet;

	private <T extends SimplePacketBase> AllPackets(Class<T> type, Function<PacketBuffer, T> factory,
		NetworkDirection direction) {
		packet = new LoadedPacket<>(type, factory, direction);
	}

	public static void registerPackets() {
		channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
			.serverAcceptedVersions(NETWORK_VERSION::equals)
			.clientAcceptedVersions(NETWORK_VERSION::equals)
			.networkProtocolVersion(() -> NETWORK_VERSION)
			.simpleChannel();
		for (AllPackets packet : values())
			packet.packet.register();
	}

	public static void sendToNear(World world, BlockPos pos, int range, Object message) {
		channel.send(PacketDistributor.NEAR
			.with(TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), range, world.getRegistryKey())), message);
	}

	private static class LoadedPacket<T extends SimplePacketBase> {
		private static int index = 0;
		BiConsumer<T, PacketBuffer> encoder;
		Function<PacketBuffer, T> decoder;
		BiConsumer<T, Supplier<Context>> handler;
		Class<T> type;
		NetworkDirection direction;

		private LoadedPacket(Class<T> type, Function<PacketBuffer, T> factory, NetworkDirection direction) {
			encoder = T::write;
			decoder = factory;
			handler = T::handle;
			this.type = type;
			this.direction = direction;
		}

		private void register() {
			channel.messageBuilder(type, index++, direction)
				.encoder(encoder)
				.decoder(decoder)
				.consumer(handler)
				.add();
		}
	}

}
