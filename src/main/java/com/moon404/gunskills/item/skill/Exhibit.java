package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.ExhibitEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.world.entity.player.Player;

public class Exhibit extends SkillItem
{
    public Exhibit(Properties properties)
    {
        super(properties, ClassType.SCOUT);
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        ExhibitEntity exhibit = new ExhibitEntity(GunSkillsEntities.EXHHIBIT.get(), player.level());
        exhibit.user = player;
        exhibit.setPos(player.getEyePosition());
        exhibit.setNoGravity(true);
        exhibit.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2, 0);
        player.level().addFreshEntity(exhibit);
        return true;
    }
}
