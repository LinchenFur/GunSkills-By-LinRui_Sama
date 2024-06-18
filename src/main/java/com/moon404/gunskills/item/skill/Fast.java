package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;

public class Fast extends SkillItem
{
    public Fast(Properties properties)
    {
        super(properties, ClassType.ATTACK);
        tooltips.add(Component.literal("放在副手，被动生效并消耗"));
        tooltips.add(Component.literal("受到伤害时获得 3 秒速度III"));
    }
}
