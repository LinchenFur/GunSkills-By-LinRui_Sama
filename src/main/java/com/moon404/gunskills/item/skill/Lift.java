package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.LiftEntity;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;

public class Lift extends SkillItem
{
    public Lift(Properties properties)
    {
        super(properties, ClassType.ROGUE);
        tooltips.add(Component.literal("按 Q 扔出，落地生效"));
        tooltips.add(Component.literal("生成一个持续 5 秒的重力电梯"));
        tooltips.add(Component.literal("可抬升最高 10 格"));
    }

    @Override
    public boolean onLand(ItemEntity entity)
    {
        LiftEntity lift = new LiftEntity(GunSkillsEntities.LIFT.get(), entity.level());
        lift.setPos(entity.position());
        entity.level().addFreshEntity(lift);
        return true;
    }
}
