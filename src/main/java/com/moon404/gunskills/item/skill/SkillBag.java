package com.moon404.gunskills.item.skill;

import java.util.Arrays;
import java.util.List;

import com.moon404.gunskills.init.GunSkillsItems;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SkillBag extends SkillItem
{
    private static int counter = 0;
    public static List<Item> attackItems = Arrays.asList(
        GunSkillsItems.SILENCE.get(),
        GunSkillsItems.SNARE.get(),
        GunSkillsItems.FAST.get(),
        GunSkillsItems.SMOKE.get()
    );
    public static List<Item> rogueItems = Arrays.asList(
        GunSkillsItems.VOID.get(),
        GunSkillsItems.STIM.get(),
        GunSkillsItems.LIFT.get(),
        GunSkillsItems.BOOT.get()
    );
    public static List<Item> supportItems = Arrays.asList(
        GunSkillsItems.PURIFY.get(),
        GunSkillsItems.HEALTH_BOTTLE.get(),
        GunSkillsItems.SHIELD_BOTTLE.get(),
        GunSkillsItems.CHARGE.get()
    );
    public static List<Item> scoutItems = Arrays.asList(
        GunSkillsItems.SCAN.get(),
        GunSkillsItems.IRE.get(),
        GunSkillsItems.EXHIBIT.get(),
        GunSkillsItems.GLOW.get()
    );

    public SkillBag(Properties properties)
    {
        super(properties, null);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("根据自己的职业获得一个技能物品"));
    }

    @Override
    public boolean onToss(Player player)
    {
        List<Item> items = rogueItems;
        if (ClassType.getClass(player) == ClassType.ATTACK) items = attackItems;
        else if (ClassType.getClass(player) == ClassType.SUPPORT) items = supportItems;
        else if (ClassType.getClass(player) == ClassType.SCOUT) items = scoutItems;
        int rand = counter;
        counter = (counter + 1) % 4;
        player.addItem(new ItemStack(items.get(rand)));
        return true;
    }
}
