package com.sophicreeper.backmath.entity.misc;

import com.sophicreeper.backmath.item.custom.tool.JanticRailgunItem;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

/// Extension of {@link ICrossbowUser} to handle custom crossbow, and jantic railguns.
public interface ChargeableWeaponUser extends ICrossbowUser {
    @Override
    void setChargingCrossbow(boolean isCharging);

    @Override
    void shootCrossbowProjectile(LivingEntity livEntity, ItemStack stack, ProjectileEntity projectile, float distanceFactor);

    @Override
    default void performCrossbowAttack(LivingEntity livEntity, float velocity) {
        Hand hand = ProjectileHelper.getWeaponHoldingHand(livEntity, stack -> stack.is(BMItemTags.CROSSBOWS));
        ItemStack handStack = livEntity.getItemInHand(hand);
        if (livEntity.isHolding(stack -> stack.is(BMItemTags.CROSSBOWS))) {
            if (handStack.getItem() instanceof JanticRailgunItem) JanticRailgunItem.performShooting(livEntity.level, livEntity, hand, handStack, 3.15F, 0);
            else CrossbowItem.performShooting(livEntity.level, livEntity, hand, handStack, velocity, (float) (14 - livEntity.level.getDifficulty().getId() * 4));
        }
        this.onCrossbowAttackPerformed();
    }

    @Override
    default void shootCrossbowProjectile(LivingEntity shooter, LivingEntity target, ProjectileEntity arrow, float distanceFactor, float velocity) {
        double targetX = target.getX() - shooter.getX();
        double targetZ = target.getZ() - shooter.getZ();
        double sqrtTargetLoc = MathHelper.sqrt(targetX * targetX + targetZ * targetZ);
        double d3 = target.getY(0.3333333333333333D) - arrow.getY() + sqrtTargetLoc * (double) 0.2F;

        Vector3f vec3F = this.getProjectileShotVector(shooter, new Vector3d(targetX, d3, targetZ), distanceFactor);
        arrow.shoot(vec3F.x(), vec3F.y(), vec3F.z(), velocity, (float) (14 - shooter.level.getDifficulty().getId() * 4));
        shooter.playSound(SoundEvents.CROSSBOW_SHOOT, 1, 1 / (shooter.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    @Nonnull
    default Vector3f getProjectileShotVector(LivingEntity livEntity, Vector3d vec3D, float distanceFactor) {
        Vector3d normVec3D = vec3D.normalize();
        Vector3d crossedVec3D = normVec3D.cross(new Vector3d(0, 1, 0));
        if (crossedVec3D.lengthSqr() <= 1.0E-7D) crossedVec3D = normVec3D.cross(livEntity.getUpVector(1));

        Quaternion quaternion = new Quaternion(new Vector3f(crossedVec3D), 90, true);
        Vector3f vec3F = new Vector3f(normVec3D);
        vec3F.transform(quaternion);
        Quaternion quaternion1 = new Quaternion(vec3F, distanceFactor, true);
        Vector3f normVec3F = new Vector3f(normVec3D);
        normVec3F.transform(quaternion1);
        return normVec3F;
    }
}
