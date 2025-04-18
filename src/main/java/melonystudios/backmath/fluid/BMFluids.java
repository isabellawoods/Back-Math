package melonystudios.backmath.fluid;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.block.BMBlocks;
import melonystudios.backmath.item.AxolotlTest;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BMFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, BackMath.MOD_ID);

    // Hillary
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> HILLARY = FLUIDS.register("hilary_fluid", () ->
            new BaseFlowingFluid.Source(BMFluids.HILLARY_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_HILLARY = FLUIDS.register("hilary_flowing", () ->
            new BaseFlowingFluid.Flowing(BMFluids.HILLARY_PROPERTIES));

    // Milkllary
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> MILKLLARY = FLUIDS.register("milklary_fluid", () ->
            new BaseFlowingFluid.Source(BMFluids.MILKLLARY_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_MILKLLARY = FLUIDS.register("milklary_flowing", () ->
            new BaseFlowingFluid.Flowing(BMFluids.MILKLLARY_PROPERTIES));

    // Liquid Aljame
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> LIQUID_ALJAME = FLUIDS.register("liquid_aljame", () ->
            new BaseFlowingFluid.Source(BMFluids.LIQUID_ALJAME_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_LIQUID_ALJAME = FLUIDS.register("flowing_liquid_aljame", () ->
            new BaseFlowingFluid.Flowing(BMFluids.LIQUID_ALJAME_PROPERTIES));

    // Liquid Manga
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> LIQUID_MANGA = FLUIDS.register("liquid_manga", () ->
            new BaseFlowingFluid.Source(BMFluids.LIQUID_MANGA_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_LIQUID_MANGA = FLUIDS.register("flowing_liquid_manga", () ->
            new BaseFlowingFluid.Flowing(BMFluids.LIQUID_MANGA_PROPERTIES));

    // Liquefied Monster
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> LIQUEFIED_MONSTER = FLUIDS.register("liquefied_monster", () ->
            new BaseFlowingFluid.Source(BMFluids.LIQUEFIED_MONSTER_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_LIQUEFIED_MONSTER = FLUIDS.register("flowing_liquefied_monster", () ->
            new BaseFlowingFluid.Flowing(BMFluids.LIQUEFIED_MONSTER_PROPERTIES));

    // Sleepishwater
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> SLEEPISHWATER = FLUIDS.register("sleepishwater", () ->
            new BaseFlowingFluid.Source(BMFluids.SLEEPISHWATER_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_SLEEPISHWATER = FLUIDS.register("flowing_sleepishwater", () ->
            new BaseFlowingFluid.Flowing(BMFluids.SLEEPISHWATER_PROPERTIES));

    // Fluid Properties
    public static final BaseFlowingFluid.Properties HILLARY_PROPERTIES = new BaseFlowingFluid.Properties(
            BMFluidTypes.HILLARY, HILLARY, FLOWING_HILLARY).slopeFindDistance(8).levelDecreasePerBlock(1)
            .block(BMBlocks.HILLARY).bucket(AxolotlTest.HILLARY_BUCKET);

    public static final BaseFlowingFluid.Properties MILKLLARY_PROPERTIES = new BaseFlowingFluid.Properties(
            BMFluidTypes.MILKLLARY, MILKLLARY, FLOWING_MILKLLARY).slopeFindDistance(8).levelDecreasePerBlock(1)
            .block(BMBlocks.MILKLLARY).bucket(AxolotlTest.MILKLLARY_BUCKET);

    public static final BaseFlowingFluid.Properties LIQUID_ALJAME_PROPERTIES = new BaseFlowingFluid.Properties(
            BMFluidTypes.LIQUID_ALJAME, LIQUID_ALJAME, FLOWING_LIQUID_ALJAME).slopeFindDistance(8).levelDecreasePerBlock(1)
            .block(BMBlocks.LIQUID_ALJAME).bucket(AxolotlTest.LIQUID_ALJAME_BUCKET);

    public static final BaseFlowingFluid.Properties LIQUID_MANGA_PROPERTIES = new BaseFlowingFluid.Properties(
            BMFluidTypes.LIQUID_MANGA, LIQUID_MANGA, FLOWING_LIQUID_MANGA).slopeFindDistance(8).levelDecreasePerBlock(1)
            .block(BMBlocks.LIQUID_MANGA).bucket(AxolotlTest.LIQUID_MANGA_BUCKET);

    public static final BaseFlowingFluid.Properties LIQUEFIED_MONSTER_PROPERTIES = new BaseFlowingFluid.Properties(
            BMFluidTypes.LIQUEFIED_MONSTER, LIQUEFIED_MONSTER, FLOWING_LIQUEFIED_MONSTER).slopeFindDistance(8).levelDecreasePerBlock(2)
            .block(BMBlocks.LIQUEFIED_MONSTER).bucket(AxolotlTest.LIQUEFIED_MONSTER_BUCKET);

    public static final BaseFlowingFluid.Properties SLEEPISHWATER_PROPERTIES = new BaseFlowingFluid.Properties(
            BMFluidTypes.SLEEPISHWATER, SLEEPISHWATER, FLOWING_SLEEPISHWATER).slopeFindDistance(8).levelDecreasePerBlock(1)
            .block(BMBlocks.SLEEPISHWATER).bucket(AxolotlTest.SLEEPISHWATER_BUCKET);
}
