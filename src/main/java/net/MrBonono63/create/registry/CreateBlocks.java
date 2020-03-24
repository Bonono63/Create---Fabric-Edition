package net.MrBonono63.create.registry;

import net.MrBonono63.create.Main;
import net.MrBonono63.create.blocks.CreateGlassBlock;
import net.MrBonono63.create.blocks.CreateGlassPaneBlock;
import net.MrBonono63.create.blocks.CreateSlabBlock;
import net.MrBonono63.create.blocks.CreateStairsBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

public class CreateBlocks {
    /*
    * ~ Welcome to the Block Registry ~
    * */

    /*
    * SCHEMATICS
    * TODO add schematic blocks and block types
    *  */

    /*
    * CONTRAPTIONS
    * TODO add all the block in this category and find out what block types and systems need to be added
    * */

    public static final Block GEARBOX = register("gearbox", new Block(FabricBlockSettings.of(Material.WOOL).build()));
    public static final Block VERTICAL_GEARBOX = register("vertical_gearbox", new Block(FabricBlockSettings.of(Material.WOOL).build()));
    public static final Block CART_ASSEMBLER = register("cart_assembler", new Block(FabricBlockSettings.copy(Blocks.STONE).build()));

    /*
    * LOGISTICS
    * TODO all all the blocks in this category and find out what block types need to be added
    * */

    /*
    * CURIOSITIES
    * TODO add Symmetry plane block types
    * TODO add cocoa log block type
    * */

    public static final Block COCOA_LOG = register("cocoa_log", new Block(FabricBlockSettings.copy(Blocks.JUNGLE_LOG).build()));
    public static final Block SYMMETRY_PLANE = register("symmetry_plane", new Block(FabricBlockSettings.copy(Blocks.AIR).build()));
    public static final Block SYMMETRY_CROSSPLANE = register("symmetry_crossplane", new Block(FabricBlockSettings.copy(Blocks.AIR).build()));
    public static final Block SYMMETRY_TRIPLEPLANE = register("symmetry_tripleplane", new Block(FabricBlockSettings.copy(Blocks.AIR).build()));
    public static final Block WINDOW_IN_A_BLOCK = register("window_in_a_block", new Block(FabricBlockSettings.copy(Blocks.AIR).build()));

    /*
    * PALETTES
    * TODO add connected textures for glass blocks etc.
    * TODO add pillar block types
    * */

    //Glass
    public static final Block TILED_GLASS = register("tiled_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block HORIZONTAL_FRAMED_GLASS = register("horizontal_framed_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block VERTICAL_FRAMED_GLASS = register("vertical_framed_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block FRAMED_GLASS = register("framed_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));

