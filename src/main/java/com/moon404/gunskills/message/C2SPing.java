package com.moon404.gunskills.message;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.struct.PingInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class C2SPing
{
    private static final String PROTOCOL_VERSION = "1";
    private static int index = 1;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunSkills.MODID, "c2s_ping"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

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
                ServerPlayer sender = ctx.get().getSender();
                ServerLevel level = sender.serverLevel();
                for (ServerPlayer player : level.players())
                {
                    if (player.getTeam() == sender.getTeam())
                    {
                        S2CPing.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), content);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        });
    }
}
