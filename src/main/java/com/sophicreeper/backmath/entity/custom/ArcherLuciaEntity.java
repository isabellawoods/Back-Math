package com.sophicreeper.backmath.entity.custom;

import com.google.common.collect.Maps;
import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.aljan.*;
import com.sophicreeper.backmath.entity.custom.termian.TermianMemberEntity;
import com.sophicreeper.backmath.entity.goal.BMRangedCrossbowAttackGoal;
import com.sophicreeper.backmath.entity.misc.BMCrossbowUser;
import com.sophicreeper.backmath.entity.misc.SophieFriendlies;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.item.custom.behavior.BMArmorItem;
import com.sophicreeper.backmath.item.custom.tool.bow.BMCrossbowItem;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.EquipmentTableUtils;
import com.sophicreeper.backmath.util.RVUtils;
import com.sophicreeper.backmath.util.fix.BMTagFixes;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

public class ArcherLuciaEntity extends TermianMemberEntity implements BMCrossbowUser, SophieFriendlies {
    private static final DataParameter<Boolean> IS_CHARGING_CROSSBOW = EntityDataManager.defineId(ArcherLuciaEntity.class, DataSerializers.BOOLEAN);
    private final Inventory inventory = new Inventory(5);

    public ArcherLuciaEntity(EntityType<ArcherLuciaEntity> entity, World world) {
        super(entity, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CHARGING_CROSSBOW, false);
    }

