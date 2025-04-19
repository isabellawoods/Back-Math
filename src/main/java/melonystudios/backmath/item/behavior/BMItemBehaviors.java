package melonystudios.backmath.item.behavior;

import com.google.common.collect.Lists;
import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.effecttype.custom.DamageEntityEffectType;
import melonystudios.backmath.item.behavior.effecttype.custom.IgniteEffectType;
import melonystudios.backmath.misc.BMDamageSources;
import melonystudios.backmath.misc.BMRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static melonystudios.backmath.item.behavior.ItemBehaviorParameters.*;
import static melonystudios.backmath.item.behavior.effecttype.BMItemBehaviorEffectTypes.*;

public class BMItemBehaviors {
    public static final DeferredRegister<ItemBehavior> BEHAVIORS = DeferredRegister.create(BMRegistries.ITEM_BEHAVIOR, BackMath.MOD_ID);

    public static final DeferredHolder<ItemBehavior, ItemBehavior> NONE = BEHAVIORS.register("none", () -> new ItemBehavior(Lists.newArrayList()));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> GLINT_ONLY = BEHAVIORS.register("glint_only", () -> new ItemBehavior(Lists.newArrayList()).glint(true));

    // Tools & Weapons
    public static final DeferredHolder<ItemBehavior, ItemBehavior> MILKED_SWORD = BEHAVIORS.register("milked_sword", () -> new ItemBehavior(Lists.newArrayList(GIVE_MILKED_SWORD_ITEM)));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> BUTTER = BEHAVIORS.register("butter", () -> new ItemBehavior(Lists.newArrayList(ADD_EXPERIENCE)));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> MILKED_BUTTER = BEHAVIORS.register("milked_butter", () -> new ItemBehavior(Lists.newArrayList(GIVE_MILKED_SWORD_ITEM, ADD_EXPERIENCE)));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> DEVIL = BEHAVIORS.register("devil", () -> new ItemBehavior(Lists.newArrayList(IGNITE)));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> MILKED_DEVIL = BEHAVIORS.register("milked_devil", () -> new ItemBehavior(Lists.newArrayList(GIVE_MILKED_SWORD_ITEM, IGNITE)));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> DEVIL_SPAREY = BEHAVIORS.register("devil_sparey", () -> new ItemBehavior(Lists.newArrayList(APPLY_DEVIL_SPAREY_EFFECTS, IGNITE)));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> MILKED_DEVIL_SPAREY = BEHAVIORS.register("milked_devil_sparey", () -> new ItemBehavior(Lists.newArrayList(APPLY_DEVIL_SPAREY_EFFECTS, GIVE_MILKED_SWORD_ITEM, IGNITE)));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> SPAREY = BEHAVIORS.register("sparey", () -> new ItemBehavior(Lists.newArrayList(APPLY_SPAREY_EFFECTS)).durabilityBarColor(SPAREY_DURABILITY));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> MILKED_SPAREY = BEHAVIORS.register("milked_sparey", () -> new ItemBehavior(Lists.newArrayList(APPLY_SPAREY_EFFECTS, GIVE_MILKED_SWORD_ITEM)).durabilityBarColor(SPAREY_DURABILITY));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> MID_TERM = BEHAVIORS.register("mid_term", () -> new ItemBehavior(Lists.newArrayList(() -> new IgniteEffectType(200), APPLY_MID_TERM_EFFECTS)).glint(true).durabilityBarColor(MID_TERM_DURABILITY));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> MILKED_MID_TERM = BEHAVIORS.register("milked_mid_term", () -> new ItemBehavior(Lists.newArrayList(GIVE_MILKED_SWORD_ITEM, () -> new IgniteEffectType(200), APPLY_MID_TERM_EFFECTS)).glint(true).durabilityBarColor(MID_TERM_DURABILITY));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> MID_TERM_SPAREY = BEHAVIORS.register("mid_term_sparey", () -> new ItemBehavior(Lists.newArrayList(APPLY_SPAREY_EFFECTS, () -> new IgniteEffectType(200), APPLY_MID_TERM_EFFECTS)).glint(true).durabilityBarColor(MID_TERM_DURABILITY));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> MILKED_MID_TERM_SPAREY = BEHAVIORS.register("milked_mid_term_sparey", () -> new ItemBehavior(Lists.newArrayList(APPLY_SPAREY_EFFECTS, GIVE_MILKED_SWORD_ITEM, () -> new IgniteEffectType(200), APPLY_MID_TERM_EFFECTS)).glint(true).durabilityBarColor(MID_TERM_DURABILITY));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> PLATEFORCED_MID_TERM = BEHAVIORS.register("plateforced_mid_term", () -> new ItemBehavior(Lists.newArrayList()).glint(true).durabilityBarColor(MID_TERM_DURABILITY));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> MECH_MECH = BEHAVIORS.register("mech_mech", () -> new ItemBehavior(Lists.newArrayList(IGNITE)));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> RAINBOW_PENCIL = BEHAVIORS.register("rainbow_pencil", () -> new ItemBehavior(Lists.newArrayList(() -> new IgniteEffectType(200), APPLY_MID_TERM_EFFECTS)).glint(true));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> WATER_TALC_POWDER = BEHAVIORS.register("water_talc_powder", () -> new ItemBehavior(Lists.newArrayList(() -> new DamageEntityEffectType(BMDamageSources.WATER_TALC_POWDER, Float.MAX_VALUE))));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> PINK_GUM_FRYING_PAN = BEHAVIORS.register("pink_gum_frying_pan", () -> new ItemBehavior(Lists.newArrayList(APPLY_PINK_GUM_FRYING_PAN_EFFECTS)));

    // Food
    public static final DeferredHolder<ItemBehavior, ItemBehavior> MANGAED_MANGO = BEHAVIORS.register("mangaed_mango", () -> new ItemBehavior(Lists.newArrayList(ADD_BAKUGOU_OUTFIT)));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> HOT_SOPHIE = BEHAVIORS.register("hot_sophie", () -> new ItemBehavior(Lists.newArrayList(IGNITE)));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> TEMPERATURE_MEAL = BEHAVIORS.register("temperature_meal", () -> new ItemBehavior(Lists.newArrayList(IGNITE, () -> new DamageEntityEffectType(BMDamageSources.TEMPERATURE_MEAL, Float.MAX_VALUE))));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> LAVA_POT = BEHAVIORS.register("lava_pot", () -> new ItemBehavior(Lists.newArrayList(IGNITE)));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> PATIENCE_TEA = BEHAVIORS.register("patience_tea", () -> new ItemBehavior(Lists.newArrayList(APPLY_PATIENCE_TEA_EFFECTS)));
    public static final DeferredHolder<ItemBehavior, ItemBehavior> PEACE_TEA = BEHAVIORS.register("peace_tea", () -> new ItemBehavior(Lists.newArrayList(APPLY_PEACE_TEA_EFFECTS)));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> DISGUST_TEA = BEHAVIORS.register("disgust_tea", () -> new ItemBehavior(Lists.newArrayList(APPLY_DISGUST_TEA_EFFECTS)));
//    public static final DeferredHolder<ItemBehavior, ItemBehavior> MOOD_TEA = BEHAVIORS.register("mood_tea", () -> new ItemBehavior(Lists.newArrayList(APPLY_MOOD_TEA_EFFECTS)));
}
