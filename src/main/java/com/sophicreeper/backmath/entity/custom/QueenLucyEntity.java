package com.sophicreeper.backmath.entity.custom;

import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.aljan.*;
import com.sophicreeper.backmath.entity.custom.termian.TermianMemberEntity;
import com.sophicreeper.backmath.entity.goal.termian.queenlucy.*;
import com.sophicreeper.backmath.entity.misc.TermianFriendlies;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.EquipmentTableUtils;
import com.sophicreeper.backmath.util.fix.TagFixes;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.variant.queenlucy.BMQueenLucyVariants;
import com.sophicreeper.backmath.variant.queenlucy.QueenLucyVariant;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public class QueenLucyEntity extends TermianMemberEntity implements TermianFriendlies, IMob {
    private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.NOTCHED_6);
    private static final DataParameter<String> SPELL = EntityDataManager.defineId(QueenLucyEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> VARIANT = EntityDataManager.defineId(QueenLucyEntity.class, DataSerializers.STRING);
    public static final Logger LOGGER = LogManager.getLogger("backmath/QueenLucyEntity");
    public int spellCooldownTicks;
    public CompoundNBT lucySpellsTag;

    public QueenLucyEntity(EntityType<QueenLucyEntity> type, World world) {
        super(type, world);
        this.setHealth(this.getMaxHealth());
        this.getNavigation().setCanFloat(true);
        this.xpReward = 450;
    }

    public QueenLucyEntity(World world) {
        super(BMEntities.QUEEN_LUCY.get(), world);
        this.setHealth(this.getMaxHealth());
        this.getNavigation().setCanFloat(true);
        this.xpReward = 450;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPELL, "none");
        this.entityData.define(VARIANT, BMQueenLucyVariants.CURRENT.get().getAssetID().toString());
    }

    @Override
    public void applySophieRaidBuffs(int currentWave, boolean spawnedWithRaid) {}

    @Override
    public SoundEvent getCelebrationSound() {
        return BMSounds.ENTITY_SOPHIE_CELEBRATE;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setVariant(TagFixes.setQueenLucyVariant(tag));

        CompoundNBT spellsTag = tag.getCompound("lucy_spells");
        if (QueenLucySpells.isValidSpell(spellsTag.getString("current_spell"))) {
            LOGGER.debug(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("console.backmath.queen_lucy.set_spell", QueenLucySpells.setFromString(spellsTag.getString(
                    "current_spell")).getName())).getString());
            this.setSpellType(QueenLucySpells.setFromString(spellsTag.getString("current_spell").toLowerCase(Locale.ROOT)));
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.queen_lucy.invalid_spell", spellsTag.getString("current_spell"))).getString());
            this.setSpellType(QueenLucySpells.NONE);
        }

        this.spellCooldownTicks = TagFixes.moveSpellTicks(tag);
        this.lucySpellsTag = spellsTag;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        CompoundNBT spellsTag = new CompoundNBT();
        tag.putString("variant", this.getVariant());
        spellsTag.putString("current_spell", this.entityData.get(SPELL).toLowerCase(Locale.ROOT));
        spellsTag.putInt("spell_cooldown_ticks", this.spellCooldownTicks);
        tag.put("lucy_spells", spellsTag);
    }

    public String getVariant() {
        if (QueenLucyVariant.DATA_DRIVEN_VARIANTS.containsKey(new ResourceLocation(this.entityData.get(VARIANT)))) {
            return this.entityData.get(VARIANT);
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.queen_lucy_variant.invalid_get", this.entityData.get(VARIANT))).getString());
        }
        return BMQueenLucyVariants.CURRENT.get().getAssetID().toString();
    }

    public QueenLucyVariant getRegistryVariant() {
        return QueenLucyVariant.DATA_DRIVEN_VARIANTS.get(new ResourceLocation(this.entityData.get(VARIANT)));
    }

    public void setVariant(QueenLucyVariant variant) {
        if (variant != null && variant.getAssetID() != null) {
            this.entityData.set(VARIANT, variant.getAssetID().toString());
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.queen_lucy_variant.invalid_set", "something")).getString());
        }

        if (variant != null && variant.getBossbarInformation() !=  null) {
            ServerBossInfo variantInfo = variant.getBossbarInformation();
            this.bossInfo.setVisible(variantInfo.isVisible());
            this.bossInfo.setColor(variantInfo.getColor());
            this.bossInfo.setOverlay(variantInfo.getOverlay());
            this.bossInfo.setDarkenScreen(variantInfo.shouldDarkenScreen());
            this.bossInfo.setPlayBossMusic(variantInfo.shouldPlayBossMusic());
            this.bossInfo.setCreateWorldFog(variantInfo.shouldCreateWorldFog());
            this.bossInfo.setName(variantInfo.getName());
        }
    }

    public boolean isCastingSpell() {
        if (this.level.isClientSide) {
            return !this.entityData.get(SPELL).equals("none");
        } else {
            return this.spellCooldownTicks > 0;
        }
    }

    public void setSpellType(QueenLucySpells spells) {
        this.entityData.set(SPELL, spells.getName());
    }

    @Override
    public ArmPose getArmPose() {
        return isCastingSpell() ? ArmPose.CASTING_SPELL : super.getArmPose();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SummonWarriorSophiesGoal(this));
        this.goalSelector.addGoal(1, new SummonArcherInsomniaSophiesGoal(this));
        this.goalSelector.addGoal(1, new SummonInsomniaSophiesGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.2D));
        this.goalSelector.addGoal(3, new EquipArmorAndHealGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(5, new LookAtGoal(this, WarriorSophieEntity.class, 6));
        this.goalSelector.addGoal(5, new LookAtGoal(this, ArcherInsomniaSophieEntity.class, 6));
        this.goalSelector.addGoal(5, new LookAtGoal(this, InsomniaSophieEntity.class, 6));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.addAttackTargets();
        super.registerGoals();
    }

    protected void addAttackTargets() {
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, QueenLucyPetEntity.class, false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (livEntity) -> livEntity.getType().is(EntityTypeTags.RAIDERS)));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AngrySophieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieFabricioEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AljamicBonesEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SleepishSkeletonEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AmaracamelerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, JanticleEntity.class, true));
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellCooldownTicks > 0) --this.spellCooldownTicks;
        if (this.tickCount % 20 == 0) this.heal(2);
        if (this.tickCount % 30 == 0) this.heal(3);
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void tick() {
        super.tick();
        this.applyArmorEffects(this);
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();
        super.aiStep();
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean addEffect(EffectInstance instance) {
        return instance.getEffect().isBeneficial();
    }

    @Override
    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean wasRecentlyHit) {
        super.dropCustomDeathLoot(source, lootingLevel, wasRecentlyHit);
        ItemEntity staffStack = this.spawnAtLocation(AxolotlTest.QUEEN_LUCY_SUMMONER_STAFF.get());
        if (staffStack != null) staffStack.setExtendedLifetime();
    }

    /// Add the given player to the list of players tracking this entity.
    /// <p>
    /// For instance, a player may track a boss in order to view its associated boss bar.
    @Override
    public void startSeenByPlayer(ServerPlayerEntity player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /// Removes the given player from the list of players tracking this entity.
    /// <p>
    /// See {@link Entity#startSeenByPlayer(ServerPlayerEntity)} for more information on tracking.
    @Override
    public void stopSeenByPlayer(ServerPlayerEntity player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public static AttributeModifierMap.MutableAttribute createQueenLucyAttributes() {
        return CreatureEntity.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 8).add(Attributes.MAX_HEALTH, 250).add(Attributes.FOLLOW_RANGE, 100).add(Attributes.MOVEMENT_SPEED, 0.30F);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT dataTag) {
        EquipmentTableUtils.equipWithGear(BMResourceLocations.QUEEN_LUCY_EQUIPMENT, this);
        this.setVariant(BMUtils.getQueenLucyVariant(this));
        return super.finalizeSpawn(world, difficulty, reason, entityData, dataTag);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (source == DamageSource.ON_FIRE) {
            return BMSounds.ENTITY_SOPHIE_HURT_ON_FIRE;
        } else if (source == DamageSource.DROWN) {
            return BMSounds.ENTITY_SOPHIE_HURT_DROWN;
        } else {
            return source == DamageSource.SWEET_BERRY_BUSH ? BMSounds.ENTITY_SOPHIE_HURT_BERRY_BUSH : BMSounds.ENTITY_SOPHIE_HURT;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return BMSounds.ENTITY_SOPHIE_DEATH;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else return entity.getType().is(BMEntityTypeTags.SOPHIE_ALLIES);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.QUEEN_LUCY_SPAWN_EGG.get());
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

    public SoundEvent getSpellSound() {
        return BMSounds.ENTITY_QUEEN_LUCY_CAST_SPELL;
    }
}
