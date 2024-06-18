package com.moon404.gunskills.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

@Mixin(Gui.class)
public class GuiMixin
{
    @Shadow
    protected long healthBlinkTime;
    @Shadow
    protected int tickCount;
    @Shadow
    protected int lastHealth;
    @Shadow
    protected long lastHealthTime;
    @Shadow
    protected int displayHealth;
    @Shadow
    protected RandomSource random;
    @Shadow
    protected void renderHearts(GuiGraphics pGuiGraphics, Player pPlayer, int pX, int pY, int pHeight, int pOffsetHeartIndex, float pMaxHealth, int pCurrentHealth, int pDisplayHealth, int pAbsorptionAmount, boolean pRenderHighlight)
    {
        
    }
}
