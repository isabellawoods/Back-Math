package com.sophicreeper.backmath.item.behavior.effecttype.custom;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import com.sophicreeper.backmath.util.revaried.RVUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ApplyTagEffectsEffectType extends ItemBehaviorEffectType {
    private final List<EffectInstance> appliedEffects;

    public ApplyTagEffectsEffectType(List<EffectInstance> appliedEffects) {
        this.appliedEffects = appliedEffects;
    }

    public List<EffectInstance> appliedEffects() {
        return this.appliedEffects;
    }

    @Override
    public void runBehavior(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        CompoundNBT tag = stack.getTag();
        List<EffectInstance> tagEffects = RVUtils.getAppliedEffectsFromNBT(target.level, stack);
        if (tag != null && tagEffects != null && !tagEffects.isEmpty()) {
            for (EffectInstance instance : tagEffects) target.addEffect(instance);
        } else if (!this.appliedEffects.isEmpty()) {
            List<EffectInstance> newEffects = convertEffectList(this.appliedEffects);
            for (EffectInstance instance : newEffects) target.addEffect(instance);
        }
    }

    public static List<EffectInstance> convertEffectList(List<? extends EffectInstance> effects) {
        List<EffectInstance> newEffects = Lists.newArrayList();
        for (EffectInstance instance : effects) newEffects.add(new EffectInstance(instance));
        return newEffects;
    }

    @Override
    public List<ITextComponent> addToTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addToTooltip(stack, world, tooltip, flag);
        addAppliedEffectsTooltip(stack, world, tooltip, 1);
        return tooltip;
    }

    public void addAppliedEffectsTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, float durationFactor) {
        List<? extends EffectInstance> appliedEffects = RVUtils.getAppliedEffectsFromNBT(world, stack);
        if (appliedEffects == null) appliedEffects = this.appliedEffects;
        List<Pair<Attribute, AttributeModifier>> attributePairList = Lists.newArrayList();

        if (appliedEffects != null && !appliedEffects.isEmpty()) {
            for (EffectInstance instance : appliedEffects) {
                IFormattableTextComponent component = new TranslationTextComponent(instance.getDescriptionId());
                Effect effect = instance.getEffect();
                Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
                if (!attributeMap.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
                        AttributeModifier modifier = entry.getValue();
                        AttributeModifier newModifier = new AttributeModifier(modifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), modifier), modifier.getOperation());
                        attributePairList.add(new Pair<>(entry.getKey(), newModifier));
                    }
                }

                if (instance.getAmplifier() > 0) component = new TranslationTextComponent("potion.withAmplifier", component, new TranslationTextComponent("potion.potency." + instance.getAmplifier()));
                if (instance.getDuration() > 20) component = new TranslationTextComponent("potion.withDuration", component, EffectUtils.formatDuration(instance, durationFactor));
                tooltip.add(getCategoryTranslation(instance, component.withStyle(RVUtils.getFromRGB(effect.getColor()))));
            }
        }

        if (!attributePairList.isEmpty()) {
            for (Pair<Attribute, AttributeModifier> attributePair : attributePairList) {
                AttributeModifier modifier = attributePair.getSecond();
                double baseAmount = modifier.getAmount();
                double amount;

                if (modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    amount = modifier.getAmount();
                } else {
                    amount = modifier.getAmount() * 100;
                }

                if (baseAmount > 0) {
                    tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.beneficial_effect", new TranslationTextComponent("attribute.modifier.plus." + modifier.getOperation().toValue(),
                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(amount), new TranslationTextComponent(attributePair.getFirst().getDescriptionId())).withStyle(RVUtils.getFromRGB(0x6FC56F))).withStyle(RVUtils.getFromRGB(0x4F7A4F)));
                } else if (baseAmount < 0) {
                    amount = amount * -1;
                    tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.harmful_effect", new TranslationTextComponent("attribute.modifier.take." + modifier.getOperation().toValue(),
                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(amount), new TranslationTextComponent(attributePair.getFirst().getDescriptionId())).withStyle(RVUtils.getFromRGB(0xD26D6D))).withStyle(RVUtils.getFromRGB(0x7F4B4B)));
                }
            }
        }
    }

    public static IFormattableTextComponent getCategoryTranslation(EffectInstance instance, Object... arguments) {
        switch (instance.getEffect().getCategory()) {
            case NEUTRAL: return new TranslationTextComponent("tooltip.backmath.behavior.neutral_effect", arguments).withStyle(TextFormatting.DARK_GRAY);
            case HARMFUL: return new TranslationTextComponent("tooltip.backmath.behavior.harmful_effect", arguments).withStyle(TextFormatting.DARK_GRAY);
            case BENEFICIAL: default: return new TranslationTextComponent("tooltip.backmath.behavior.beneficial_effect", arguments).withStyle(TextFormatting.DARK_GRAY);
        }
    }
}
