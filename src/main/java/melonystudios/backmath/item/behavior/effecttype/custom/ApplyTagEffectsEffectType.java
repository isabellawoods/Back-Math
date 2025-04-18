package melonystudios.backmath.item.behavior.effecttype.custom;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.util.RVUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import javax.management.Attribute;
import java.util.List;
import java.util.Map;

public class ApplyTagEffectsEffectType extends ItemBehaviorEffectType {
    private final List<MobEffectInstance> appliedEffects;

    public ApplyTagEffectsEffectType(List<MobEffectInstance> appliedEffects) {
        this.appliedEffects = appliedEffects;
    }

    public List<MobEffectInstance> appliedEffects() {
        return this.appliedEffects;
    }

    @Override
    public void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        CustomData tag = stack.get(DataComponents.CUSTOM_DATA);
        List<MobEffectInstance> tagEffects = RVUtils.getAppliedEffectsFromNBT(target.level(), stack);
        if (tag != null && tagEffects != null && !tagEffects.isEmpty()) {
            for (MobEffectInstance instance : tagEffects) target.addEffect(instance);
        } else if (!this.appliedEffects.isEmpty()) {
            List<MobEffectInstance> newEffects = convertEffectList(this.appliedEffects);
            for (MobEffectInstance instance : newEffects) target.addEffect(instance);
        }
    }

    public static List<MobEffectInstance> convertEffectList(List<? extends MobEffectInstance> effects) {
        List<MobEffectInstance> newEffects = Lists.newArrayList();
        for (MobEffectInstance instance : effects) newEffects.add(new MobEffectInstance(instance));
        return newEffects;
    }

    @Override
    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.addToTooltip(stack, context, tooltip, flag);
        addAppliedEffectsTooltip(stack, context, tooltip, 1);
        return tooltip;
    }

    public void addAppliedEffectsTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, float durationFactor) {
        List<? extends MobEffectInstance> appliedEffects = RVUtils.getAppliedEffectsFromNBT(context.level(), stack);
        if (appliedEffects == null) appliedEffects = this.appliedEffects;
        List<Pair<Attribute, AttributeModifier>> attributePairList = Lists.newArrayList();

        if (appliedEffects != null && !appliedEffects.isEmpty()) {
            for (MobEffectInstance instance : appliedEffects) {
                MutableComponent component = Component.translatable(instance.getDescriptionId());
                MobEffect effect = instance.getEffect().value();
//                Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
                /*if (!attributeMap.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
                        AttributeModifier modifier = entry.getValue();
                        AttributeModifier newModifier = new AttributeModifier(modifier.id(), effect.getAttributeModifierValue(instance.getAmplifier(), modifier), modifier.operation());
                        attributePairList.add(new Pair<>(entry.getKey(), newModifier));
                    }
                }*/

                if (instance.getAmplifier() > 0) component = Component.translatable("potion.withAmplifier", component, Component.translatable("potion.potency." + instance.getAmplifier()));
                if (instance.getDuration() > 20) component = Component.translatable("potion.withDuration", component, MobEffectUtil.formatDuration(instance, durationFactor, context.tickRate()));
                tooltip.add(getCategoryTranslation(instance, component.withStyle(RVUtils.getFromRGB(effect.getColor()))));
            }
        }

        if (!attributePairList.isEmpty()) {
            for (Pair<Attribute, AttributeModifier> attributePair : attributePairList) {
                AttributeModifier modifier = attributePair.getSecond();
                double baseAmount = modifier.amount();
                double amount;

                if (modifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE && modifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    amount = modifier.amount();
                } else {
                    amount = modifier.amount() * 100;
                }

                if (baseAmount > 0) {
                    tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.beneficial_effect", Component.translatable("attribute.modifier.plus." + modifier.operation(),
                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(amount), Component.translatable(attributePair.getFirst().getName())).withStyle(RVUtils.getFromRGB(0x6FC56F))).withStyle(RVUtils.getFromRGB(0x4F7A4F)));
                } else if (baseAmount < 0) {
                    amount = amount * -1;
                    tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.harmful_effect", Component.translatable("attribute.modifier.take." + modifier.operation(),
                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(amount), Component.translatable(attributePair.getFirst().getName())).withStyle(RVUtils.getFromRGB(0xD26D6D))).withStyle(RVUtils.getFromRGB(0x7F4B4B)));
                }
            }
        }
    }

    public static MutableComponent getCategoryTranslation(MobEffectInstance instance, Object... arguments) {
        return switch (instance.getEffect().value().getCategory()) {
            case NEUTRAL -> Component.translatable("tooltip.backmath.behavior.neutral_effect", arguments).withStyle(ChatFormatting.DARK_GRAY);
            case HARMFUL -> Component.translatable("tooltip.backmath.behavior.harmful_effect", arguments).withStyle(ChatFormatting.DARK_GRAY);
            default -> Component.translatable("tooltip.backmath.behavior.beneficial_effect", arguments).withStyle(ChatFormatting.DARK_GRAY);
        };
    }
}
