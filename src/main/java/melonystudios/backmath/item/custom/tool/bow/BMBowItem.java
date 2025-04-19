package melonystudios.backmath.item.custom.tool.bow;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.util.BMKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BMBowItem extends ProjectileWeaponItem {
    private final Supplier<ItemBehavior> behavior;
    private final boolean forcedCriticalArrow;
    private final boolean canBeDamaged;
    private final int additionalArrowDamage;
    private final int flameInTicks;
    public int useDuration;

    public BMBowItem(boolean forcedCriticalArrow, boolean canBeDamaged, int additionalArrowDamage, int flameInTicks, int useDuration, Supplier<ItemBehavior> behavior, Properties properties) {
        super(properties);
        this.behavior = behavior;
        this.forcedCriticalArrow = forcedCriticalArrow;
        this.canBeDamaged = canBeDamaged;
        this.additionalArrowDamage = additionalArrowDamage;
        this.flameInTicks = flameInTicks;
        this.useDuration = useDuration;
    }

    public BMBowItem(boolean forcedCriticalArrow, boolean canBeDamaged, int additionalArrowDamage, int flameInTicks, int useDuration, Properties properties) {
        this(forcedCriticalArrow, canBeDamaged, additionalArrowDamage, flameInTicks, useDuration, BMItemBehaviors.NONE, properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity livEntity, int remainingTime) {
        super.releaseUsing(stack, world, livEntity, remainingTime);
        this.shootArrow(stack, world, livEntity, remainingTime);
    }

    /// Called when the player stops using an Item (stops holding the right mouse button).
    public void shootArrow(ItemStack stack, Level world, LivingEntity livEntity, int timeLeft) {
        if (livEntity instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);
            if (!itemstack.isEmpty()) {
                int usedTime = this.getUseDuration(stack, livEntity) - timeLeft;
                usedTime = EventHooks.onArrowLoose(stack, world, player, usedTime, !itemstack.isEmpty());
                if (usedTime < 0) return;
                float arrowVelocity = getArrowVelocity(usedTime);
                if (this.useDuration < 22) arrowVelocity = 1;

                if (!(arrowVelocity < 0.1)) {
                    List<ItemStack> projectilesList = draw(stack, itemstack, player);
                    if (world instanceof ServerLevel serverWorld && !projectilesList.isEmpty()) {
                        this.shoot(serverWorld, player, player.getUsedItemHand(), stack, projectilesList, arrowVelocity * 3, 1, arrowVelocity == 1, null);
                    }

                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1, 1 / (world.getRandom().nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow) {
            arrow.setCritArrow(this.forcedCriticalArrow);
            arrow.setBaseDamage(arrow.getBaseDamage() + this.additionalArrowDamage);
            if (false && shooter instanceof Player player) {
                sendMessage(player, Component.translatable("tooltip." + BackMath.MOD_ID + ".arrow_damage", arrow.getBaseDamage()));
            }
        }
        if (this.flameInTicks > 0) projectile.setRemainingFireTicks(this.flameInTicks);
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0, velocity, inaccuracy);
    }

    public static float getArrowVelocity(int charge) {
        float trueCharge = (float) charge / 20;
        trueCharge = (trueCharge * trueCharge + trueCharge * 2) / 3;
        if (trueCharge > 1) trueCharge = 1;
        return trueCharge;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livEntity) {
        return this.useDuration; // Default is 72.000.
    }

    @Override
    @NotNull
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return this.canBeDamaged ? 0 : super.getDurabilityUse(stack);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        boolean hasProjectile = !player.getProjectile(handStack).isEmpty();

        InteractionResultHolder<ItemStack> actionResult = EventHooks.onArrowNock(handStack, world, player, hand, hasProjectile);
        if (actionResult != null) return actionResult;

        if (!player.getAbilities().instabuild && !hasProjectile) {
            return InteractionResultHolder.fail(handStack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(handStack);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player attacker, Entity target) {
        if (target instanceof LivingEntity livEntity) this.behavior.get().run(stack, attacker, livEntity, target.level());
        return super.onLeftClickEntity(stack, attacker, target);
    }

    private static void sendMessage(Player player, Component messageComponent) {
        ((ServerPlayer) player).sendSystemMessage(messageComponent, true);
    }

    @Override
    @NotNull
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.behavior.get().hasGlint(stack) || super.isFoil(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return this.behavior.get().getDurabilityBarColor(stack, super.getBarColor(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (flag.isAdvanced()) {
            if (!BMKeys.isShiftDown()) tooltip.add(Component.translatable("tooltip.backmath.hold_shift.bow", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
            if (BMKeys.isShiftDown()) {
                tooltip.add(Component.translatable("tooltip.backmath.hold_shift.bow", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.translatable("tooltip.backmath.empty"));
                tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".bow.forced_critical_arrow", Component.translatable(this.forcedCriticalArrow ? "tooltip." + BackMath.MOD_ID + ".false" : "tooltip." + BackMath.MOD_ID + ".true")));
                tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".bow.can_be_damaged", Component.translatable(this.canBeDamaged ? "tooltip." + BackMath.MOD_ID + ".false" : "tooltip." + BackMath.MOD_ID + ".true")));
                tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".bow.additional_arrow_damage", this.additionalArrowDamage));
                tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".bow.flame_duration", StringUtil.formatTickDuration(this.flameInTicks, context.tickRate())));
                tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".bow.use_duration", StringUtil.formatTickDuration(this.useDuration, context.tickRate())));
            }
        }

        if (!this.behavior.get().effects.isEmpty()) {
            tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".empty"));
            tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.when_used").withStyle(ChatFormatting.GRAY));
        }
        for (Supplier<ItemBehaviorEffectType> type : this.behavior.get().effects) {
            if (type != null) type.get().addToTooltip(stack, context, tooltip, flag);
        }
    }
}
