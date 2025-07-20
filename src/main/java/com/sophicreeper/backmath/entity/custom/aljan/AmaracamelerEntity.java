package com.sophicreeper.backmath.entity.custom.aljan;

import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.goal.amaracameler.*;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.fix.TagFixes;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.world.biome.BMBiomes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class AmaracamelerEntity extends MobEntity implements IMob {
    private static final DataParameter<Integer> AMARACAMELER_SIZE = EntityDataManager.defineId(AmaracamelerEntity.class, DataSerializers.INT);
    public float squishAmount;
    public float squishFactor;
    public float previousSquishFactor;
    private boolean wasOnGround;

    public AmaracamelerEntity(EntityType<AmaracamelerEntity> entity, World world) {
        super(entity, world);
        this.moveControl = new AmaracamelerMovementController(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ALFloatGoal(this));
        this.goalSelector.addGoal(2, new ALAttackGoal(this));
        this.goalSelector.addGoal(3, new ALFaceRandomGoal(this));
        this.goalSelector.addGoal(5, new ALHopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MalaikaEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, 10, true, false, livEntity -> Math.abs(livEntity.getY() - this.getY()) <= 4));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, livEntity -> Math.abs(livEntity.getY() - this.getY()) <= 4));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AMARACAMELER_SIZE, 1);
    }

    protected void setSize(int size, boolean resetHealth) {
        this.entityData.set(AMARACAMELER_SIZE, size);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(size * size);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2F + 0.1F * (float) size);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(size);
        if (resetHealth) this.setHealth(this.getMaxHealth());
        this.xpReward = size;
    }

    /// Returns the size of the amaracameler.
    public int getSize() {
        return this.entityData.get(AMARACAMELER_SIZE);
    }

    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("size", this.getSize() - 1);
        tag.putBoolean("was_on_ground", this.wasOnGround);
    }

    /// (abstract) Protected helper method to read subclass entity data from NBT.
    public void readAdditionalSaveData(CompoundNBT tag) {
        int sizeTag = TagFixes.renameSize(tag);
        if (sizeTag < 0) sizeTag = 0;

        this.setSize(sizeTag + 1, false);
        super.readAdditionalSaveData(tag);
        this.wasOnGround = TagFixes.renameWasOnGround(tag);
    }

    /// Fix [MC-118616](https://bugs.mojang.com/browse/MC-118616) for amaracamelers.
    @Override
    @Nonnull
    public SoundCategory getSoundSource() {
        return SoundCategory.HOSTILE;
    }

    public boolean isSmallAmaracameler() {
        return this.getSize() <= 1;
    }

    protected IParticleData getSquishParticle() {
        return new ItemParticleData(ParticleTypes.ITEM, new ItemStack(AxolotlTest.STICKY_AMARACAMEL_BLOCK.get()));
    }

    protected boolean shouldDespawnInPeaceful() {
        return this.getSize() > 0;
    }

    /// Called to update the entity's position/logic.
    public void tick() {
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.previousSquishFactor = this.squishFactor;
        super.tick();
        if (this.onGround && !this.wasOnGround) {
            int amaracamelerSize = this.getSize();

            if (this.spawnCustomParticles()) amaracamelerSize = 0; // Don't spawn particles if it's handled by the implementation itself.
            for (int j = 0; j < amaracamelerSize * 8; ++j) {
                float f = this.random.nextFloat() * ((float) Math.PI * 2F);
                float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float) amaracamelerSize * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float) amaracamelerSize * 0.5F * f1;
                this.level.addParticle(this.getSquishParticle(), this.getX() + (double) f2, this.getY(), this.getZ() + (double) f3, 0, 0, 0);
            }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1) / 0.8F);
            this.squishAmount = -0.5F;
        } else if (!this.onGround && this.wasOnGround) {
            this.squishAmount = 1;
        }

        this.wasOnGround = this.onGround;
        this.decreaseSquish();
    }

    protected void decreaseSquish() {
        this.squishAmount *= 0.6F;
    }

    /// Gets the amount of time the amaracameler needs to wait between jumps.
    public int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (AMARACAMELER_SIZE.equals(key)) {
            this.refreshDimensions();
            this.yRot = this.yHeadRot;
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) this.doWaterSplashEffect();
        }

        super.onSyncedDataUpdated(key);
    }

    @Override
    @Nonnull
    @SuppressWarnings("unchecked")
    public EntityType<? extends AmaracamelerEntity> getType() {
        return (EntityType<? extends AmaracamelerEntity>) super.getType();
    }

    @Override
    public void remove(boolean keepData) {
        int size = this.getSize();
        if (!this.level.isClientSide && size > 1 && this.isDeadOrDying() && !this.removed) {
            ITextComponent mobName = this.getCustomName();
            boolean flag = this.isNoAi();
            float amaracamelerSizeFourth = (float) size / 4;
            int amaracamelerSizeHalved = size / 2;
            int k = 2 + this.random.nextInt(3);

            for (int l = 0; l < k; ++l) {
                float f1 = ((float) (l % 2) - 0.5F) * amaracamelerSizeFourth;
                float f2 = ((float) (l / 2) - 0.5F) * amaracamelerSizeFourth;

                AmaracamelerEntity amaracameler = this.getType().create(this.level);
                if (this.isPersistenceRequired()) amaracameler.setPersistenceRequired();
                amaracameler.setCustomName(mobName);
                amaracameler.setNoAi(flag);
                amaracameler.setInvulnerable(this.isInvulnerable());
                amaracameler.setSize(amaracamelerSizeHalved, true);
                amaracameler.moveTo(this.getX() + (double) f1, this.getY() + 0.5D, this.getZ() + (double) f2, this.random.nextFloat() * 360, 0);
                this.level.addFreshEntity(amaracameler);
            }
        }

        super.remove(keepData);
    }

    /// Applies a velocity to the entities, to push them away from each other.
    public void push(Entity entity) {
        super.push(entity);
        if (entity.getType().is(BMEntityTypeTags.AMARACAMELER_TARGETS) && this.canDamageEntity()) this.dealDamage((LivingEntity) entity);
    }

    /// Called by a player entity when they collide with an entity.
    public void playerTouch(PlayerEntity player) {
        if (this.canDamageEntity()) this.dealDamage(player);
    }

    protected void dealDamage(LivingEntity livEntity) {
        if (this.isAlive()) {
            int size = this.getSize();
            if (this.distanceToSqr(livEntity) < 0.6D * (double) size * 0.6D * (double) size && this.canSee(livEntity) && livEntity.hurt(DamageSource.mobAttack(this), this.getAttackDamage())) {
                this.playSound(BMSounds.ENTITY_AMARACAMELER_ATTACK, 1, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1);
                this.doEnchantDamageEffects(this, livEntity);
            }
        }
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 0.625F * size.height;
    }

    /// Indicates weather the amaracameler is able to damage the player (based upon the amaracameler's size).
    public boolean canDamageEntity() {
        return !this.isSmallAmaracameler() && this.isEffectiveAi();
    }

    protected float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return this.isSmallAmaracameler() ? BMSounds.ENTITY_AMARACAMELER_HURT_SMALL : BMSounds.ENTITY_AMARACAMELER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.isSmallAmaracameler() ? BMSounds.ENTITY_AMARACAMELER_DEATH_SMALL : BMSounds.ENTITY_AMARACAMELER_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return this.isSmallAmaracameler() ? BMSounds.ENTITY_AMARACAMELER_SQUISH_SMALL : BMSounds.ENTITY_AMARACAMELER_SQUISH;
    }

    @Nonnull
    protected ResourceLocation getDefaultLootTable() {
        return this.getSize() == 1 ? BMResourceLocations.SMALL_AMARACAMELER : BMResourceLocations.MEDIUM_OR_LARGE_AMARACAMELER;
    }

    public static boolean checkAmaracamelerSpawnRules(EntityType<AmaracamelerEntity> amaracameler, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            if (Objects.equals(world.getBiome(pos), BMBiomes.AMARACAMEL_STICKS.get()) && pos.getY() > 50 && pos.getY() < 70 && rand.nextFloat() < 0.5F && rand.nextFloat() < world.getMoonBrightness() && world.getMaxLocalRawBrightness(pos) <= rand.nextInt(8)) {
                return checkMobSpawnRules(amaracameler, world, reason, pos, rand);
            }

            if (!(world instanceof ISeedReader)) return false;

            ChunkPos chunkPos = new ChunkPos(pos);
            boolean isSlimeChunk = SharedSeedRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, ((ISeedReader) world).getSeed(), 987234911L).nextInt(10) == 0;
            if (rand.nextInt(10) == 0 && isSlimeChunk && pos.getY() < 40) {
                return checkMobSpawnRules(amaracameler, world, reason, pos, rand);
            }
        }
        return false;
    }

    /// Returns the volume for the sounds this mob makes.
    public float getSoundVolume() {
        return 0.4F * (float) this.getSize();
    }

    /// The speed it takes to move the living entity's <code>rotationPitch</code> through the {@link net.minecraft.entity.ai.controller.LookController#setLookAt setLookAt} method. This is only currently use in wolves.
    public int getMaxHeadXRot() {
        return 0;
    }

    /// Returns true if the amaracameler makes a sound when it jumps (based upon the amaracameler's size).
    public boolean makesSoundOnJump() {
        return this.getSize() > 0;
    }

    /// Causes this entity to do an upwards motion (jumping).
    protected void jumpFromGround() {
        Vector3d deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(deltaMovement.x, this.getJumpPower(), deltaMovement.z);
        this.hasImpulse = true;
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        int randInt = this.random.nextInt(3);
        if (randInt < 2 && this.random.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) ++randInt;

        int j = 1 << randInt;
        this.setSize(j, true);
        return super.finalizeSpawn(world, difficulty, spawnReason, spawnData, dataTag);
    }

    public float getSoundPitch() {
        float size = this.isSmallAmaracameler() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1) * size;
    }

    public SoundEvent getJumpSound() {
        return this.isSmallAmaracameler() ? BMSounds.ENTITY_AMARACAMELER_JUMP_SMALL : BMSounds.ENTITY_AMARACAMELER_JUMP;
    }

    @Nonnull
    public EntitySize getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(0.255F * (float) this.getSize());
    }

    /// Called when the amaracameler spawns particles on landing, see {@link #tick}.
    /// <p>
    /// Return <code>true</code> to prevent the spawning of the default particles.
    protected boolean spawnCustomParticles() {
        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.AMARACAMELER_SPAWN_EGG.get());
    }
}
