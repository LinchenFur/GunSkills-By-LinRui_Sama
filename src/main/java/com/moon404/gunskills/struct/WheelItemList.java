package com.moon404.gunskills.struct;

import java.util.Arrays;
import java.util.List;

import com.moon404.gunskills.init.GunSkillsItems;

import net.minecraft.world.item.Item;

public class WheelItemList
{
    public static List<Item> recoverList;

    public static void init()
    {
        recoverList = Arrays.asList(
            GunSkillsItems.SHIELD_BOOST.get(),
            GunSkillsItems.PHOENIX_KIT.get(),
            GunSkillsItems.SHIELD_BATTERY.get(),
            GunSkillsItems.SHIELD_CELL.get(),
            GunSkillsItems.MED_KIT.get(),
            GunSkillsItems.SYRINGE.get()
        );
    }
}
