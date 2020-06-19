package net.MrBonono63.create.items;

import net.minecraft.item.Item;

public class VerticalGearBox extends Item {
    public VerticalGearBox(Settings settings) {
        super(settings);
    }

    @Override
    protected String getOrCreateTranslationKey() {
        return "item.create.gearbox";
    }
}
