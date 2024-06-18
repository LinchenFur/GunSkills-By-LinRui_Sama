package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.TotemEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Totem extends SkillItem
{
    public Totem(Properties properties)
    {
        super(properties, ClassType.ATTACK);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("记录自己的当前位置"));
        tooltips.add(Component.literal("10 秒内碎甲则会传送回去"));
        tooltips.add(Component.literal("没有护盾时不会生效"));
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
