package net.MrBonono63.create.registry;

import net.MrBonono63.create.Main;
import net.MrBonono63.create.blocks.CreateGlassBlock;
import net.MrBonono63.create.blocks.CreateGlassPaneBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

public class CreateBlocks {

    // TODO add conveyor blocks, shaft,

    //Gear Boxes
    public static final Block GEARBOX = register("gearbox", new Block(FabricBlockSettings.of(Material.WOOL).build()));
    public static final Block VERTICAL_GEARBOX = register("vertical_gearbox", new Block(FabricBlockSettings.of(Material.WOOL).build()));

    //Ores
    public static final Block COPPER_ORE = register("copper_ore", new Block(FabricBlockSettings.copy(Blocks.IRON_ORE).build()));
    public static final Block ZINC_ORE = register("zinc_ore", new Block(FabricBlockSettings.copy(Blocks.GOLD_ORE).build()));

    //Glass
    public static final Block TILED_GLASS = register("tiled_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block HORIZONTAL_FRAMED_GLASS = register("horizontal_framed_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block VERTICAL_FRAMED_GLASS = register("vertical_framed_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));

    public static final Block HORIZONTAL_FRAMED_GLASS_PANE = register("horizontal_framed_glass_pane", new CreateGlassPaneBlock(1.0f,1.0f,16.0f, 16.0f,16.0f,FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block TILED_GLASS_PANE = register("tiled_glass_pane", new CreateGlassPaneBlock(1.0f,1.0f,16.0f,16.0f,16.0f,FabricBlockSettings.copy(Blocks.GLASS).build()));

    //MISC
    public static final Block COCOA_LOG = register("cocoa_log", new Block(FabricBlockSettings.copy(Blocks.JUNGLE_LOG).build()));

    //CART MISC
    public static final Block CART_ASSEMBLER = register("cart_assembler", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));

    //SLABS
    public static final Block LIMESTONE_SLAB = register("limestone_slab", new Block(FabricBlockSettings.copy(Blocks.STONE_SLAB).build()));
    public static final Block POLISHED_LIMESTONE_SLAB = register("polished_limestone_slab", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));
    public static final Block LIMESTONE_BRICKS_SLAB = register("limestone_bricks_slab", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));

    //WALLS
    public static final Block WEATHERED_LIMESTONE_WALL = register("weathered_limestone_wall", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));
    public static final Block LIMESTONE_WALL = register("limestone_wall", new Block(FabricBlockSettings.copy(Blocks.END_STONE_BRICK_WALL).build()));
    public static final Block LIMESTONE_BRICKS_WALL = register("limestone_bricks_wall", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));

    //STAIRS
    public static final Block WEATEHRED_LIMESTONE_STAIRS = register("weathered_limestone_stairs", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));
    public static final Block LIMESTONE_STAIRS = register("limestone_stairs", new Block(FabricBlockSettings.copy(Blocks.STONE_STAIRS).build()));
    public static final Block LIMESTONE_BRICKS_STAIRS = register("limestone_bricks_stairs", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));

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
