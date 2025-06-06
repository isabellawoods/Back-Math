package com.sophicreeper.backmath.effect;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.effect.custom.DisgustTeaEffect;
import com.sophicreeper.backmath.effect.custom.MoodTeaEffect;
import com.sophicreeper.backmath.effect.custom.PatienceTeaEffect;
import com.sophicreeper.backmath.effect.custom.SuperchargedEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BMEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, BackMath.MOD_ID);

    public static final RegistryObject<Effect> PATIENCE = EFFECTS.register("patience", PatienceTeaEffect::new);
    public static final RegistryObject<Effect> DISGUST = EFFECTS.register("disgust", DisgustTeaEffect::new);
    public static final RegistryObject<Effect> MOOD = EFFECTS.register("mood", MoodTeaEffect::new);
    public static final RegistryObject<Effect> SUPERCHARGED = EFFECTS.register("supercharged", SuperchargedEffect::new);
}
