package melonystudios.backmath.misc;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class BMRegistries {
    public static final Registry<ItemBehavior> ITEM_BEHAVIOR = new RegistryBuilder<ItemBehavior>(ResourceKey.createRegistryKey(BackMath.backMath("item_behavior"))).create();
    public static final Registry<ItemBehaviorEffectType> ITEM_BEHAVIOR_EFFECT_TYPE = new RegistryBuilder<ItemBehaviorEffectType>(ResourceKey.createRegistryKey(BackMath.backMath("item_behavior_effect_type"))).create();
}
