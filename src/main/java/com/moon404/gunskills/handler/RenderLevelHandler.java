package com.moon404.gunskills.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.struct.PingInfo;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
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
    private static Pose pose;
    public static List<PingInfo> pingInfos = new ArrayList<>();

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event)
    {
        if (event.getStage() == Stage.AFTER_TRIPWIRE_BLOCKS)
            pose = event.getPoseStack().last();
        if (event.getStage() != Stage.AFTER_LEVEL) return;
        curTick = event.getRenderTick() + event.getPartialTick();

        VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.lines());
        Camera camera = event.getCamera();
        Vec3 pos = camera.getPosition();
                
        PoseStack poseStack = new PoseStack();
        poseStack.last().pose().set(pose.pose());
        poseStack.last().normal().set(pose.normal());

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
        }
    }
}
