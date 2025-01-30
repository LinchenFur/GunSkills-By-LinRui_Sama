package com.moon404.gunskills.init;

import java.util.Arrays;

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
    public static final KeyMapping DROPA_KEY = new KeyMapping("key.gunskills.dropa", InputConstants.KEY_X, "key.categories.gameplay");
    public static final KeyMapping DROPB_KEY = new KeyMapping("key.gunskills.dropb", InputConstants.KEY_C, "key.categories.gameplay");
    public static final KeyMapping DROPC_KEY = new KeyMapping("key.gunskills.dropc", InputConstants.KEY_V, "key.categories.gameplay");

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event)
    {
        event.register(PING_KEY);
        event.register(DROPA_KEY);
        event.register(DROPB_KEY);
        event.register(DROPC_KEY);
        Minecraft mc = Minecraft.getInstance();
        Options options = mc.options;
        int index = ArrayUtils.indexOf(options.keyMappings, options.keyTogglePerspective);
        options.keyMappings = ArrayUtils.addAll(Arrays.copyOfRange(options.keyMappings, 0, index), Arrays.copyOfRange(options.keyMappings, index + 1, options.keyMappings.length));
        options.keyTogglePerspective.setKey(InputConstants.UNKNOWN);
    }
}
