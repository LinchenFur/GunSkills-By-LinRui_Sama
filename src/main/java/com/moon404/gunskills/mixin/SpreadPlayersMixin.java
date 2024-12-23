package com.moon404.gunskills.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.minecraft.server.commands.SpreadPlayersCommand$Position")
public class SpreadPlayersMixin
{
    @ModifyVariable(method = "getSpawnY", at = @At("HEAD"), ordinal = 0)
    public int maxSpawnY(int pY)
    {
        return 256;
    }
}
