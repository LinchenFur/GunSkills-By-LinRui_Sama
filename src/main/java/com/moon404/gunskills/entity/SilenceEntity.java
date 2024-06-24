package com.moon404.gunskills.entity;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import com.moon404.gunskills.init.GunSkillsEffects;
import com.moon404.gunskills.init.GunSkillsItems;

import com.moon404.gunskills.item.skill.Purify;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class SilenceEntity extends ThrowableItemProjectile
{
    public Player user;

    public SilenceEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
    }

    @Override
    protected Item getDefaultItem()
    {
        return GunSkillsItems.SILENCE.get();
    }

    protected void onHit(HitResult pResult)
    {
        if (this.user == null) return;
        super.onHit(pResult);
        List<Player> players = new ArrayList<>();
        for (Player player : this.level().players())
        {
            if (!player.isSpectator() && this.distanceTo(player) <= 4)
            {
                players.add(player);
            }
        }
        if (Purify.purified(players))
        {
            this.user.displayClientMessage(Component.literal("静默效果被净化"), true);
        }
        else
        {
            for (Player player : players)
            {
                player.addEffect(new MobEffectInstance(GunSkillsEffects.SILENCE.get(), 100));
                player.hurt(player.damageSources().playerAttack(this.user), 4);
            }
            this.user.displayClientMessage(Component.literal("静默命中敌人数：" + players.size()), true);
        }
        this.kill();
    }

    public void tick()
    {
        if (this.level() instanceof ServerLevel level)
        {
            Vector3f color = new Vector3f(0.67F, 0, 0F);
            DustParticleOptions options = new DustParticleOptions(color, 1.5F);
            level.sendParticles(options, this.getX(), this.getY(), this.getZ(), 0, 0, 0, 0, 0);
        }
        super.tick();
        if (this.tickCount >= 200)
        {
            this.kill();
        }
    }
}
