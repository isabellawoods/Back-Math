package melonystudios.backmath.item.custom.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BMFoods {
    public static final FoodProperties TODDY = new FoodProperties.Builder().nutrition(10).saturationModifier(1).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200), 1).build();
    public static final FoodProperties ALJAME = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7F).effect(() ->
            new MobEffectInstance(MobEffects.POISON, 100), 1).effect(() ->
            new MobEffectInstance(MobEffects.BLINDNESS, 600), 1).build();
    public static final FoodProperties LAGUSTA = new FoodProperties.Builder().nutrition(9).saturationModifier(0.7F).build();
    public static final FoodProperties AMARACAMEL = new FoodProperties.Builder().nutrition(5).saturationModifier(2.2F).build();
    public static final FoodProperties ALJAME_TEACUP = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2F).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1).effect(
            // 1 in a 1,000,000 (million)
            () -> new MobEffectInstance(MobEffects.POISON, 1200, 100), 0.000001F).effect(
            () -> new MobEffectInstance(MobEffects.SATURATION, 100), 0.5F).build();
    public static final FoodProperties PINE_JELLY = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).effect(
            () -> new MobEffectInstance(MobEffects.ABSORPTION, 6000, 1), 1).build();
    public static final FoodProperties JANTIQUIFIED_ALJAME = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2F).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1).effect(
            () -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1).alwaysEdible().build();
    public static final FoodProperties HOLY_JANTIQUIFIED_ALJAME = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2F).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1).effect(
            () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1).alwaysEdible().build();

    // Ported from AxolotlTest.
    public static final FoodProperties CHOCCY_MILK_BUCKET = new FoodProperties.Builder().nutrition(5).saturationModifier(0.8F).effect(
            () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1), 1).effect(
            () -> new MobEffectInstance(MobEffects.DIG_SPEED, 100, 1), 1).build();
    public static final FoodProperties CHOCCY_MILK_BOTTLE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.8F).effect( // saturationModifier() was 6.5F.
            () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.DIG_SPEED, 50, 0), 0.5F).build();
    public static final FoodProperties RAW_MINCED_MEAT = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).fast().build();
    public static final FoodProperties COOKED_MINCED_MEAT = new FoodProperties.Builder().nutrition(3).saturationModifier(1.67F).fast().build();
    public static final FoodProperties WATER_TALCUM_POWDER = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build();
    public static final FoodProperties WATER_POT_BREAD = new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties HILLARY_JAM_BREAD = new FoodProperties.Builder().nutrition(7).saturationModifier(2.65F).build();
    public static final FoodProperties MILKLLARY_JAM_BREAD = new FoodProperties.Builder().nutrition(10).saturationModifier(0.3F).build();
    public static final FoodProperties BERRY_JAM_BREAD = new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build();
    public static final FoodProperties HOT_SOPHIE_AND_COLD_FABRICIO_MEAL = new FoodProperties.Builder().nutrition(2).saturationModifier(4.75F).build();

    // Back Math 1.8.0:
    // Foods
    public static final FoodProperties GUAVA = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5F).build();
    public static final FoodProperties JABUTICABA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.4F).build();

    // Cut Fruits
    public static final FoodProperties HALVED_APPLE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.15F).fast().build();
    public static final FoodProperties HALVED_GOLDEN_APPLE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.6F).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 50, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1).alwaysEdible().fast().build();
    public static final FoodProperties HALVED_ENCHANTED_GOLDEN_APPLE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.6F).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1), 1).effect(
            () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3000, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 1), 1).alwaysEdible().fast().build();
    public static final FoodProperties HALVED_CARROT = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).fast().build();
    public static final FoodProperties HALVED_SWEET_BERRIES = new FoodProperties.Builder().nutrition(1).saturationModifier(0.05F).fast().build();
    public static final FoodProperties HALVED_BAKED_POTATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).fast().build();
    public static final FoodProperties HALVED_HONEY_BOTTLE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.05F).fast().build();
    public static final FoodProperties HALVED_ALJAME = new FoodProperties.Builder().nutrition(2).saturationModifier(1.75F).effect(() ->
            new MobEffectInstance(MobEffects.POISON, 50), 1).effect(() ->
            new MobEffectInstance(MobEffects.BLINDNESS, 300), 1).fast().build();
    public static final FoodProperties HALVED_JANTIQUIFIED_ALJAME = new FoodProperties.Builder().nutrition(2).saturationModifier(1.75F).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 50, 1), 1).effect(
            () -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1).alwaysEdible().build();
    public static final FoodProperties HALVED_HOLY_JANTIQUIFIED_ALJAME = new FoodProperties.Builder().nutrition(2).saturationModifier(1.75F).effect(
            () -> new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1).effect(
            () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3000, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3000, 0), 1).effect(
            () -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 3), 1).alwaysEdible().build();
    public static final FoodProperties HALVED_GUAVA = new FoodProperties.Builder().nutrition(2).saturationModifier(0.25F).fast().build();
    public static final FoodProperties HALVED_JABUTICABA = new FoodProperties.Builder().nutrition(1).saturationModifier(0.2F).fast().build();
    public static final FoodProperties ALJAMIC_BERRY = new FoodProperties.Builder().nutrition(5).saturationModifier(3.5F).build();
    public static final FoodProperties HALVED_ALJAMIC_BERRY = new FoodProperties.Builder().nutrition(2).saturationModifier(1.75F).fast().build();
    public static final FoodProperties AMARACAMEL_JUICE = new FoodProperties.Builder().nutrition(6).saturationModifier(3.2F).build();

    // Ported from AxolotlTest.
    public static final FoodProperties FRIED_EGG_BREAD = new FoodProperties.Builder().nutrition(5).saturationModifier(0.93F).build();
    public static final FoodProperties BREAD_WITH_PAO = new FoodProperties.Builder().nutrition(10).saturationModifier(0.12F).build();
}
