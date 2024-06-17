package com.moon404.gunskills.handler;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.item.skill.SkillItem;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, value = Dist.DEDICATED_SERVER)
public class ItemTossHandler
{
    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event)
    {
        if (event.getEntity().getItem().getItem() instanceof SkillItem item)
        {
            event.setCanceled(item.onToss(event.getPlayer()));
        }
    }
}
