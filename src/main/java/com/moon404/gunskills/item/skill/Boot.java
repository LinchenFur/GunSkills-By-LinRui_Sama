package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;

public class Boot extends SkillItem
{
    public Boot(Properties properties)
    {
        super(properties, ClassType.ROGUE);
        tooltips.add(Component.literal("放在副手，被动生效并消耗"));
        tooltips.add(Component.literal("摔落时不会被减速"));
    }
}
