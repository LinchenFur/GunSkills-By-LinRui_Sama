package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class Stim extends SkillItem
{
    public Stim(Properties properties)
    {
        super(properties, ClassType.ROGUE);
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        float hp = player.getHealth();
        hp -= 4;
        if (hp < 1) hp = 1;
        player.setHealth(hp);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1));
        return true;
    }
}
