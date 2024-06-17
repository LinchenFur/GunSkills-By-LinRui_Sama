package com.moon404.gunskills.init;

import com.moon404.gunskills.GunSkills;
import com.moon404.gunskills.mobeffect.Silence;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GunSkillsEffects
{
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GunSkills.MODID);

    public static final RegistryObject<MobEffect> SILENCE = REGISTER.register("silence", () -> new Silence(MobEffectCategory.HARMFUL, 0xAA0000));
}