    @Override
    public void applySophieRaidBuffs(int currentWave, boolean spawnedWithRaid) {
        this.enchantSpawnedWeapon(1);
        for (EquipmentSlotType slotType : EquipmentSlotType.values()) {
            if (slotType.getType() == EquipmentSlotType.Group.ARMOR) this.enchantSpawnedArmor(1, slotType);
        }
        ItemStack crossbowStack = new ItemStack(AxolotlTest.ANGELIC_CROSSBOW.get());
        Map<Enchantment, Integer> enchantmentMap = Maps.newHashMap();
        enchantmentMap.put(Enchantments.QUICK_CHARGE, 1);
        enchantmentMap.put(Enchantments.MULTISHOT, 1);
        EnchantmentHelper.setEnchantments(enchantmentMap, crossbowStack);
        this.setItemSlot(EquipmentSlotType.MAINHAND, crossbowStack);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new BMRangedCrossbowAttackGoal<>(this, 1.1D, 12));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.1D, Ingredient.of(BMItemTags.ARCHER_LUCIA_TEMPT_ITEMS), false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AngrySophieEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, livEntity -> livEntity.getType().is(EntityTypeTags.RAIDERS)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, QueenLucyPetEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, JanticleEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, InsomniaZombieEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombieFabricioEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AljamicBonesEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SleepishSkeletonEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AlcalyteEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AmaracamelerEntity.class, true));
    }

    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        ListNBT inventoryNBTList = new ListNBT();

        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) inventoryNBTList.add(RVUtils.saveStack(stack, new CompoundNBT()));
        }
        tag.put("inventory", inventoryNBTList);
        tag.putBoolean("is_charging_crossbow", this.entityData.get(IS_CHARGING_CROSSBOW));
    }

    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        ListNBT inventoryNBTList = BMTagFixes.renameInventory(tag);

        for (int i = 0; i < inventoryNBTList.size(); ++i) {
            ItemStack stack = RVUtils.loadStack(inventoryNBTList.getCompound(i));
            if (!stack.isEmpty()) this.inventory.addItem(stack);
        }
        this.setCanPickUpLoot(true);
        this.entityData.set(IS_CHARGING_CROSSBOW, tag.getBoolean("is_charging_crossbow"));
    }

    @Override
    public void tick() {
        super.tick();
        this.updateEffectHelmet(this, BMItemTags.PROVIDES_WATER_BREATHING, Effects.WATER_BREATHING);
        this.updateEffectHelmet(this, BMItemTags.PROVIDES_RESISTANCE, Effects.DAMAGE_RESISTANCE);
    }

    @Override
    @Nonnull
    public ItemStack getProjectile(ItemStack shootableStack) {
        if (shootableStack.getItem() instanceof ShootableItem) {
            Predicate<ItemStack> predicate = ((ShootableItem) shootableStack.getItem()).getAllSupportedProjectiles();
            ItemStack ammoStack = ShootableItem.getHeldProjectile(this, predicate);
            return ammoStack.isEmpty() ? new ItemStack(Items.ARROW) : ammoStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    @Override
    public void setChargingCrossbow(boolean isCharging) {
        this.entityData.set(IS_CHARGING_CROSSBOW, isCharging);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ArmPose getArmPose() {
        if (this.entityData.get(IS_CHARGING_CROSSBOW)) {
            return ArmPose.CROSSBOW_CHARGE;
        } else if (this.isHolding(item -> item instanceof CrossbowItem)) {
            return ArmPose.CROSSBOW_HOLD;
        } else {
            return this.isAggressive() ? ArmPose.ATTACKING : ArmPose.NEUTRAL;
        }
    }

    @Override
    protected void enchantSpawnedWeapon(float chance) {
        super.enchantSpawnedWeapon(chance);
        if (this.random.nextInt(300) == 0) {
            ItemStack heldStack = this.getMainHandItem();
            if (heldStack.getItem().is(BMItemTags.CROSSBOWS)) {
                Map<Enchantment, Integer> crossbowEnchantsMap = EnchantmentHelper.getEnchantments(heldStack);
                crossbowEnchantsMap.putIfAbsent(Enchantments.PIERCING, 1);
                EnchantmentHelper.setEnchantments(crossbowEnchantsMap, heldStack);
                this.setItemSlot(EquipmentSlotType.MAINHAND, heldStack);
            }
        }
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else return entity.getType().is(BMEntityTypeTags.SOPHIE_ALLIES);
    }

    public static AttributeModifierMap.MutableAttribute createArcherLuciaAttributes() {
        return CreatureEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 25).add(Attributes.ATTACK_KNOCKBACK, 0.25F).add(Attributes.FOLLOW_RANGE, 12).add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.23F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (source == DamageSource.ON_FIRE) {
            return BMSounds.ENTITY_LUCIA_HURT_ON_FIRE;
        } else if (source == DamageSource.DROWN) {
            return BMSounds.ENTITY_LUCIA_HURT_DROWN;
        } else {
            return source == DamageSource.SWEET_BERRY_BUSH ? BMSounds.ENTITY_LUCIA_HURT_BERRY_BUSH : BMSounds.ENTITY_LUCIA_HURT;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return BMSounds.ENTITY_LUCIA_DEATH;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.ARCHER_LUCIA_SPAWN_EGG.get());
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        EquipmentTableUtils.equipWithGear(BMResourceLocations.ARCHER_LUCIA_EQUIPMENT, this);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        return super.finalizeSpawn(world, difficulty, spawnReason, spawnData, dataTag);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean wasRecentlyHit) {
        super.dropCustomDeathLoot(source, lootingLevel, wasRecentlyHit);
        this.inventory.removeAllItems().forEach(this::spawnAtLocation);
    }

    @Override
    public SoundEvent getCelebrationSound() {
        return BMSounds.ENTITY_LUCIA_CELEBRATE;
    }

    /// Why were Back Math mobs leashable? (29-7-23)
    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean canFireProjectileWeapon(ShootableItem shootableItem) {
        return shootableItem.is(BMItemTags.CROSSBOWS);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        this.performCrossbowAttack(this, 1.6F);
    }

    @Override
    public void shootCrossbowProjectile(LivingEntity livEntity, ItemStack stack, ProjectileEntity arrow, float distanceFactor) {
        this.shootCrossbowProjectile(this, livEntity, arrow, distanceFactor, 1.6F);
    }

    public void pickUpItem(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (stack.getItem() instanceof BMArmorItem) {
            super.pickUpItem(itemEntity);
        } else {
            Item item = stack.getItem();
            if (this.wantsItem(item)) {
                this.onItemPickup(itemEntity);
                ItemStack stack1 = this.inventory.addItem(stack);
                if (stack1.isEmpty()) {
                    itemEntity.remove();
                } else {
                    stack.setCount(stack1.getCount());
                }
            }
        }
    }

    private boolean wantsItem(Item item) {
        return item instanceof BMArmorItem || item instanceof BMCrossbowItem || item.is(BMItemTags.ARCHER_LUCIA_CAN_PICKUP);
    }

    public boolean setSlot(int inventorySlot, ItemStack stack) {
        if (super.setSlot(inventorySlot, stack)) {
            return true;
        } else {
            int slotIndex = inventorySlot - 300;
            if (slotIndex >= 0 && slotIndex < this.inventory.getContainerSize()) {
                this.inventory.setItem(slotIndex, stack);
                return true;
            } else {
                return false;
            }
        }
    }
}
