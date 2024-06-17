package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.SilenceEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.world.entity.player.Player;

public class Silence extends SkillItem
{
    public Silence(Properties properties)
    {
        super(properties, ClassType.ATTACK);
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        SilenceEntity silence = new SilenceEntity(GunSkillsEntities.SILENCE.get(), player.level());
        silence.user = player;
        silence.setPos(player.getEyePosition());
        silence.setNoGravity(true);
        silence.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2, 0);
        player.level().addFreshEntity(silence);
        return true;
    }
}
