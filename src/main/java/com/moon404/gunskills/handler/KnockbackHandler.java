package com.moon404.gunskills.handler;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KnockbackHandler
{
    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (entity.getAbsorptionAmount() > 0)
        {
            event.setCanceled(true);
        }
        if (entity.hasEffect(MobEffects.LEVITATION))
        {
            event.setCanceled(true);
        }
    }
}
