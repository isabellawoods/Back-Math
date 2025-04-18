package melonystudios.backmath.item.behavior;

import com.google.common.collect.Lists;
import melonystudios.backmath.item.AxolotlTest;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;

import java.util.List;

public class ItemBehaviorParameters {
    // Durability bars
    public static final int MID_TERM_DURABILITY = 0x1DC2D1;
    public static final int SPAREY_DURABILITY = 0x85C284;

    // Food settings
    public static final FoodSettings FOOD = new FoodSettings().animation(UseAnim.EAT).consumeSound(SoundEvents.GENERIC_EAT);
    public static final FoodSettings DRINK = new FoodSettings(true);
    public static final FoodSettings BOTTLE_DRINK = new FoodSettings(true).defaultRemainder(() -> Items.GLASS_BOTTLE);
    public static final FoodSettings BUCKET_DRINK = new FoodSettings(true).defaultRemainder(() -> Items.BUCKET);
    public static final FoodSettings ALJAMIC_BOTTLE_DRINK = new FoodSettings(true).defaultRemainder(AxolotlTest.ALJAMIC_GLASS_BOTTLE);
    public static final FoodSettings JAM = new FoodSettings(true).useDuration(40).animation(UseAnim.EAT).consumeSound(SoundEvents.HONEY_DRINK).defaultRemainder(AxolotlTest.JAM_POT);
    public static final FoodSettings SPICE_POT = new FoodSettings(true).useDuration(40).animation(UseAnim.EAT).consumeSound(SoundEvents.HONEY_DRINK).defaultRemainder(AxolotlTest.SPICE_POT);
    public static final FoodSettings WINE = new FoodSettings(true).defaultRemainder(AxolotlTest.CORK_STOPPER).useDuration(40);
    public static final FoodSettings POPSICLE = new FoodSettings().animation(UseAnim.EAT).consumeSound(SoundEvents.GENERIC_EAT).defaultRemainder(() -> Items.STICK);

    public static List<MobEffectInstance> getPeaceTeaEffects() {
        List<MobEffectInstance> effects = Lists.newArrayList(new MobEffectInstance(MobEffects.ABSORPTION, 6000), new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000), new MobEffectInstance(MobEffects.HEAL, 6000), new MobEffectInstance(MobEffects.REGENERATION, 6000),
                new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000), new MobEffectInstance(MobEffects.DIG_SPEED, 6000), new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000), new MobEffectInstance(MobEffects.JUMP, 6000),
                new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000), new MobEffectInstance(MobEffects.WATER_BREATHING, 6000),
                new MobEffectInstance(MobEffects.NIGHT_VISION, 6000), new MobEffectInstance(MobEffects.SATURATION, 6000), new MobEffectInstance(MobEffects.LUCK, 6000), new MobEffectInstance(MobEffects.SLOW_FALLING, 6000),
                new MobEffectInstance(MobEffects.CONDUIT_POWER, 6000), new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 6000), new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 6000));
        effects.add(new MobEffectInstance(MobEffects.INVISIBILITY, 6000));
        effects.add(new MobEffectInstance(MobEffects.GLOWING, 6000));
        return effects;
    }
}
