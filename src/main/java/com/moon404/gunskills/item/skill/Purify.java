package com.moon404.gunskills.item.skill;

import java.util.List;

import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsItems;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Purify extends SkillItem
{
    public Purify(Properties properties)
    {
        super(properties, ClassType.SUPPORT);
        tooltips.add(Component.literal("放在副手，被动生效并消耗"));
        tooltips.add(Component.literal("被技能效果命中时"));
        tooltips.add(Component.literal("取消该技能的所有目标"));
    }

    public static boolean purified(List<Player> players)
    {
        for (Player player : players)
        {
            ItemStack offhandStack = player.getOffhandItem();
            if (offhandStack.getItem() == GunSkillsItems.PURIFY.get() && !player.hasEffect(GunSkillsEffects.SILENCE.get()) && ClassType.getClass(player) == ClassType.SUPPORT)
            {
                offhandStack.setCount(offhandStack.getCount() - 1);
                return true;
            }
        }
        return false;
    }
}
