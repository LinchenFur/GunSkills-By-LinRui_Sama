package com.moon404.gunskills.entity;

import org.joml.Vector3f;
import com.moon404.gunskills.init.GunSkillsItems;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SnareEntity extends ThrowSkillEntity
{
    public SnareEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
        color = new Vector3f(0F, 0, 0.67F);
    }

    @Override
    protected Item getDefaultItem()
    {
        return GunSkillsItems.SNARE.get();
    }

    @Override
    protected void onPurify()
    {
        this.user.displayClientMessage(Component.literal("电弧陷阱效果被净化"), true);
    }

    @Override
    protected void onEffect()
    {
        for (Player player : lastTickPlayers)
        {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false, true));
            player.hurt(player.damageSources().playerAttack(this.user), 4);
        }
        this.user.displayClientMessage(Component.literal("电弧陷阱命中敌人数：" + lastTickPlayers.size()), true);
    }
}
