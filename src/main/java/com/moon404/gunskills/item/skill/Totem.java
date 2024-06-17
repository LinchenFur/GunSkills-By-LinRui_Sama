package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.TotemEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.world.entity.player.Player;

public class Totem extends SkillItem
{
    public Totem(Properties properties)
    {
        super(properties, ClassType.ATTACK);
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        if (player.getAbsorptionAmount() == 0) return false;
        TotemEntity totem = new TotemEntity(GunSkillsEntities.TOTEM.get(), player.level());
        totem.setPos(player.position());
        totem.player = player;
        player.level().addFreshEntity(totem);
        return true;
    }
}
