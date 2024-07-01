package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.SmokeEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Smoke extends SkillItem
{
    public Smoke(Properties properties)
    {
        super(properties, ClassType.ATTACK);
        tooltips.add(Component.literal("按 Q 扔出，落地生效"));
        tooltips.add(Component.literal("在落地点创建一片烟雾"));
        tooltips.add(Component.literal("半径为 4，持续 10 秒"));
    }
    
    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        SmokeEntity smoke = new SmokeEntity(GunSkillsEntities.SMOKE.get(), player.level());
        smoke.setPos(player.getEyePosition());
        smoke.setNoGravity(true);
        smoke.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3.2F, 0);
        player.level().addFreshEntity(smoke);
        return true;
    }
}
