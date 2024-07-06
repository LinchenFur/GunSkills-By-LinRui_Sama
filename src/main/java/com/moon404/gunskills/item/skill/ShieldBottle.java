package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.ShieldBottleEntity;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;

public class ShieldBottle extends SkillItem
{
    public ShieldBottle(Properties properties)
    {
        super(properties, ClassType.SUPPORT);
        tooltips.add(Component.literal("按 Q 扔出，落地生效"));
        tooltips.add(Component.literal("生成一个持续 4 秒的护盾恢复区域"));
        tooltips.add(Component.literal("站在其中的玩家每秒恢复 2 点护盾"));
    }

    @Override
    public boolean onLand(ItemEntity entity)
    {
        ShieldBottleEntity bottle = new ShieldBottleEntity(GunSkillsEntities.SHIELD_BOTTLE.get(), entity.level());
        bottle.setPos(entity.position());
        entity.level().addFreshEntity(bottle);
        return false;
    }
}
