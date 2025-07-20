package com.sophicreeper.backmath.proxy;

import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.block.BMFluids;
import com.sophicreeper.backmath.blockentity.BMBlockEntities;
import com.sophicreeper.backmath.container.BMContainers;
import com.sophicreeper.backmath.container.screen.CrateScreen;
import com.sophicreeper.backmath.config.BMConfigs;
import com.sophicreeper.backmath.dispenser.CrystallizerRecipesDispenseBehavior;
import com.sophicreeper.backmath.dispenser.EyeOfEnderDispenseBehavior;
import com.sophicreeper.backmath.dispenser.vanilla.BucketDispenseBehavior;
import com.sophicreeper.backmath.effect.BMEffects;
import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.entity.custom.aljan.AmaracamelerEntity;
import com.sophicreeper.backmath.entity.custom.aljan.MalaikaEntity;
import com.sophicreeper.backmath.entity.custom.termian.TermianPatrollerEntity;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.item.behavior.BMItemBehaviors;
import com.sophicreeper.backmath.item.behavior.effecttype.BMItemBehaviorEffectTypes;
import com.sophicreeper.backmath.item.custom.BMSpawnEggItem;
import com.sophicreeper.backmath.item.custom.tool.CarewniItem;
import com.sophicreeper.backmath.item.custom.tool.MechMechSwordItem;
import com.sophicreeper.backmath.item.custom.tool.MidTermLongswordItem;
import com.sophicreeper.backmath.loot.BMLootFunctions;
import com.sophicreeper.backmath.loot.BMLootModifiers;
import com.sophicreeper.backmath.loot.BMLootParameterSets;
import com.sophicreeper.backmath.misc.*;
import com.sophicreeper.backmath.particle.BMParticleTypes;
import com.sophicreeper.backmath.util.BMVanillaCompatibility;
import com.sophicreeper.backmath.variant.queenlucy.BMQueenLucyVariants;
import com.sophicreeper.backmath.variant.queenlucypet.BMQueenLucyPetVariants;
import com.sophicreeper.backmath.variant.wansophie.BMWandererSophieVariants;
import com.sophicreeper.backmath.world.biome.BMBiomes;
import com.sophicreeper.backmath.world.carver.BMConfiguredCarvers;
import com.sophicreeper.backmath.world.carver.BMWorldCarvers;
import com.sophicreeper.backmath.world.dimension.BMDimensions;
import com.sophicreeper.backmath.world.feature.BMFeature;
import com.sophicreeper.backmath.world.structure.BMStructures;
import com.sophicreeper.backmath.world.surface.BMSurfaceBuilders;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.Items;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class CommonProxy {
    CommonProxy() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BMBlocks.BLOCKS.register(eventBus);
        AxolotlTest.ITEMS.register(eventBus);
        BMEntities.ENTITIES.register(eventBus);
        BMEffects.EFFECTS.register(eventBus);
        BMFluids.FLUIDS.register(eventBus);
        BMFeature.FEATURES.register(eventBus);
        BMWorldCarvers.WORLD_CARVERS.register(eventBus);
        BMBiomes.BIOMES.register(eventBus);
        BMPaintings.PAINTINGS.register(eventBus);
        BMPotions.POTIONS.register(eventBus);
        BMStructures.STRUCTURES.register(eventBus);
        BMLootModifiers.LOOT_MODIFIERS.register(eventBus);
        BMParticleTypes.PARTICLES.register(eventBus);
        BMBlockEntities.BLOCK_ENTITIES.register(eventBus);
        BMContainers.CONTAINERS.register(eventBus);
        BMItemBehaviors.BEHAVIORS.register(eventBus);
        BMItemBehaviorEffectTypes.TYPES.register(eventBus);
        BMWandererSophieVariants.VARIANTS.register(eventBus);
        BMQueenLucyVariants.VARIANTS.register(eventBus);
        BMQueenLucyPetVariants.VARIANTS.register(eventBus);
        BMLootParameterSets.init();
        BMLootFunctions.init();
        BMSounds.registerSounds();
        BMRegistries.init();
        BMStatistics.init();

        eventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        RegistryKey<Biome> backFieldKey = RegistryKey.create(ForgeRegistries.Keys.BIOMES, BMBiomes.BACK_FIELD.getId());
        RegistryKey<Biome> originalBackFieldsKey = RegistryKey.create(ForgeRegistries.Keys.BIOMES, BMBiomes.ORIGINAL_BACK_FIELDS.getId());
        RegistryKey<Biome> modifiedBackFieldsKey = RegistryKey.create(ForgeRegistries.Keys.BIOMES, BMBiomes.MODIFIED_BACK_FIELDS.getId());
        RegistryKey<Biome> angelicWoodsKey = RegistryKey.create(ForgeRegistries.Keys.BIOMES, BMBiomes.ANGELIC_WOODS.getId());

        if (BMConfigs.COMMON_CONFIGS.backFieldGen.get()) BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(backFieldKey, 5));
        if (BMConfigs.COMMON_CONFIGS.originalBackFieldsGen.get()) {
            BiomeDictionary.addTypes(angelicWoodsKey, FOREST, OVERWORLD, DENSE);
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(originalBackFieldsKey, 15));
        }
        if (BMConfigs.COMMON_CONFIGS.modifiedBackFieldsGen.get()) {
            BiomeDictionary.addTypes(angelicWoodsKey, FOREST, OVERWORLD, MODIFIED, DENSE);
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(modifiedBackFieldsKey, 10));
        }
        if (BMConfigs.COMMON_CONFIGS.angelicWoodsGen.get()) {
            BiomeDictionary.addTypes(angelicWoodsKey, OVERWORLD, LUSH, OCEAN, SPARSE);
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(angelicWoodsKey, 12));
        }

        event.enqueueWork(() -> {
            // Entity Spawning, but it now works! I just needed both the old and this new code together instead of deleting the old code.
            if (BMConfigs.COMMON_CONFIGS.groundMobSpawningAljan.get()) {
                EntitySpawnPlacementRegistry.register(BMEntities.INSOMNIA_ZOMBIE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.ZOMBIE_FABRICIO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.ALJAMIC_BONES.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.SLEEPISH_SKELETON.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.AMARACAMELER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AmaracamelerEntity::checkAmaracamelerSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.MALAIKA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MalaikaEntity::checkMalaikaSpawnRules);
            }

            if (BMConfigs.COMMON_CONFIGS.groundMobSpawningBackFields.get()) {
                EntitySpawnPlacementRegistry.register(BMEntities.WANDERER_SOPHIE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TermianPatrollerEntity::checkTermianSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.ARCHER_LUCIA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TermianPatrollerEntity::checkTermianSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.SHY_ALCALYTE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TermianPatrollerEntity::checkTermianSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.KARATE_LUCIA.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TermianPatrollerEntity::checkTermianSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.ANGRY_SOPHIE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.INSOMNIA_SOPHIE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TermianPatrollerEntity::checkMonsterSpawnRules);
                EntitySpawnPlacementRegistry.register(BMEntities.ARCHER_INSOMNIA_SOPHIE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TermianPatrollerEntity::checkMonsterSpawnRules);
            }

            // Dynamic Registries
            // DynamicRegistries.REGISTRIES.put(BMRegistries.WANDERER_SOPHIE_VARIANT_REG, new DynamicRegistries.CodecHolder<>(BMRegistries.WANDERER_SOPHIE_VARIANT_REG, WandererSophieVariant.CODEC, null));
        });

        // Particles
        // Minecraft.getInstance().particleEngine.register(BMParticleTypes.HILLARY_FLAME.get(), HillaryFlameParticle.Factory::new);
        // Minecraft.getInstance().particleEngine.register(BMParticleTypes.JANTICAL.get(), JanticalParticle.Factory::new);
        // Minecraft.getInstance().particleEngine.register(BMParticleTypes.WARMTERM.get(), MidTermParticle.WarmtermFactory::new);
        // Minecraft.getInstance().particleEngine.register(BMParticleTypes.COLDTERM.get(), MidTermParticle.ColdtermFactory::new);

        // Wood Types for signs
        WoodType.register(BMWoodTypes.CRYSTALLINE_BIRCH);
        WoodType.register(BMWoodTypes.GOLDENWOOD);
        WoodType.register(BMWoodTypes.GUAVA);
        WoodType.register(BMWoodTypes.JABUTICABA);
        WoodType.register(BMWoodTypes.CORK_OAK);
        WoodType.register(BMWoodTypes.ALJANWOOD);
        WoodType.register(BMWoodTypes.ALJANCAP);
        WoodType.register(BMWoodTypes.INSOMNIAN);
        WoodType.register(BMWoodTypes.AVONDALIC_WILLOW);

        // Screens
        ScreenManager.register(BMContainers.CRATE.get(), CrateScreen::new);

        // Attribute Modifiers
        MidTermLongswordItem.MODIFIERS.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID.fromString("4925a97b-4689-4deb-9f89-8d046f480d0a"), "Mid-Term Longsword Reach Modifier", BMConfigs.COMMON_CONFIGS.midTermLongswordReachIncrease.get(), AttributeModifier.Operation.ADDITION));
        CarewniItem.MODIFIERS.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID.fromString("4925a97b-4689-4deb-9f89-8d046f480d0a"), "Carewni Reach Modifier", BMConfigs.COMMON_CONFIGS.carewniReachIncrease.get(),AttributeModifier.Operation.ADDITION));
        MechMechSwordItem.MODIFIERS.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID.fromString("4925a97b-4689-4deb-9f89-8d046f480d0a"), "Mech-Mech Reach Modifier", BMConfigs.COMMON_CONFIGS.mechMechReachDecrease.get(), AttributeModifier.Operation.ADDITION));

        // Other Things to Load
        BMPotions.addPotionRecipes();
        BMDimensions.init();
        BMConfiguredCarvers.init();
        BMVanillaCompatibility.initCompats();
        BMSurfaceBuilders.init();
        BMStructures.setupStructures();

        // Spawn Eggs
        BMSpawnEggItem.initBackMathEggs();

        // Dispense Behaviors
        DispenserBlock.registerBehavior(Items.ENDER_EYE, new EyeOfEnderDispenseBehavior());
        DispenserBlock.registerBehavior(Items.MILK_BUCKET, new CrystallizerRecipesDispenseBehavior());
        DispenserBlock.registerBehavior(AxolotlTest.HILLARY_BUCKET.get(), new BucketDispenseBehavior());
        DispenserBlock.registerBehavior(AxolotlTest.MILKLLARY_BUCKET.get(), new BucketDispenseBehavior());
        DispenserBlock.registerBehavior(AxolotlTest.LIQUID_ALJAME_BUCKET.get(), new BucketDispenseBehavior());
        DispenserBlock.registerBehavior(AxolotlTest.LIQUID_MANGA_BUCKET.get(), new BucketDispenseBehavior());
        DispenserBlock.registerBehavior(AxolotlTest.LIQUEFIED_MONSTER_BUCKET.get(), new BucketDispenseBehavior());
        DispenserBlock.registerBehavior(AxolotlTest.SLEEPISHWATER_BUCKET.get(), new BucketDispenseBehavior());
    }
}
