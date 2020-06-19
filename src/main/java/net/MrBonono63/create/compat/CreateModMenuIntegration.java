package net.MrBonono63.create.compat;

import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.MrBonono63.create.Main;
import net.MrBonono63.create.config.CreateConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

import java.util.Optional;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class CreateModMenuIntegration implements ModMenuApi {
    @Override
    public String getModId() {
        return Main.MOD_ID;
    }

    public Optional<Supplier<Screen>> getConfigScreen(Screen screen) {
        return Optional.of(AutoConfig.getConfigScreen(CreateConfig.class, screen));
    }
}
