package com.moon404.gunskills.message;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.handler.RenderGuiHandler;
import com.moon404.gunskills.struct.DamageInfo;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ShowDamageMessage
{
    private static final String PROTOCOL_VERSION = "1";
    private static int index = 1;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunSkills.MODID, "show_damage"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register()
    {
        INSTANCE.registerMessage(index++, DamageInfo.class,
        (content, buf) ->
        {
            buf.writeFloat(content.amount);
            buf.writeInt(content.color);
        },
        (buf) ->
        {
            DamageInfo content = new DamageInfo();
            content.amount = buf.readFloat();
            content.color = buf.readInt();
            return content;
        },
        (content, ctx) ->
        {
            ctx.get().enqueueWork(() ->
            {
                RenderGuiHandler.lastDamage.amount += content.amount;
                RenderGuiHandler.lastDamage.color = content.color;
                RenderGuiHandler.lastDamage.startTick = -1;
            });
            ctx.get().setPacketHandled(true);
        });
    }
}
