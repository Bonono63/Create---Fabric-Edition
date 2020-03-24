package net.MrBonono63.create;

import net.MrBonono63.create.registry.CreateBlocks;
import net.MrBonono63.create.registry.CreateItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
	public static final String MOD_ID = "create";

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(CreateItems.BELT_CONNECTOR));

	@Override
	public void onInitialize() {
		//registry init
		CreateItems.init();
		CreateBlocks.init();

		System.out.println("Hello Fabric world!");
	}
}
