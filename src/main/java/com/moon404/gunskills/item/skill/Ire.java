package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;

public class Ire extends SkillItem
{
    public Ire(Properties properties)
    {
        super(properties, ClassType.SCOUT);
        tooltips.add(Component.literal("放在副手，被动生效并消耗"));
        tooltips.add(Component.literal("造成伤害时令目标发光 5 秒"));
    }
}
