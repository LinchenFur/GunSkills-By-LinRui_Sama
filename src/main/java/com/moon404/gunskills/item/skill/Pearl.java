package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.PearlEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class Pearl extends SkillItem
{
    public Pearl(Properties properties)
    {
        super(properties, ClassType.ROGUE);
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 4));
        PearlEntity pearl = new PearlEntity(player.level(), player);
        pearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1, 0);
        player.level().addFreshEntity(pearl);
        return true;
    }
}
