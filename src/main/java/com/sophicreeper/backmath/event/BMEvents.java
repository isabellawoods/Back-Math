package com.sophicreeper.backmath.event;

import com.mojang.serialization.Codec;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.block.model.LightBakedModel;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.registry.wsvariant.WandererSophieVariantManager;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.world.carver.BMCarverGeneration;
import com.sophicreeper.backmath.world.ore.BMOreGeneration;
import com.sophicreeper.backmath.world.plant.BMPlantGeneration;
import com.sophicreeper.backmath.world.structure.BMStructureGeneration;
import com.sophicreeper.backmath.world.structure.BMStructures;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sophicreeper.backmath.BackMath.LOGGER;

@Mod.EventBusSubscriber(modid = BackMath.MOD_ID)
public class BMEvents {
    private static WandererSophieVariantManager MANAGER_INSTANCE;

    @SubscribeEvent
    public static void biomeLoadEvent(final BiomeLoadingEvent event) {
        BMOreGeneration.generateOres(event);
        BMOreGeneration.generateAljanstoneOres(event);
        BMOreGeneration.generateSleepingstoneOres(event);
        BMCarverGeneration.generateCarvers(event);
        BMPlantGeneration.generatePlantsAndTrees(event);

        BMStructureGeneration.generateStructures(event);
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        for (BlockState blockState : BMBlocks.INSOMNIAN_TULIP.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.stateToModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                LOGGER.warn(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.insomnian_tulip.model_not_found").getString()));
            } else if (existingModel instanceof LightBakedModel) {
                LOGGER.warn(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.light_baked_model.replacement_attempt").getString()));
            } else {
                LightBakedModel lightBakedModel = new LightBakedModel();
                event.getModelRegistry().put(variantMRL, lightBakedModel);
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                Method GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation chunkGeneratorRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));

                if (chunkGeneratorRL != null && chunkGeneratorRL.getNamespace().equals("terraforged")) {
                    return;
                }
            } catch (Exception exception) {
                LogManager.getLogger().error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.using_terraforged_chunk_generator", serverWorld.dimension().location()).getString()));
            }

            // Prevent spawning our structure in Vanilla's superflat world.
            if (serverWorld.getChunkSource().generator instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }
            // Who cares about structures not generating in Superflat?
            // Ok, maybe this was causing issues with Superflat worlds.

            // Adding our structure to the Map.
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(BMStructures.SOPHIE_TOWER.get(), DimensionStructuresSettings.DEFAULTS.get(BMStructures.SOPHIE_TOWER.get()));
            tempMap.putIfAbsent(BMStructures.FABRICIO_HIDEOUT_DUNGEON.get(), DimensionStructuresSettings.DEFAULTS.get(BMStructures.FABRICIO_HIDEOUT_DUNGEON.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CARTOGRAPHER) {
            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();

            // Level 3 "Journeyman"
            trades.get(3).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 8), new ItemStack(Items.COMPASS), BMUtils.makeBackFieldsExplorerMap(trader),
                    12, 10, 0.2F));
        }
    }

    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        List<VillagerTrades.ITrade> genericTrades = event.getGenericTrades();

        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.GUARANA_OAK_SAPLING.get(), 1),
                // Max Trades, XP, Price Multiplier
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.CRYSTALLINE_BIRCH_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.MANGO_OAK_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.MANGAED_MANGO_OAK_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.GRAPE_VINE_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.LEMON_OAK_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.PINEAPPLE_OAK_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.ORANGE_OAK_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.BANANA_JUNGLE_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.ALJAME_BIRCH_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.GUAVA_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.JABUTICABA_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(AxolotlTest.CORK_OAK_SAPLING.get(), 1),
                8, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(AxolotlTest.RED_YELLOW_ALLIUM.get(), 1),
                12, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(AxolotlTest.FRIED_EGG_FLOWER.get(), 1),
                12, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(AxolotlTest.TURTLE_FRIED_EGG_FLOWER.get(), 1),
                12, 1, 0.05F));
        genericTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(AxolotlTest.RED_YELLOW_DYE.get(), 3),
                12, 1, 0.05F));
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        MANAGER_INSTANCE = new WandererSophieVariantManager();
        event.addListener(MANAGER_INSTANCE);
    }

    public static WandererSophieVariantManager getWandererSophieVariantManager() {
        if (MANAGER_INSTANCE != null) return MANAGER_INSTANCE;
        throw new IllegalStateException("Back Math: Cannot retrieve WandererSophieVariantManager until resources have loaded it.");
    }
}
