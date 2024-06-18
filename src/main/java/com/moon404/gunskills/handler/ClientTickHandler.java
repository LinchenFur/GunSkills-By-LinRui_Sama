package com.moon404.gunskills.handler;

import java.util.List;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.WheelGui;
import com.moon404.gunskills.init.GunSkillsConfigs;
import com.moon404.gunskills.struct.WheelItemList;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, value = Dist.CLIENT)
public class ClientTickHandler
{
    public static int holding;
    public static int duration;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        int lastHolding = holding;
        holding = -1;
        List<Item> list = null;
        int recoverIndex = GunSkillsConfigs.RECOVER_INDEX.get().intValue() - 1;
        if (recoverIndex > -1 && mc.options.keyHotbarSlots[recoverIndex].isDown())
        {
            holding = recoverIndex;
            list = WheelItemList.recoverList;
        }
        if (holding > -1 && lastHolding == holding) duration += 1;
        else duration = 0;
        if (duration > 5) WheelGui.activate(list);
    }
}
