package com.sophicreeper.backmath.entity.custom;

import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.aljan.*;
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
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InsomniaSophieEntity extends TermianMemberEntity implements TermianFriendlies, IMob {
    public InsomniaSophieEntity(EntityType<InsomniaSophieEntity> type, World world) {
        super(type, world);
        this.xpReward = 3 + this.level.random.nextInt(6);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.6F, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.6F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, QueenLucyPetEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, JanticleEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (livEntity) -> livEntity.getType().is(EntityTypeTags.RAIDERS)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AngrySophieEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieFabricioEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AljamicBonesEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SleepishSkeletonEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WolfEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AmaracamelerEntity.class, true));
    }

    @Override
    public void applySophieRaidBuffs(int currentWave, boolean spawnedWithRaid) {
        this.enchantSpawnedWeapon(1);
        for (EquipmentSlotType slotType : EquipmentSlotType.values()) {
            if (slotType.getType() == EquipmentSlotType.Group.ARMOR) this.enchantSpawnedArmor(1, slotType);
        }
    }

    public static AttributeModifierMap.MutableAttribute createInsomniaSophieAttributes() {
        return MonsterEntity.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.MAX_HEALTH, 28).add(Attributes.FOLLOW_RANGE, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.23F);
    }

    @Override
    public void tick() {
        super.tick();
        this.applyArmorEffects(this);
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();
        this.updateNoActionTime();
        super.aiStep();
    }

    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else return entity.getType().is(BMEntityTypeTags.SOPHIE_ALLIES);
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

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.INSOMNIA_SOPHIE_SPAWN_EGG.get());
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        EquipmentTableUtils.equipWithGear(BMResourceLocations.INSOMNIA_SOPHIE_EQUIPMENT, this);
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.populateDefaultEquipmentSlots(difficulty);
        return spawnData;
    }

    @Override
    public SoundEvent getCelebrationSound() {
        return BMSounds.ENTITY_SOPHIE_CELEBRATE;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (!super.doHurtTarget(entity)) {
            return false;
        } else {
            if (entity instanceof LivingEntity && !(entity instanceof InsomniaSophieEntity || entity instanceof ArcherInsomniaSophieEntity)) {
                ((LivingEntity) entity).addEffect(new EffectInstance(Effects.POISON, 200));
                ((LivingEntity) entity).addEffect(new EffectInstance(Effects.BLINDNESS, 600));
            }
            return true;
        }
    }

    @Override
    @Nonnull
    public SoundCategory getSoundSource() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected boolean shouldDropExperience() {
        return true;
    }

    @Override
    protected boolean shouldDropLoot() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && super.hurt(source, amount);
    }
}
