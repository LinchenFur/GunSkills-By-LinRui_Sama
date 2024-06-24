package com.moon404.gunskills.handler;

import com.moon404.gunskills.item.skill.SkillItem;

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
        if (event.getEntity().getItem().getItem() instanceof SkillItem item)
        {
            event.setCanceled(item.onToss(event.getPlayer()));
        }
    }
}
