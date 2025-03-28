package com.sophicreeper.backmath.entity.custom.termian;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.goal.termian.TermianPatrolGoal;
import com.sophicreeper.backmath.entity.misc.HasBust;
import com.sophicreeper.backmath.entity.misc.WornOutfit;
import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.misc.BMBreastPhysics;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.RVUtils;
import com.sophicreeper.backmath.util.tag.BMBlockTags;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import net.minecraft.entity.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.entity.monster.MonsterEntity.isDarkEnoughToSpawn;

public abstract class TermianPatrollerEntity extends CreatureEntity implements WornOutfit, HasBust {
    private static final DataParameter<String> OUTFIT_TEXTURE = EntityDataManager.defineId(TermianPatrollerEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> CAPE_TEXTURE = EntityDataManager.defineId(TermianPatrollerEntity.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> CAPE_VISIBILITY = EntityDataManager.defineId(TermianPatrollerEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> BUST_SIZE = EntityDataManager.defineId(TermianPatrollerEntity.class, DataSerializers.FLOAT);
    private BlockPos patrolTarget;
    private boolean patrolLeader;
    private boolean patrolling;
    private boolean sneaking;
    public double prevChasingPosX;
    public double prevChasingPosY;
    public double prevChasingPosZ;
    public double chasingPosX;
    public double chasingPosY;
    public double chasingPosZ;
    public float prevCameraYaw;
    public float cameraYaw;

    public TermianPatrollerEntity(EntityType<? extends CreatureEntity> mob, World world) {
        super(mob, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OUTFIT_TEXTURE, "");
        this.entityData.define(CAPE_TEXTURE, BackMath.backMath("cape/cherry_blossom").toString());
        this.entityData.define(CAPE_VISIBILITY, true);
        this.entityData.define(BUST_SIZE, 0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new TermianPatrolGoal<>(this, 0.7D, 0.595D));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        if (this.patrolTarget != null) tag.putIntArray("patrol_target", new int[] {this.patrolTarget.getX(), this.patrolTarget.getY(), this.patrolTarget.getZ()});
        tag.putBoolean("patrol_leader", this.patrolLeader);
        tag.putBoolean("is_patrolling", this.patrolling);
        if (this.getType().is(BMEntityTypeTags.ELIGIBLE_TO_CAPES)) {
            CompoundNBT capeTag = new CompoundNBT();
            capeTag.putString("texture", this.entityData.get(CAPE_TEXTURE));
            capeTag.putBoolean("visible", this.entityData.get(CAPE_VISIBILITY));
            tag.put("cape", capeTag);
        }
        tag.putFloat("bust_size", this.entityData.get(BUST_SIZE));
        if (!this.entityData.get(OUTFIT_TEXTURE).isEmpty()) tag.putString("outfit", this.entityData.get(OUTFIT_TEXTURE));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("patrol_target", TagTypes.INTEGER_ARRAY)) this.patrolTarget = RVUtils.readBlockPos(tag, "patrol_target");
        this.patrolLeader = tag.getBoolean("patrol_leader");
        this.patrolling = tag.getBoolean("is_patrolling");
        if (this.getType().is(BMEntityTypeTags.ELIGIBLE_TO_CAPES)) {
            this.entityData.set(CAPE_TEXTURE, tag.getCompound("cape").getString("texture"));
            this.entityData.set(CAPE_VISIBILITY, tag.getCompound("cape").getBoolean("visible"));
        }
        this.setBustSize(tag.getFloat("bust_size"));
        if (tag.contains("outfit", TagTypes.STRING)) this.entityData.set(OUTFIT_TEXTURE, tag.getString("outfit"));
    }

    @Override
    public void tick() {
        super.tick();
        this.updateCape();
        this.updatePose();
        this.getBreastPhysics().update(this, this.getBustSize());
    }

    @Override
    public void aiStep() {
        this.prevCameraYaw = this.cameraYaw;
        float f;
        if (this.onGround && !this.isDeadOrDying() && !this.isSwimming()) {
            f = Math.min(0.1F, MathHelper.sqrt(getHorizontalDistanceSqr(this.getDeltaMovement())));
        } else {
            f = 0;
        }
        this.cameraYaw += (f - this.cameraYaw) * 0.4F;
        this.sneaking = !this.isSwimming() && this.canEnterPose(Pose.CROUCHING) && (this.isShiftKeyDown() || !this.isSleeping() && !this.canEnterPose(Pose.STANDING));
        super.aiStep();
    }

    @Override
    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof CreatureEntity) {
            CreatureEntity entity = (CreatureEntity) this.getVehicle();
            this.yBodyRot = entity.yBodyRot;
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0;
        }
    }

