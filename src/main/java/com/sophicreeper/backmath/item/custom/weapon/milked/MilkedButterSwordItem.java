package com.sophicreeper.backmath.item.custom.weapon.milked;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.config.BMConfigs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MilkedButterSwordItem extends SwordItem {
    public MilkedButterSwordItem(IItemTier tier, int attackDamage, float swingSpeed, Properties properties) {
        super(tier, attackDamage, swingSpeed, properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            int experiencePoints = stack.getOrCreateTag().getInt("experience_points");
            if (stack.hasTag()) {
                ((PlayerEntity) livingEntity).giveExperiencePoints(experiencePoints);
            } else {
                ((PlayerEntity) livingEntity).giveExperiencePoints(500);
            }
        }
        return super.onItemUseFinish(stack, world, livingEntity);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (BMConfigs.COMMON_CONFIGS.milkedSwordsEnabled.get()) player.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET));
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (stack.hasTag() && stack.getTag().contains("experience_points")) {
            tooltip.add(new TranslationTextComponent("messages." + BackMath.MOD_ID + ".butter_sword.experience_points", stack.getTag().getInt("experience_points")));
        }
        super.addInformation(stack, world, tooltip, flag);
    }
}
