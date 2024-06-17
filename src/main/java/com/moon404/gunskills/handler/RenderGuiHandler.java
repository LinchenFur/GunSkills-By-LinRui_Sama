package com.moon404.gunskills.handler;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.struct.DamageInfo;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, value = Dist.CLIENT)
public class RenderGuiHandler
{
    public static DamageInfo lastDamage = new DamageInfo();

    @SubscribeEvent
    public static void onRenderGui(CustomizeGuiOverlayEvent event)
    {
        Minecraft mc = Minecraft.getInstance();

        if (lastDamage.startTick == -1)
            lastDamage.startTick = RenderLevelHandler.curTick;

        if (RenderLevelHandler.curTick < lastDamage.startTick + 20)
        {
            int xc = mc.getWindow().getGuiScaledWidth() / 2;
            int yc = mc.getWindow().getGuiScaledHeight() / 2;
            NumberFormat formatter = new DecimalFormat("0.0");
            String s = formatter.format(lastDamage.amount);
            int width = mc.font.width(s);
            event.getGuiGraphics().drawString(mc.font, s, xc - width / 2, yc - mc.font.wordWrapHeight(s, width) * 2, lastDamage.color);
        }
        else
        {
            lastDamage.amount = 0;
        }
    }
}
