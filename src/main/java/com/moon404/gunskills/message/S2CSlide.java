package com.moon404.gunskills.message;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.animation.Slide;
import com.moon404.gunskills.struct.SlideInfo;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class S2CSlide
{
    private static final String PROTOCOL_VERSION = "1";
    private static int index = 1;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunSkills.MODID, "s2c_slide"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register()
    {
        INSTANCE.registerMessage(index++, SlideInfo.class,
        (content, buf) ->
        {
            buf.writeUtf(content.name);
            buf.writeInt(content.action);
        },
        (buf) ->
        {
            SlideInfo content = new SlideInfo(buf.readUtf(), buf.readInt());
            return content;
        },
        (content, ctx) ->
        {
            ctx.get().enqueueWork(() ->
            {
                if (content.action == 1)
                {
                    Slide.slidingPlayers.add(content.name);
                }
                else
                {
                    Slide.slidingPlayers.remove(content.name);
                }
            });
            ctx.get().setPacketHandled(true);
        });
    }
}
