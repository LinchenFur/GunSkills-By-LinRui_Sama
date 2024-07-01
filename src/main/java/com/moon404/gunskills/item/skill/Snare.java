package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.SnareEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Snare extends SkillItem
{
    public Snare(Properties properties)
    {
        super(properties, ClassType.ATTACK);
        tooltips.add(Component.literal("按 Q 扔出，落地生效"));
        tooltips.add(Component.literal("对落地点半径为 4 范围的玩家"));
        tooltips.add(Component.literal("造成 4 点伤害、5 秒缓慢III效果"));
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        SnareEntity snare = new SnareEntity(GunSkillsEntities.SNARE.get(), player.level());
        snare.user = player;
        snare.setPos(player.getEyePosition());
        snare.setNoGravity(true);
        snare.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3.2F, 0);
        player.level().addFreshEntity(snare);
        return true;
    }
}
