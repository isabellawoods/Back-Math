package melonystudios.backmath.fluid;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.block.ExtendedFluidType;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import static melonystudios.backmath.util.BMResourceLocations.*;

public class BMFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, BackMath.MOD_ID);

    // The tint color is actually an ARGB value, so the transparent hillary from 16/09/2023 was caused by this.
    public static final DeferredHolder<FluidType, FluidType> HILLARY = FLUID_TYPES.register("hillary", () ->
            new ExtendedFluidType(HILLARY_STILL, FLOWING_HILLARY, HILLARY_OVERLAY, 0xFFD7D0EF, new Vector3f(
                    184f / 255, 170f / 255, 227f / 255), FluidType.Properties.create().canHydrate(true).supportsBoating(true)
                    .canConvertToSource(true).viscosity(500)));

    public static final DeferredHolder<FluidType, FluidType> MILKLLARY = FLUID_TYPES.register("milkllary", () ->
            new ExtendedFluidType(MILKLLARY_STILL, FLOWING_MILKLLARY, MILKLLARY_OVERLAY, 0xFFD4CfE1, new Vector3f(
                    191f / 255, 184f / 255, 211f / 255), FluidType.Properties.create().canHydrate(true).supportsBoating(true)
                    .viscosity(500).rarity(Rarity.UNCOMMON)));

    public static final DeferredHolder<FluidType, FluidType> LIQUID_ALJAME = FLUID_TYPES.register("liquid_aljame", () ->
            new ExtendedFluidType(LIQUID_ALJAME_STILL, FLOWING_LIQUID_ALJAME, LIQUID_ALJAME_OVERLAY, 0xFFD0D8D8, new Vector3f(
                    186f / 255, 198f / 255, 198f / 255), FluidType.Properties.create().supportsBoating(true).viscosity(500)));

    public static final DeferredHolder<FluidType, FluidType> LIQUID_MANGA = FLUID_TYPES.register("liquid_manga", () ->
            new ExtendedFluidType(LIQUID_MANGA_STILL, FLOWING_LIQUID_MANGA, LIQUID_MANGA_OVERLAY, 0xFF95B3DC, new Vector3f(
                    125f / 255, 163f / 255, 212f / 255), FluidType.Properties.create().supportsBoating(true).viscosity(500)));

    public static final DeferredHolder<FluidType, FluidType> LIQUEFIED_MONSTER = FLUID_TYPES.register("liquefied_monster", () ->
            new ExtendedFluidType(LIQUEFIED_MONSTER_STILL, FLOWING_LIQUEFIED_MONSTER, LIQUEFIED_MONSTER_OVERLAY, 0xFF30582E, new Vector3f(
                    43f / 255, 79f / 255, 41f / 255), FluidType.Properties.create().supportsBoating(true).canSwim(false).density(2000).viscosity(2000)));

    public static final DeferredHolder<FluidType, FluidType> SLEEPISHWATER = FLUID_TYPES.register("sleepishwater", () ->
            new ExtendedFluidType(SLEEPISHWATER_STILL, FLOWING_SLEEPISHWATER, SLEEPISHWATER_OVERLAY, 0xFF8853A0, new Vector3f(
                    130f / 255, 82f / 255, 151f / 255), FluidType.Properties.create().canHydrate(true).supportsBoating(true)
                    .canConvertToSource(true).viscosity(500)));
}
