package com.moon404.gunskills.handler;

import com.moon404.gunskills.init.GunSkillsItems;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class PlayerTickHandler
{
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event)
    {
        if (event.phase != Phase.END) return;
        if (event.side != LogicalSide.SERVER) return;
        Player player = event.player;

        if (player.hasEffect(MobEffects.BLINDNESS))
        {
            Item holding = player.getInventory().getSelected().getItem();
            if (!(holding instanceof AirItem) && holding != GunSkillsItems.VOID.get())
            {
                player.removeEffect(MobEffects.INVISIBILITY);
                player.removeEffect(MobEffects.BLINDNESS);
            }
            else if (player.hasEffect(MobEffects.GLOWING))
            {
                player.removeEffect(MobEffects.INVISIBILITY);
                player.removeEffect(MobEffects.BLINDNESS);
            }
        }
    }
}
