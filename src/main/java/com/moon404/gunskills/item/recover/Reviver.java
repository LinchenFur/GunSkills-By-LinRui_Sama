package com.moon404.gunskills.item.recover;

import com.moon404.gunskills.Utils;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Reviver extends RecoverItem
{
    public Reviver(Item.Properties properties)
    {
        super(properties);
        tooltips.add(Component.literal("复活所有阵亡队友"));
    }

    public int getUseDuration(ItemStack stack)
    {
        return 200;
    }

    private static boolean hasDeadTeammate(ServerPlayer serverPlayer, Level level)
    {
        for (Player player : level.players())
        {
            if (player.isSpectator() && player.getTeam() == serverPlayer.getTeam())
            {
                return true;
            }
        }
        return false;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        if (!level.isClientSide && entity instanceof ServerPlayer player)
        {
            if (hasDeadTeammate(player, level))
            {
                Utils.invokeFunction(player, "gunskills:revive");
            }
            else
            {
                player.displayClientMessage(Component.literal("没有可以复活的队友"), true);
                return stack;
            }
        }
        stack.setDamageValue(0);
        return this.isEdible() ? entity.eat(level, stack) : stack;
    }
}
