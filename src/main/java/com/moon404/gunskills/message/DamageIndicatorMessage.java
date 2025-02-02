package com.moon404.gunskills.message;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.handler.RenderGuiHandler;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class DamageIndicatorMessage
{
    private static final String PROTOCOL_VERSION = "1";
    private static int index = 1;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunSkills.MODID, "damage_indicator"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register()
    {
        INSTANCE.registerMessage(index++, DamageIndicator.class,
        (content, buf) ->
        {
            buf.writeFloat(content.x);
            buf.writeFloat(content.z);
        },
        (buf) ->
        {
            DamageIndicator content = new DamageIndicator();
            content.x = buf.readFloat();
            content.z = buf.readFloat();
            return content;
        },
        (content, ctx) ->
        {
            ctx.get().enqueueWork(() ->
            {
                RenderGuiHandler.lastIndicator.startTick = -1;
                RenderGuiHandler.lastIndicator.x = content.x;
                RenderGuiHandler.lastIndicator.z = content.z;
            });
            ctx.get().setPacketHandled(true);
        });
    }

    public static class DamageIndicator
    {
        public float startTick;
        public float x, z;
    }
}
