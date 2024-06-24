package com.moon404.gunskills.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.commands.data.EntityDataAccessor;
import net.minecraft.world.entity.Entity;

@Mixin(EntityDataAccessor.class)
public class DataMixin
{
    @Shadow
    private Entity entity;
    /**
     * @author Moon-404
     * @reason 允许data指令修改玩家数据
     */
    @Overwrite
    public void setData(CompoundTag pOther)
    {
        UUID uuid = this.entity.getUUID();
        this.entity.load(pOther);
        this.entity.setUUID(uuid);
    }
}
