// PlayerEquipmentHandler.java
package com.moon404.gunskills.events;

import com.moon404.gunskills.GunSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = GunSkills.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEquipmentHandler {

    // 进化规则配置（保持不变）
    private static final List<EvolutionRule> EVOLUTION_RULES = Arrays.asList(
        new EvolutionRule(
            "mcapex:evo_shield_white",
            "mcapex:evo_shield_blue",
            30
        ),
        new EvolutionRule(
            "mcapex:evo_shield_blue",
            "mcapex:evo_shield_purple",
            60
        ),
        new EvolutionRule(
            "mcapex:evo_shield_purple",
            "mcapex:evo_shield_red",
            120
        )
    );

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        // 原有逻辑保持不变
        if (event.phase != PlayerTickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        checkAndEvolveArmor(player);
    }

    private static void checkAndEvolveArmor(ServerPlayer player) {
        // 原有逻辑保持不变
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplate.isEmpty()) return;

        for (EvolutionRule rule : EVOLUTION_RULES) {
            if (tryEvolveArmor(chestplate, player, rule)) {
                break;
            }
        }
    }

    private static boolean tryEvolveArmor(ItemStack currentArmor, ServerPlayer player, EvolutionRule rule) {
        // 验证当前护甲类型（保持不变）
        ResourceLocation currentId = ForgeRegistries.ITEMS.getKey(currentArmor.getItem());
        if (!currentId.toString().equals(rule.currentItemId)) return false;

        // 获取等级数据
        CompoundTag tag = currentArmor.getTag();
        if (tag == null || !tag.contains("level_count")) return false;
        int currentLevel = tag.getInt("level_count");
        
        // 检查等级条件（新增等级差值计算）
        if (currentLevel < rule.requiredLevel) return false;  // 必须达到最低要求
        int excessLevel = Math.max(0, currentLevel - rule.requiredLevel);  // 关键修改点

        // 创建新护甲（携带超额等级）
        CompoundTag newTag = tag.copy();
        newTag.putInt("level_count", excessLevel);  // 修改点：使用差值
        
        ItemStack newArmor = new ItemStack(
            ForgeRegistries.ITEMS.getValue(new ResourceLocation(rule.nextItemId))
        );
        newArmor.setTag(newTag);

        // 替换护甲（保持不变）
        player.setItemSlot(EquipmentSlot.CHEST, newArmor);
        player.inventoryMenu.broadcastChanges();
        
        return true;
    }

    // 进化规则内部类（保持不变）
    private static class EvolutionRule {
        public final String currentItemId;
        public final String nextItemId;
        public final int requiredLevel;

        public EvolutionRule(String currentItemId, String nextItemId, int requiredLevel) {
            this.currentItemId = currentItemId;
            this.nextItemId = nextItemId;
            this.requiredLevel = requiredLevel;
        }
    }
}