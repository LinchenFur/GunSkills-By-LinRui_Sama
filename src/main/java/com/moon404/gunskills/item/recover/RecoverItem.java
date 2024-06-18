package com.moon404.gunskills.item.recover;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public abstract class RecoverItem extends Item
{
    public static final FoodProperties RECOVER_ITEM = (new FoodProperties.Builder()).alwaysEat().build();
    protected List<Component> tooltips;

    public RecoverItem(Item.Properties properties)
    {
        super(properties);
        tooltips = new ArrayList<>();
    }

    public abstract int getUseDuration(ItemStack stack);

    public abstract ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity);

    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.EAT;
    }

    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int pRemainingUseDuration)
    {
        if (level.isClientSide) return;
        stack.setDamageValue(pRemainingUseDuration);
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int pTimeCharged)
    {
        if (level.isClientSide) return;
        stack.removeTagKey("Damage");
    }

    public int getMaxDamage(ItemStack stack)
    {
        return getUseDuration(stack);
    }

    public boolean isBarVisible(ItemStack stack)
    {
        return stack.getDamageValue() != 0;
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
    {
        NumberFormat formatter = new DecimalFormat("0.0");
        String s = formatter.format((float)getUseDuration(pStack) / 20.0f);
        pTooltipComponents.add(Component.literal("长按右键使用，时间：" + s + " 秒"));
        pTooltipComponents.addAll(tooltips);
    }
}
