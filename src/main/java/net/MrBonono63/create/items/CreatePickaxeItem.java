package net.MrBonono63.create.items;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class CreatePickaxeItem extends PickaxeItem {
    public CreatePickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
