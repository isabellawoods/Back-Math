package com.sophicreeper.backmath.entity.custom.alcalyte;

import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.config.BMConfigs;
import com.sophicreeper.backmath.entity.misc.HasBreasts;
import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.entity.outfit.OutfitWearer;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMBreastPhysics;
import com.sophicreeper.backmath.misc.AlcalyteFoodData;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlcalyteEntity extends CreatureEntity implements OutfitWearer, HasBreasts {
    private static final DataParameter<Float> BUST_SIZE = EntityDataManager.defineId(AlcalyteEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<String> OUTFIT_TEXTURE = EntityDataManager.defineId(AlcalyteEntity.class, DataSerializers.STRING);
    protected final AlcalyteFoodData foodData = new AlcalyteFoodData();
    private BMBreastPhysics breastPhysics;

    public AlcalyteEntity(EntityType<? extends AlcalyteEntity> type, World world) {
        super(type, world);
        ((GroundPathNavigator) this.getNavigation()).setCanOpenDoors(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        return super.isAlliedTo(entity) || entity.getType().is(BMEntityTypeTags.ALCALYTES);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos) {
        return this.level.getBlockState(pos).is(BMBlocks.POISON_ROSE.get()) ? -5 : super.getWalkTargetValue(pos);
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
    public String getOutfitDefinition() {
        return this.entityData.get(OUTFIT_TEXTURE);
    }

    @Override
    public void setOutfitDefinition(String outfitDefinition) {
        this.entityData.set(OUTFIT_TEXTURE, outfitDefinition);
    }

    @Override
    public boolean isWearingOutfit() {
        String outfitTexture = this.entityData.get(OUTFIT_TEXTURE);
        return !outfitTexture.isEmpty() && OutfitDefinition.DATA_DRIVEN_OUTFITS.containsKey(ResourceLocation.tryParse(outfitTexture));
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

    public boolean isHurt() {
        return this.getHealth() > 0 && this.getHealth() < this.getMaxHealth();
    }

    public boolean canEat(boolean canAlwaysEat) {
        return canAlwaysEat || this.foodData.canEat();
    }

    public boolean shouldEat() {
        return this.foodData.getNutritionLevel() <= 16;
    }

    public void causeExhaustion(float exhaustion) {
        if (!this.level.isClientSide) this.foodData.addExhaustion(exhaustion);
    }

    public AlcalyteFoodData getFoodData() {
        return this.foodData;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT tag) {
        this.setBustSize(this.random.nextFloat());
        return super.finalizeSpawn(world, difficulty, spawnReason, spawnData, tag);
    }

    @Override
    @Nonnull
    public ItemStack eat(World world, ItemStack stack) {
        this.foodData.eat(stack.getItem(), stack);
        world.playSound(null, this.getX(), this.getY(), this.getZ(), BMSounds.ENTITY_ALCALYTE_BURP, this.getSoundSource(), 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        return super.eat(world, stack);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) this.foodData.tick(this);
        ItemStack headStack = this.getItemBySlot(EquipmentSlotType.HEAD);
        boolean acceptableHelmets = headStack.getItem().is(BMItemTags.PROVIDES_WATER_BREATHING);
        if (acceptableHelmets && !this.isEyeInFluid(FluidTags.WATER)) {
            this.addEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0, false, false, true));
        }
        this.getBreastPhysics().update(this, this.getBustSize());
    }

    @Override
    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof CreatureEntity) {
            CreatureEntity entity = (CreatureEntity) this.getVehicle();
            this.yBodyRot = entity.yBodyRot;
        }
    }

    @Override
    public void aiStep() {
        if (this.foodData.canEat() && this.tickCount % 10 == 0) this.foodData.setNutritionLevel(this.foodData.getNutritionLevel() + 1);
        super.aiStep();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OUTFIT_TEXTURE, "");
        this.entityData.define(BUST_SIZE, 0F);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setBustSize(tag.getFloat("bust_size"));
        if (tag.contains("outfit", TagTypes.STRING)) this.entityData.set(OUTFIT_TEXTURE, tag.getString("outfit"));
        this.foodData.readFoodStats(tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("bust_size", this.entityData.get(BUST_SIZE));
        this.foodData.writeFoodStats(tag);
        if (!this.entityData.get(OUTFIT_TEXTURE).isEmpty()) tag.putString("outfit", this.entityData.get(OUTFIT_TEXTURE));
    }

    protected void populateAlcalyteEquipmentSlots() {
        if (this.random.nextFloat() < BMConfigs.COMMON_CONFIGS.alcalyteArmorChance.get()) {
            int rand = this.random.nextInt(2);
            float chancePerDifficulty = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
            if (this.random.nextFloat() < 0.095F) ++rand;
            if (this.random.nextFloat() < 0.095F) ++rand;
            if (this.random.nextFloat() < 0.095F) ++rand;
            boolean populateArmor = true;

            EquipmentSlotType[] armorSlots = new EquipmentSlotType[] {EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
            for (EquipmentSlotType slotType : armorSlots) {
                if (slotType.getType() == EquipmentSlotType.Group.ARMOR) {
                    ItemStack stack = this.getItemBySlot(slotType);
                    if (!populateArmor && this.random.nextFloat() < chancePerDifficulty) break;

                    populateArmor = false;
                    if (stack.isEmpty()) {
                        Item armorItem = getAlcalyteArmorByChance(slotType, rand);
                        Item weaponItem = getSwordByChance(rand);
                        if (armorItem != null) this.setItemSlot(slotType, new ItemStack(armorItem));
                        if (weaponItem != null) this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(weaponItem));
                    }
                }
            }
        }
        else this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(AxolotlTest.ALJANSTONE_KNIFE.get()));
    }

    @Nullable
    public static Item getAlcalyteArmorByChance(EquipmentSlotType slotType, int chance) {
        switch (slotType) {
            case HEAD:
                if (chance == 0) {
                    return AxolotlTest.JANTSKIN_HELMET.get();
                } else if (chance == 1) {
                    return AxolotlTest.ARCHER_FABRICIO_HOOD.get();
                } else if (chance == 2) {
                    return AxolotlTest.ALJAMEED_HELMET.get();
                } else if (chance == 3) {
                    return AxolotlTest.MOONERING_HELMET.get();
                } else if (chance == 4) {
                    return AxolotlTest.JANTIQUIFIED_MOONERING_HELMET.get();
                }
            case CHEST:
                if (chance == 0) {
                    return AxolotlTest.JANTSKIN_CHESTPLATE.get();
                } else if (chance == 1) {
                    return AxolotlTest.ARCHER_FABRICIO_VEST.get();
                } else if (chance == 2) {
                    return AxolotlTest.ALJAMEED_CHESTPLATE.get();
                } else if (chance == 3) {
                    return AxolotlTest.MOONERING_CHESTPLATE.get();
                } else if (chance == 4) {
                    return AxolotlTest.JANTIQUIFIED_MOONERING_CHESTPLATE.get();
                }
            case LEGS:
                if (chance == 0) {
                    return AxolotlTest.JANTSKIN_LEGGINGS.get();
                } else if (chance == 1) {
                    return Items.AIR;
                } else if (chance == 2) {
                    return AxolotlTest.ALJAMEED_LEGGINGS.get();
                } else if (chance == 3) {
                    return AxolotlTest.MOONERING_LEGGINGS.get();
                } else if (chance == 4) {
                    return AxolotlTest.JANTIQUIFIED_MOONERING_LEGGINGS.get();
                }
            case FEET:
                if (chance == 0) {
                    return AxolotlTest.JANTSKIN_BOOTS.get();
                } else if (chance == 1) {
                    return Items.AIR;
                } else if (chance == 2) {
                    return AxolotlTest.ALJAMEED_BOOTS.get();
                } else if (chance == 3) {
                    return AxolotlTest.MOONERING_BOOTS.get();
                } else if (chance == 4) {
                    return AxolotlTest.JANTIQUIFIED_MOONERING_BOOTS.get();
                }
            default: return null;
        }
    }

    public static Item getSwordByChance(int chance) {
        switch (chance) {
            case 0: return AxolotlTest.ALJANWOOD_SWORD.get();
            case 1: return AxolotlTest.ALJANSTONE_SWORD.get();
            case 2: return AxolotlTest.ALJAMEED_BLADE.get();
            case 3: return AxolotlTest.MOONERING_SWORD.get();
            case 4: return AxolotlTest.JANTIQUIFIED_MOONERING_SWORD.get();
            default: return null;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean superRuns = super.hurt(source, amount);
        if (this.level.isClientSide) {
            return false;
        } else {
            if (superRuns && source.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) source.getEntity();
                if (this.canAttack(target) && EntityPredicates.ATTACK_ALLOWED.test(target)) this.setTarget(target);
            }
            return superRuns;
        }
    }

    @Override
    protected void actuallyHurt(DamageSource source, float amount) {
        this.causeExhaustion(source.getFoodExhaustion());
        super.actuallyHurt(source, amount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (source == DamageSource.ON_FIRE) {
            return BMSounds.ENTITY_ALCALYTE_HURT_ON_FIRE;
        } else if (source == DamageSource.DROWN) {
            return BMSounds.ENTITY_ALCALYTE_HURT_DROWN;
        } else {
            return source == DamageSource.SWEET_BERRY_BUSH ? BMSounds.ENTITY_ALCALYTE_HURT_BERRY_BUSH : BMSounds.ENTITY_ALCALYTE_HURT;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return BMSounds.ENTITY_ALCALYTE_DEATH;
    }
}
