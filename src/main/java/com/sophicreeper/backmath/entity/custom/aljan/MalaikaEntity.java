package com.sophicreeper.backmath.entity.custom.aljan;

import com.sophicreeper.backmath.entity.misc.TermianFriendlies;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.tag.BMBlockTags;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.*;

import javax.annotation.Nullable;
import java.util.Random;

public class MalaikaEntity extends MonsterEntity implements TermianFriendlies {
    public MalaikaEntity(EntityType<MalaikaEntity> entity, World world) {
        super(entity, world);
        this.xpReward = 50;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(3, new LookAtGoal(this, MalaikaEntity.class, 6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.entityAttackTargets();
        super.registerGoals();
    }

    protected void entityAttackTargets() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (livEntity) -> livEntity.getType().is(BMEntityTypeTags.MALAIKA_TARGETS)));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, true));
    }

    public static AttributeModifierMap.MutableAttribute createMalaikaAttributes() {
        return CreatureEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 40).add(Attributes.MOVEMENT_SPEED, 0.23F).add(Attributes.ATTACK_KNOCKBACK, 1.25F)
                .add(Attributes.FOLLOW_RANGE, 16).add(Attributes.ATTACK_DAMAGE, 5).add(Attributes.FOLLOW_RANGE, 10).add(Attributes.ARMOR, 5);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 2.1F;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public void tick() {
        super.tick();
        ItemStack headStack = this.getItemBySlot(EquipmentSlotType.HEAD);
        boolean acceptableHelmets = headStack.getItem().is(BMItemTags.PROVIDES_WATER_BREATHING);

        if (acceptableHelmets && !this.isEyeInFluid(FluidTags.WATER)) {
            this.addEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0, false, false, true));
        }
    }

    public void aiStep() {
        this.updateSwingTime();
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) this.heal(1);
        }
        super.aiStep();
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        return spawnData;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        if (source == DamageSource.ON_FIRE) {
            return BMSounds.ENTITY_MALAIKA_HURT_ON_FIRE;
        } else if (source == DamageSource.DROWN) {
            return BMSounds.ENTITY_MALAIKA_HURT_DROWN;
        } else {
            return source == DamageSource.SWEET_BERRY_BUSH ? BMSounds.ENTITY_MALAIKA_HURT_BERRY_BUSH : BMSounds.ENTITY_MALAIKA_HURT;
        }
    }

    protected SoundEvent getDeathSound() {
        return BMSounds.ENTITY_MALAIKA_DEATH;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.MALAIKA_SPAWN_EGG.get());
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
//        this.populateAljanEquipmentSlots(difficulty);
//        EquipmentTableUtils.equipWithGear(BMResourceLocations.MALAIKA_EQUIPMENT, this);
    }

    public static boolean checkMalaikaSpawnRules(EntityType<MalaikaEntity> malaika, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getBlockState(pos.below()).is(BMBlockTags.MALAIKA_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8;
    }
}
