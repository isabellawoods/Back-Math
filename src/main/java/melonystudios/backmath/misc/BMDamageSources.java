package melonystudios.backmath.misc;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class BMDamageSources {
    private static final DamageType WATER_TALC_POWDER_TYPE = new DamageType("water_talc_powder", 0);
    public static final DamageSource WATER_TALC_POWDER = new DamageSource(Holder.direct(WATER_TALC_POWDER_TYPE));
}
