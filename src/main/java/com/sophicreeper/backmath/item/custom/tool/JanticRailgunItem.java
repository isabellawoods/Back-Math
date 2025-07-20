package com.sophicreeper.backmath.item.custom.tool;

import com.sophicreeper.backmath.entity.custom.misc.JanticBoltEntity;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.item.behavior.BMItemBehaviors;
import com.sophicreeper.backmath.item.custom.tool.bow.BMCrossbowItem;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.revaried.RVUtils;
import com.sophicreeper.backmath.util.tag.BMEnchantmentTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
        return stack -> stack.getItem().is(BMItemTags.JANTIC_RAILGUN_PROJECTILES);
    }

    @Override
    @Nonnull
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return stack -> stack.getItem().is(BMItemTags.JANTIC_RAILGUN_PROJECTILES);
    }

    @Override
    public boolean useOnRelease(ItemStack railgunStack) {
        return railgunStack.getItem().is(BMItemTags.RAILGUNS);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (isCharged(handStack)) {
            performShooting(world, player, hand, handStack, 3.15F, 0);
            setCharged(handStack, false);
            return ActionResult.consume(handStack);
        } else if (!player.getProjectile(handStack).isEmpty()) {
            if (!isCharged(handStack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }
            return ActionResult.consume(handStack);
        } else {
            return ActionResult.fail(handStack);
        }
    }

    @Override
    public void releaseUsing(ItemStack railgunStack, World world, LivingEntity livEntity, int remainingTime) {
        int useDuration = this.getUseDuration(railgunStack) - remainingTime;
        float powerForTime = getPowerForTime(useDuration);
        if (powerForTime >= 1 && !isCharged(railgunStack) && tryLoadProjectile(livEntity, railgunStack)) {
            setCharged(railgunStack, true);
            SoundCategory category = livEntity instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound(null, livEntity.getX(), livEntity.getY(), livEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, category, 1, 1 / (random.nextFloat() * 0.5F + 1) + 0.2F);
        }
    }

    private static boolean tryLoadProjectile(LivingEntity livEntity, ItemStack railgunStack) {
        boolean isInCreative = livEntity instanceof PlayerEntity && ((PlayerEntity) livEntity).abilities.instabuild;
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
                if (ammoStack.isEmpty() && shooter instanceof PlayerEntity) ((PlayerEntity) shooter).inventory.removeItem(ammoStack);
            } else stack = ammoStack.copy();
            addChargedProjectile(railgunStack, stack);
            return true;
        }
    }

    public static boolean isCharged(ItemStack railgunStack) {
        return railgunStack.getTag() != null && railgunStack.getTag().getBoolean("charged");
    }

    public static void setCharged(ItemStack railgunStack, boolean charged) {
        railgunStack.getOrCreateTag().putBoolean("charged", charged);
    }

    private static void addChargedProjectile(ItemStack railgunStack, ItemStack ammoStack) {
        CompoundNBT tag = railgunStack.getOrCreateTag();
        if (!tag.contains("charged_projectile", TagTypes.COMPOUND)) {
            tag.put("charged_projectile", RVUtils.saveStack(ammoStack, new CompoundNBT()));
        }
    }

    private static ItemStack getChargedProjectile(ItemStack railgunStack) {
        if (railgunStack.getTag() != null && railgunStack.getTag().contains("charged_projectile", TagTypes.COMPOUND)) {
            return RVUtils.loadStack(railgunStack.getTag().getCompound("charged_projectile"));
        } else {
            return ItemStack.EMPTY;
        }
    }

    private static void clearChargedProjectile(ItemStack railgunStack) {
        if (railgunStack.getTag() != null) railgunStack.getTag().remove("charged_projectile");
    }

    private static void shootProjectile(World world, LivingEntity shooter, Hand hand, ItemStack railgunStack, ItemStack ammoStack, float velocity, float inaccuracy) {
        if (!world.isClientSide) {
            JanticBoltEntity janticBolt = getJanticBolt(world, shooter, ammoStack);

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
            world.addFreshEntity(janticBolt);
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1, 1);
        }
    }

    private static JanticBoltEntity getJanticBolt(World world, LivingEntity shooter, ItemStack ammoStack) {
        JanticBoltEntity janticBolt = new JanticBoltEntity(world, shooter, shooter.getX(), shooter.getEyeY() - (double) 0.15F, shooter.getZ());
        janticBolt.setBoltStack(ammoStack);
        janticBolt.setDeltaMovement(janticBolt.getDeltaMovement().multiply(1.5, 1.5, 1.5));
        janticBolt.setShotFromCrossbow(true);
        return janticBolt;
    }

    public static void performShooting(World world, LivingEntity shooter, Hand usedHand, ItemStack stack, float velocity, float inaccuracy) {
        ItemStack chargedProjectileStack = getChargedProjectile(stack);
        shootProjectile(world, shooter, usedHand, stack, chargedProjectileStack, velocity, inaccuracy);
        onRailgunShot(world, shooter, stack);
    }

    private static void onRailgunShot(World world, LivingEntity shooter, ItemStack railgunStack) {
        if (shooter instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) shooter;
            if (!world.isClientSide) CriteriaTriggers.SHOT_CROSSBOW.trigger(player, railgunStack);
            player.awardStat(Stats.ITEM_USED.get(railgunStack.getItem()));
        }
        clearChargedProjectile(railgunStack);
    }

    @Override
    public void onUseTick(World world, LivingEntity livEntity, ItemStack stack, int count) {
        if (!world.isClientSide) {
            SoundEvent quickChargeLoading = SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            SoundEvent middleLoading = SoundEvents.CROSSBOW_LOADING_MIDDLE;
            float useDuration = (float) (stack.getUseDuration() - count) / LOADING_TIME;
            if (useDuration < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (useDuration >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                world.playSound(null, livEntity.getX(), livEntity.getY(), livEntity.getZ(), quickChargeLoading, SoundCategory.PLAYERS, 0.5F, 1);
            }

            if (useDuration >= 0.5F && middleLoading != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                world.playSound(null, livEntity.getX(), livEntity.getY(), livEntity.getZ(), middleLoading, SoundCategory.PLAYERS, 0.5F, 1);
            }

            // Retroactive fix for https://bugs.mojang.com/browse/MC-165461 for jantic railguns.
            if (useDuration >= 1) this.releaseUsing(stack, world, livEntity, livEntity.getUseItemRemainingTicks());
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return LOADING_TIME;
    }

    @Override
    @Nonnull
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    private static float getPowerForTime(int useTime) {
        float trueUseTime = (float) useTime / LOADING_TIME;
        if (trueUseTime > 1) trueUseTime = 1;
        return trueUseTime;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack railgunStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        Items.STONE.appendHoverText(railgunStack, world, tooltip, flag);
        ItemStack chargedProjectileStack = getChargedProjectile(railgunStack);
        if (chargedProjectileStack.getItem() != Items.AIR) tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".projectile", chargedProjectileStack.getDisplayName()).withStyle(TextFormatting.GRAY));
    }

    @Override
    public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairStack) {
        return repairStack.getItem().is(BMItemTags.INGOTS_MOONERING);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return !enchantment.isIn(BMEnchantmentTags.JANTIC_RAILGUN_NOT_APPLICABLE) && super.canApplyAtEnchantingTable(stack, enchantment);
    }
}
