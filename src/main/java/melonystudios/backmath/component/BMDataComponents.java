package melonystudios.backmath.component;

import com.mojang.serialization.Codec;
import melonystudios.backmath.BackMath;
import melonystudios.backmath.component.custom.MilkedSwordItem;
import melonystudios.backmath.component.custom.SuperchargeSettings;
import melonystudios.backmath.component.custom.UseRemainder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BMDataComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BackMath.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SuperchargeSettings>> SUPERCHARGE_SETTINGS = COMPONENTS.registerComponentType("supercharge_settings",
            builder -> builder.persistent(SuperchargeSettings.CODEC).networkSynchronized(SuperchargeSettings.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UseRemainder>> USE_REMAINDER = COMPONENTS.registerComponentType("use_remainder",
            builder -> builder.persistent(UseRemainder.CODEC).networkSynchronized(UseRemainder.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MilkedSwordItem>> MILKED_SWORD_ITEM = COMPONENTS.registerComponentType("milked_sword_item",
            builder -> builder.persistent(MilkedSwordItem.CODEC).networkSynchronized(MilkedSwordItem.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> WOOD_TYPE = COMPONENTS.registerComponentType("wood_type",
            builder -> builder.persistent(Codec.STRING));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DURABILITY_BAR_COLOR = COMPONENTS.registerComponentType("durability_bar_color",
            builder -> builder.persistent(ExtraCodecs.intRange(0, 16777215)));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORED_EXPERIENCE = COMPONENTS.registerComponentType("stored_experience",
            builder -> builder.persistent(ExtraCodecs.intRange(0, Integer.MAX_VALUE)));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TICKS_ON_FIRE = COMPONENTS.registerComponentType("ticks_on_fire",
            builder -> builder.persistent(ExtraCodecs.intRange(0, Integer.MAX_VALUE)));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> BAG_LOOT_TABLE = COMPONENTS.registerComponentType("bag_loot_table",
            builder -> builder.persistent(ResourceLocation.CODEC));

    // sparey_settings {prohibited_entities, effective_entities, strength_effect, weakness_effect, prohibition_weakness_effect}
    // - strength_effect: effect list
    // applied_effects {effect list}
}
