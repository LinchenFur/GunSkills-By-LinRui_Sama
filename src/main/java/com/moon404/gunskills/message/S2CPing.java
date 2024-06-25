package com.moon404.gunskills.message;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.handler.RenderLevelHandler;
import com.moon404.gunskills.struct.PingInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class S2CPing
{
    private static final String PROTOCOL_VERSION = "1";
    private static int index = 1;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunSkills.MODID, "s2c_ping"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register()
    {
        INSTANCE.registerMessage(index++, PingInfo.class,
        (content, buf) ->
        {
            buf.writeInt(content.x);
            buf.writeInt(content.y);
            buf.writeInt(content.z);
            buf.writeInt(content.t);
        },
        (buf) ->
        {
            PingInfo content = new PingInfo();
            content.x = buf.readInt();
            content.y = buf.readInt();
            content.z = buf.readInt();
            content.t = buf.readInt();
            return content;
        },
        (content, ctx) ->
        {
            ctx.get().enqueueWork(() ->
            {
                content.t += RenderLevelHandler.curTick;
                RenderLevelHandler.pingInfos.add(content);
            });
            ctx.get().setPacketHandled(true);
        });
    }
}
