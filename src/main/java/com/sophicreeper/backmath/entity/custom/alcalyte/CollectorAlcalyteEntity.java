package com.sophicreeper.backmath.entity.custom.alcalyte;

import com.sophicreeper.backmath.entity.goal.ExtendedMeleeAttackGoal;
import com.sophicreeper.backmath.entity.goal.PickupWantedItemsGoal;
import com.sophicreeper.backmath.entity.goal.alcalyte.HarvestCropsGoal;
import com.sophicreeper.backmath.entity.misc.HasBust;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMBreastPhysics;
import com.sophicreeper.backmath.util.RVUtils;
import com.sophicreeper.backmath.util.fix.BMTagFixes;
import com.sophicreeper.backmath.util.tag.BMBlockTags;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CollectorAlcalyteEntity extends GroupAlcalyteEntity implements HasBust {
    private static final DataParameter<Float> BUST_SIZE = EntityDataManager.defineId(CollectorAlcalyteEntity.class, DataSerializers.FLOAT);
    private final Inventory inventory = new Inventory(36);
    private int eatingCooldown = 0;

    public CollectorAlcalyteEntity(EntityType<CollectorAlcalyteEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        // this.goalSelector.addGoal(1, new ConsumeItemGoal<>(this, SoundEvents.GENERIC_EAT, AlcalyteEntity::shouldEat));
        this.goalSelector.addGoal(1, new PickupWantedItemsGoal(this, 16, stack -> stack.getItem().is(BMItemTags.COLLECTOR_ALCALYTES_CAN_PICKUP)));
        this.goalSelector.addGoal(1, new HarvestCropsGoal(this, state -> state.getBlock().is(BMBlockTags.ALCALYTES_CAN_HARVEST)));
        this.goalSelector.addGoal(2, new ExtendedMeleeAttackGoal(this, 1.4, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtGoal(this, MobEntity.class, 6));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, livEntity -> livEntity.getType().is(BMEntityTypeTags.ALCALYTE_TARGETS)));
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public boolean isInventoryFull(ItemStack stack) {
        return this.inventory.canAddItem(stack);
    }

    public void addToInventory(ItemStack stack) {
        this.inventory.addItem(stack);
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
        return new BMBreastPhysics();
    }

    public static AttributeModifierMap.MutableAttribute createCollectorAlcalyteAttributes() {
        return CreatureEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MOVEMENT_SPEED, 0.23F).add(Attributes.ATTACK_DAMAGE, 3);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BUST_SIZE, 0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("bust_size", this.entityData.get(BUST_SIZE));
        ListNBT inventoryNBTList = new ListNBT();
        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) inventoryNBTList.add(RVUtils.saveStack(stack, new CompoundNBT()));
        }
        tag.put("inventory", inventoryNBTList);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setBustSize(tag.getFloat("bust_size"));
        ListNBT inventoryNBTList = BMTagFixes.renameInventory(tag);
        for (int i = 0; i < inventoryNBTList.size(); ++i) {
            ItemStack stack = RVUtils.loadStack(inventoryNBTList.getCompound(i));
            if (!stack.isEmpty()) this.inventory.addItem(stack);
        }
    }

    @Override
    public void tick() {
        super.tick();
        --this.eatingCooldown;
        if (!this.level.isClientSide && this.eatingCooldown <= 0 && (this.getFoodData().getNutritionLevel() <= 6 || this.getHealth() < 10)) {
            this.eatingCooldown = 40;
            ItemStack stack = findConsumableInInventory();
            if (stack != null) this.eat(this.level, stack.split(1));
        }
        this.getBreastPhysics().update(this, this.getBustSize());
    }

    public ItemStack findConsumableInInventory() {
        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack stack = this.inventory.getItem(i);
            if (stack.getItem().isEdible()) return stack;
        }
        return null;
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();
        super.aiStep();
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        return this.canPickUpLoot() && stack.getItem().is(BMItemTags.COLLECTOR_ALCALYTES_CAN_PICKUP);
    }

    @Override
    protected void pickUpItem(ItemEntity entity) {
        this.onItemPickup(entity);
        pickUpItem(this, entity);
    }

    public static void pickUpItem(CollectorAlcalyteEntity collector, ItemEntity itemEntity) {
        collector.getNavigation().stop();
        ItemStack collectedStack = itemEntity.getItem();
        collector.take(itemEntity, itemEntity.getItem().getCount());
        itemEntity.remove();

        Item collectedItem = collectedStack.getItem();
        if (collectedItem.getFoodProperties() != null && collector.canEat(collectedItem.getFoodProperties().canAlwaysEat())) {
            collector.eat(collector.level, collectedStack);
        } else {
            if (!collector.equipItemIfPossible(collectedStack)) collector.addToInventory(collectedStack);
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean wasRecentlyHit) {
        super.dropCustomDeathLoot(source, lootingLevel, wasRecentlyHit);
        this.inventory.removeAllItems().forEach(this::spawnAtLocation);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT tag) {
        this.populateAlcalyteEquipmentSlots();
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.setBustSize(this.random.nextFloat());
        return super.finalizeSpawn(world, difficulty, spawnReason, spawnData, tag);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.COLLECTOR_ALCALYTE_SPAWN_EGG.get());
    }
}
