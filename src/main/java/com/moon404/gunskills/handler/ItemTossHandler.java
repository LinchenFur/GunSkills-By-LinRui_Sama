package com.moon404.gunskills.handler;

import com.moon404.gunskills.item.skill.SkillItem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemTossHandler
{
    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event)
    {
        Level level = event.getPlayer().level();
        if (level.isClientSide) return;
        ItemStack itemStack = event.getEntity().getItem();
        Player player = event.getPlayer();
        if (itemStack.getItem() instanceof SkillItem item)
        {
            if (item.onToss(player))
            {
                int count = itemStack.getCount();
                if (count == 1)
                {
                    event.setCanceled(true);
                }
                else
                {
                    itemStack.setCount(count - 1);
                }
            }
        }
    }
}
