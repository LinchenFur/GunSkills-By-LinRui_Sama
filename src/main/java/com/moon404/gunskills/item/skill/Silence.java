package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.SilenceEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Silence extends SkillItem
{
    public Silence(Properties properties)
    {
        super(properties, ClassType.ATTACK);
        tooltips.add(Component.literal("按 Q 扔出，落地生效"));
        tooltips.add(Component.literal("对落地点半径为 4 范围的玩家"));
        tooltips.add(Component.literal("造成 4 点伤害、5 秒沉默与禁疗"));
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
