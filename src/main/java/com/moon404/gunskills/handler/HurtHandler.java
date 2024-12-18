package com.moon404.gunskills.handler;

import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsItems;
import com.moon404.gunskills.message.GlowMessage;
import com.moon404.gunskills.message.ShowDamageMessage;
import com.moon404.gunskills.struct.ClassType;
import com.moon404.gunskills.struct.DamageInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public class HurtHandler
{
    @SubscribeEvent
    public static void onLivingDamage(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        if (event.getEntity() instanceof Player player && player.damageSources().fall() == event.getSource())
        {
            ItemStack itemStack = player.getOffhandItem();
            if (itemStack.getItem() == GunSkillsItems.BOOT.get() && !player.hasEffect(GunSkillsEffects.SILENCE.get()) && ClassType.getClass(player) == ClassType.ROGUE)
            {
                itemStack.setCount(itemStack.getCount() - 1);
            }
            else
            {
                double duration = Math.sqrt(event.getAmount()) * 10;
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)duration, 4, false, false, true));
            }
            event.setCanceled(true);
            return;
        }

        if (event.getEntity() instanceof Player player && player.damageSources().outOfBorder() == event.getSource())
        {
            event.setAmount((float)player.level().getWorldBorder().getDamagePerBlock());
            return;
        }

        if (source.getEntity() instanceof Player a && event.getEntity() instanceof Player b && a.getTeam() == b.getTeam())
        {
            event.setCanceled(true);
            return;
        }

        if (source.getEntity() instanceof ServerPlayer from)
        {
            DamageInfo damage = new DamageInfo();
            damage.amount = event.getAmount();
            if (event.getEntity() instanceof Player target)
            {
                if (target.getAbsorptionAmount() > 0)
                {
                    damage.getArmorColor(target.experienceLevel);
                }
            }
            from.giveExperiencePoints((int)(damage.amount * 100));
            ShowDamageMessage.INSTANCE.send(PacketDistributor.PLAYER.with(() -> {return from;}), damage);

            ItemStack itemStack = from.getOffhandItem();
            if (itemStack.getItem() == GunSkillsItems.IRE.get() && !event.getEntity().hasEffect(MobEffects.GLOWING) && !from.hasEffect(GunSkillsEffects.SILENCE.get()) && ClassType.getClass(from) == ClassType.SCOUT)
            {
                event.getEntity().addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false, true));
                if (event.getEntity() instanceof Player target)
                {
                    GlowMessage.sendToTeam(from.getTeam(), target, 100);
                }
                itemStack.setCount(itemStack.getCount() - 1);
            }
        }

        if (event.getEntity() instanceof Player player)
        {
            ItemStack itemStack = player.getOffhandItem();
            if (!player.hasEffect(MobEffects.MOVEMENT_SPEED) && itemStack.getItem() == GunSkillsItems.FAST.get() && !player.hasEffect(GunSkillsEffects.SILENCE.get()) && ClassType.getClass(player) == ClassType.ATTACK)
            {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 2, false, false, true));
                itemStack.setCount(itemStack.getCount() - 1);
            }
        }
    }
}
