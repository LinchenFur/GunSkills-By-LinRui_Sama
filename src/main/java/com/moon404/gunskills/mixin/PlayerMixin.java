package com.moon404.gunskills.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.moon404.gunskills.animation.Slide;

import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public class PlayerMixin extends EntityMixin
{
    @Shadow
    public int experienceLevel;

    /**
     * @author Moon-404
     * @reason 等级与进化护甲相关
     */
    @Overwrite
    public int getXpNeededForNextLevel()
    {
        if (this.experienceLevel == 1) return 2500;
        if (this.experienceLevel == 2) return 5000;
        if (this.experienceLevel == 3) return 12500;
        return Integer.MAX_VALUE;
    }

    /**
     * @author Moon-404
     * @reason 玩家死亡不掉落经验
     */
    @Overwrite
    public int getExperienceReward()
    {
        return 0;
    }

    /**
     * @author Moon-404
     * @reason 滑铲动作适配
     */
    @Overwrite
    public boolean isStayingOnGroundSurface()
    {
        return this.isShiftKeyDown() && !Slide.sliding;
    }
}
