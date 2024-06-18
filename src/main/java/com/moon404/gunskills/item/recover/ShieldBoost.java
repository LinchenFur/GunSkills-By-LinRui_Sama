package com.moon404.gunskills.item.recover;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShieldBoost extends RecoverItem
{
    public ShieldBoost(Item.Properties properties)
    {
        super(properties);
        tooltips.add(Component.literal("提供 25 护盾进化经验"));
        tooltips.add(Component.literal("使用此物品造成的护盾进化会恢复等量护盾值"));
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 30;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        if (!level.isClientSide && entity instanceof Player player)
        {
            int oldlevel = player.experienceLevel;
            player.giveExperiencePoints(2500);
            int newlevel = player.experienceLevel;
            if (newlevel > oldlevel)
            {
                player.setAbsorptionAmount(player.getAbsorptionAmount() + 4);
            }
        }
        stack.setDamageValue(0);
        return this.isEdible() ? entity.eat(level, stack) : stack;
    }
}
