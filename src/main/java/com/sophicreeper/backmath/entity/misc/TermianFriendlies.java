package com.sophicreeper.backmath.entity.misc;

import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;

/// Interface containing methods used on all termians.
public interface TermianFriendlies {
    /// Ticks the effects of all helmets of mine that can give effects (pastel turtle shells, and creeper lord helmets from <i>Creeper Edits</i>).
    default void applyArmorEffects(LivingEntity livEntity) {
        this.applyArmorEffect(livEntity, BMItemTags.PROVIDES_WATER_BREATHING, Effects.WATER_BREATHING);
        this.applyArmorEffect(livEntity, BMItemTags.PROVIDES_RESISTANCE, Effects.DAMAGE_RESISTANCE);
    }

    /// Ticks the specified effect to see if it can still be applied.
    /// @param livEntity The entity to apply the effects to.
    /// @param effect An item tag for which items can be equipped.
    /// @param providesEffect Which effect to give when it can be applied.
    default void applyArmorEffect(LivingEntity livEntity, ITag.INamedTag<Item> providesEffect, Effect effect) {
        ItemStack headStack = livEntity.getItemBySlot(EquipmentSlotType.HEAD);
        boolean acceptableHelmets = headStack.getItem().is(providesEffect);
        if (acceptableHelmets && !livEntity.isEyeInFluid(FluidTags.WATER)) {
            livEntity.addEffect(new EffectInstance(effect, 200, 0, false, false, true));
        }
    }
}
