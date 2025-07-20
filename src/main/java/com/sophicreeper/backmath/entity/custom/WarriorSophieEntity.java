package com.sophicreeper.backmath.entity.custom;

import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.aljan.InsomniaZombieEntity;
import com.sophicreeper.backmath.entity.custom.aljan.JanticleEntity;
import com.sophicreeper.backmath.entity.custom.aljan.ZombieFabricioEntity;
import com.sophicreeper.backmath.entity.custom.termian.TermianMemberEntity;
import com.sophicreeper.backmath.entity.misc.TermianFriendlies;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.EquipmentTableUtils;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.*;

import javax.annotation.Nullable;

public class WarriorSophieEntity extends TermianMemberEntity implements TermianFriendlies {
    public WarriorSophieEntity(EntityType<WarriorSophieEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1D, false));
        this.goalSelector.addGoal(4, new LookAtGoal(this, QueenLucyEntity.class, 6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.addAttackTargets();
    }

    protected void addAttackTargets() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, JanticleEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (livEntity) -> livEntity.getType().is(EntityTypeTags.RAIDERS)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AngrySophieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieFabricioEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, QueenLucyPetEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, true));
    }

    @Override
    public void applySophieRaidBuffs(int currentWave, boolean spawnedWithRaid) {
        for (EquipmentSlotType slotType : EquipmentSlotType.values()) {
            if (slotType.getType() == EquipmentSlotType.Group.ARMOR) {
                this.enchantSpawnedArmor(0.5F * currentWave, slotType);
            }
        }
    }

    @Override
    public ArmPose getArmPose() {
        if (this.isAggressive()) {
            return ArmPose.ATTACKING;
        } else {
            return this.isCelebrating() ? ArmPose.CELEBRATING : ArmPose.NEUTRAL;
        }
    }

    public static AttributeModifierMap.MutableAttribute createWarriorSophieAttributes() {
        return CreatureEntity.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 6).add(Attributes.MAX_HEALTH, 36).add(Attributes.FOLLOW_RANGE, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    public void tick() {
        super.tick();
        this.applyArmorEffects(this);
    }

    public void aiStep() {
        this.updateSwingTime();
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) {
                this.heal(1);
            }
        }
        super.aiStep();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        if (source == DamageSource.ON_FIRE) {
            return BMSounds.ENTITY_SOPHIE_HURT_ON_FIRE;
        } else if (source == DamageSource.DROWN) {
            return BMSounds.ENTITY_SOPHIE_HURT_DROWN;
        } else {
            return source == DamageSource.SWEET_BERRY_BUSH ? BMSounds.ENTITY_SOPHIE_HURT_BERRY_BUSH : BMSounds.ENTITY_SOPHIE_HURT;
        }
    }

    protected SoundEvent getDeathSound() {
        return BMSounds.ENTITY_SOPHIE_DEATH;
    }

    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else return entity.getType().is(BMEntityTypeTags.SOPHIE_ALLIES);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.WARRIOR_SOPHIE_SPAWN_EGG.get());
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (!super.doHurtTarget(entity)) {
            return false;
        } else {
            if (entity instanceof LivingEntity && !entity.fireImmune()) {
                ItemStack devilSword = new ItemStack(AxolotlTest.DEVIL_SWORD.get());
                if (this.getItemBySlot(EquipmentSlotType.MAINHAND).equals(devilSword)) {
                    LivingEntity livEntity = (LivingEntity) entity;
                    livEntity.setSecondsOnFire(5);
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT dataTag) {
        EquipmentTableUtils.equipWithGear(BMResourceLocations.WARRIOR_SOPHIE_EQUIPMENT, this);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        return super.finalizeSpawn(world, difficulty, reason, entityData, dataTag);
    }

    @Override
    public SoundEvent getCelebrationSound() {
        return BMSounds.ENTITY_SOPHIE_CELEBRATE;
    }
}
