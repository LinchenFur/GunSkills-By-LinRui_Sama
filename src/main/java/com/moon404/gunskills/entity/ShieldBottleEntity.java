package com.moon404.gunskills.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import java.util.List;

public class ShieldBottleEntity extends Marker {
    public ShieldBottleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        if (this.tickCount > 40) { // 持续2秒
            this.kill();
            return;
        }

        if (this.level() instanceof ServerLevel level) {
            // 蓝色粒子效果
            Vector3f color = new Vector3f(0.33F, 0.33F, 1.00F);
            level.sendParticles(
                new DustParticleOptions(color, 1.0F), // 正确尺寸参数
                this.getX(), 
                this.getY() + 0.2, 
                this.getZ(),
                1,    // 数量
                0.5,  // x偏移
                0.1,  // y偏移
                0.5,  // z偏移
                0.1   // 速度
            );

            // 每秒执行一次
            if (this.tickCount % 20 == 0) {
                for (Player player : level.players()) {
                    if (this.distanceTo(player) <= 4.0) {
                        repairChestplate(player);
                    }
                }
            }
        }
    }

    private void repairChestplate(Player player) {
        ItemStack chestplate = player.getInventory().getArmor(2); // 胸甲槽位
        
        if (!chestplate.isEmpty() && chestplate.isDamaged()) {
            // 修复10点耐久（DamageValue是已损失耐久值）
            int newDamage = Math.max(chestplate.getDamageValue() - 10, 0);
            chestplate.setDamageValue(newDamage);

            // 同步给客户端
            if (player instanceof ServerPlayer serverPlayer) {
                List<Pair<EquipmentSlot, ItemStack>> slots = List.of(
                    Pair.of(EquipmentSlot.CHEST, chestplate.copy())
                );
                serverPlayer.connection.send(
                    new ClientboundSetEquipmentPacket(player.getId(), slots)
                );
            }
        }
    }
}