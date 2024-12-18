package com.moon404.gunskills.handler;

import com.moon404.gunskills.Utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LogoutHandler
{
    @SubscribeEvent
    public static void onLogout(PlayerLoggedOutEvent event)
    {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer)
        {
            Utils.invokeFunction(serverPlayer, "gunskills:logout");
        }
    }
}
