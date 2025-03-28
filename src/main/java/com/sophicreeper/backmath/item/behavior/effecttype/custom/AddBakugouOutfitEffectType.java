package com.sophicreeper.backmath.item.behavior.effecttype.custom;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.RVUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AddBakugouOutfitEffectType extends ItemBehaviorEffectType {
    @Override
    public void runBehavior(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        BMUtils.addBakugouOutfit(target);
    }

    @Override
    public List<ITextComponent> addToTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addToTooltip(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.neutral_effect", new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.bakugou_outfit").withStyle(RVUtils.getFromRGB(0xFF8A50)))
                .withStyle(RVUtils.getFromRGB(0xCD473B)));
        return tooltip;
    }
}
