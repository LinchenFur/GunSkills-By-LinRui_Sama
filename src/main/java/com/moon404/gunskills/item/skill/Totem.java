package com.moon404.gunskills.item.skill;

import com.moon404.gunskills.entity.TotemEntity;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsEntities;
import com.moon404.gunskills.struct.ClassType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Totem extends SkillItem {
    public Totem(Properties properties) {
        super(properties, ClassType.ATTACK);
        tooltips.add(Component.literal("按 Q 扔出，立即生效"));
        tooltips.add(Component.literal("记录当前位置，需穿戴未损坏的胸甲"));
        tooltips.add(Component.literal("10 秒内胸甲完全损坏会传送回去"));
        tooltips.add(Component.literal("胸甲已损坏时无法使用"));
    }

    @Override
    public boolean onToss(Player player) {
        if (ClassType.getClass(player) != this.classType) return false;
        if (player.hasEffect(GunSkillsEffects.SILENCE.get())) return false;
        if (!isChestplateValid(player)) return false;

        TotemEntity totem = new TotemEntity(GunSkillsEntities.TOTEM.get(), player.level());
        totem.setPos(player.position());
        totem.player = player;
        player.level().addFreshEntity(totem);
        return true;
    }

    private boolean isChestplateValid(Player player) {
        ItemStack chestplate = player.getInventory().getArmor(2);
        return !chestplate.isEmpty() && 
               chestplate.isDamageableItem() && 
               chestplate.getDamageValue() < chestplate.getMaxDamage() - 1;
    }
}