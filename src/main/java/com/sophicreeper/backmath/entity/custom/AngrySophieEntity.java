package com.sophicreeper.backmath.entity.custom;

import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.misc.HasBreasts;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMBreastPhysics;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.EquipmentTableUtils;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AngrySophieEntity extends MonsterEntity implements HasBreasts {
    private static final DataParameter<Float> BUST_SIZE = EntityDataManager.defineId(AngrySophieEntity.class, DataSerializers.FLOAT);
    private BMBreastPhysics breastPhysics;

    public AngrySophieEntity(EntityType<AngrySophieEntity> type, World world) {
        super(type, world);
        this.xpReward = 3 + this.level.random.nextInt(5);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.addAttackTargets();
        super.registerGoals();
    }

    protected void addAttackTargets() {
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, QueenLucyEntity.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, QueenLucyPetEntity.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, WarriorSophieEntity.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WandererSophieEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ArcherLuciaEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, KarateLuciaEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, InsomniaSophieEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ArcherInsomniaSophieEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1D, false));
    }

    public static AttributeModifierMap.MutableAttribute createAngrySophieAttributes() {
        return MonsterEntity.createMonsterAttributes()
                // She had 75 health at first (I think) now it's 45, but I think it still a lot.
                // But now she'll have 28 health, because it was still too much.
                .add(Attributes.MAX_HEALTH, 28).add(Attributes.ATTACK_KNOCKBACK, 0.25F).add(Attributes.FOLLOW_RANGE, 12).add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.23F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BUST_SIZE, 0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("bust_size", this.entityData.get(BUST_SIZE));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setBustSize(tag.getFloat("bust_size"));
    }

    @Override
    public void tick() {
        super.tick();
        this.getBreastPhysics().update(this, this.getBustSize());
    }

    @Override
    protected int getExperienceReward(PlayerEntity player) {
        return 10;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 1.62F;
    }

    @Override
    @Nonnull
    protected SoundEvent getSwimSound() {
        return BMSounds.ENTITY_TERMIAN_SWIM;
    }

    @Override
    @Nonnull
    protected SoundEvent getSwimSplashSound() {
        return BMSounds.ENTITY_TERMIAN_SPLASH;
    }

    @Override
    @Nonnull
    protected SoundEvent getSwimHighSpeedSplashSound() {
        return BMSounds.ENTITY_TERMIAN_SPLASH_HIGH_SPEED;
    }

    @Nonnull
    protected SoundEvent getHurtSound(DamageSource source) {
        if (source == DamageSource.ON_FIRE) {
            return BMSounds.ENTITY_SOPHIE_HURT_ON_FIRE;
        } else if (source == DamageSource.DROWN) {
            return BMSounds.ENTITY_SOPHIE_HURT_DROWN;
        } else {
            return source == DamageSource.SWEET_BERRY_BUSH ? BMSounds.ENTITY_SOPHIE_HURT_BERRY_BUSH : BMSounds.ENTITY_SOPHIE_HURT;
        }
    }

    @Nonnull
    protected SoundEvent getDeathSound() {
        return BMSounds.ENTITY_SOPHIE_DEATH;
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
        if (this.breastPhysics == null) this.breastPhysics = new BMBreastPhysics();
        return this.breastPhysics;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.ANGRY_SOPHIE_SPAWN_EGG.get());
    }

    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(world, difficulty, spawnReason, spawnData, dataTag);
        EquipmentTableUtils.equipWithGear(BMResourceLocations.ANGRY_SOPHIE_EQUIPMENT, this);
        if (this.getItemBySlot(EquipmentSlotType.CHEST).getItem().is(BMItemTags.CRATES)) this.setDropChance(EquipmentSlotType.CHEST, 0.5F);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.setBustSize(this.random.nextFloat());
        return super.finalizeSpawn(world, difficulty, spawnReason, spawnData, dataTag);
    }

    @Override
    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof CreatureEntity) {
            CreatureEntity entity = (CreatureEntity) this.getVehicle();
            this.yBodyRot = entity.yBodyRot;
        }
    }
}
