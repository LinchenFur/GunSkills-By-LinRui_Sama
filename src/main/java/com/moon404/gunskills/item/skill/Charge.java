package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Charge extends SkillItem
{
    public Charge(Properties properties)
    {
        super(properties, ClassType.SUPPORT);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("所有友方玩家获得25进化点数"));
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        for (Player target : player.level().players())
        {
            if (target.getTeam() == player.getTeam())
            {
                int oldlevel = target.experienceLevel;
                target.giveExperiencePoints(2500);
                int newlevel = target.experienceLevel;
                if (newlevel > oldlevel)
                {
                    target.setAbsorptionAmount(target.getAbsorptionAmount() + 4);
                }
            }
        }
        return true;
    }
}
