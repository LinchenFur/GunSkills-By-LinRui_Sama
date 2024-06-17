package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.HealthBottleEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class HealthBottle extends SkillItem
{
    public HealthBottle(Properties properties)
    {
        super(properties, ClassType.SUPPORT);
    }

    @Override
    public boolean onLand(ItemEntity entity)
    {
        if (entity.getOwner() instanceof Player player)
        {
            if (ClassType.getClass(player) != this.classType) return false;
            if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        }
        HealthBottleEntity bottle = new HealthBottleEntity(GunSkillsEntities.HEALTH_BOTTLE.get(), entity.level());
        bottle.setPos(entity.position());
        entity.level().addFreshEntity(bottle);
        entity.kill();
        return false;
    }
}
