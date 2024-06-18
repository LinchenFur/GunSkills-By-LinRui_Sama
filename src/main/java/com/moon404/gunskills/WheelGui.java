package com.moon404.gunskills;

import java.util.List;

import com.moon404.gunskills.message.ChangeItemMessage;
import com.moon404.gunskills.message.ChangeItemMessage.ChangeItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class WheelGui extends Screen
{
    public static boolean active = false;
    public static final WheelGui INSTANCE = new WheelGui();
    public static List<Item> itemList;
    public static final float distance = 30;

    public WheelGui()
    {
        super(Component.literal("Choose"));
    }

    public static void activate(List<Item> list)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == null)
        {
            active = true;
            mc.setScreen(INSTANCE);
        }
        itemList = list;
    }

    public static void deactivate()
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == INSTANCE)
        {
            active = false;
            mc.setScreen(null);
        }
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton)
    {
        int index = getMouseIndex(pMouseX, pMouseY, true);
        ChangeItem message = new ChangeItem();
        message.itemid = Item.getId(itemList.get(index));
        ChangeItemMessage.INSTANCE.sendToServer(message);
        deactivate();
        return false;
    }

    @Override
    public void removed()
    {
        active = false;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {
        Minecraft mc = Minecraft.getInstance();
        int xc = mc.getWindow().getGuiScaledWidth() / 2;
        int yc = mc.getWindow().getGuiScaledHeight() / 2;
        int index = WheelGui.getMouseIndex(mc.mouseHandler.xpos(), mc.mouseHandler.ypos(), false);
        int num = WheelGui.itemList.size();
        double singleRadian = 3.14 * 2 / num;
        for (int i = 0; i < num; i++)
        {
            int dx = (int)(distance * Math.sin(i * singleRadian));
            int dy = (int)(distance * Math.cos(i * singleRadian));
            if (i == index)
            {
                pGuiGraphics.renderItem(Items.BLACK_STAINED_GLASS_PANE.getDefaultInstance(), xc + dx - 8, yc + dy - 8);
            }
            pGuiGraphics.renderItem(WheelGui.itemList.get(i).getDefaultInstance(), xc + dx - 8, yc + dy - 8);
        }
    }


    public static int getMouseIndex(double pMouseX, double pMouseY, boolean scale)
    {
        int num = itemList.size();
        double singleRadian = 3.14 * 2 / num;
        Minecraft mc = Minecraft.getInstance();
        int xc = (scale ? mc.getWindow().getGuiScaledWidth() : mc.getWindow().getScreenWidth()) / 2;
        int yc = (scale ? mc.getWindow().getGuiScaledHeight() : mc.getWindow().getScreenHeight()) / 2;
        double radian = Math.atan2(pMouseX - xc, pMouseY - yc);
        if (radian < 0) radian += 6.28;
        radian += singleRadian / 2;
        int index = (int)(radian / singleRadian);
        if (index >= num) index -= num;
        return index;
    }
}
