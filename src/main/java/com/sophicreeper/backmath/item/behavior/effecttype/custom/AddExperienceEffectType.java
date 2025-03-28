package com.sophicreeper.backmath.item.behavior.effecttype.custom;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.RVUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AddExperienceEffectType extends ItemBehaviorEffectType {
    private final int defaultAmount;

    public AddExperienceEffectType(int defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    @Override
    public void runBehavior(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("stored_experience", TagTypes.ANY_NUMERIC)) {
            int storedExperience = tag.getInt("stored_experience");
            // Gives the player the amount of points in the "stored_experience" tag.
            attacker.giveExperiencePoints(storedExperience);
        } else {
            // Gives the player 500 (by default) experience points (XP points).
            attacker.giveExperiencePoints(this.defaultAmount);
        }
    }

    @Override
    public List<ITextComponent> addToTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addToTooltip(stack, world, tooltip, flag);
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("stored_experience", TagTypes.ANY_NUMERIC)) {
            IFormattableTextComponent component = new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.experience", tag.getInt("stored_experience")).withStyle(RVUtils.getFromRGB(8453920));
            tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.beneficial_effect", component).withStyle(RVUtils.getFromRGB(0x588039)));
        } else {
            IFormattableTextComponent component = new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.experience", this.defaultAmount).withStyle(RVUtils.getFromRGB(8453920));
            tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.beneficial_effect", component).withStyle(RVUtils.getFromRGB(0x588039)));
        }
        return tooltip;
    }
}
