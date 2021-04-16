package com.mrbonono63.create;

import com.mrbonono63.create.content.logistics.block.inventories.AdjustableCrateContainer;
import com.mrbonono63.create.content.logistics.block.inventories.AdjustableCrateScreen;
import com.mrbonono63.create.content.logistics.item.filter.AttributeFilterContainer;
import com.mrbonono63.create.content.logistics.item.filter.AttributeFilterScreen;
import com.mrbonono63.create.content.logistics.item.filter.FilterContainer;
import com.mrbonono63.create.content.logistics.item.filter.FilterScreen;
import com.mrbonono63.create.content.schematics.block.SchematicTableContainer;
import com.mrbonono63.create.content.schematics.block.SchematicTableScreen;
import com.mrbonono63.create.content.schematics.block.SchematicannonContainer;
import com.mrbonono63.create.content.schematics.block.SchematicannonScreen;
import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.ContainerType.IFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.network.IContainerFactory;

public enum AllContainerTypes {

	SCHEMATIC_TABLE(SchematicTableContainer::new),
	SCHEMATICANNON(SchematicannonContainer::new),
	FLEXCRATE(AdjustableCrateContainer::new),
	FILTER(FilterContainer::new),
	ATTRIBUTE_FILTER(AttributeFilterContainer::new),

	;

	public ContainerType<? extends Container> type;
	private IFactory<?> factory;

	private <C extends Container> AllContainerTypes(IContainerFactory<C> factory) {
		this.factory = factory;
	}

	public static void register(RegistryEvent.Register<ContainerType<?>> event) {
		for (AllContainerTypes container : values()) {
			container.type = new ContainerType<>(container.factory)
				.setRegistryName(new ResourceLocation(Create.ID, Lang.asId(container.name())));
			event.getRegistry()
				.register(container.type);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerScreenFactories() {
		bind(SCHEMATIC_TABLE, SchematicTableScreen::new);
		bind(SCHEMATICANNON, SchematicannonScreen::new);
		bind(FLEXCRATE, AdjustableCrateScreen::new);
		bind(FILTER, FilterScreen::new);
		bind(ATTRIBUTE_FILTER, AttributeFilterScreen::new);
	}

	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	private static <C extends Container, S extends Screen & IHasContainer<C>> void bind(AllContainerTypes c,
		IScreenFactory<C, S> factory) {
		ScreenManager.registerFactory((ContainerType<C>) c.type, factory);
	}

}
