package net.MrBonono63.create;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.MrBonono63.create.config.CreateConfig;
import net.MrBonono63.create.registry.CreateBlocks;
import net.MrBonono63.create.registry.CreateItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
	public static final String MOD_ID = "create";

	public static Logger LOG = LogManager.getLogger("Create - [FE]");

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(id("group"), () -> new ItemStack(CreateBlocks.COGWHEEL));

	@Override
	public void onInitialize() {

		AutoConfig.register(CreateConfig.class, JanksonConfigSerializer::new);

		CreateItems.init();
		CreateBlocks.init();
	}
}
