package com.sophicreeper.backmath.entity.custom;

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
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
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

public class KarateLuciaEntity extends TermianMemberEntity implements TermianFriendlies {
    public KarateLuciaEntity(EntityType<KarateLuciaEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else return entity.getType().is(BMEntityTypeTags.SOPHIE_ALLIES);
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

    @Override
    public void applySophieRaidBuffs(int currentWave, boolean spawnedWithRaid) {
        this.enchantSpawnedWeapon(1);
        for (EquipmentSlotType slotType : EquipmentSlotType.values()) {
            if (slotType.getType() == EquipmentSlotType.Group.ARMOR) this.enchantSpawnedArmor(1, slotType);
        }
    }

    protected void addAttackTargets() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AngrySophieEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, JanticleEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, InsomniaZombieEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombieFabricioEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AljamicBonesEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SleepishSkeletonEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (livEntity) -> livEntity.getType().is(EntityTypeTags.RAIDERS)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AmaracamelerEntity.class, true));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5D, false));
    }

    public static AttributeModifierMap.MutableAttribute createKarateLuciaAttributes() {
        return CreatureEntity.createMobAttributes()
                // Old karate Lucia health was 50.
                // Old new karate Lucia health was 28.
                .add(Attributes.MAX_HEALTH, 20).add(Attributes.FOLLOW_RANGE, 32).add(Attributes.ATTACK_DAMAGE, 6).add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ARMOR, 3);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
        EquipmentTableUtils.equipWithGear(BMResourceLocations.KARATE_LUCIA_EQUIPMENT, this);
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.populateDefaultEquipmentSlots(difficulty);
        return spawnData;
    }

    @Override
    public SoundEvent getCelebrationSound() {
        return BMSounds.ENTITY_LUCIA_CELEBRATE;
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
            return BMSounds.ENTITY_LUCIA_HURT_ON_FIRE;
        } else if (source == DamageSource.DROWN) {
            return BMSounds.ENTITY_LUCIA_HURT_DROWN;
        } else {
            return source == DamageSource.SWEET_BERRY_BUSH ? BMSounds.ENTITY_LUCIA_HURT_BERRY_BUSH : BMSounds.ENTITY_LUCIA_HURT;
        }
    }

    protected SoundEvent getDeathSound() {
        return BMSounds.ENTITY_SOPHIE_DEATH;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.KARATE_LUCIA_SPAWN_EGG.get());
    }
}
