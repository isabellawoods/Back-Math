package melonystudios.backmath.item.custom.tool;

import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.component.custom.ChargedProjectile;
import melonystudios.backmath.item.AxolotlTest;
import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.custom.tool.bow.BMCrossbowItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class JanticRailgunItem extends BMCrossbowItem {
    public static int LOADING_TIME = 50;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public JanticRailgunItem(Properties properties) {
        super(BMItemBehaviors.NONE, properties);
    }

    @Override
    @Nonnull
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(AxolotlTest.JANTICAL); // stack.is(BMItemTags.JANTIC_RAILGUN_PROJECTILES);
    }

    @Override
    @Nonnull
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return stack -> stack.is(AxolotlTest.JANTICAL); // stack.is(BMItemTags.JANTIC_RAILGUN_PROJECTILES);
    }

    @Override
    public boolean useOnRelease(ItemStack railgunStack) {
        return railgunStack.getItem() == this; // railgunStack.getItem().is(BMItemTags.RAILGUNS);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (isCharged(handStack)) {
            performShooting(world, player, hand, handStack, 3.15F, 0);
            return InteractionResultHolder.consume(handStack);
        } else if (!player.getProjectile(handStack).isEmpty()) {
            if (!isCharged(handStack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }
            return InteractionResultHolder.consume(handStack);
        } else {
            return InteractionResultHolder.fail(handStack);
        }
    }

    @Override
    public void releaseUsing(ItemStack railgunStack, Level world, LivingEntity livEntity, int remainingTime) {
        int useDuration = this.getUseDuration(railgunStack, livEntity) - remainingTime;
        float powerForTime = getPowerForTime(useDuration);
        if (powerForTime >= 1 && !isCharged(railgunStack) && tryLoadProjectile(livEntity, railgunStack)) {
            SoundSource category = livEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            world.playSound(null, livEntity.getX(), livEntity.getY(), livEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, category, 1, 1 / (world.getRandom().nextFloat() * 0.5F + 1) + 0.2F);
        }
    }

    private static boolean tryLoadProjectile(LivingEntity livEntity, ItemStack railgunStack) {
        boolean isInCreative = livEntity instanceof Player && ((Player) livEntity).getAbilities().instabuild;
        ItemStack projectileStack = livEntity.getProjectile(railgunStack);
        if (projectileStack.isEmpty() && isInCreative) projectileStack = new ItemStack(AxolotlTest.JANTICAL.get());
        return loadProjectile(livEntity, railgunStack, projectileStack, isInCreative);
    }

    private static boolean loadProjectile(LivingEntity shooter, ItemStack railgunStack, ItemStack ammoStack, boolean isInCreative) {
        if (ammoStack.isEmpty()) {
            return false;
        } else {
            ItemStack stack;
            if (!isInCreative) {
                stack = ammoStack.split(1);
                if (ammoStack.isEmpty() && shooter instanceof Player) ((Player) shooter).getInventory().removeItem(ammoStack);
            } else stack = ammoStack.copy();
            addChargedProjectile(railgunStack, stack);
            return true;
        }
    }

    public static boolean isCharged(ItemStack railgunStack) {
        return railgunStack.has(BMDataComponents.CHARGED_PROJECTILE);
    }

    private static void addChargedProjectile(ItemStack railgunStack, ItemStack ammoStack) {
        railgunStack.set(BMDataComponents.CHARGED_PROJECTILE, new ChargedProjectile(ammoStack));
    }

    private static ItemStack getChargedProjectile(ItemStack railgunStack) {
        ChargedProjectile projectile = railgunStack.get(BMDataComponents.CHARGED_PROJECTILE);
        if (projectile != null && projectile.projectileStack() != null) return projectile.projectileStack();
        return ItemStack.EMPTY;
    }

    private static void clearChargedProjectile(ItemStack railgunStack) {
        railgunStack.remove(BMDataComponents.CHARGED_PROJECTILE);
    }

    private static void shootProjectile(Level world, LivingEntity shooter, InteractionHand hand, ItemStack railgunStack, ItemStack ammoStack, float velocity, float inaccuracy) {
        if (!world.isClientSide) {
            /*JanticBoltEntity janticBolt = getJanticBolt(world, shooter, ammoStack);

            if (shooter instanceof ICrossbowUser) {
                ICrossbowUser crossbowUser = (ICrossbowUser) shooter;
                if (crossbowUser.getTarget() != null) crossbowUser.shootCrossbowProjectile(crossbowUser.getTarget(), railgunStack, janticBolt, (float) 0);
            } else {
                Vector3d upVector = shooter.getUpVector(1);
                Quaternion quaternion = new Quaternion(new Vector3f(upVector), 0, true);
                Vector3d viewVector = shooter.getViewVector(1);
                Vector3f vector3F = new Vector3f(viewVector);
                vector3F.transform(quaternion);
                janticBolt.shoot(vector3F.x(), vector3F.y(), vector3F.z(), velocity, inaccuracy);
            }

            railgunStack.hurtAndBreak(1, shooter, livEntity -> livEntity.broadcastBreakEvent(hand));
            world.addFreshEntity(janticBolt);*/
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1, 1);
        }
    }

    /*private static JanticBoltEntity getJanticBolt(Level world, LivingEntity shooter, ItemStack ammoStack) {
        JanticBoltEntity janticBolt = new JanticBoltEntity(world, shooter, shooter.getX(), shooter.getEyeY() - (double) 0.15F, shooter.getZ());
        janticBolt.setBoltStack(ammoStack);
        janticBolt.setDeltaMovement(janticBolt.getDeltaMovement().multiply(1.5, 1.5, 1.5));
        janticBolt.setShotFromCrossbow(true);
        return janticBolt;
    }*/

    public static void performShooting(Level world, LivingEntity shooter, InteractionHand usedHand, ItemStack stack, float velocity, float inaccuracy) {
        ItemStack chargedProjectileStack = getChargedProjectile(stack);
        shootProjectile(world, shooter, usedHand, stack, chargedProjectileStack, velocity, inaccuracy);
        onRailgunShot(world, shooter, stack);
    }

    private static void onRailgunShot(Level world, LivingEntity shooter, ItemStack railgunStack) {
        if (shooter instanceof ServerPlayer player) {
            if (!world.isClientSide) CriteriaTriggers.SHOT_CROSSBOW.trigger(player, railgunStack);
            player.awardStat(Stats.ITEM_USED.get(railgunStack.getItem()));
        }
        clearChargedProjectile(railgunStack);
    }

    @Override
    public void onUseTick(Level world, LivingEntity livEntity, ItemStack stack, int count) {
        if (!world.isClientSide) {
            SoundEvent quickChargeLoading = SoundEvents.CROSSBOW_QUICK_CHARGE_1.value();
            SoundEvent middleLoading = SoundEvents.CROSSBOW_LOADING_MIDDLE.value();
            float useDuration = (float) (stack.getUseDuration(livEntity) - count) / LOADING_TIME;
            if (useDuration < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (useDuration >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                world.playSound(null, livEntity.getX(), livEntity.getY(), livEntity.getZ(), quickChargeLoading, SoundSource.PLAYERS, 0.5F, 1);
            }

            if (useDuration >= 0.5F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                world.playSound(null, livEntity.getX(), livEntity.getY(), livEntity.getZ(), middleLoading, SoundSource.PLAYERS, 0.5F, 1);
            }

            // Retroactive fix for https://bugs.mojang.com/browse/MC-165461 for jantic railguns.
            if (useDuration >= 1) this.releaseUsing(stack, world, livEntity, livEntity.getUseItemRemainingTicks());
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livEntity) {
        return LOADING_TIME;
    }

    @Override
    @Nonnull
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW;
    }

    private static float getPowerForTime(int useTime) {
        float trueUseTime = (float) useTime / LOADING_TIME;
        if (trueUseTime > 1) trueUseTime = 1;
        return trueUseTime;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack railgunStack, TooltipContext  context, List<Component> tooltip, TooltipFlag flag) {
        Items.STONE.appendHoverText(railgunStack, context, tooltip, flag);
        ItemStack chargedProjectileStack = getChargedProjectile(railgunStack);
        if (chargedProjectileStack.getItem() != Items.AIR) tooltip.add(Component.translatable(this.getDescriptionId() + ".projectile", chargedProjectileStack.getDisplayName()).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairStack) {
        return repairStack.is(AxolotlTest.MOONERING_INGOT); // repairStack.is(BMItemTags.INGOTS_MOONERING);
    }
}
