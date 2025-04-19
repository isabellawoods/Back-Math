package melonystudios.backmath.item.custom.tool.bow;

import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.behavior.effecttype.BMItemBehaviorEffectTypes;
import melonystudios.backmath.item.behavior.effecttype.custom.ApplyTagEffectsEffectType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MidTermBowItem extends BMBowItem {
    public MidTermBowItem(Properties properties) {
        super(true, false, 0, 200, 10, BMItemBehaviors.MID_TERM, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof LivingEntity livEntity) this.useDuration = /*livEntity.hasEffect(BMEffects.SUPERCHARGED.get()) ? 11 : COMMON_CONFIGS.midTermBowFRD.get();*/ 10;
    }

    @Override
    public void onUseTick(Level world, LivingEntity livEntity, ItemStack stack, int timeLeft) {
        if (timeLeft == 1) this.shootArrow(stack, world, livEntity, timeLeft);
    }

    @Override
    @NotNull
    public AbstractArrow customArrow(AbstractArrow oldArrow, ItemStack projectileStack, ItemStack weaponStack) {
        AbstractArrow abstractArrow = projectileStack.getItem() instanceof ArrowItem item && oldArrow.getOwner() instanceof LivingEntity livEntity ? item.createArrow(oldArrow.level(), projectileStack, livEntity, weaponStack) : null;
        if (abstractArrow instanceof Arrow arrow) {
            List<MobEffectInstance> appliedEffects = ((ApplyTagEffectsEffectType) BMItemBehaviorEffectTypes.APPLY_MID_TERM_EFFECTS.get()).appliedEffects();
            for (MobEffectInstance instance : appliedEffects) arrow.addEffect(instance);
            return arrow;
        }
        return super.customArrow(oldArrow, projectileStack, weaponStack);
    }
}
