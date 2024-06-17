package com.moon404.gunskills.init;

import com.moon404.gunskills.GunSkills;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GunSkillsBlocks
{
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, GunSkills.MODID);

    public static final RegistryObject<Block> SMOKE = REGISTER.register("smoke", () -> new Block(BlockBehaviour.Properties.of().air().noCollission()));
}
