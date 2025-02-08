package com.moon404.gunskills.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class GunSkillsConfigs
{
    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.ConfigValue<Integer> RECOVER_INDEX;
    public static final ForgeConfigSpec.ConfigValue<Integer> DROPA_INDEX;
    public static final ForgeConfigSpec.ConfigValue<Integer> DROPB_INDEX;
    public static final ForgeConfigSpec.ConfigValue<Integer> DROPC_INDEX;
    public static final ForgeConfigSpec.ConfigValue<Float> STEP_SOUND_MUL;
    public static final ForgeConfigSpec.ConfigValue<Float> STEP_RANGE;

    static
    {
        RECOVER_INDEX = CLIENT_BUILDER.comment(" 回复品轮盘的快捷栏位（取值：0-9，0代表不启用此功能）").defineInRange("Recover hotbar", 4, 0, 9);
        DROPA_INDEX = CLIENT_BUILDER.comment(" 快捷丢弃栏位A（取值：1-9）").defineInRange("dropa hotbar", 5, 1, 9);
        DROPB_INDEX = CLIENT_BUILDER.comment(" 快捷丢弃栏位B（取值：1-9）").defineInRange("dropb hotbar", 6, 1, 9);
        DROPC_INDEX = CLIENT_BUILDER.comment(" 快捷丢弃栏位C（取值：1-9）").defineInRange("dropc hotbar", 7, 1, 9);
        STEP_SOUND_MUL = CLIENT_BUILDER.comment(" 脚步声放大倍率（默认：6.0）").define("step sound multipier", 6.0f);
        CLIENT_CONFIG = CLIENT_BUILDER.build();

        STEP_RANGE = SERVER_BUILDER.comment(" 脚步声传播距离（默认：32.0）").define("step sound multipier", 32.0f);
        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
