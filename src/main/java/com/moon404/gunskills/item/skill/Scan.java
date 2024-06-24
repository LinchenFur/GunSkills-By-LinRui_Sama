package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.message.GlowMessage;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class Scan extends SkillItem
{
    public Scan(Properties properties)
    {
        super(properties, ClassType.SCOUT);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("得到距离最近敌人的距离和坐标"));
        tooltips.add(Component.literal("自己不会被暴露，敌人也不会知道"));
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        double mindis = Float.MAX_VALUE;
        Player nearest = null;
        for (Player target : player.level().players())
        {
            if (!target.isSpectator() && target.getTeam() != player.getTeam())
            {
                double dis = target.distanceTo(player);
                if (dis < mindis)
                {
                    mindis = dis;
                    nearest = target;
                }
            }
        }
        if (nearest == null)
        {
            Component component = Component.literal("未扫描到任何敌人");
            player.displayClientMessage(component, true);
        }
        else
        {
            String message = "最近的敌人在 " + (int)mindis + " 格外";
            Component component = Component.literal(message);
            player.displayClientMessage(component, true);
            nearest.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
            GlowMessage.sendToTeam(player.getTeam(), nearest, 60);
        }
        return true;
    }
}
