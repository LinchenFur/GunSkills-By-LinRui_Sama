package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.HealthBottleEntity;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;

public class HealthBottle extends SkillItem
{
    public HealthBottle(Properties properties)
    {
        super(properties, ClassType.SUPPORT);
        tooltips.add(Component.literal("按 Q 扔出，落地生效"));
        tooltips.add(Component.literal("生成一个持续 4 秒的生命恢复区域"));
        tooltips.add(Component.literal("站在其中的玩家每秒恢复 2 点生命"));
    }

    @Override
    public boolean onLand(ItemEntity entity)
    {
        HealthBottleEntity bottle = new HealthBottleEntity(GunSkillsEntities.HEALTH_BOTTLE.get(), entity.level());
        bottle.setPos(entity.position());
        entity.level().addFreshEntity(bottle);
        return false;
    }
}
