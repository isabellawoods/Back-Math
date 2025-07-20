package com.sophicreeper.backmath.misc;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class BMBreastPhysics {
    private float bounceVelocity = 0;
    private float velocity = 0;
    private float targetBounce = 0;
    private float bustSize;
    private float preBounce;
    private boolean justSneaking = false;
    private boolean alreadySleeping = false;
    private Vector3d motion;
    private Vector3d prePos;

    public BMBreastPhysics() {}

    public void update(CreatureEntity entity, float bustSize) {
        this.preBounce = this.bustSize;
        if (this.prePos == null) {
            this.prePos = entity.position();
        } else {
            this.motion = entity.position().subtract(this.prePos);
            this.prePos = entity.position();
            float bounceIntensity = bustSize * 3 * (0.34F * bustSize + 1.2F);
            this.targetBounce = (float) this.motion.y() * bounceIntensity;
            if (entity.getPose() == Pose.CROUCHING && !this.justSneaking) {
                this.justSneaking = true;
                this.targetBounce = bounceIntensity;
            }

            if (entity.getPose() != Pose.CROUCHING && this.justSneaking) {
                this.justSneaking = false;
                this.targetBounce = bounceIntensity;
            }

            float distanceFromMin;
            float distanceFromMax;
            float bounceAmount;

            if (entity.getVehicle() != null) {
                if (entity.getVehicle() instanceof BoatEntity) {
                    BoatEntity boat = (BoatEntity) entity.getVehicle();
                    int rowingTime = (int) boat.getRowingTime(0, entity.animationPosition);
                    int rowingTime1 = (int) boat.getRowingTime(1, entity.animationPosition);
                    distanceFromMin = (float) MathHelper.clampedLerp(-1.0471975803375244, -0.2617993950843811, (MathHelper.sin((float) (-rowingTime1)) + 1) / 2);
                    distanceFromMax = (float) MathHelper.clampedLerp(-0.7853981852531433, 0.7853981852531433, (MathHelper.sin((float) (-rowingTime) + 1) + 1) / 2);
                    if (distanceFromMin < -1 || distanceFromMax < -0.6F) {
                        this.targetBounce = bounceIntensity / 3.25F;
                    }
                }

                if (entity.getVehicle() instanceof MinecartEntity) {
                    MinecartEntity minecart = (MinecartEntity) entity.getVehicle();
                    bounceAmount = (float) minecart.getDeltaMovement().lengthSqr();
                    if (Math.random() * (double) bounceAmount < 0.5 && bounceAmount > 0.2F) {
                        if (Math.random() > 0.5) {
                            this.targetBounce = -bounceIntensity / 6;
                        } else {
                            this.targetBounce = bounceIntensity / 6;
                        }
                    }
                }

                if (entity.getVehicle() instanceof AbstractHorseEntity) {
                    AbstractHorseEntity horse = (AbstractHorseEntity) entity.getVehicle();
                    bounceAmount = (float) horse.getDeltaMovement().length();
                    if (horse.tickCount % this.clampMovement(bounceAmount) == 5 && bounceAmount > 0.1F) {
                        this.targetBounce = bounceIntensity / 4;
                    }
                }

                if (entity.getVehicle() instanceof PigEntity) {
                    PigEntity pig = (PigEntity) entity.getVehicle();
                    bounceAmount = (float) pig.getDeltaMovement().length();
                    if (pig.tickCount % this.clampMovement(bounceAmount) == 5 && bounceAmount > 0.08F) {
                        this.targetBounce = bounceIntensity / 4;
                    }
                }

                if (entity.getVehicle() instanceof StriderEntity) {
                    StriderEntity strider = (StriderEntity) entity.getVehicle();
                    this.targetBounce += ((float) (strider.getPassengersRidingOffset() * 3) - 4.5F) * bounceIntensity;
                }
            }

            if (entity.swinging && entity.getPose() != Pose.SLEEPING) {
                if (Math.random() > 0.5) {
                    this.targetBounce = -0.1F * bounceIntensity;
                } else {
                    this.targetBounce = 0.1F * bounceIntensity;
                }
            }

            if (entity.getPose() == Pose.SLEEPING && !this.alreadySleeping) {
                this.targetBounce = bounceIntensity;
                this.alreadySleeping = true;
            }

            if (entity.getPose() != Pose.SLEEPING && this.alreadySleeping) {
                this.targetBounce = bounceIntensity;
                this.alreadySleeping = false;
            }

            float momentumMultiplier = 0.95F;
            bounceAmount = 0.45F * (1 - momentumMultiplier) + 0.15F;
            bounceAmount = MathHelper.clamp(bounceAmount, 0.15F, 0.6F);
            float delta = 2.25F - bounceAmount;
            distanceFromMin = Math.abs(this.bounceVelocity + 0.5F) * 0.5F;
            distanceFromMax = Math.abs(this.bounceVelocity - 2.65F) * 0.5F;
            if (this.bounceVelocity < -0.5F) {
                this.targetBounce += distanceFromMin;
            }

            if (this.bounceVelocity > 2.5F) {
                this.targetBounce -= distanceFromMax;
            }

            if (this.targetBounce < -1.5F) {
                this.targetBounce = -1.5F;
            }

            if (this.targetBounce > 2.5F) {
                this.targetBounce = 2.5F;
            }

            this.velocity = MathHelper.lerp(bounceAmount, this.velocity, (this.targetBounce - this.bounceVelocity) * delta);
            this.bounceVelocity += this.velocity * momentumMultiplier * 1.1625F;
            this.bustSize = this.bounceVelocity;
        }
    }

    public float getPreBounce() {
        return this.preBounce;
    }

    public float getBreastBounce() {
        return this.bustSize;
    }

    private int clampMovement(float movement) {
        int i = (int) (10 - movement * 2);
        if (i < 1) i = 1;

        return i;
    }
}
