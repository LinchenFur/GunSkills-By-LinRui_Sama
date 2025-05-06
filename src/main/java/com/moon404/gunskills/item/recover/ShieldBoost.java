package com.moon404.gunskills.item.recover;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.List;

public class ShieldBoost extends RecoverItem {
    public ShieldBoost(Item.Properties properties) {
        super(properties);
        tooltips.add(Component.literal("为胸甲增加30级强化"));
        tooltips.add(Component.literal("强化等级存储在胸甲的NBT标签中"));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            ItemStack chestplate = player.getInventory().getArmor(2); // 获取胸甲
            
            if (!chestplate.isEmpty()) {
                // 操作NBT数据
                CompoundTag tag = chestplate.getOrCreateTag();
                int currentLevel = tag.getInt("level_count");
                tag.putInt("level_count", currentLevel + 30);
                chestplate.setTag(tag);
                
                // 同步到客户端
                if (player instanceof ServerPlayer serverPlayer) {
                    List<Pair<EquipmentSlot, ItemStack>> slots = List.of(
                        Pair.of(EquipmentSlot.CHEST, chestplate)
                    );
                    serverPlayer.connection.send(
                        new ClientboundSetEquipmentPacket(player.getId(), slots)
                    );
                }
            }
        }
        stack.setDamageValue(0);
        return this.isEdible() ? entity.eat(level, stack) : stack;
    }
}