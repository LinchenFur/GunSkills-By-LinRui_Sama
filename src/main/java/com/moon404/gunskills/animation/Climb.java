package com.moon404.gunskills.animation;

import com.moon404.gunskills.GunSkills;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = GunSkills.MODID, value = Dist.CLIENT, bus = Bus.FORGE)
public class Climb
{
    public static int tickCount = 0;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event)
    {
        if (event.phase != Phase.START) return;
        if (event.player instanceof LocalPlayer player)
        {
            Minecraft minecraft = Minecraft.getInstance();
            Options options = minecraft.options;
            if (player.horizontalCollision && !player.onGround())
            {
                if (options.keyUp.isDown() && tickCount < 20)
                {
                    Vec3 delta = player.getDeltaMovement();
                    if (0.1 >= delta.y)
                    {
                        tickCount++;
                        player.setDeltaMovement(delta.x, 0.1, delta.z);
                    }
                }
            }
            if (player.onGround())
            {
                tickCount = 0;
            }
        }
    }
}
