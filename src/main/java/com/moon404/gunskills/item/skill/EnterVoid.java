package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.struct.ClassType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EnterVoid extends SkillItem
{
    public EnterVoid(Properties properties)
    {
        super(properties, ClassType.ROGUE);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("获得 5 秒隐身与失明"));
        tooltips.add(Component.literal("手持物品会提前结束效果"));
        tooltips.add(Component.literal("被发光时也会提前结束效果"));
        tooltips.add(Component.literal("生效时如果背包有空位"));
        tooltips.add(Component.literal("会自动将手持物品放入背包"));
    }

    private static int getFreeSlot(Inventory inventory)
    {
        for (int i = 9; i < inventory.items.size(); i++)
        {
            if (inventory.items.get(i).isEmpty())
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        Inventory inventory = player.getInventory();
        int freeBagSlot = getFreeSlot(inventory);
        if (freeBagSlot >= 0)
        {
            ItemStack itemStack = player.getMainHandItem();
            if (!itemStack.isEmpty())
            {
                inventory.setItem(freeBagSlot, itemStack.copy());
                inventory.removeItem(itemStack);
            }
        }
        freeBagSlot = getFreeSlot(inventory);
        if (freeBagSlot >= 0)
        {
            ItemStack itemStack = player.getOffhandItem();
            if (!itemStack.isEmpty())
            {
                inventory.setItem(freeBagSlot, itemStack.copy());
                inventory.removeItem(itemStack);
            }
        }
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 100, 0, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false, true));
        return true;
    }
}
