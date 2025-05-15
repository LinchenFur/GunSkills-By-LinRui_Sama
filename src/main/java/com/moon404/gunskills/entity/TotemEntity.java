package com.moon404.gunskills.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TotemEntity extends Marker {
    public Player player;

    public TotemEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        if (this.tickCount >= 200) { // 10秒后自动销毁
            this.kill();
            return;
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            // 每1秒显示剩余时间
            if (this.tickCount % 20 == 0) {
                int secondsLeft = (200 - this.tickCount) / 20;
                if (player != null) {
                    player.displayClientMessage(
                        Component.literal("胸甲回溯剩余时间: " + secondsLeft + "秒"), true
                    );
                }
            }

            // 每tick生成基础粒子效果
            spawnBaseParticles(serverLevel);

            // 检测胸甲是否损坏
            if (isChestplateBroken()) {
                teleportPlayer(serverLevel);
                this.kill();
            }
        }
    }

    private boolean isChestplateBroken() {
        if (player == null || player.isRemoved()) return false;
        ItemStack chestplate = player.getInventory().getArmor(2);
        return chestplate.getDamageValue() >= chestplate.getMaxDamage() - 1;
    }

    private void teleportPlayer(ServerLevel level) {
        if (player == null) return;

        // 传送玩家
        player.teleportTo(this.getX(), this.getY(), this.getZ());

        // 播放传送音效
        level.playSound(
            null, 
            player.getX(), player.getY(), player.getZ(),
            SoundEvents.ENDERMAN_TELEPORT, 
            SoundSource.PLAYERS, 
            1.0F, 1.0F
        );

        // 生成传送粒子
        level.sendParticles(
            ParticleTypes.PORTAL,
            player.getX(), player.getY() + 1.0, player.getZ(), // 粒子生成位置
            50,  // 粒子数量
            0.5, 0.5, 0.5, // 随机偏移范围
            0.2   // 粒子速度
        );
    }

    private void spawnBaseParticles(ServerLevel level) {
        // 持续生成环绕图腾的粒子
        level.sendParticles(
            ParticleTypes.ELECTRIC_SPARK,
            this.getX(), 
            this.getY() + 0.5, 
            this.getZ(),
            3,  // 粒子数量
            0.2, 0.1, 0.2, // 随机偏移
            0.02 // 粒子速度
        );
    }
}