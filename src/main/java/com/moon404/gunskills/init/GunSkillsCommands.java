package com.moon404.gunskills.init;

import com.moon404.gunskills.ChooseCommand;
import com.moon404.gunskills.ClassCommand;
import com.moon404.gunskills.GunSkills;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, bus = Bus.FORGE)
public class GunSkillsCommands
{
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event)
    {
        ClassCommand.register(event.getDispatcher());
        ChooseCommand.register(event.getDispatcher());
    }
}
