package net.MrBonono63.create.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.MrBonono63.create.Main;

public class CreateItems {
    //nuggets
    public static final Item COPPER_NUGGET = register("copper_nugget", new Item(newSettings()));
    public static final Item ZINC_NUGGET = register("zinc_nugget", new Item(newSettings()));
    public static final Item BRASS_NUGGET = register("brass_nugget", new Item(newSettings()));

    //sheets
    public static final Item IRON_SHEETS = register("iron_sheets", new Item(newSettings()));
    public static final Item GOLD_SHEETS = register("gold_sheets", new Item(newSettings()));
    public static final Item COPPER_SHEETS = register("copper_sheets", new Item(newSettings()));
    public static final Item BRASS_SHEETS = register("brass_sheets", new Item(newSettings()));
    public static final Item LAPIS_SHEETS = register("lapis_sheets", new Item(newSettings()));

    //crushed ores
    public static final Item CRUSHED_IRON = register("crushed_iron", new Item(newSettings()));
    public static final Item CRUSHED_GOLD = register("crushed_gold", new Item(newSettings()));
    public static final Item CRUSHED_COOPER = register("crushed_copper", new Item(newSettings()));
    public static final Item CRUSHED_ZINC = register("crushed_zinc", new Item(newSettings()));
    public static final Item CRUSHED_BRASS = register("crushed_brass", new Item(newSettings()));

    //Ingots/Bars
    public static final Item ANDESITE_ALLOY = register("andesite_alloy", new Item(newSettings()));
    public static final Item COPPER_INGOT = register("copper_ingot", new Item(newSettings()));
    public static final Item ZINC_BAR = register("zinc_bar", new Item(newSettings()));
    public static final Item BRASS_INGOT = register("brass_ingot", new Item(newSettings()));

    //Misc
    public static final Item SAND_PAPER = register("sand_paper", new Item(newSettings()));
    public static final Item RED_SAND_PAPER = register("red_sand_paper", new Item(newSettings()));
    public static final Item POWDERED_OBSIDIAN = register("powdered_obsidian", new Item(newSettings()));
    public static final Item ROSE_QUARTZ = register("rose_quartz", new Item(newSettings()));
    public static final Item POLISHED_ROSE_QUARTZ = register("polished_rose_quartz", new Item(newSettings()));
    public static final Item CHROMATIC_COMPOUND = register("chromatic_compound", new Item(newSettings()));
    public static final Item SHADOW_STEEL = register("shadow_steel", new Item(newSettings()));
    public static final Item REFINED_RADIANCE = register("refined_radiance", new Item(newSettings()));
    public static final Item ELECTRON_TUBE = register("electron_tube", new Item(newSettings()));
    public static final Item INTEGRATED_CIRCUIT = register("integrated_circuit", new Item(newSettings()));
    public static final Item WHEAT_FLOUR = register("wheat_flour", new Item(newSettings()));
    public static final Item DOUGH = register("dough", new Item(newSettings()));

    //Schematics
    public static final Item EMPTY_SCHEMATIC = register("empty_schematic", new Item(newSettings()));

    private CreateItems() {
    }

    static Item.Settings newSettings() {
        return new Item.Settings().group(Main.GROUP);
    }

    public static void init() {

    }

    protected static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, Main.id(name),item);
    }
}
