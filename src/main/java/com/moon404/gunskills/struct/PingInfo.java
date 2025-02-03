package com.moon404.gunskills.struct;

import net.minecraft.world.phys.Vec2;

public class PingInfo
{
    public int x, y, z;
    public int t; // 持续时长或到期时间
    public Vec2 screenPos;
    public float depth, distance;
    public String sender;
}
