package melonystudios.backmath.item.custom.behavior;

import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.function.Supplier;

public class BMSwordItem extends SwordItem {
    private final Supplier<ItemBehavior> behavior;
    public boolean cancelAttackBehavior = false;

    public BMSwordItem(Supplier<ItemBehavior> behavior, Tier tier, ItemAttributeModifiers modifiers, Properties properties) {
        super(tier, properties.attributes(modifiers));
        this.behavior = behavior;
    }

    public BMSwordItem(Supplier<ItemBehavior> behavior, Tier tier, float attackDamage, float swingSpeed, Properties properties) {
        super(tier, properties.attributes(createAttributes(tier, attackDamage, swingSpeed)));
        this.behavior = behavior;
    }

    public BMSwordItem(Tier tier, float attackDamage, float swingSpeed, Properties properties) {
        this(BMItemBehaviors.NONE, tier, attackDamage, swingSpeed, properties);
    }

    public Supplier<ItemBehavior> getBehavior() {
        return this.behavior;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player attacker, Entity target) {
        return super.onLeftClickEntity(stack, attacker, target);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!this.cancelAttackBehavior) {
            if (attacker instanceof Player player) this.behavior.get().run(stack, player, target, target.level());
            else this.behavior.get().run(stack, null, target, target.level());
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (!this.behavior.get().effects.isEmpty()) tooltip.add(Component.translatable("tooltip.backmath.behavior.when_used").withStyle(ChatFormatting.GRAY));
        for (Supplier<ItemBehaviorEffectType> type : this.behavior.get().effects) {
            if (type != null) type.get().addToTooltip(stack, context, tooltip, flag);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.behavior.get().hasGlint(stack) || super.isFoil(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return this.behavior.get().getDurabilityBarColor(stack, super.getBarColor(stack));
    }
}
