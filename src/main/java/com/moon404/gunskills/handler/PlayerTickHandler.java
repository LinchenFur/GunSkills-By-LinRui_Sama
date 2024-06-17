package com.moon404.gunskills.handler;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.init.GunSkillsItems;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, value = Dist.DEDICATED_SERVER)
public class PlayerTickHandler
{
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event)
    {
        if (event.phase != Phase.END) return;
        Player player = event.player;

        if (player.hasEffect(MobEffects.BLINDNESS))
        {
            Item holding = player.getInventory().getSelected().getItem();
            if (!(holding instanceof AirItem) && holding != GunSkillsItems.VOID.get())
            {
                player.removeEffect(MobEffects.INVISIBILITY);
                player.removeEffect(MobEffects.BLINDNESS);
            }
        }
    }
}
