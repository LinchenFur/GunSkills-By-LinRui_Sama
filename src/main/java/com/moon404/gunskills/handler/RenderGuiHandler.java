package com.moon404.gunskills.handler;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.message.DamageIndicatorMessage.DamageIndicator;
import com.moon404.gunskills.struct.DamageInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, value = Dist.CLIENT)
public class RenderGuiHandler
{
    public static DamageInfo lastDamage = new DamageInfo();

    public static DamageIndicator lastIndicator = new DamageIndicator();

    private static final ResourceLocation TEXTURE = new ResourceLocation(GunSkills.MODID, "textures/gui/indicator.png");

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

        if (lastIndicator.startTick == -1)
            lastIndicator.startTick = RenderLevelHandler.curTick;
        
        if (RenderLevelHandler.curTick < lastIndicator.startTick + 20)
        {
            LocalPlayer player = mc.player;
            Vec3 lookVec3 = player.getLookAngle();
            Vec2 lookVec2 = new Vec2((float)lookVec3.x, (float)lookVec3.z).normalized();
            double dx = lastIndicator.x - player.getX();
            double dz = lastIndicator.z - player.getZ();
            Vec2 srcVec2 = new Vec2((float)dx, (float)dz).normalized();
            double dot = lookVec2.dot(srcVec2);
            double angle = Math.acos(dot);
            double cross = lookVec2.x * srcVec2.y - lookVec2.y * srcVec2.x;
            if (cross < 0) angle = Math.PI * 2 - angle;
            double rawAngle = angle;
            angle -= Math.PI / 2;
            if (angle < 0) angle += Math.PI * 2;
            double distance = 50;
            int xc = mc.getWindow().getGuiScaledWidth() / 2;
            int yc = mc.getWindow().getGuiScaledHeight() / 2;
            double x = xc + distance * Math.cos(angle);
            double y = yc + distance * Math.sin(angle);
            PoseStack poseStack = event.getGuiGraphics().pose();
            poseStack.pushPose();
            poseStack.translate(x, y, 0);
            poseStack.mulPose(Axis.ZP.rotation((float)rawAngle));
            event.getGuiGraphics().blit(TEXTURE, -16, -8, 0, 0, 32, 16, 32, 16);
            poseStack.popPose();
        }
        else
        {
            lastIndicator.startTick = 0;
        }
    }
}
