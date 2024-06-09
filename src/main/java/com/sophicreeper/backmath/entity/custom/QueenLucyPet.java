package com.sophicreeper.backmath.entity.custom;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.goal.QLPOwnersTargetGoal;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.misc.BMRegistries;
import com.sophicreeper.backmath.variant.queenlucypet.BMQueenLucyPetVariants;
import com.sophicreeper.backmath.variant.queenlucypet.QueenLucyPetVariant;
import com.sophicreeper.backmath.util.BMTags;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.fix.BMTagFixes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class QueenLucyPet extends TameableEntity {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final DataParameter<String> VARIANT = EntityDataManager.defineId(QueenLucyPet.class, DataSerializers.STRING);
    public static final Predicate<LivingEntity> NON_TAMED_TARGETS = livEntity -> livEntity.getType().is(BMTags.EntityTypes.QLP_TARGETS_NOT_TAMED);

    public QueenLucyPet(EntityType<QueenLucyPet> type, World world) {
        super(type, world);
        this.moveControl = new FlyingMovementController(this, 10, false);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1);
        this.setPathfindingMalus(PathNodeType.COCOA, -1);
        this.setTame(false);
    }

    public boolean isBaby() {
        return false;
    }

    public void aiStep() {
        this.updateSwingTime();
        if (this.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) {
                this.heal(1);
            }
        }
        super.aiStep();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2F, Ingredient.of(BMTags.Items.QUEEN_LUCY_PET_TEMPT_ITEMS), false));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2.1F, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.2F, 10, 2, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(6, new LookAtGoal(this, QueenLucyPet.class, 8));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new QLPOwnersTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class ,10, false, false, (livEntity) -> livEntity.getType().is(BMTags.EntityTypes.QLP_TARGETS_TAMED)));
        this.targetSelector.addGoal(3, new NonTamedTargetGoal<>(this, LivingEntity.class, false, NON_TAMED_TARGETS));
        super.registerGoals();
    }

    private static ITextComponent removeAction(ITextComponent name) {
        IFormattableTextComponent textComponent = name.copy().setStyle(name.getStyle().withClickEvent(null));

        for(ITextComponent component : name.getSiblings()) {
            textComponent.append(removeAction(component));
        }

        return textComponent;
    }

    @Override
    public ITextComponent getName() {
        return this.getCustomName() != null ? removeAction(this.getCustomName()) : this.getRegistryVariant().getDisplayName();
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        BMUtils.setRandomQLPRegistryBasedVariant(this);
        if (spawnData == null) spawnData = new AgeableEntity.AgeableData(false);

        return super.finalizeSpawn(world, difficulty, spawnReason, spawnData, dataTag);
    }

    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof CreatureEntity) {
            CreatureEntity entity = (CreatureEntity) this.getVehicle();
            this.yBodyRot = entity.yBodyRot;
        }
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (!this.isTame() && handStack.getItem().is(BMTags.Items.QUEEN_LUCY_PET_TAME_ITEMS)) {
            if (!player.abilities.instabuild) handStack.shrink(1);

            if (!this.level.isClientSide) {
                if (this.random.nextInt(10) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }
            }
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else if (handStack.getItem().is(BMTags.Items.QUEEN_LUCY_PET_POISONOUS_ITEMS)) {
            if (!player.abilities.instabuild) handStack.shrink(1);

            this.addEffect(new EffectInstance(Effects.POISON, 900));
            if (player.isCreative() || !this.isInvulnerable()) this.hurt(DamageSource.playerAttack(player), Float.MAX_VALUE);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else if (!this.isFlying() && this.isTame() && this.isOwnedBy(player)) {
            if (!this.level.isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
                displaySittingMessage(player, this.isOrderedToSit());
            }

            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    public void displaySittingMessage(PlayerEntity player, boolean isSitting) {
        if (isSitting && player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            serverPlayer.sendMessage(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".queen_lucy_pet.sit_down", this.getName()), ChatType.GAME_INFO, Util.NIL_UUID);
        } else if (!isSitting && player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            serverPlayer.sendMessage(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".queen_lucy_pet.stand_up", this.getName()), ChatType.GAME_INFO, Util.NIL_UUID);
        }
    }

    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingPathNavigator = new FlyingPathNavigator(this, world);
        flyingPathNavigator.setCanOpenDoors(true);
        flyingPathNavigator.setCanFloat(true);
        flyingPathNavigator.setCanPassDoors(true);
        return flyingPathNavigator;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return 0.81F;
    }

    public boolean canMate(AnimalEntity otherQLP) {
        return false;
    }

    public static AttributeModifierMap.MutableAttribute createQueenLucyPetAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 350).add(Attributes.ATTACK_DAMAGE, 17).add(Attributes.FLYING_SPEED, 0.4F);
    }

    @Nullable
    @Override
    public QueenLucyPet getBreedOffspring(ServerWorld world, AgeableEntity ageableEntity) {
        return null;
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
        return new ItemStack(AxolotlTest.QUEEN_LUCY_SUMMONER_STAFF.get());
    }

    public String getVariant() {
        if (BMRegistries.QUEEN_LUCY_PET_VARIANT.containsKey(ResourceLocation.tryParse(this.entityData.get(VARIANT)))) {
            return this.entityData.get(VARIANT);
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.queen_lucy_pet_variant.invalid_get", this.entityData.get(VARIANT))).getString());
        }
        return BMQueenLucyPetVariants.CURRENT.get().getRegistryName().toString();
    }

    public QueenLucyPetVariant getRegistryVariant() {
        return BMRegistries.QUEEN_LUCY_PET_VARIANT.getValue(ResourceLocation.tryParse(this.entityData.get(VARIANT)));
    }

    public void setVariant(QueenLucyPetVariant variant) {
        if (BMRegistries.QUEEN_LUCY_PET_VARIANT.containsValue(variant)) {
            this.entityData.set(VARIANT, variant.getRegistryName().toString());
        } else {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.queen_lucy_pet_variant.invalid_set", variant.getRegistryName().toString())).getString());
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, BMQueenLucyPetVariants.CURRENT.get().getRegistryName().toString());
    }

    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setVariant(BMTagFixes.fixQueenLucyPetVariantTag(tag));
        tag.remove("Variant");
    }

    public boolean isFlying() {
        return !this.onGround;
    }
}
