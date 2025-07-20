package com.sophicreeper.backmath.entity.custom;

import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.aljan.*;
import com.sophicreeper.backmath.entity.custom.termian.TermianMemberEntity;
import com.sophicreeper.backmath.entity.goal.BMRangedBowAttackGoal;
import com.sophicreeper.backmath.entity.misc.TermianFriendlies;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.item.custom.tool.bow.BMBowItem;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.EquipmentTableUtils;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArcherInsomniaSophieEntity extends TermianMemberEntity implements IRangedAttackMob, TermianFriendlies, IMob {
    private final BMRangedBowAttackGoal<ArcherInsomniaSophieEntity> aiArrowAttack = new BMRangedBowAttackGoal<>(this, 1, 20, 15);
    private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
        public void stop() {
            super.stop();
            ArcherInsomniaSophieEntity.this.setAggressive(false);
        }

        public void start() {
            super.start();
            ArcherInsomniaSophieEntity.this.setAggressive(true);
        }
    };

    public ArcherInsomniaSophieEntity(EntityType<ArcherInsomniaSophieEntity> type, World world) {
        super(type, world);
        this.reassessWeaponGoal();
        this.xpReward = 3 + this.level.random.nextInt(6);
    }

    @Override
    public void tick() {
        super.tick();
        this.applyArmorEffects(this);
    }

    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof CreatureEntity) {
            CreatureEntity entity = (CreatureEntity) this.getVehicle();
            this.yBodyRot = entity.yBodyRot;
        }
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
        return new ItemStack(AxolotlTest.ARCHER_INSOMNIA_SOPHIE_SPAWN_EGG.get());
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(world, difficulty, spawnReason, spawnData, dataTag);
        EquipmentTableUtils.equipWithGear(BMResourceLocations.ARCHER_INSOMNIA_SOPHIE_EQUIPMENT, this);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.reassessWeaponGoal();
        return spawnData;
    }

    @Override
    public SoundEvent getCelebrationSound() {
        return BMSounds.ENTITY_SOPHIE_CELEBRATE;
    }

    public void reassessWeaponGoal() {
        if (this.level != null && !this.level.isClientSide) {
            this.goalSelector.removeGoal(this.aiAttackOnCollide);
            this.goalSelector.removeGoal(this.aiArrowAttack);
            ItemStack bowStack = this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, item -> item.is(BMItemTags.BOWS)));
            if (bowStack.getItem() instanceof BMBowItem) {
                int attackCooldown = 20;
                if (this.level.getDifficulty() != Difficulty.HARD) {
                    attackCooldown = 40;
                }

                this.aiArrowAttack.setAttackCooldown(attackCooldown);
                this.goalSelector.addGoal(4, this.aiArrowAttack);
            } else {
                this.goalSelector.addGoal(4, this.aiAttackOnCollide);
            }
        }
    }

    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else return entity.getType().is(BMEntityTypeTags.SOPHIE_ALLIES);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BMRangedBowAttackGoal<>(this, 1.1D, 8, 8));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AngrySophieEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (livEntity) -> livEntity.getType().is(EntityTypeTags.RAIDERS)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, JanticleEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, QueenLucyPetEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieFabricioEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AljamicBonesEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SleepishSkeletonEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AmaracamelerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, true));
    }

    @Override
    public void applySophieRaidBuffs(int currentWave, boolean spawnedWithRaid) {
        this.enchantSpawnedWeapon(1);
        for (EquipmentSlotType slotType : EquipmentSlotType.values()) {
            if (slotType.getType() == EquipmentSlotType.Group.ARMOR) this.enchantSpawnedArmor(1, slotType);
        }
    }

    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack ammoStack = this.getProjectile(this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, item -> item.is(BMItemTags.BOWS))));
        AbstractArrowEntity arrow = this.fireArrow(ammoStack, distanceFactor);

        if (this.getMainHandItem().getItem() instanceof BMBowItem) arrow = ((BMBowItem) this.getMainHandItem().getItem()).customArrow(arrow);

        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - arrow.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        arrow.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level.getDifficulty().getId() * 4));

        this.playSound(BMSounds.ENTITY_SOPHIE_SHOOT, 1, 1 / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(arrow);
    }

    protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
        return ProjectileHelper.getMobArrow(this, arrowStack, distanceFactor);
    }

    public boolean canFireProjectileWeapon(ShootableItem shootableItem) {
        return shootableItem.is(BMItemTags.BOWS);
    }

    public static AttributeModifierMap.MutableAttribute createArcherInsomniaSophieAttributes() {
        return MonsterEntity.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.MAX_HEALTH, 28).add(Attributes.FOLLOW_RANGE, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.23F);
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