    @Override
    public void updateSwimming() {
        if (this.isSwimming()) {
            this.setSwimming(this.isInWater() && !this.isPassenger());
        } else if (this.getTarget() != null && this.getTarget().isInWater() && this.isUnderWater()) {
            this.setSwimming(true);
        } else {
            this.setSwimming(this.isUnderWater() && !this.isPassenger());
        }
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        switch (pose) {
            case SWIMMING: case FALL_FLYING: case SPIN_ATTACK: return 0.4F;
            case CROUCHING: return 1.27F;
            default: return 1.62F;
        }
    }

    public boolean canBePatrolLeader() {
        return true;
    }

    private void updatePose() {
        if (this.canEnterPose(Pose.SWIMMING)) {
            Pose pose;
            if (this.isFallFlying()) {
                pose = Pose.FALL_FLYING;
            } else if (this.isSleeping()) {
                pose = Pose.SLEEPING;
            } else if (this.isSwimming()) {
                pose = Pose.SWIMMING;
            } else if (this.isAutoSpinAttack()) {
                pose = Pose.SPIN_ATTACK;
            } else if (this.isShiftKeyDown()) {
                pose = Pose.CROUCHING;
            } else {
                pose = Pose.STANDING;
            }

            Pose pose1;
            if (!this.isSpectator() && !this.isPassenger() && !this.canEnterPose(pose)) {
                if (this.canEnterPose(Pose.CROUCHING)) {
                    pose1 = Pose.CROUCHING;
                } else {
                    pose1 = Pose.SWIMMING;
                }
            } else {
                pose1 = pose;
            }

            this.setPose(pose1);
        }
    }

