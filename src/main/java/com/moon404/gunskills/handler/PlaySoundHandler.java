package com.moon404.gunskills.handler;

import com.moon404.gunskills.init.GunSkillsConfigs;

import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlaySoundHandler
{
    @SubscribeEvent
    public static void onPlaySound(PlayLevelSoundEvent event)
    {
        String name = event.getSound().get().getLocation().getPath();
        if (name.startsWith("block") && name.endsWith("step"))
        {
            event.setNewVolume(event.getOriginalVolume() * GunSkillsConfigs.STEP_SOUND_MUL.get());
        }
    }
}
