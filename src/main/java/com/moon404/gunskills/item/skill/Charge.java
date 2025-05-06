package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.struct.ClassType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Charge extends SkillItem
{
    public Charge(Properties properties)
    {
        super(properties, ClassType.SUPPORT);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("为队友胸甲增加30级计数"));
    }

    @Override
    public boolean onToss(Player player)
    {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;

        if (!player.level().isClientSide)
        {
            for (Player target : player.level().players())
            {
                if (target.getTeam() == player.getTeam())
                {
                    modifyChestplateLevel(target);
                }
            }
        }
        return true;
    }

    private void modifyChestplateLevel(Player player)
    {
        // 获取胸甲栏位（索引2对应胸甲）
        ItemStack chestplate = player.getInventory().getArmor(2);
        
        if (!chestplate.isEmpty())
        {
            CompoundTag tag = chestplate.getOrCreateTag();
            int currentLevel = tag.getInt("level_count");
            tag.putInt("level_count", currentLevel + 30);
            chestplate.setTag(tag);
            
            // 触发更新
            player.getInventory().setChanged();
            player.inventoryMenu.broadcastChanges();
        }
    }
}