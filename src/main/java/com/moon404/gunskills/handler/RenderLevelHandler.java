package com.moon404.gunskills.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joml.Vector4f;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.struct.PingInfo;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, value = Dist.CLIENT)
public class RenderLevelHandler
{
    public static float curTick;
    public static List<PingInfo> pingInfos = new ArrayList<>();

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event)
    {
        if (event.getStage() != Stage.AFTER_TRIPWIRE_BLOCKS) return;
        curTick = event.getRenderTick() + event.getPartialTick();

        Minecraft mc = Minecraft.getInstance();
        VertexConsumer vertexConsumer = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines());
        Camera camera = event.getCamera();
        Vec3 pos = camera.getPosition();
                
        PoseStack poseStack = event.getPoseStack();
        Iterator<PingInfo> iterator = pingInfos.iterator();
        while (iterator.hasNext())
        {
            PingInfo pingInfo = iterator.next();
            if (curTick > pingInfo.t)
            {
                iterator.remove();
                continue;
            }
            Vec3 target = new Vec3(pingInfo.x, pingInfo.y, pingInfo.z);
            Vec3 start = target.add(pos.reverse());
            Vec3 end = start.add(1, 1, 1);
            AABB box = new AABB(start, end);
            LevelRenderer.renderLineBox(poseStack, vertexConsumer, box, 1, 1, 1, 1);

            // reference: https://github.com/LukenSkyne/Minecraft-Ping-Wheel/blob/1.20.1/common/src/main/java/nx/pingwheel/common/helper/MathUtils.java
            target = new Vec3(pingInfo.x + 0.5, pingInfo.y + 0.5, pingInfo.z + 0.5);
            start = target.add(pos.reverse());
            Window window = mc.getWindow();
            Vector4f worldPosRel = new Vector4f(start.toVector3f(), 1f);
            worldPosRel.mul(event.getPoseStack().last().pose());
            worldPosRel.mul(event.getProjectionMatrix());
            float depth = worldPosRel.w;
            if (depth != 0) worldPosRel.div(depth);
            pingInfo.depth = depth;
            pingInfo.screenPos = new Vec2(
                window.getGuiScaledWidth() * (0.5f + worldPosRel.x * 0.5f),
                window.getGuiScaledHeight() * (0.5f - worldPosRel.y * 0.5f));
            pingInfo.distance = (float)target.distanceTo(pos);
        }
    }
}
