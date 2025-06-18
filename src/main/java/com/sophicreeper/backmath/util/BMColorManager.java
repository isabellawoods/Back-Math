package com.sophicreeper.backmath.util;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.config.BMConfigs;
import com.sophicreeper.backmath.block.BMBlocks;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.sophicreeper.backmath.item.AxolotlTest.*;

@Mod.EventBusSubscriber(modid = BackMath.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BMColorManager {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColorHandlers(final ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, reader, pos, tintIndex) -> reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5D, 1), BMBlocks.ALJAMIC_GRASS_BLOCK.get(), BMBlocks.AVONDALIC_NYLIUM.get());

        event.getBlockColors().register((state, reader, pos, tintIndex) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor(),
                BMBlocks.JABUTICABA_LEAVES.get(), BMBlocks.GUARANA_OAK_LEAVES.get(), BMBlocks.MANGO_OAK_LEAVES.get(), BMBlocks.PINEAPPLE_OAK_LEAVES.get(), BMBlocks.LEMON_OAK_LEAVES.get(),
                BMBlocks.BANANA_JUNGLE_LEAVES.get(), BMBlocks.MANGAED_MANGO_OAK_LEAVES.get(), BMBlocks.ORANGE_OAK_LEAVES.get(), BMBlocks.CORK_OAK_LEAVES.get(), BMBlocks.ALJANWOOD_LEAVES.get(),
                BMBlocks.ALJANCAP_LEAVES.get(), BMBlocks.INSOMNIAN_LEAVES.get(), BMBlocks.AMARACAP_LEAVES.get(), BMBlocks.AVONDALIC_WILLOW_LEAVES.get());

        event.getBlockColors().register((state, reader, pos, tintIndex) -> FoliageColors.getEvergreenColor(), BMBlocks.GRAPE_VINE_LEAVES.get());
        event.getBlockColors().register((state, reader, pos, tintIndex) -> FoliageColors.getBirchColor(), BMBlocks.ALJAME_BIRCH_LEAVES.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColorHandlers(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> GrassColors.get(0.5D, 1), BMBlocks.ALJAMIC_GRASS_BLOCK.get(), BMBlocks.AVONDALIC_NYLIUM.get(),
                BMBlocks.JABUTICABA_LEAVES.get(), BMBlocks.GUARANA_OAK_LEAVES.get(), BMBlocks.MANGO_OAK_LEAVES.get(), BMBlocks.PINEAPPLE_OAK_LEAVES.get(), BMBlocks.LEMON_OAK_LEAVES.get(),
                BMBlocks.BANANA_JUNGLE_LEAVES.get(), BMBlocks.MANGAED_MANGO_OAK_LEAVES.get(), BMBlocks.ORANGE_OAK_LEAVES.get(), BMBlocks.CORK_OAK_LEAVES.get(), BMBlocks.ALJANWOOD_LEAVES.get(),
                BMBlocks.ALJANCAP_LEAVES.get(), BMBlocks.INSOMNIAN_LEAVES.get(), BMBlocks.AMARACAP_LEAVES.get(), BMBlocks.AVONDALIC_WILLOW_LEAVES.get());

        event.getItemColors().register((stack, tintIndex) -> FoliageColors.getEvergreenColor(), BMBlocks.GRAPE_VINE_LEAVES.get());
        event.getItemColors().register((stack, tintIndex) -> FoliageColors.getBirchColor(), BMBlocks.ALJAME_BIRCH_LEAVES.get());

        event.getItemColors().register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtils.getColor(stack), EFFECT_JAM.get());

        event.getItemColors().register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((IDyeableArmorItem) stack.getItem()).getColor(stack), KARATE_HEADBAND.get());

        if (BMConfigs.COMMON_CONFIGS.tintSpawnEggs.get()) {
            event.getItemColors().register((stack, tintIndex) -> ((SpawnEggItem) stack.getItem()).getColor(tintIndex), WANDERER_SOPHIE_SPAWN_EGG.get(), ANGRY_SOPHIE_SPAWN_EGG.get(),
                    INSOMNIA_SOPHIE_SPAWN_EGG.get(), ARCHER_INSOMNIA_SOPHIE_SPAWN_EGG.get(), WARRIOR_SOPHIE_SPAWN_EGG.get(), QUEEN_LUCY_SPAWN_EGG.get(), QUEEN_LUCY_PET_SPAWN_EGG.get(),
                    ARCHER_LUCIA_SPAWN_EGG.get(), KARATE_LUCIA_SPAWN_EGG.get(), SHY_ALCALYTE_SPAWN_EGG.get(), COLLECTOR_ALCALYTE_SPAWN_EGG.get(), MALAIKA_SPAWN_EGG.get(),
                    INSOMNIA_ZOMBIE_SPAWN_EGG.get(), ZOMBIE_FABRICIO_SPAWN_EGG.get(), ALJAMIC_BONES_SPAWN_EGG.get(), SLEEPISH_SKELETON_SPAWN_EGG.get(), AMARACAMELER_SPAWN_EGG.get(),
                    JANTICLE_SPAWN_EGG.get());
        }
    }
}
