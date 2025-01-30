package com.moon404.gunskills.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class GunSkillsConfigs
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> RECOVER_INDEX;
    public static final ForgeConfigSpec.ConfigValue<Integer> DROPA_INDEX;
    public static final ForgeConfigSpec.ConfigValue<Integer> DROPB_INDEX;
    public static final ForgeConfigSpec.ConfigValue<Integer> DROPC_INDEX;

    static
    {
        RECOVER_INDEX = BUILDER.comment(" 回复品轮盘的快捷栏位（取值：0-9，0代表不启用此功能）").defineInRange("Recover hotbar", 4, 0, 9);
        DROPA_INDEX = BUILDER.comment(" 快捷丢弃栏位A（取值：1-9）").defineInRange("dropa hotbar", 5, 1, 9);
        DROPB_INDEX = BUILDER.comment(" 快捷丢弃栏位B（取值：1-9）").defineInRange("dropb hotbar", 6, 1, 9);
        DROPC_INDEX = BUILDER.comment(" 快捷丢弃栏位C（取值：1-9）").defineInRange("dropc hotbar", 7, 1, 9);
        SPEC = BUILDER.build();
    }
}
