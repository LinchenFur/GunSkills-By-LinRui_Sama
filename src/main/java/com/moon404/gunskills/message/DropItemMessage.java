package com.moon404.gunskills.message;

import com.moon404.gunskills.GunSkills;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class DropItemMessage
{
    private static final String PROTOCOL_VERSION = "1";
    private static int index = 1;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(GunSkills.MODID, "drop_item"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register()
    {
        INSTANCE.registerMessage(index++, DropItem.class,
        (content, buf) ->
        {
            buf.writeInt(content.itemid);
        },
        (buf) ->
        {
            DropItem content = new DropItem();
            content.itemid = buf.readInt();
            return content;
        },
        (content, ctx) ->
        {
            ctx.get().enqueueWork(() ->
            {
                ServerPlayer player = ctx.get().getSender();
                Inventory inventory = player.getInventory();
                ItemStack itemStack = inventory.getItem(content.itemid);
                if (itemStack.isEmpty()) return;
                ItemStack drop = itemStack.split(1);
                player.drop(drop, true);
            });
            ctx.get().setPacketHandled(true);
        });
    }

    public static class DropItem
    {
        public int itemid;
    }
}
