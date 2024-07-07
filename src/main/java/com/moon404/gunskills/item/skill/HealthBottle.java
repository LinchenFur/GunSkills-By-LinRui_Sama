package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.HealthBottleEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class HealthBottle extends SkillItem
{
    public HealthBottle(Properties properties)
    {
        super(properties, ClassType.SUPPORT);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("在每个队友的脚下生成一个生命恢复区域"));
        tooltips.add(Component.literal("其中的玩家每秒恢复 2 点生命，持续 2 秒"));
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        for (Player target : player.level().players())
        {
            if (!target.isSpectator() && target.getTeam() == player.getTeam())
            {
                HealthBottleEntity bottle = new HealthBottleEntity(GunSkillsEntities.HEALTH_BOTTLE.get(), target.level());
                bottle.setPos(target.position());
                target.level().addFreshEntity(bottle);
            }
        }
        return true;
    }
}
