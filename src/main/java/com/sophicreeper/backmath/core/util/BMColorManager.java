package com.sophicreeper.backmath.core.util;

/*@Mod.EventBusSubscriber(modid = BackMath.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BMColorManager {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColorHandlers(final ColorHandlerEvent.Block event) {
        event.getBlockColors().register((x, reader, pos, u) -> reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColor.get(0.5d, 1.0d),
                BMBlocks.ALJAMIC_GRASS_BLOCK.get(), BMBlocks.AVONDALIC_NYLIUM.get());

        event.getBlockColors().register((x, reader, pos, u) -> reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault(),
                BMBlocks.JABUTICABA_LEAVES.get(), BMBlocks.GUARANA_OAK_LEAVES.get(), BMBlocks.MANGO_OAK_LEAVES.get(), BMBlocks.PINEAPPLE_OAK_LEAVES.get(), BMBlocks.LEMON_OAK_LEAVES.get(),
                BMBlocks.BANANA_JUNGLE_LEAVES.get(), BMBlocks.MANGAED_MANGO_OAK_LEAVES.get(), BMBlocks.ORANGE_OAK_LEAVES.get(), BMBlocks.CORK_OAK_LEAVES.get(), BMBlocks.ALJANWOOD_LEAVES.get(),
                BMBlocks.ALJANCAP_LEAVES.get(), BMBlocks.INSOMNIAN_LEAVES.get(), BMBlocks.AMARACAP_LEAVES.get(), BMBlocks.AVONDALIC_WILLOW_LEAVES.get());

        event.getBlockColors().register((x, reader, pos, u) -> FoliageColors.getSpruce(), BMBlocks.GRAPE_VINE_LEAVES.get());
        event.getBlockColors().register((x, reader, pos, u) -> FoliageColors.getBirch(), BMBlocks.ALJAME_BIRCH_LEAVES.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColorHandlers(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, color) -> GrassColor.get(0.5d, 1.0d), AxolotlTest.ALJAMIC_GRASS_BLOCK.get(), BMBlocks.AVONDALIC_NYLIUM.get(),
                BMBlocks.JABUTICABA_LEAVES.get(), BMBlocks.GUARANA_OAK_LEAVES.get(), BMBlocks.MANGO_OAK_LEAVES.get(), BMBlocks.PINEAPPLE_OAK_LEAVES.get(), BMBlocks.LEMON_OAK_LEAVES.get(),
                BMBlocks.BANANA_JUNGLE_LEAVES.get(), BMBlocks.MANGAED_MANGO_OAK_LEAVES.get(), BMBlocks.ORANGE_OAK_LEAVES.get(), BMBlocks.CORK_OAK_LEAVES.get(), BMBlocks.ALJANWOOD_LEAVES.get(),
                BMBlocks.ALJANCAP_LEAVES.get(), BMBlocks.INSOMNIAN_LEAVES.get(), BMBlocks.AMARACAP_LEAVES.get(), BMBlocks.AVONDALIC_WILLOW_LEAVES.get());

        event.getItemColors().register((stack, color) -> FoliageColors.getSpruce(), BMBlocks.GRAPE_VINE_LEAVES.get());
        event.getItemColors().register((stack, color) -> FoliageColors.getBirch(), BMBlocks.ALJAME_BIRCH_LEAVES.get());
    }
}*/
