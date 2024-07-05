package com.moon404.gunskills.entity;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import com.moon404.gunskills.item.skill.Purify;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public abstract class ThrowSkillEntity extends ThrowableItemProjectile
{
    public Player user;
    protected Vector3f color;
    protected int duration = 200;
    protected int range = 6;
    protected List<Player> lastTickPlayers = new ArrayList<>();
    private List<Player> targetPlayers = new ArrayList<>();
    
    public ThrowSkillEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
    }

    public void tick()
    {
        if (this.level() instanceof ServerLevel level)
        {
            DustParticleOptions options = new DustParticleOptions(color, 1.5F);
            level.sendParticles(options, this.getX(), this.getY(), this.getZ(), 0, 0, 0, 0, 0);
            findTargetPlayers();
            if (targetPlayers.size() < lastTickPlayers.size())
            {
                if (Purify.purified(lastTickPlayers)) onPurify();
                else onEffect();
                this.kill();
                return;
            }
            lastTickPlayers.clear();
            lastTickPlayers.addAll(targetPlayers);
        }
        super.tick();
        if (this.tickCount >= duration) this.kill();
    }

    protected void onHit(HitResult pResult)
    {
        if (this.user == null) return;
        super.onHit(pResult);
        findTargetPlayers();
        lastTickPlayers.clear();
        lastTickPlayers.addAll(targetPlayers);
        if (Purify.purified(lastTickPlayers)) onPurify();
        else onEffect();
        this.kill();
    }

    protected abstract void onPurify();

    protected abstract void onEffect();

    protected void findTargetPlayers()
    {
        targetPlayers.clear();
        for (Player player : this.level().players())
        {
            if (!player.isSpectator() && this.distanceTo(player) <= range && player.getTeam() != this.user.getTeam())
            {
                targetPlayers.add(player);
            }
        }
    }
}
