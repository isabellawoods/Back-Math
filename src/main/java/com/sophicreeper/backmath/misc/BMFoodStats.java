package com.sophicreeper.backmath.misc;

import com.sophicreeper.backmath.entity.custom.alcalyte.AlcalyteEntity;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BMFoodStats {
    private int nutritionLevel = 20;
    private float saturationLevel;
    private float exhaustionLevel;
    private int tickTimer;
    private int lastNutritionLevel = 20;

    public BMFoodStats() {
        this.saturationLevel = 5;
    }

    public void eat(int nutrition, float saturationModifier) {
        this.nutritionLevel = Math.min(nutrition + this.nutritionLevel, 20);
        this.saturationLevel = Math.min(this.saturationLevel + (float) nutrition * saturationModifier * 2, (float) this.nutritionLevel);
    }

    public void eat(Item item, ItemStack stack) {
        if (item.isEdible() && item.getFoodProperties() != null) {
            Food food = item.getFoodProperties();
            this.eat(food.getNutrition(), food.getSaturationModifier());
        }
    }

    public void tick(AlcalyteEntity alcalyte) {
        Difficulty difficulty = alcalyte.level.getDifficulty();
        this.lastNutritionLevel = this.nutritionLevel;
        if (this.exhaustionLevel > 4) {
            this.exhaustionLevel -= 4;
            if (this.saturationLevel > 0) {
                this.saturationLevel = Math.max(this.saturationLevel - 1, 0);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.nutritionLevel = Math.max(this.nutritionLevel - 1, 0);
            }
        }

        boolean naturalRegenerationEnabled = alcalyte.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        if (naturalRegenerationEnabled && this.saturationLevel > 0 && alcalyte.isHurt() && this.nutritionLevel >= 20) {
            ++this.tickTimer;
            if (this.tickTimer >= 10) {
                float minSaturation = Math.min(this.saturationLevel, 6);
                alcalyte.heal(minSaturation / 6);
                this.addExhaustion(minSaturation);
                this.tickTimer = 0;
            }
        } else if (naturalRegenerationEnabled && this.nutritionLevel >= 18 && alcalyte.isHurt()) {
            ++this.tickTimer;
            if (this.tickTimer >= 80) {
                alcalyte.heal(1);
                this.addExhaustion(6);
                this.tickTimer = 0;
            }
        } else if (this.nutritionLevel <= 0) {
            ++this.tickTimer;
            if (this.tickTimer >= 80) {
                if (alcalyte.getHealth() > 10 || difficulty == Difficulty.HARD || alcalyte.getHealth() > 1 && difficulty == Difficulty.NORMAL) {
                    alcalyte.hurt(DamageSource.STARVE, 1);
                }
                this.tickTimer = 0;
            }
        } else {
            this.tickTimer = 0;
        }
    }

    public void readFoodStats(CompoundNBT tag) {
        if (tag.contains("food_statistics", TagTypes.COMPOUND)) {
            this.nutritionLevel = tag.getInt("nutrition_level");
            this.tickTimer = tag.getInt("tick_timer");
            this.saturationLevel = tag.getInt("saturation_level");
            this.exhaustionLevel = tag.getInt("exhaustion_level");
        }
    }

    public void writeFoodStats(CompoundNBT tag) {
        CompoundNBT foodStats = new CompoundNBT();
        foodStats.putInt("nutrition_level", this.nutritionLevel);
        foodStats.putInt("tick_timer", this.tickTimer);
        foodStats.putFloat("saturation_level", this.saturationLevel);
        foodStats.putFloat("exhaustion_level", this.exhaustionLevel);
        tag.put("food_statistics", foodStats);
    }

    public void addExhaustion(float exhaustion) {
        this.exhaustionLevel = Math.min(this.exhaustionLevel + exhaustion, 40);
    }

    public int getNutritionLevel() {
        return this.nutritionLevel;
    }

    public boolean canEat() {
        return this.nutritionLevel < 20;
    }

    public float getSaturationLevel() {
        return this.saturationLevel;
    }

    public void setNutritionLevel(int nutrition) {
        this.nutritionLevel = nutrition;
    }

    @OnlyIn(Dist.CLIENT)
    public void setSaturation(float saturation) {
        this.saturationLevel = saturation;
    }
}
