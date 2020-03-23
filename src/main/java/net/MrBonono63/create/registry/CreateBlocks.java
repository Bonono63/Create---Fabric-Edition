package net.MrBonono63.create.registry;

import net.MrBonono63.create.Main;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

public class CreateBlocks {

    // TODO add conveyers etc.
    //public static final Block Shaft = register("", new );

    //Ores
    public static final Block COPPER_ORE = register("copper_ore", new Block(FabricBlockSettings.copy(Blocks.IRON_ORE).build()));
    public static final Block ZINC_ORE = register("zinc_ore", new Block(FabricBlockSettings.copy(Blocks.GOLD_ORE).build()));

    //Glass
    public static final Block TILED_GLASS = register("tiled_glass", new Block(FabricBlockSettings.copy(Blocks.GLASS).build()));

    private CreateBlocks() {
    }

    public static void init() {
    }

    static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    static <T extends Block> T register(String name, T block) {
        return register(name, block, new Item.Settings().group(Main.GROUP));
    }

    static <T extends Block> T register(String name, T block, Function<T, BlockItem> itemFactory) {
        return register(name, block, itemFactory.apply(block));
    }

    static <T extends Block> T register(String name, T block, BlockItem item) {
        T b = Registry.register(Registry.BLOCK, Main.id(name), block);
        if (item != null) {
            CreateItems.register(name, item);
        }
        return b;
    }
}
