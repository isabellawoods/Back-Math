package melonystudios.backmath.block;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.fluid.BMFluids;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BMBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BackMath.MOD_ID);

    public static final DeferredBlock<LiquidBlock> HILLARY = BLOCKS.register("hilary_fluid", () -> new LiquidBlock(BMFluids.HILLARY.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().strength(100).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
    public static final DeferredBlock<LiquidBlock> MILKLLARY = BLOCKS.register("milklary", () -> new LiquidBlock(BMFluids.MILKLLARY.get(), BlockBehaviour.Properties.ofFullCopy(HILLARY.get())));
    public static final DeferredBlock<LiquidBlock> LIQUID_ALJAME = BLOCKS.register("liquid_aljame", () -> new LiquidBlock(BMFluids.LIQUID_ALJAME.get(), BlockBehaviour.Properties.ofFullCopy(HILLARY.get()).mapColor(MapColor.CLAY)));
    public static final DeferredBlock<LiquidBlock> LIQUID_MANGA = BLOCKS.register("liquid_manga", () -> new LiquidBlock(BMFluids.LIQUID_MANGA.get(), BlockBehaviour.Properties.ofFullCopy(HILLARY.get()).mapColor(MapColor.TERRACOTTA_BLUE)));
    public static final DeferredBlock<LiquidBlock> LIQUEFIED_MONSTER = BLOCKS.register("liquefied_monster", () -> new LiquidBlock(BMFluids.LIQUEFIED_MONSTER.get(), BlockBehaviour.Properties.ofFullCopy(HILLARY.get()).mapColor(MapColor.COLOR_GREEN)));
    public static final DeferredBlock<LiquidBlock> SLEEPISHWATER = BLOCKS.register("sleepishwater", () -> new LiquidBlock(BMFluids.SLEEPISHWATER.get(), BlockBehaviour.Properties.ofFullCopy(HILLARY.get()).mapColor(MapColor.COLOR_PURPLE)));
}
