package net.MrBonono63.create.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.world.dimension.DimensionType;

public class CreateBlazingPickaxe extends PickaxeItem {
    public CreateBlazingPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity miner) {
        return !shouldTakeDamage(worldIn, stack) || super.postMine(stack, worldIn, state, pos, miner);
    }

    protected boolean shouldTakeDamage(World world, ItemStack stack) {
        return true;
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(2);
        return !shouldTakeDamage(attacker.world, stack) || super.postHit(stack, target, attacker);
    }
}
