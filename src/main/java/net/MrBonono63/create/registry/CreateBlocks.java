package net.MrBonono63.create.registry;

import net.MrBonono63.create.Main;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

public class CreateBlocks {

    //public static final Block Shaft = register("", new );

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
