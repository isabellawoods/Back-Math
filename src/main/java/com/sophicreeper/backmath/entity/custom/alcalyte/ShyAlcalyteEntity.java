package com.sophicreeper.backmath.entity.custom.alcalyte;

import com.sophicreeper.backmath.entity.custom.aljan.MalaikaEntity;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.util.tag.BMEntityTypeTags;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.*;

import javax.annotation.Nullable;

public class ShyAlcalyteEntity extends AlcalyteEntity {
    public ShyAlcalyteEntity(EntityType<ShyAlcalyteEntity> type, World world) {
        super(type, world);
        this.maxUpStep = 1;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.1, Ingredient.of(BMItemTags.SHY_ALCALYTE_TEMPT_ITEMS), true));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, LivingEntity.class, 24, 1.6, 2.6, livEntity -> !livEntity.getType().is(BMEntityTypeTags.SHY_ALCALYTE_FRIENDLIES)));
        this.goalSelector.addGoal(2, new PanicGoal(this, 2.1));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6));
        this.goalSelector.addGoal(4, new LookAtGoal(this, AlcalyteEntity.class, 6));
        this.goalSelector.addGoal(4, new LookAtGoal(this, MalaikaEntity.class, 5));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        super.registerGoals();
    }

    public static AttributeModifierMap.MutableAttribute createShyAlcalyteAttributes() {
        return CreatureEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.23F);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(AxolotlTest.SHY_ALCALYTE_SPAWN_EGG.get());
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
            if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) this.heal(1);
        }
        super.aiStep();
    }

    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
        spawnData = super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
        this.setBustSize(0);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        return spawnData;
    }
}
