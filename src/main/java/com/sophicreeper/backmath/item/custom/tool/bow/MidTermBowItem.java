package com.sophicreeper.backmath.item.custom.tool.bow;

import com.sophicreeper.backmath.effect.BMEffects;
import com.sophicreeper.backmath.item.behavior.BMItemBehaviors;
import com.sophicreeper.backmath.item.behavior.effecttype.BMItemBehaviorEffectTypes;
import com.sophicreeper.backmath.item.behavior.effecttype.custom.ApplyTagEffectsEffectType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

import java.util.List;

import static com.sophicreeper.backmath.config.BMConfigs.COMMON_CONFIGS;

public class MidTermBowItem extends BMBowItem {
    public MidTermBowItem(Properties properties) {
        super(COMMON_CONFIGS.midTermBowFCA.get(), COMMON_CONFIGS.midTermBowCBD.get(), COMMON_CONFIGS.midTermBowAAD.get(), COMMON_CONFIGS.midTermBowFIT.get(), COMMON_CONFIGS.midTermBowFRD.get(), BMItemBehaviors.MID_TERM, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof LivingEntity) this.useDuration = ((LivingEntity) entity).hasEffect(BMEffects.SUPERCHARGED.get()) ? 11 : COMMON_CONFIGS.midTermBowFRD.get();
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity livEntity, int timeLeft) {
        if (timeLeft == 1) this.shootArrow(stack, livEntity.level, livEntity, timeLeft);
    }

    @Override
    public AbstractArrowEntity customArrow(AbstractArrowEntity oldArrow) {
        ArrowEntity arrow = new ArrowEntity(oldArrow.level, oldArrow.getX(), oldArrow.getY(), oldArrow.getZ());
        List<EffectInstance> appliedEffects = ((ApplyTagEffectsEffectType) BMItemBehaviorEffectTypes.APPLY_MID_TERM_EFFECTS.get()).appliedEffects();
        for (EffectInstance instance : appliedEffects) arrow.addEffect(instance);
        return arrow;
    }
}
