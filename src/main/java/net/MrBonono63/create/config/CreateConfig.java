package net.MrBonono63.create.config;

import blue.endless.jankson.Comment;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

/*
Loosely based on Exotic Blocks and the wiki for autoconfig
* */

@Config(name = "create")
@Config.Gui.Background("textures/block/jungle_log.png")
public class CreateConfig implements ConfigData {

    /*
    * Server Side Configs
    * */
    @ConfigEntry.Category("Server Configs")

    @ConfigEntry.BoundedDiscrete(min = 20, max = 100)
    @Comment("How fast Cocoa logs mature and produce cocoa in game.")
    public static int cocoaLogGrowthSpeed = 20;

    /*
    * Client Side Configs
    * */
    @ConfigEntry.Category("Client Configs")
    public static int a;
}
