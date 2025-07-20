package com.sophicreeper.backmath.misc;

import com.sophicreeper.backmath.BackMath;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BMStatistics {
    public static final ResourceLocation INTERACT_WITH_MEAL_COOKER = register("interact_with_meal_cooker");
    public static final ResourceLocation CHANGE_CRYSTALLIZER_MOLD = register("change_crystallizer_mold");
    public static final ResourceLocation CRAFT_IN_CRYSTALLIZER = register("craft_in_crystallizer");
    public static final ResourceLocation CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD = register("change_crystalline_crystallizer_mold");
    public static final ResourceLocation CRAFT_IN_CRYSTALLINE_CRYSTALLIZER = register("craft_in_crystalline_crystallizer");
    public static final ResourceLocation OPEN_CRATE = register("open_crate");

    private static ResourceLocation register(String name) {
        ResourceLocation registryName = BackMath.backMath(name);
        Registry.register(Registry.CUSTOM_STAT, name, registryName);
        Stats.CUSTOM.get(registryName, IStatFormatter.DEFAULT);
        return registryName;
    }

    public static void init() {}
}
