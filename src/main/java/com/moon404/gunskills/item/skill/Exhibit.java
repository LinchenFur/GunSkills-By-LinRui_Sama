package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.ExhibitEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Exhibit extends SkillItem
{
    public Exhibit(Properties properties)
    {
        super(properties, ClassType.SCOUT);
        tooltips.add(Component.literal("按 Q 扔出，落地生效"));
        tooltips.add(Component.literal("对落地点半径为 6 范围的玩家"));
        tooltips.add(Component.literal("给与 5 秒发光效果（包括队友）"));
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
        exhibit.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3.2F, 0);
        player.level().addFreshEntity(exhibit);
        return true;
    }
}
