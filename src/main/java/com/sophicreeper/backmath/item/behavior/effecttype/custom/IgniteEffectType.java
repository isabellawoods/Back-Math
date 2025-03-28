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
import net.minecraft.util.DamageSource;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class IgniteEffectType extends ItemBehaviorEffectType {
    private final int seconds;

    public IgniteEffectType(int seconds) {
        this.seconds = seconds;
    }

    public IgniteEffectType() {
        this(5);
    }

    @Override
    public void runBehavior(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        if (!target.isInvulnerableTo(DamageSource.IN_FIRE) || !target.isInvulnerableTo(DamageSource.ON_FIRE)) {
            CompoundNBT tag = stack.getTag();
            if (tag != null && tag.contains("seconds_on_fire", TagTypes.ANY_NUMERIC)) target.setSecondsOnFire(tag.getInt("seconds_on_fire"));
            else target.setSecondsOnFire(this.seconds);
        }
    }

    @Override
    public List<ITextComponent> addToTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addToTooltip(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.harmful_effect", new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.ablaze",
                StringUtils.formatTickDuration(this.seconds * 20)).withStyle(RVUtils.getFromRGB(0xE1A61E))).withStyle(TextFormatting.DARK_GRAY));
        return tooltip;
    }
}