    @Override
    @Nonnull
    public EntitySize getDimensions(Pose pose) {
        switch (pose) {
            case SLEEPING: return SLEEPING_DIMENSIONS;
            case FALL_FLYING: case SWIMMING: case SPIN_ATTACK: return EntitySize.scalable(0.6F, 0.6F);
            case DYING: return EntitySize.scalable(0.2F, 0.2F);
            case CROUCHING: return EntitySize.scalable(0.6F, 1.5F);
            case STANDING: default: return super.getDimensions(pose);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        ItemStack handStack = this.getItemInHand(Hand.MAIN_HAND);
        if (handStack != ItemStack.EMPTY && entity instanceof LivingEntity) {
            handStack.getItem().hurtEnemy(handStack, (LivingEntity) entity, this);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public void travel(Vector3d position) {
        if (this.isSwimming() && !this.isPassenger()) {
            double yLookAngle = this.getLookAngle().y;
            double d = yLookAngle < -0.2D ? 0.085D : 0.06D;
            if (yLookAngle <= 0 || this.jumping || !this.level.getBlockState(new BlockPos(this.getX(), this.getY() + 1 - 0.1D, this.getZ())).getFluidState().isEmpty()) {
                Vector3d deltaMovement = this.getDeltaMovement();
                this.setDeltaMovement(deltaMovement.add(0, (yLookAngle - deltaMovement.y) * d, 0));
            }
        }
        super.travel(position);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT dataTag) {
        if (reason != SpawnReason.PATROL && reason != SpawnReason.EVENT && reason != SpawnReason.STRUCTURE && this.random.nextFloat() < 0.06F && this.canBePatrolLeader()) this.patrolLeader = true;

        if (this.isPatrolLeader()) {
            this.setItemSlot(EquipmentSlotType.HEAD, BMUtils.getTermianBannerInstance());
            this.setDropChance(EquipmentSlotType.HEAD, 2);
        }
        if (reason == SpawnReason.PATROL) this.patrolling = true;

        this.setBustSize(this.random.nextFloat());

        // Capes
        this.setCapeVisibility(this.random.nextInt(16) != 0);
        BMUtils.setRandomCape(this, this.random);

        return super.finalizeSpawn(world, difficulty, reason, entityData, dataTag);
    }

    public static boolean checkTermianSpawnRules(EntityType<? extends CreatureEntity> termian, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getBlockState(pos.below()).is(BMBlockTags.TERMIANS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8;
    }

    public static boolean checkTermianPatrolSpawnRules(EntityType<? extends TermianPatrollerEntity> termian, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getBrightness(LightType.BLOCK, pos) <= 8 && checkAnyLightMonsterSpawnRules(termian, world, reason, pos, rand);
    }

    private static boolean checkAnyLightMonsterSpawnRules(EntityType<? extends CreatureEntity> mob, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(mob, world, reason, pos, rand);
    }

    public static boolean checkMonsterSpawnRules(EntityType<? extends CreatureEntity> mob, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(world, pos, rand) && checkMobSpawnRules(mob, world, reason, pos, rand);
    }

    public boolean removeWhenFarAway(double distance) {
        return !this.patrolling;
    }

    @Nonnull
    protected SoundEvent getSwimSound() {
        return BMSounds.ENTITY_TERMIAN_SWIM;
    }

    @Nonnull
    protected SoundEvent getSwimSplashSound() {
        return BMSounds.ENTITY_TERMIAN_SPLASH;
    }

    @Nonnull
    protected SoundEvent getSwimHighSpeedSplashSound() {
        return BMSounds.ENTITY_TERMIAN_SPLASH_HIGH_SPEED;
    }

    @Override
    public boolean isCrouching() {
        return this.sneaking;
    }

    public void setPatrolTarget(BlockPos pos) {
        this.patrolTarget = pos;
        this.patrolling = true;
    }

    public BlockPos getPatrolTarget() {
        return this.patrolTarget;
    }

    public boolean hasPatrolTarget() {
        return this.patrolTarget != null;
    }

    public void setPatrolLeader(boolean leader) {
        this.patrolLeader = leader;
        this.patrolling = true;
    }

    public boolean isPatrolLeader() {
        return this.patrolLeader;
    }

    public boolean canJoinPatrol() {
        return true;
    }

    public void findPatrolTarget() {
        this.patrolTarget = this.blockPosition().offset(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
        this.patrolling = true;
    }

    public boolean isPatrolling() {
        return this.patrolling;
    }

    public void setPatrolling(boolean patrolling) {
        this.patrolling = patrolling;
    }

    public String getCapeTexture() {
        return this.entityData.get(CAPE_TEXTURE);
    }

    public void setCapeTexture(String capeTexture) {
        this.entityData.set(CAPE_TEXTURE, capeTexture);
    }

    public boolean getCapeVisibility() {
        return this.entityData.get(CAPE_VISIBILITY);
    }

    public void setCapeVisibility(boolean visible) {
        this.entityData.set(CAPE_VISIBILITY, visible);
    }

    @Override
    public String getOutfitTexture() {
        return this.entityData.get(OUTFIT_TEXTURE);
    }

    @Override
    public void setOutfitTexture(String outfitTexture) {
        this.entityData.set(OUTFIT_TEXTURE, outfitTexture);
    }

    @Override
    public boolean isWearingOutfit() {
        String outfitTexture = this.entityData.get(OUTFIT_TEXTURE);
        return !outfitTexture.isEmpty() && OutfitDefinition.DATA_DRIVEN_OUTFITS.containsKey(ResourceLocation.tryParse(outfitTexture));
    }

    @Override
    public float getBustSize() {
        return this.entityData.get(BUST_SIZE);
    }

    @Override
    public void setBustSize(float bustSize) {
        this.entityData.set(BUST_SIZE, bustSize);
    }

    @Override
    public BMBreastPhysics getBreastPhysics() {
        return new BMBreastPhysics();
    }

    private void updateCape() {
        this.prevChasingPosX = this.chasingPosX;
        this.prevChasingPosY = this.chasingPosY;
        this.prevChasingPosZ = this.chasingPosZ;
        double d0 = this.getX() - this.chasingPosX;
        double d1 = this.getY() - this.chasingPosY;
        double d2 = this.getZ() - this.chasingPosZ;

        if (d0 > 10) {
            this.chasingPosX = this.getX();
            this.prevChasingPosX = this.chasingPosX;
        }

        if (d2 > 10) {
            this.chasingPosZ = this.getZ();
            this.prevChasingPosZ = this.chasingPosZ;
        }

        if (d1 > 10) {
            this.chasingPosY = this.getY();
            this.prevChasingPosY = this.chasingPosY;
        }

        if (d0 < -10) {
            this.chasingPosX = this.getX();
            this.prevChasingPosX = this.chasingPosX;
        }

        if (d2 < -10) {
            this.chasingPosZ = this.getZ();
            this.prevChasingPosZ = this.chasingPosZ;
        }

        if (d1 < -10) {
            this.chasingPosY = this.getY();
            this.prevChasingPosY = this.chasingPosY;
        }

        this.chasingPosX += d0 * 0.25D;
        this.chasingPosZ += d2 * 0.25D;
        this.chasingPosY += d1 * 0.25D;
    }
}
