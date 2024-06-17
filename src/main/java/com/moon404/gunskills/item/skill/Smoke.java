package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.SmokeEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.world.entity.player.Player;

public class Smoke extends SkillItem
{
    public Smoke(Properties properties)
    {
        super(properties, ClassType.ATTACK);
    }
    
    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        SmokeEntity smoke = new SmokeEntity(GunSkillsEntities.SMOKE.get(), player.level());
        smoke.setPos(player.getEyePosition());
        smoke.setNoGravity(true);
        smoke.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2, 0);
        player.level().addFreshEntity(smoke);
        return true;
    }
}
