package com.sophicreeper.backmath.item.behavior.effecttype.custom;

import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ApplyDevilSpareyEffectsType extends SpareyEffectType {
    @Override
    public void runBehavior(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        if (target.getType().is(inSpareyProhibitedTag(stack))) {
            // Give sword user hitting Queen Lucy Weakness LXIII (63) for 30 secs.
            attacker.addEffect(getSpareyEffect(new EffectInstance(Effects.WEAKNESS, 600, 64), stack, attacker.level, "prohibition_weakness_effect"));
        } else if (target.getType().is(inDevilSpareyEffectivesTag(stack))) {
            // If the sword user hits one of these:
            // - Wanderer Sophie, (Archer) Insomnia Sophie, Warrior Sophie, Queen Lucy Pet, Archer Lucia, Karate Lucia, Shy Alcalyte, Collector Alcalyte or Malaika
            // Give player Strength III effect for 2.5 secs (or 3 secs rounded)
            attacker.addEffect(getSpareyEffect(new EffectInstance(Effects.DAMAGE_BOOST, 50, 2), stack, attacker.level, "strength_effect"));
        } else {
            // Give user Weakness II for 10 secs.
            attacker.addEffect(getSpareyEffect(new EffectInstance(Effects.WEAKNESS, 200, 1), stack, attacker.level, "weakness_effect"));
        }
    }

    @Override
    public List<ITextComponent> addToTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addToTooltip(stack, world, tooltip, flag);
        String effectiveTag = stack.getTag() != null && stack.getTag().contains("effective_entities", TagTypes.STRING) ? "#" + stack.getTag().getString("effective_entities") : "#backmath:devil_sparey_effectives";
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.sparey.in_tag", effectiveTag).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.beneficial_effect", BMUtils.addEffectTooltip(Effects.DAMAGE_BOOST, 200, 1)).withStyle(TextFormatting.DARK_GRAY));

        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.sparey.else").withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.harmful_effect", BMUtils.addEffectTooltip(Effects.WEAKNESS, 50, 2)).withStyle(TextFormatting.DARK_GRAY));

        String prohibitedTag = stack.getTag() != null && stack.getTag().contains("prohibited_entities", TagTypes.STRING) ? "#" + stack.getTag().getString("prohibited_entities") : "#backmath:sparey_prohibited";
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.sparey.in_tag", prohibitedTag).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.harmful_effect", BMUtils.addEffectTooltip(Effects.WEAKNESS, 600, 64)).withStyle(TextFormatting.DARK_GRAY));
        return tooltip;
    }
}
