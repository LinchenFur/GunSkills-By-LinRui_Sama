package com.moon404.gunskills.handler;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.mojang.math.Axis;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.message.DamageIndicatorMessage.DamageIndicator;
import com.moon404.gunskills.struct.DamageInfo;
import com.moon404.gunskills.struct.PingInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(GunSkills.MODID, "textures/gui/ping.png");

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

        for (PingInfo info : RenderLevelHandler.pingInfos)
        {
            Vec2 pos = info.screenPos;
            if (info.depth <= 0) pos = pos.scale(-1);
            int x = (int)pos.x;
            int width = mc.getWindow().getGuiScaledWidth() - 8;
            if (x < 8) x = 8;
            else if (x > width) x = width;
            int y = (int)pos.y;
            int height = mc.getWindow().getGuiScaledHeight() - 8;
            if (y < 8) y = 8;
            else if (y > height) y = height;

            pos = new Vec2(x, y);
            Vec2 center = new Vec2((width + 8) / 2, (height + 8) / 2);
            double alpha = Math.sqrt(center.distanceToSqr(pos)) / Math.sqrt(center.distanceToSqr(Vec2.ZERO));
            if (alpha < 0.25) alpha = 0.25;
            else if (alpha > 1) alpha = 1;
            float scale = (float)Math.sqrt(alpha);

            GuiGraphics guiGraphics = event.getGuiGraphics();
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(x, y, 0);
            poseStack.scale(scale, scale, scale);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, (float)alpha);

            guiGraphics.blit(TEXTURE2, -8, -8, 0, 0, 16, 16, 16, 16);
            width = mc.font.width(info.sender);
            height = mc.font.wordWrapHeight(info.sender, width);
            guiGraphics.drawString(mc.font, info.sender, -width / 2, -height / 2 - 12, 0xFFFF00);
            String s = (int)info.distance + "格";
            width = mc.font.width(s);
            height = mc.font.wordWrapHeight(s, width);
            guiGraphics.drawString(mc.font, s, -width / 2, -height / 2 + 12, 0xFFFF00);

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
            poseStack.popPose();
        }
    }
}
