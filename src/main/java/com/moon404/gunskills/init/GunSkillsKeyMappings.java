package com.moon404.gunskills.init;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.blaze3d.platform.InputConstants;
import com.moon404.gunskills.GunSkills;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
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
        Minecraft mc = Minecraft.getInstance();
        Options options = mc.options;
        mc.options.keyMappings = ArrayUtils.addAll((KeyMapping[])(new KeyMapping[]{options.keyAttack, options.keyUse, options.keyUp, options.keyLeft, options.keyDown, options.keyRight, options.keyJump, options.keyShift, options.keySprint, options.keyDrop, options.keyInventory, options.keyChat, options.keyPlayerList, options.keyPickItem, options.keyCommand, options.keySocialInteractions, options.keyScreenshot, /*options.keyTogglePerspective,*/ options.keySmoothCamera, options.keyFullscreen, options.keySpectatorOutlines, options.keySwapOffhand, options.keySaveHotbarActivator, options.keyLoadHotbarActivator, options.keyAdvancements}), (KeyMapping[])options.keyHotbarSlots);
        options.keyTogglePerspective.setKey(InputConstants.UNKNOWN);
    }
}
