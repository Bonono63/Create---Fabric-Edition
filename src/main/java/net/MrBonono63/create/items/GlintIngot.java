package net.MrBonono63.create.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GlintIngot extends Item {
    public GlintIngot(Settings settings) {
        super(settings);
    }

    public boolean hasEnchantmentGlint(ItemStack stack) {
        return true;
    }
}
