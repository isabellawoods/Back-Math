package com.sophicreeper.backmath.entity.custom;

import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.entity.custom.aljan.*;
import com.sophicreeper.backmath.entity.custom.termian.TermianMemberEntity;
import com.sophicreeper.backmath.entity.misc.TermianFriendlies;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.EquipmentTableUtils;
import com.sophicreeper.backmath.util.fix.TagFixes;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import com.sophicreeper.backmath.variant.wansophie.BMWandererSophieVariants;
import com.sophicreeper.backmath.variant.wansophie.WandererSophieVariant;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class WandererSophieEntity extends TermianMemberEntity implements TermianFriendlies {
    private static final DataParameter<String> VARIANT = EntityDataManager.defineId(WandererSophieEntity.class, DataSerializers.STRING);
    public static final Logger LOGGER = LogManager.getLogger("backmath/WandererSophieEntity");

    public WandererSophieEntity(EntityType<WandererSophieEntity> type, World world) {
        super(type, world);
    }

    public WandererSophieEntity(World world) {
        super(BMEntities.WANDERER_SOPHIE.get(), world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, BMWandererSophieVariants.YELLOW_AXOLOTL.get().getAssetID().toString());
    }

    @Override
    public void applySophieRaidBuffs(int currentWave, boolean spawnedWithRaid) {
        this.enchantSpawnedWeapon(1);
        for (EquipmentSlotType slotType : EquipmentSlotType.values()) {
            if (slotType.getType() == EquipmentSlotType.Group.ARMOR) this.enchantSpawnedArmor(1, slotType);
        }
    }

    @Override
    public SoundEvent getCelebrationSound() {
        return BMSounds.ENTITY_SOPHIE_CELEBRATE;
    }

    @Override
    public void tick() {
        super.tick();
        this.applyArmorEffects(this);
    }

    public void aiStep() {
        this.updateSwingTime();
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) this.heal(1);
        }
        super.aiStep();
    }

    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setVariant(TagFixes.updateWandererSophieVariant(tag));
        tag.remove("Variant");
    }

    public String getVariant() {
        if (WandererSophieVariant.isVariantRegistered(this.entityData.get(VARIANT))) {
            return this.entityData.get(VARIANT);
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.wanderer_sophie_variant.invalid_get", this.entityData.get(VARIANT))).getString());
        }
        return BMWandererSophieVariants.YELLOW_AXOLOTL.get().getAssetID().toString();
    }

    public WandererSophieVariant getRegistryVariant() {
        return WandererSophieVariant.DATA_DRIVEN_VARIANTS.get(new ResourceLocation(this.entityData.get(VARIANT)));
    }

    public void setVariant(WandererSophieVariant variant) {
        if (variant != null && variant.getAssetID() != null) {
            this.entityData.set(VARIANT, variant.getAssetID().toString());
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.wanderer_sophie_variant.invalid_set", "currently logger doesn't work")).getString());
        }
    }

    public void setVariant(ResourceLocation variant) {
        if (variant != null && WandererSophieVariant.DATA_DRIVEN_VARIANTS.containsKey(variant)) {
            this.entityData.set(VARIANT, variant.toString());
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.wanderer_sophie_variant.invalid_set", "currently logger doesn't work")).getString());
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
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.1, Ingredient.of(BMItemTags.WANDERER_SOPHIE_TEMPT_ITEMS), false));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtGoal(this, WandererSophieEntity.class, 6));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.addAttackTargets();
    }

    protected void addAttackTargets() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AngrySophieEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, JanticleEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (livEntity) -> livEntity.getType().is(EntityTypeTags.RAIDERS)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieFabricioEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AljamicBonesEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SleepishSkeletonEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AmaracamelerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createWandererSophieAttributes() {
        // Old wanderer Sophie health was 70.
        // Old new wanderer Sophie health was 35.
        return CreatureEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.ATTACK_KNOCKBACK, 0.25F).add(Attributes.FOLLOW_RANGE, 12)
                .add(Attributes.ATTACK_DAMAGE, 3).add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
        BMUtils.randomizeWandererSophieVariant(this);
        EquipmentTableUtils.equipWithGear(BMResourceLocations.WANDERER_SOPHIE_EQUIPMENT, this);
        if (this.getItemBySlot(EquipmentSlotType.CHEST).getItem().is(BMItemTags.CRATES)) this.setDropChance(EquipmentSlotType.CHEST, 0.5F);
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.populateDefaultEquipmentSlots(difficulty);
        return spawnData;
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

    /// If this mob can be leashed.
    /// <p>
    /// Old Back Math shenanigans.
    /// @return If this mob can be leashed.
    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.WANDERER_SOPHIE_SPAWN_EGG.get());
    }
}
