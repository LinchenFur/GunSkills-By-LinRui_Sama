package com.moon404.gunskills.handler;

import com.moon404.gunskills.Utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DeathHandler
{
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer death)
        {
            if (event.getSource().getEntity() instanceof ServerPlayer killer)
            {
                Utils.invokeFunction(killer, "gunskills:kill");
            }
            Utils.invokeFunction(death, "gunskills:death");
        }
    }
}
