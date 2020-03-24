package net.MrBonono63.create.registry;

import net.MrBonono63.create.items.CreatePickaxeItem;
import net.MrBonono63.create.items.GlintIngot;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.registry.Registry;
import net.MrBonono63.create.Main;

public class CreateItems {
    /*
    * Welcome to the Item Registry
    * */

    //TODO setup custom tooltip system

    //nuggets
    public static final Item COPPER_NUGGET = register("copper_nugget", new Item(newSettings()));
    public static final Item ZINC_NUGGET = register("zinc_nugget", new Item(newSettings()));
    public static final Item BRASS_NUGGET = register("brass_nugget", new Item(newSettings()));

    //sheets
    public static final Item IRON_SHEET = register("iron_sheet", new Item(newSettings()));
    public static final Item GOLD_SHEET = register("gold_sheet", new Item(newSettings()));
    public static final Item COPPER_SHEET = register("copper_sheet", new Item(newSettings()));
    public static final Item BRASS_SHEET = register("brass_sheet", new Item(newSettings()));
    public static final Item LAPIS_PLATE = register("lapis_plate", new Item(newSettings()));

    //crushed ores
    public static final Item CRUSHED_IRON = register("crushed_iron", new Item(newSettings()));
    public static final Item CRUSHED_GOLD = register("crushed_gold", new Item(newSettings()));
    public static final Item CRUSHED_COOPER = register("crushed_copper", new Item(newSettings()));
    public static final Item CRUSHED_ZINC = register("crushed_zinc", new Item(newSettings()));
    public static final Item CRUSHED_BRASS = register("crushed_brass", new Item(newSettings()));

    //Ingots/Bars
    public static final Item ANDESITE_ALLOY = register("andesite_alloy", new Item(newSettings()));
    public static final Item COPPER_INGOT = register("copper_ingot", new Item(newSettings()));
    public static final Item ZINC_INGOT = register("zinc_ingot", new Item(newSettings()));
    public static final Item BRASS_INGOT = register("brass_ingot", new Item(newSettings()));

    //Misc
    public static final Item SAND_PAPER = register("sand_paper", new Item(newSettings()));
    public static final Item RED_SAND_PAPER = register("red_sand_paper", new Item(newSettings()));
    public static final Item OBSIDIAN_DUST = register("obsidian_dust", new Item(newSettings()));
    public static final Item ROSE_QUARTZ = register("rose_quartz", new Item(newSettings()));
    public static final Item POLISHED_ROSE_QUARTZ = register("polished_rose_quartz", new Item(newSettings()));
    public static final Item CHROMATIC_COMPOUND = register("chromatic_compound", new Item(newSettings()));
    public static final Item SHADOW_STEEL = register("shadow_steel", new Item(newSettings()));
    public static final Item REFINED_RADIANCE = register("refined_radiance", new GlintIngot(newSettings()));
    public static final Item ELECTRON_TUBE = register("electron_tube", new Item(newSettings()));
    public static final Item INTEGRATED_CIRCUIT = register("integrated_circuit", new Item(newSettings()));
    public static final Item FLOUR = register("flour", new Item(newSettings()));
    public static final Item DOUGH = register("dough", new Item(newSettings()));
    public static final Item ZINC_HANDLE = register("zinc_handle", new Item(newSettings()));
    public static final Item PROPELLER = register("propeller", new Item(newSettings()));
    public static final Item WHISK = register("whisk", new Item(newSettings()));
    public static final Item BRASS_HAND = register("brass_hand", new Item(newSettings()));
    public static final Item SLOT_COVER = register("slot_cover", new Item(newSettings()));
    //TODO add fertilizer properties
    public static final Item TREE_FERTILIZER = register("tree_fertilizer", new Item(newSettings()));

    //conveyer belt TODO add functionality
    public static final Item BELT_CONNECTOR = register("belt_connector", new Item(newSettings()));

    //Schematics TODO add actaul uses
    public static final Item EMPTY_BLUEPRINT = register("empty_blueprint", new Item(newSettings()));
    public static final Item BLUEPRINT_AND_QUILL = register("blueprint_and_quill", new Item(newSettings()));
    public static final Item BLUEPRINT = register("blueprint", new Item(newSettings()));

    /*
    *TOOLS
    * TODO add tool properties (make the tools usable)
     */

    //brass tools
    public static final Item WRENCH = register("wrench", new Item(newSettings()));
    public static final Item GOGGLES = register("goggles", new Item(newSettings()));


    //blaze tools
    public static final Item BLAZING_PICKAXE = register("blazing_pickaxe", new CreatePickaxeItem(ToolMaterials.GOLD, 1, -2.8f, newSettings()));
    public static final Item BLAZING_SHOVEL = register("blazing_shovel", new Item(newSettings()));
    public static final Item BLAZING_AXE = register("blazing_axe", new Item(newSettings()));
    public static final Item BLAZING_SWORD = register("blazing_sword", new Item(newSettings()));

    //rose quartz tools
    public static final Item ROSE_QUARTZ_PICKAXE = register("rose_quartz_pickaxe", new Item(newSettings()));
    public static final Item ROSE_QUARTZ_SHOVEL = register("rose_quartz_shovel", new Item(newSettings()));
    public static final Item ROSE_QUARTZ_AXE = register("rose_quartz_axe", new Item(newSettings()));
    public static final Item ROSE_QUARTZ_SWORD = register("rose_quartz_sword", new Item(newSettings()));

    //shadow steel
    public static final Item SHADOW_STEEL_PICKAXE = register("shadow_steel_pickaxe", new Item(newSettings()));
    public static final Item SHADOW_STEEL_MATTOCK = register("shadow_steel_mattock", new Item(newSettings()));
    public static final Item SHADOW_STEEL_SWORD = register("shadow_steel_sword", new Item(newSettings()));

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