    public static final Block OAK_GLASS = register("oak_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block SPRUCE_GLASS = register("spruce_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block BIRCH_GLASS = register("birch_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block JUNGLE_GLASS = register("jungle_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block DARK_OAK_GLASS = register("dark_oak_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block ACACIA_GLASS = register("acacia_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block IRON_GLASS = register("iron_glass", new CreateGlassBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));

    public static final Block HORIZONTAL_FRAMED_GLASS_PANE = register("horizontal_framed_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block VERTICAL_FRAMED_GLASS_PANE = register("vertical_framed_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block TILED_GLASS_PANE = register("tiled_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block FRAMED_GLASS_PANE = register("framed_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block OAK_GLASS_PANE = register("oak_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block SPRUCE_GLASS_PANE = register("spruce_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block BIRCH_GLASS_PANE = register("birch_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block JUNGLE_GLASS_PANE = register("jungle_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block DARK_OAK_GLASS_PANE = register("dark_oak_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block ACACIA_GLASS_PANE = register("acacia_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));
    public static final Block IRON_GLASS_PANE = register("iron_glass_pane", new CreateGlassPaneBlock(FabricBlockSettings.copy(Blocks.GLASS).build()));

    //Vanilla Bricks and Layers
    public static final Block GRANITE_BRICKS = register("granite_bricks", new Block(FabricBlockSettings.copy(Blocks.GRANITE).build()));
    public static final Block GRANITE_LAYERS = register("granite_layers", new Block(FabricBlockSettings.copy(Blocks.GRANITE).build()));
    public static final Block DIORITE_BRICKS = register("diorite_bricks", new Block(FabricBlockSettings.copy(Blocks.DIORITE).build()));
    public static final Block DIORITE_LAYERS = register("diorite_layers", new Block(FabricBlockSettings.copy(Blocks.DIORITE).build()));
    public static final Block ANDESITE_BRICKS = register("andesite_bricks", new Block(FabricBlockSettings.copy(Blocks.ANDESITE).build()));
    public static final Block ANDESITE_LAYERS = register("andesite_layers", new Block(FabricBlockSettings.copy(Blocks.ANDESITE).build()));

    //GABBRO
    public static final Block GABBRO = register("gabbro", new Block(FabricBlockSettings.copy(Blocks.GRANITE).build()));
    public static final Block GABBRO_SLAB = register("gabbro_slab", new CreateSlabBlock(FabricBlockSettings.copy(Blocks.GRANITE).build()));
    public static final Block GABBRO_STAIRS = register("gabbro_stairs", new CreateStairsBlock(GABBRO.getDefaultState(),FabricBlockSettings.copy(Blocks.GRANITE).build()));
    public static final Block GABBRO_WALL = register("gabbro_wall", new WallBlock(FabricBlockSettings.copy(Blocks.GRANITE).build()));
    public static final Block POLISHED_GABBRO = register("polished_gabbro", new Block(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block GABBRO_BRICKS = register("gabbro_bricks", new Block(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block GABBRO_BRICKS_WALL = register("gabbro_bricks_wall", new WallBlock(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block GABBRO_BRICKS_STAIRS = register("gabbro_bricks_stairs", new CreateStairsBlock(GABBRO_BRICKS.getDefaultState(),FabricBlockSettings.copy(GABBRO).build()));
    public static final Block PAVED_GABBRO_BRICKS = register("paved_gabbro_bricks", new Block(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block PAVED_GABBRO_BRICKS_SLAB = register("paved_gabbro_bricks_slab", new CreateSlabBlock(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block INDENTED_GABBRO = register("indented_gabbro", new Block(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block INDENTED_GABBRO_SLAB = register("indented_gabbro_slab", new CreateSlabBlock(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block SLIGHTLY_MOSSY_GABBRO_BRICKS = register("slightly_mossy_gabbro_bricks", new Block(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block MOSSY_GABBRO_BRICKS = register("mossy_gabbro_bricks", new Block(FabricBlockSettings.copy(GABBRO).build()));
    public static final Block GABBRO_LAYERS = register("gabbro_layers", new Block(FabricBlockSettings.copy(GABBRO).build()));

    //DOLOMITE
    public static final Block DOLOMITE = register("dolomite", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK).build()));
    public static final Block DOLOMITE_WALL = register("dolomite_wall", new WallBlock(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK).build()));
    public static final Block DOLOMITE_SLAB = register("dolomite_slab", new CreateSlabBlock(FabricBlockSettings.copy(DOLOMITE).build()));
    public static final Block DOLOMITE_STAIRS = register("dolomite_stairs", new CreateStairsBlock(DOLOMITE.getDefaultState(), FabricBlockSettings.copy(DOLOMITE).build()));
    public static final Block DOLOMITE_BRICKS = register("dolomite_bricks", new Block(FabricBlockSettings.copy(DOLOMITE).build()));
    public static final Block POLISHED_DOLOMITE = register("polished_dolomite", new Block(FabricBlockSettings.copy(DOLOMITE).build()));
    public static final Block DOLOMITE_PILLAR = register("dolomite_pillar", new PillarBlock(FabricBlockSettings.copy(DOLOMITE).build()));
    public static final Block DOLOMITE_LAYERS = register("dolomite_layers", new Block(FabricBlockSettings.copy(DOLOMITE).build()));

    //LIMESTONE
    public static final Block LIMESAND = register("limesand", new FallingBlock(FabricBlockSettings.copy(Blocks.SAND).build()));
    public static final Block LIMESTONE = register("limestone", new Block(FabricBlockSettings.copy(Blocks.SANDSTONE).build()));
    public static final Block LIMESTONE_SLAB = register("limestone_slab", new CreateSlabBlock(FabricBlockSettings.copy(LIMESTONE).build()));
    public static final Block LIMESTONE_WALL = register("limestone_wall", new WallBlock(FabricBlockSettings.copy(LIMESTONE).build()));
    public static final Block LIMESTONE_STAIRS = register("limestone_stairs", new CreateStairsBlock(LIMESTONE.getDefaultState(),FabricBlockSettings.copy(LIMESTONE).build()));
    public static final Block LIMESTONE_PILLAR = register("limestone_pillar", new PillarBlock(FabricBlockSettings.copy(LIMESTONE).build()));
    public static final Block LIMESTONE_LAYERS = register("limestone_layers", new Block(FabricBlockSettings.copy(LIMESTONE).build()));

    //WEATHERED LIMESTONE
    public static final Block WEATHERED_LIMESTONE = register("weathered_limestone", new Block(FabricBlockSettings.copy(Blocks.ANDESITE).build()));
    public static final Block WEATHERED_LIMESTONE_STAIRS = register("weathered_limestone_stairs", new CreateStairsBlock(WEATHERED_LIMESTONE.getDefaultState(),FabricBlockSettings.copy(WEATHERED_LIMESTONE).build()));
    public static final Block WEATHERED_LIMESTONE_SLAB = register("weathered_limestone_slab", new CreateSlabBlock(FabricBlockSettings.copy(WEATHERED_LIMESTONE).build()));
    public static final Block WEATHERED_LIMESTONE_WALL = register("weathered_limestone_wall", new WallBlock(FabricBlockSettings.copy(WEATHERED_LIMESTONE).build()));
    public static final Block WEATHERED_LIMESTONE_PILLAR = register("weathered_limestone_pillar", new PillarBlock(FabricBlockSettings.copy(WEATHERED_LIMESTONE).build()));
    public static final Block WEATHERED_LIMESTONE_LAYERS = register("weathered_limestone_layers", new Block(FabricBlockSettings.copy(WEATHERED_LIMESTONE).build()));

    //WEATHERED LIMESTONE BRICKS
    public static final Block WEATHERED_LIMESTONE_BRICKS = register("weathered_limestone_bricks", new Block(FabricBlockSettings.copy(WEATHERED_LIMESTONE).build()));
    public static final Block WEATHERED_LIMESTONE_BRICKS_STAIRS = register("weathered_limestone_bricks_stairs", new CreateStairsBlock(WEATHERED_LIMESTONE_BRICKS.getDefaultState(),FabricBlockSettings.copy(WEATHERED_LIMESTONE_BRICKS).build()));
    public static final Block WEATHERED_LIMESTONE_BRICKS_SLAB = register("weathered_limestone_bricks_slab", new CreateSlabBlock(FabricBlockSettings.copy(WEATHERED_LIMESTONE_BRICKS).build()));
    public static final Block WEATHERED_LIMESTONE_BRICKS_WALL = register("weathered_limestone_bricks_wall", new WallBlock(FabricBlockSettings.copy(WEATHERED_LIMESTONE_BRICKS).build()));

    //LIMESTONE BRICKS
    public static final Block LIMESTONE_BRICKS = register("limestone_bricks", new Block(FabricBlockSettings.copy(LIMESTONE).build()));
    public static final Block LIMESTONE_BRICKS_WALL = register("limestone_bricks_wall", new WallBlock(FabricBlockSettings.copy(LIMESTONE_BRICKS).build()));
    public static final Block LIMESTONE_BRICKS_SLAB = register("limestone_bricks_slab", new CreateSlabBlock(FabricBlockSettings.copy(LIMESTONE_BRICKS).build()));
    public static final Block LIMESTONE_BRICKS_STAIRS = register("limestone_bricks_stairs", new CreateStairsBlock(LIMESTONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(LIMESTONE_BRICKS).build()));

    //POLISHED LIMESTONE
    public static final Block POLISHED_LIMESTONE = register("polished_limestone", new Block(FabricBlockSettings.copy(LIMESTONE).build()));
    public static final Block POLISHED_LIMESTONE_SLAB = register("polished_limestone_slab", new CreateSlabBlock(FabricBlockSettings.copy(POLISHED_LIMESTONE).build()));

    //POLISHED WEATHERED LIMESTONE
    public static final Block POLISHED_WEATHERED_LIMESTONE = register("polished_weathered_limestone", new Block(FabricBlockSettings.copy(WEATHERED_LIMESTONE).build()));
    public static final Block POLISHED_WEATHERED_LIMESTONE_SLAB = register("polished_weathered_limestone_slab", new CreateSlabBlock(FabricBlockSettings.copy(POLISHED_WEATHERED_LIMESTONE).build()));

    //SCORIA
    public static final Block NATURAL_SCORIA = register("natural_scoria", new Block(FabricBlockSettings.copy(Blocks.ANDESITE).build()));
    public static final Block SCORIA = register("scoria", new Block(FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block SCORIA_STAIRS = register("scoria_stairs", new CreateStairsBlock(SCORIA.getDefaultState(),FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block SCORIA_WALL = register("scoria_wall", new WallBlock(FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block SCORIA_SLAB = register("scoria_slab", new CreateSlabBlock(FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block POLISHED_SCORIA = register("polished_scoria", new Block(FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block POLISHED_SCORIA_SLAB = register("polished_scoria_slab", new CreateSlabBlock(FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block SCORIA_BRICKS = register("scoria_bricks", new Block(FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block SCORIA_LAYERS = register("scoria_layers", new Block(FabricBlockSettings.copy(NATURAL_SCORIA).build()));
    public static final Block SCORIA_PILLAR = register("scoria_pillar", new PillarBlock(FabricBlockSettings.copy(NATURAL_SCORIA).build()));

    //DARK SCORIA
    public static final Block DARK_SCORIA = register("dark_scoria", new Block(FabricBlockSettings.copy(Blocks.ANDESITE).build()));
    public static final Block POLISHED_DARK_SCORIA = register("polished_dark_scoria", new Block(FabricBlockSettings.copy(DARK_SCORIA).build()));
    public static final Block DARK_SCORIA_TILES = register("dark_scoria_tiles", new Block(FabricBlockSettings.copy(DARK_SCORIA).build()));
    public static final Block DARK_SCORIA_TILES_SLAB = register("dark_scoria_tiles_slab", new CreateSlabBlock(FabricBlockSettings.copy(DARK_SCORIA).build()));
    public static final Block DARK_SCORIA_TILES_STAIRS = register("dark_scoria_tiles_stairs", new CreateStairsBlock(DARK_SCORIA.getDefaultState(),FabricBlockSettings.copy(DARK_SCORIA).build()));
    public static final Block DARK_SCORIA_BRICKS = register("dark_scoria_bricks", new Block(FabricBlockSettings.copy(DARK_SCORIA).build()));
    public static final Block DARK_SCORIA_BRICKS_STAIRS = register("dark_scoria_bricks_stairs", new CreateStairsBlock(DARK_SCORIA.getDefaultState(),FabricBlockSettings.copy(DARK_SCORIA).build()));
    public static final Block DARK_SCORIA_BRICKS_SLAB = register("dark_scoria_bricks_slab", new CreateSlabBlock(FabricBlockSettings.copy(DARK_SCORIA).build()));
    public static final Block DARK_SCORIA_BRICKS_WALL = register("dark_scoria_bricks_wall", new WallBlock(FabricBlockSettings.copy(DARK_SCORIA).build()));

    /*
    * MATERIALS
    * TODO add oxidizing block type
    *  */

    public static final Block ZINC_ORE = register("zinc_ore", new Block(FabricBlockSettings.copy(Blocks.GOLD_ORE).build()));
    public static final Block COPPER_ORE = register("copper_ore", new Block(FabricBlockSettings.copy(Blocks.IRON_ORE).build()));
    public static final Block COPPER_BLOCK = register("copper_block", new Block(FabricBlockSettings.copy(Blocks.IRON_ORE).build()));
    public static final Block COPPER_SHINGLES = register("copper_shingles", new Block(FabricBlockSettings.copy(Blocks.IRON_ORE).build()));

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
