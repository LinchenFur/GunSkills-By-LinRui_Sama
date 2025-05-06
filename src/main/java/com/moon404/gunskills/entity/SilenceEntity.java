package com.moon404.gunskills.entity;

import org.joml.Vector3f;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsItems;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SilenceEntity extends ThrowSkillEntity
{
    public SilenceEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
        color = new Vector3f(0.67F, 0, 0F);
    }

    @Override
    protected Item getDefaultItem()
    {
        return GunSkillsItems.SILENCE.get();
    }

    @Override
    protected void onPurify()
    {
        this.user.displayClientMessage(Component.literal("静默效果被净化"), true);
    }

    @Override
    protected void onEffect()
    {
        for (Player player : lastTickPlayers)
        {
            player.addEffect(new MobEffectInstance(GunSkillsEffects.SILENCE.get(), 100, 0, false, false, true));
            player.hurt(player.damageSources().playerAttack(this.user), 10);
        }
        this.user.displayClientMessage(Component.literal("静默命中敌人数：" + lastTickPlayers.size()), true);
    }
}
