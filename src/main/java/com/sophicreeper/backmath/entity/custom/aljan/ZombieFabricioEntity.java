package com.sophicreeper.backmath.entity.custom.aljan;

import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.entity.custom.*;
import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.alcalyte.ShyAlcalyteEntity;
import com.sophicreeper.backmath.entity.goal.StompTurtleEggGoal;
import com.sophicreeper.backmath.entity.goal.ZombieAttackGoal;
import com.sophicreeper.backmath.entity.misc.ZombieGroupData;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.fix.TagFixes;
import com.sophicreeper.backmath.util.tag.BMBlockTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ZombieFabricioEntity extends MonsterEntity implements AljanMobUtils {
    private static final AttributeModifier BABY_ZOMBIE_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836"), "Baby Zombie Fabricio Speed Bonus", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final DataParameter<Boolean> IS_BABY = EntityDataManager.defineId(ZombieFabricioEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_CONVERTING_TO_DROWNED = EntityDataManager.defineId(ZombieFabricioEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_CONVERTING_TO_SHY_ALCALYTE = EntityDataManager.defineId(ZombieFabricioEntity.class, DataSerializers.BOOLEAN);
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, difficulty -> difficulty == Difficulty.HARD);
    private UUID converterUUID;
    private boolean canBreakDoors;
    private int shyAlcalyteConversionTicks;
    private int ticksSubmergedInWater;
    private int drownedConversionTicks;

    public ZombieFabricioEntity(EntityType<ZombieFabricioEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1, false));
        this.goalSelector.addGoal(4, new StompTurtleEggGoal(this, 1, 3));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.addAttackTargets();
    }

    protected void addAttackTargets() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MalaikaEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WandererSophieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WarriorSophieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaSophieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ArcherInsomniaSophieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ArcherLuciaEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, KarateLuciaEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, QueenLucyEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createZombieFabricioAttributes() {
        return MonsterEntity.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35).add(Attributes.MOVEMENT_SPEED, 0.23F).add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ARMOR, 2).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_BABY, false);
        this.entityData.define(IS_CONVERTING_TO_DROWNED, false);
        this.entityData.define(IS_CONVERTING_TO_SHY_ALCALYTE, false);
    }

    public boolean isConvertingToDrowned() {
        return this.entityData.get(IS_CONVERTING_TO_DROWNED);
    }

    /// Sets or removes the {@link BreakDoorGoal} task.
    public void setBreakDoorsAITask(boolean enabled) {
        if (this.supportsBreakDoorGoal() && GroundPathHelper.hasGroundPathNavigation(this)) {
            if (this.canBreakDoors != enabled) {
                this.canBreakDoors = enabled;
                ((GroundPathNavigator) this.getNavigation()).setCanOpenDoors(enabled);
                if (enabled) {
                    this.goalSelector.addGoal(1, this.breakDoorGoal);
                } else {
                    this.goalSelector.removeGoal(this.breakDoorGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.removeGoal(this.breakDoorGoal);
            this.canBreakDoors = false;
        }
    }

    protected boolean supportsBreakDoorGoal() {
        return true;
    }

    @Override
    public boolean isBaby() {
        return this.entityData.get(IS_BABY);
    }

    /// Get the experience points the entity currently has.
    protected int getExperienceReward(PlayerEntity player) {
        if (this.isBaby()) this.xpReward = (int) ((float) this.xpReward * 2.5F);
        return super.getExperienceReward(player);
    }

    @Override
    public void setBaby(boolean baby) {
        this.entityData.set(IS_BABY, baby);
        if (this.level != null && !this.level.isClientSide) {
            ModifiableAttributeInstance speedAttribute = this.getAttribute(Attributes.MOVEMENT_SPEED);
            speedAttribute.removeModifier(BABY_ZOMBIE_SPEED_MODIFIER);
            if (baby) speedAttribute.addTransientModifier(BABY_ZOMBIE_SPEED_MODIFIER);
        }
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> parameter) {
        if (IS_BABY.equals(parameter)) this.refreshDimensions();
        super.onSyncedDataUpdated(parameter);
    }

    protected boolean convertsInWater() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isConvertingToShyAlcalyte();
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isConvertingToDrowned()) {
                --this.drownedConversionTicks;

                if (this.drownedConversionTicks < 0 && ForgeEventFactory.canLivingConvert(this, EntityType.DROWNED, timer -> this.drownedConversionTicks = timer)) {
                    this.convertToDrowned();
                }
            } else if (this.isConvertingToShyAlcalyte()) {
                int conversionProgress = this.getConversionToShyAlcalyteProgress();
                this.shyAlcalyteConversionTicks -= conversionProgress;
                if (this.shyAlcalyteConversionTicks <= 0 && ForgeEventFactory.canLivingConvert(this, BMEntities.SHY_ALCALYTE.get(), timer -> this.shyAlcalyteConversionTicks = timer)) {
                    this.convertToShyAlcalyte();
                }
            } else if (this.convertsInWater()) {
                if (this.isEyeInFluid(FluidTags.WATER)) {
                    ++this.ticksSubmergedInWater;
                    if (this.ticksSubmergedInWater >= 600) {
                        this.startConversionToDrowned(300);
                    }
                } else {
                    this.ticksSubmergedInWater = -1;
                }
            }
        }
        super.tick();
    }

    /// Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn.
    public void aiStep() {
        if (this.isAlive()) {
            boolean shouldBurn = this.shouldBurnInDay() && this.isSunBurnTick();
            if (shouldBurn) {
                ItemStack headStack = this.getItemBySlot(EquipmentSlotType.HEAD);
                if (!headStack.isEmpty()) {
                    if (headStack.isDamageableItem()) {
                        headStack.setDamageValue(headStack.getDamageValue() + this.random.nextInt(2));
                        if (headStack.getDamageValue() >= headStack.getMaxDamage()) {
                            this.broadcastBreakEvent(EquipmentSlotType.HEAD);
                            this.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                        }
                    }
                    shouldBurn = false;
                }

                if (shouldBurn) this.setSecondsOnFire(8);
            }
        }
        super.aiStep();
    }

    @Nonnull
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (handStack.getItem().is(BMItemTags.CAN_CURE_ZOMBIE_FABRICIOS)) {
            if (this.hasEffect(Effects.BLINDNESS)) {
                if (!player.abilities.instabuild) handStack.shrink(1);

                if (!this.level.isClientSide) this.startConversionToShyAlcalyte(player.getUUID(), this.random.nextInt(2401) + 3600);
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.CONSUME;
            }
        } else {
            return super.mobInteract(player, hand);
        }
    }

    private void startConversionToDrowned(int conversionTicks) {
        this.drownedConversionTicks = conversionTicks;
        this.getEntityData().set(IS_CONVERTING_TO_DROWNED, true);
    }

    protected void convertToDrowned() {
        if (!this.isSilent()) this.level.levelEvent(null, Constants.WorldEvents.ZOMBIE_CONVERT_TO_DROWNED_SOUND, this.blockPosition(), 0);
        DrownedEntity drowned = this.convertTo(EntityType.DROWNED, true);
        if (drowned != null) ForgeEventFactory.onLivingConvert(this, EntityType.DROWNED.create(this.level));
    }

    protected void convertToShyAlcalyte() {
        ShyAlcalyteEntity shyAlcalyte = this.convertTo(BMEntities.SHY_ALCALYTE.get(), true);
        if (shyAlcalyte != null) {
            shyAlcalyte.addEffect(new EffectInstance(Effects.CONFUSION, 200, 0));
            if (!this.isSilent()) this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), BMSounds.ENTITY_ZOMBIE_FABRICIO_CURE, this.getSoundSource(), 2, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1, false);
            ForgeEventFactory.onLivingConvert(this, BMEntities.SHY_ALCALYTE.get().create(this.level));
        }
    }

    public boolean isConvertingToShyAlcalyte() {
        return this.getEntityData().get(IS_CONVERTING_TO_SHY_ALCALYTE);
    }

    public void startConversionToShyAlcalyte(@Nullable UUID playerUUID, int conversionTicks) {
        this.converterUUID = playerUUID;
        this.shyAlcalyteConversionTicks = conversionTicks;
        this.entityData.set(IS_CONVERTING_TO_SHY_ALCALYTE, true);
        this.removeEffect(Effects.BLINDNESS);
        this.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, conversionTicks, Math.min(this.level.getDifficulty().getId() - 1, 0)));
        this.level.broadcastEntityEvent(this, (byte) 16);
    }

    @Override
    public void handleEntityEvent(byte eventID) {
        if (eventID == 16) {
            if (!this.isSilent()) this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), BMSounds.ENTITY_ZOMBIE_FABRICIO_CURE, this.getSoundSource(), 1 + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
        } else {
            super.handleEntityEvent(eventID);
        }
    }

    private int getConversionToShyAlcalyteProgress() {
        int progress = 1;
        if (this.random.nextFloat() < 0.01F) {
            int area = 0;
            BlockPos.Mutable mutablePos = new BlockPos.Mutable();

            for (int i = (int) this.getX() - 4; i < (int) this.getX() + 4 && area < 14; ++i) {
                for (int l = (int) this.getY() - 4; l < (int) this.getY() + 4 && area < 14; ++l) {
                    for (int k = (int) this.getZ() - 4; k < (int) this.getZ() + 4 && area < 14; ++k) {
                        Block block = this.level.getBlockState(mutablePos.set(k, l, k)).getBlock();
                        if (block.is(BMBlockTags.HELPS_ON_ZOMBIE_CONVERSION)) {
                            if (this.random.nextFloat() < 0.3F) ++progress;
                            ++area;
                        }
                    }
                }
            }
        }
        return progress;
    }

    protected boolean shouldBurnInDay() {
        return true;
    }

    public boolean doHurtTarget(Entity entity) {
        boolean hurtTarget = super.doHurtTarget(entity);
        if (hurtTarget) {
            float localDifficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getSpecialMultiplier();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < localDifficulty * 0.3F) {
                entity.setSecondsOnFire(2 * (int) localDifficulty);
            }
        }
        return hurtTarget;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Nonnull
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Nonnull
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1);
    }

    @Nonnull
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.ZOMBIE_FABRICIO_SPAWN_EGG.get());
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        this.populateAljanEquipmentSlots(this, this.random, difficulty);
        /*if (this.level.getDifficulty() == Difficulty.HARD) {
            EquipmentTableUtils.equipWithGear(BMResourceLocations.ZOMBIE_WEAPONS_HARD_DIFFICULTY, this);
        } else {
            EquipmentTableUtils.equipWithGear(BMResourceLocations.ZOMBIE_WEAPONS_BELOW_HARD_DIFFICULTY, this);
        }*/
    }

    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("is_baby", this.isBaby());
        tag.putBoolean("can_break_doors", this.canBreakDoors());
        tag.putInt("ticks_submerged_in_water", this.isInWater() ? this.ticksSubmergedInWater : -1);
        tag.putInt("drowned_conversion_ticks", this.isConvertingToDrowned() ? this.drownedConversionTicks : -1);
        tag.putInt("fabricio_conversion_ticks", this.isConvertingToShyAlcalyte() ? this.shyAlcalyteConversionTicks : -1);
        if (this.converterUUID != null) tag.putUUID("converter_uuid", this.converterUUID);
    }

    /// (abstract) Protected helper method to read subclass entity data from NBT.
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setBaby(tag.getBoolean("is_baby"));
        this.setBreakDoorsAITask(TagFixes.renameCanBreakDoors(tag));
        this.ticksSubmergedInWater = tag.getInt("ticks_submerged_in_water");
        if (tag.contains("converter_uuid", TagTypes.INTEGER_ARRAY)) this.converterUUID = tag.getUUID("converter_uuid");
        if (tag.contains("drowned_conversion_ticks", TagTypes.ANY_NUMERIC) && tag.getInt("drowned_conversion_ticks") > -1) {
            this.startConversionToDrowned(tag.getInt("drowned_conversion_ticks"));
        }
        TagFixes.renameFabricioConversionTicks(tag, this);
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isBaby() ? 0.93F : 1.74F;
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(world, difficulty, spawnReason, spawnData, dataTag);
        float regionalMultiplier = difficulty.getSpecialMultiplier();
        this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * regionalMultiplier);
        if (spawnData == null) spawnData = new ZombieGroupData(getBabySpawnOdds(world.getRandom()), true);

        if (spawnData instanceof ZombieGroupData) {
            ZombieGroupData groupData = (ZombieGroupData) spawnData;
            if (groupData.isBaby) {
                this.setBaby(true);
                if (groupData.canSpawnJockey) {
                    if ((double) world.getRandom().nextFloat() < 0.05D) {
                        List<ChickenEntity> nearbyChickens = world.getEntitiesOfClass(ChickenEntity.class, this.getBoundingBox().inflate(5, 3, 5), EntityPredicates.ENTITY_NOT_BEING_RIDDEN);
                        if (!nearbyChickens.isEmpty()) {
                            ChickenEntity nearbyChicken = nearbyChickens.get(0);
                            nearbyChicken.setChickenJockey(true);
                            this.startRiding(nearbyChicken);
                        }
                    } else if ((double) world.getRandom().nextFloat() < 0.05D) {
                        ChickenEntity chicken = EntityType.CHICKEN.create(this.level);
                        assert chicken != null;
                        chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0);
                        chicken.finalizeSpawn(world, difficulty, SpawnReason.JOCKEY, null, null);
                        chicken.setChickenJockey(true);
                        this.startRiding(chicken);
                        world.addFreshEntity(chicken);
                    }
                }
            }

            this.setBreakDoorsAITask(this.supportsBreakDoorGoal() && this.random.nextFloat() < regionalMultiplier * 0.1F);
            this.populateDefaultEquipmentSlots(difficulty);
            this.populateDefaultEquipmentEnchantments(difficulty);
        }

        if (this.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
            LocalDate localDate = LocalDate.now();
            int dayOfMonth = localDate.getDayOfMonth();
            int monthOfYear = localDate.getMonth().getValue();
            if (monthOfYear == 10 && dayOfMonth == 31 && this.random.nextFloat() < 0.25F) {
                this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.05F;
            }
        }

        this.applyAttributeBonuses(regionalMultiplier);
        return spawnData;
    }

    public static boolean getBabySpawnOdds(Random rand) {
        return rand.nextFloat() < ForgeConfig.SERVER.zombieBabyChance.get();
    }

    protected void applyAttributeBonuses(float regionalMultiplier) {
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random Knockback Res. Bonus", this.random.nextDouble() * (double) 0.05F, AttributeModifier.Operation.ADDITION));
        double amount = this.random.nextDouble() * 1.5D * (double) regionalMultiplier;
        if (amount > 1) {
            this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random Follow Range Bonus", amount, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }

        if (this.random.nextFloat() < regionalMultiplier * 0.05F) {
            this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Leader Zombie Fabricio Bonus", this.random.nextDouble() * 0.25D + 0.5D, AttributeModifier.Operation.ADDITION));
            this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Leader Zombie Fabricio Bonus", this.random.nextDouble() * 3 + 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
            this.setBreakDoorsAITask(this.supportsBreakDoorGoal());
        }
    }

    /// Returns the Y Offset of this entity.
    public double getMyRidingOffset() {
        return this.isBaby() ? 0 : -0.45D;
    }
}
