package com.moon404.gunskills.init;

import com.mojang.blaze3d.platform.InputConstants;
import com.moon404.gunskills.GunSkills;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class GunSkillsKeyMappings
{
    public static final KeyMapping PING_KEY = new KeyMapping("key.gunskills.ping", InputConstants.Type.MOUSE, 2, "key.categories.gameplay");

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event)
    {
        event.register(PING_KEY);
    }
}
