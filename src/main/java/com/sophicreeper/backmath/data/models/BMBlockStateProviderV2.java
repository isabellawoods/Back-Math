package com.sophicreeper.backmath.data.models;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.block.BMBlocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

import static com.sophicreeper.backmath.block.BMBlocks.*;

// June (04/08/2024): Version 2 of the block state and model provider.
// Fail count: 17
// Fails:
//   - No "block/devil_trapdoor" texture;
//   - "Cannot set models for a state that has already been configured";
//   - No "backmath:block/devil_bricks_door_bottom" texture;
//   - No "guarana_fruit_leaves" texture;
//   - Aljameed Pressure Plate doesn't have "powered (true/false)" property;
//   - No "block/aljamic_farmland_side_moist" texture;
//   - No "block/aljamic_onions_stage4" texture;
//   - No "milkllary_block_bottom" texture;
//   - No "jantical_block_bottom" texture;
// Now works!
//   - Duplicates (blockstates/acacia_grape_vine_post);
//   - Duplicates (blockstates/hardened_amaracamel_block);
//   - Duplicates (models/block/hardened_amaracamel_block);
//   - No "block/crystalline_birch_leaves" block model;
//   - No "block/grape_vine_posts/crystalline_birch" block model;
//   - No "block/hillaried_stone_pillar" block model;
//   - No "block/obsidian_infused_mid_term_block" block model.
// Works again!
// Fix: Rename the "_powered" model of pressure plates to "_down";
// Fix: Remove "waterlogged" states from grape vine posts and lanterns.
//   - No "block/smooth_aljanstone_slab_side" block model;
public class BMBlockStateProviderV2 extends BMBlockStateModels {
    public BMBlockStateProviderV2(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, BackMath.MOD_ID, fileHelper);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Back Math - Block State Definitions and Models V2";
    }

    @Override
    protected void registerStatesAndModels() {
        // Block Families
        blockFamily(modLoc("block/devil_block"), "devil").fullBlock(DEVIL_BLOCK.get()).stairs(DEVIL_STAIRS.get()).slab(DEVIL_SLAB.get()).wall(DEVIL_WALL.get()).fence(DEVIL_FENCE.get()).fenceGate(DEVIL_FENCE_GATE.get()).door(DEVIL_DOOR.get())
                .trapdoor(DEVIL_TRAPDOOR.get()).pressurePlate(DEVIL_PRESSURE_PLATE.get()).button(DEVIL_BUTTON.get()).concrete(RED_YELLOW_CONCRETE_POWDER.get(), RED_YELLOW_CONCRETE.get()).terracotta(RED_YELLOW_TERRACOTTA.get(),
                        RED_YELLOW_GLAZED_TERRACOTTA.get()).wool(RED_YELLOW_WOOL.get(), RED_YELLOW_CARPET.get()).stainedGlass(RED_YELLOW_STAINED_GLASS.get(), RED_YELLOW_STAINED_GLASS_PANE.get()).build();
        blockFamily(modLoc("block/devil_bricks"), "devil_bricks").fullBlock(DEVIL_BRICKS.get()).stairs(DEVIL_BRICK_STAIRS.get()).slab(DEVIL_BRICK_SLAB.get()).wall(DEVIL_BRICK_WALL.get()).build();
        blockFamily(modLoc("block/angelic_block"), "angelic").fullBlock(ANGELIC_BLOCK.get()).door(ANGELIC_DOOR.get()).trapdoor(ANGELIC_TRAPDOOR.get()).pressurePlate(ANGELIC_PRESSURE_PLATE.get()).button(ANGELIC_BUTTON.get()).build();
        blockFamily(modLoc("block/angelic_bricks"), "angelic_bricks").fullBlock(ANGELIC_BRICKS.get()).stairs(ANGELIC_BRICK_STAIRS.get()).slab(ANGELIC_BRICK_SLAB.get()).wall(ANGELIC_BRICK_WALL.get()).build();
        blockFamily(modLoc("block/mid_term_block_side"), "mid_term").door(MID_TERM_DOOR.get()).trapdoor(MID_TERM_TRAPDOOR.get()).build();
        blockFamily(modLoc("block/obsidian_infused_mid_term_block_side"), "obsidian_infused_mid_term").door(OBSIDIAN_INFUSED_MID_TERM_DOOR.get()).trapdoor(OBSIDIAN_INFUSED_MID_TERM_TRAPDOOR.get()).build();

        // Wood Block Families
        blockFamily(modLoc("block/crystalline_birch_planks"), "crystalline_birch").log(CRYSTALLINE_BIRCH_LOG.get(), CRYSTALLINE_BIRCH_WOOD.get(), STRIPPED_CRYSTALLINE_BIRCH_LOG.get(), STRIPPED_CRYSTALLINE_BIRCH_WOOD.get())
                .fullBlock(CRYSTALLINE_BIRCH_LEAVES.get()).fullBlock(CRYSTALLINE_BIRCH_PLANKS.get()).stairs(CRYSTALLINE_BIRCH_STAIRS.get()).slab(CRYSTALLINE_BIRCH_SLAB.get()).fence(CRYSTALLINE_BIRCH_FENCE.get()).fenceGate(CRYSTALLINE_BIRCH_FENCE_GATE.get())
                .door(CRYSTALLINE_BIRCH_DOOR.get()).trapdoor(CRYSTALLINE_BIRCH_TRAPDOOR.get()).button(CRYSTALLINE_BIRCH_BUTTON.get()).pressurePlate(CRYSTALLINE_BIRCH_PRESSURE_PLATE.get()).grapeVinePost(CRYSTALLINE_BIRCH_GRAPE_VINE_POST.get())
                .ladder(CRYSTALLINE_BIRCH_LADDER.get()).sign(CRYSTALLINE_BIRCH_SIGN.get(), CRYSTALLINE_BIRCH_WALL_SIGN.get()).build();

        // Other Blocks
        simpleBlock(DEVIL_ORE.get());
        simpleBlock(DEEPSLATE_DEVIL_ORE.get());
        simpleBlock(NETHER_DEVIL_ORE.get());
        simpleBlock(CHRISTIAN_MID_TERM_BLOCK.get());
        simpleBlock(CHRISTIAN_MID_TERM_ANGELIC_ALLOY_BLOCK.get(), models().cubeBottomTop("christian_mid_term_angelic_alloy_block", modLoc("block/christian_mid_term_angelic_alloy_block"), modLoc("block/angelic_block"), modLoc("block/christian_mid_term_block")));
        simpleBlock(CHRISTIAN_MID_TERM_DEVIL_ALLOY_BLOCK.get(), models().cubeBottomTop("christian_mid_term_devil_alloy_block", modLoc("block/christian_mid_term_devil_alloy_block"), modLoc("block/devil_block"), modLoc("block/christian_mid_term_block")));
        simpleBlock(DEVIL_ANGELIC_ALLOY_BLOCK.get(), models().cubeBottomTop("devil_angelic_alloy_block", modLoc("block/devil_angelic_alloy_block"), modLoc("block/angelic_block"), modLoc("block/devil_block")));
        simpleBlock(MID_TERM_ORE.get());
        simpleBlock(OBSIDIAN_MID_TERM_ORE.get());
        simpleBlock(MID_TERM_BLOCK.get(), models().cubeBottomTop("mid_term_block", modLoc("block/mid_term_block_side"), modLoc("block/mid_term_block_bottom"), modLoc("block/mid_term_block_top")));
        simpleBlock(OBSIDIAN_INFUSED_MID_TERM_BLOCK.get(), models().cubeBottomTop("obsidian_infused_mid_term_block", modLoc("block/obsidian_infused_mid_term_block_side"), modLoc("block/mid_term_block_bottom"), modLoc("block/mid_term_block_top")));
        simpleBlock(RAW_MID_TERM_BLOCK.get());
        simpleBlock(RAW_DEVIL_BLOCK.get());
        simpleBlock(CHISELED_DEVIL_BLOCK.get());
        simpleBlock(CHISELED_DEVIL_BLOCK_SOPHIE.get());
        simplePlantWithPotted(FRIED_EGG_FLOWER.get(), POTTED_FRIED_EGG_FLOWER.get(), modLoc("block/cooked_egg_flower"));
        simplePlantWithPotted(TURTLE_FRIED_EGG_FLOWER.get(), POTTED_TURTLE_FRIED_EGG_FLOWER.get(), modLoc("block/turtle_fried_egg_flower"));
        simpleBlock(RAW_ANGELIC_BLOCK.get());
        fruitLeavesV2(GUARANA_OAK_LEAVES.get(), mcLoc("block/oak_leaves"), "guaranas");
        fruitLeavesV2(MANGO_OAK_LEAVES.get(), mcLoc("block/oak_leaves"), "mangoes");
        fruitLeavesV2(MANGAED_MANGO_OAK_LEAVES.get(), mcLoc("block/oak_leaves"), "mangaed_mangoes");
        fruitLeaves(GRAPE_VINE_LEAVES.get(), modLoc("block/spruce_fruit_leaves"), modLoc("block/grape_fruit_leaves"));
        fruitLeavesV2(ALJAME_BIRCH_LEAVES.get(), mcLoc("block/birch_leaves"), "aljames");
        fruitLeaves(PINEAPPLE_OAK_LEAVES.get(), modLoc("block/oak_fruit_leaves"), modLoc("block/pineapple_fruit_leaves"));
        fruitLeavesV2(LEMON_OAK_LEAVES.get(), mcLoc("block/oak_leaves"), "lemons");
        fruitLeavesV2(ORANGE_OAK_LEAVES.get(), mcLoc("block/oak_leaves"), "oranges");
        fruitLeavesV2(BANANA_JUNGLE_LEAVES.get(), mcLoc("block/jungle_leaves"), "bananas");
        simplePlantWithPotted(GUARANA_OAK_SAPLING.get(), POTTED_GUARANA_OAK_SAPLING.get(), modLoc("block/guarana_oak_sapling"));
        simplePlantWithPotted(MANGO_OAK_SAPLING.get(), POTTED_MANGO_OAK_SAPLING.get(), modLoc("block/mango_oak_sapling"));
        simplePlantWithPotted(MANGAED_MANGO_OAK_SAPLING.get(), POTTED_MANGAED_MANGO_OAK_SAPLING.get(), modLoc("block/mangaed_mango_oak_sapling"));
        simplePlantWithPotted(GRAPE_VINE_SAPLING.get(), POTTED_GRAPE_VINE_SAPLING.get(), modLoc("block/grape_vine_sapling"));
        simplePlantWithPotted(ALJAME_BIRCH_SAPLING.get(), POTTED_ALJAME_BIRCH_SAPLING.get(), modLoc("block/aljame_birch_sapling"));
        simplePlantWithPotted(PINEAPPLE_OAK_SAPLING.get(), POTTED_PINEAPPLE_OAK_SAPLING.get(), modLoc("block/pineapple_oak_sapling"));
        simplePlantWithPotted(LEMON_OAK_SAPLING.get(), POTTED_LEMON_OAK_SAPLING.get(), modLoc("block/lemon_oak_sapling"));
        simplePlantWithPotted(ORANGE_OAK_SAPLING.get(), POTTED_ORANGE_OAK_SAPLING.get(), modLoc("block/orange_oak_sapling"));
        simplePlantWithPotted(BANANA_JUNGLE_SAPLING.get(), POTTED_BANANA_JUNGLE_SAPLING.get(), modLoc("block/banana_jungle_sapling"));
        grapeVinePost(OAK_GRAPE_VINE_POST.get(), mcLoc("block/oak_planks"));
        grapeVinePost(SPRUCE_GRAPE_VINE_POST.get(), mcLoc("block/spruce_planks"));
        grapeVinePost(BIRCH_GRAPE_VINE_POST.get(), mcLoc("block/birch_planks"));
        grapeVinePost(JUNGLE_GRAPE_VINE_POST.get(), mcLoc("block/jungle_planks"));
        grapeVinePost(ACACIA_GRAPE_VINE_POST.get(), mcLoc("block/acacia_planks"));
        grapeVinePost(DARK_OAK_GRAPE_VINE_POST.get(), mcLoc("block/dark_oak_planks"));
        grapeVinePost(CRIMSON_GRAPE_VINE_POST.get(), mcLoc("block/crimson_planks"));
        grapeVinePost(WARPED_GRAPE_VINE_POST.get(), mcLoc("block/warped_planks"));
        lantern(HILLARY_LANTERN.get(), false);
        lantern(MID_TERM_HILLARY_LANTERN.get(), true);
        lantern(MID_TERM_LANTERN.get(), true);
        lantern(MID_TERM_SOUL_LANTERN.get(), true);
        simplePlantWithPotted(RED_YELLOW_ALLIUM.get(), POTTED_RED_YELLOW_ALLIUM.get(), modLoc("block/red_yellow_flower"));
        simpleBlock(ANGELIC_ORE.get());
        simpleBlock(DEEPSLATE_ANGELIC_ORE.get());
        cake(HILLARY_CAKE.get());
        cake(MILKLLARY_CAKE.get());
        fluid(HILLARY.get(), modLoc("fluid/hilary_still"));
        fluid(MILKLLARY.get(), modLoc("fluid/milklary_still"));
        fluid(LIQUID_ALJAME.get(), modLoc("fluid/liquid_aljame"));
        fluid(LIQUID_MANGA.get(), modLoc("fluid/liquid_manga"));
        fluid(LIQUEFIED_MONSTER.get(), modLoc("fluid/liquefied_monster"));
        fluid(SLEEPISHWATER.get(), modLoc("fluid/sleepishwater"));
        simpleBlock(HILLARY_TORCH.get(), models().torch("hillary_torch", modLoc("block/hillary_torch")));
        getVariantBuilder(HILLARY_WALL_TORCH.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(wallTorch("hillary_wall_torch", modLoc("block/hillary_torch"))).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                .toYRot() + 90).build());
        crystallizer(CRYSTALLIZER.get());
        crystallineCrystallizer(CRYSTALLINE_CRYSTALLIZER.get());
        head(WANDERER_SOPHIE_HEAD.get());
        head(WANDERER_SOPHIE_WALL_HEAD.get());
        head(ANGRY_SOPHIE_HEAD.get());
        head(ANGRY_SOPHIE_WALL_HEAD.get());
        simpleBlock(CRYSTALLINE_ANGELIC_ORE.get());
        simpleBlock(CRYSTALLINE_ANGELIC_BLOCK.get());
        toy(ALICE_TOY.get());
        toy(ALAN_TOY.get());
        // Innovator Toy
        // Tyler Toy
        // Malena Toy
        simpleBlock(ANGELICAL_CASING.get());
        // Meal Cooker
        // Queen Lucy Relic
        head(QUEEN_LUCY_HEAD.get());
        head(QUEEN_LUCY_WALL_HEAD.get());
        simplePlantWithPotted(CRYSTALLINE_BIRCH_SAPLING.get(), POTTED_CRYSTALLINE_BIRCH_SAPLING.get(), modLoc("block/crystalline_birch_sapling"));
        simpleBlock(CRACKED_ANGELIC_BRICKS.get());
        simpleBlock(CRYSTALLINE_BIRCH_LEAVES.get());
        emotionalSquid(TITO.get());
        emotionalSquid(TOTI.get());

        // ---------------------------------------------------
        // TODO: BACK MATH 1.7.0: FABRICIOS TA... I MEAN, ALJAMIC WARS CONTENT
        // ---------------------------------------------------

        // Block Families
        blockFamily(modLoc("block/aljameed_block"), "aljameed").fullBlock(ALJAMEED_BLOCK.get()).weightedPressurePlate(ALJAMEED_PRESSURE_PLATE.get()).button(ALJAMEED_BUTTON.get()).build();
        blockFamily(modLoc("block/moonering_block"), "moonering").fullBlock(MOONERING_BLOCK.get()).weightedPressurePlate(MOONERING_PRESSURE_PLATE.get()).button(MOONERING_BUTTON.get()).build();

        // Miscellaneous
        simpleBlock(ALJAMEED_ORE.get());
        simpleBlock(MOONERING_ORE.get());
        simpleBlock(ALJAMIC_COPPER_ORE.get());
        simpleBlock(ALJAMIC_TIN_ORE.get());
        simpleBlock(JANTIC_ORE.get());
        simpleBlock(SLEEPINGSTONE_ALJAMEED_ORE.get());
        simpleBlock(SLEEPINGSTONE_MOONERING_ORE.get());
        simpleBlock(SLEEPINGSTONE_ALJAMIC_COPPER_ORE.get());
        simpleBlock(SLEEPINGSTONE_ALJAMIC_TIN_ORE.get());
        simpleBlock(SLEEPINGSTONE_JANTIC_ORE.get());
        simpleBlock(CHARJAN_COAL_BLOCK.get());
        simpleBlock(HARDENED_AMARACAMEL_BLOCK.get(), models().cubeTop("hardened_amaracamel_block", modLoc("block/hardened_amaracamel_block_side"), modLoc("block/hardened_amaracamel_block_top")));
        simpleBlock(MILKLLARY_BLOCK.get(), models().cubeBottomTop("milkllary_block", modLoc("block/milkllary_block_side"), modLoc("block/milkllary_block_bottom"), modLoc("block/milkllary_block_top")));
        simpleBlock(RAW_ALJAMEED_BLOCK.get());
        simpleBlock(RAW_MOONER_BLOCK.get());
        simplePlantWithPotted(ALJAN_TULIP.get(), POTTED_ALJAN_TULIP.get(), modLoc("block/aljan_tulip"));
        simplePlantWithPotted(POISON_ROSE.get(), POTTED_POISON_ROSE.get(), modLoc("block/poison_rose"));
        insomnianTulip(INSOMNIAN_TULIP.get());
        simpleBlock(POTTED_INSOMNIAN_TULIP.get(), models().withExistingParent("potted_insomnian_tulip", "block/flower_pot_cross").texture("plant", "block/insomnian_tulip_combined"));
        simplePlantWithPotted(ALJANSHROOM.get(), POTTED_ALJANSHROOM.get(), modLoc("block/aljanshroom"));
        simplePlantWithPotted(SLEEPSHROOM.get(), POTTED_SLEEPSHROOM.get(), modLoc("block/sleepshroom"));
        simplePlantWithPotted(SLEEPYSHROOM.get(), POTTED_SLEEPYSHROOM.get(), modLoc("block/sleepyshroom"));
        bag(AMARACAMEL_BATTER_BAG.get());
        bag(BOOT_PACK.get());
        grassBlock(ALJAMIC_GRASS_BLOCK.get(), modLoc("block/aljamic_grass_block"), modLoc("block/aljamic_dirt"));
        simpleBlock(ALJAMIC_DIRT.get());
        getVariantBuilder(ALJAMIC_FARMLAND.get()).forAllStates(state -> {
            String isMoist = moistIndex(state.getValue(BlockStateProperties.MOISTURE));
            return ConfiguredModel.builder().modelFile(models().getBuilder("aljamic_farmland" + isMoist).parent(models().getExistingFile(modLoc("block/template_pixel_short_block"))).texture("top", modLoc("block/aljamic_farmland" + isMoist)).texture(
                    "side", modLoc("block/aljamic_farmland_side" + isMoist)).texture("dirt", modLoc("block/aljamic_dirt"))).build();
        });
        head(ALJAMIC_BONES_SKULL.get());
        head(ALJAMIC_BONES_WALL_SKULL.get());
        head(SLEEPISH_SKELETON_SKULL.get());
        head(SLEEPISH_SKELETON_WALL_SKULL.get());
        head(ZOMBIE_FABRICIO_HEAD.get());
        head(ZOMBIE_FABRICIO_WALL_HEAD.get());
        simpleBlock(BMBlocks.CHARJAN_WOOD_TORCH.get(), models().torch("charjan_wood_torch", modLoc("block/charjan_wood_torch")));
        getVariantBuilder(BMBlocks.CHARJAN_WOOD_WALL_TORCH.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(wallTorch("charjan_wood_wall_torch",
                modLoc("block/charjan_wood_torch"))).rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90).build());
        wheatIndexCrop(CARAMELED_WHEAT.get(), WILD_CARAMELED_WHEAT.get(), "carameled_wheat");
        potatoIndexCrop(ALJAMIC_ONIONS.get(), WILD_ALJAMIC_ONIONS.get(), "aljamic_onions");
        chain(DEVIL_CHAIN.get());
        chain(ANGELIC_CHAIN.get());
        chain(CHRISTIAN_MID_TERM_CHAIN.get());
        chain(MILKLLARY_CHAIN.get());
        chain(MID_HILLARY_CHAIN.get());
        chain(MID_TERM_CHAIN.get());
        chain(OBSIDIAN_INFUSED_MID_TERM_CHAIN.get());
        chain(ALJAMEED_CHAIN.get());
        chain(MOONERING_CHAIN.get());
        // Sticky Amaracamel Block

        // Aljanstone
        blockFamily(modLoc("block/aljanstone"), "aljanstone").fullBlock(ALJANSTONE.get()).stairs(ALJANSTONE_STAIRS.get()).slab(ALJANSTONE_SLAB.get()).wall(ALJANSTONE_WALL.get()).build();
        blockFamily(modLoc("block/cobbled_aljanstone"), "cobbled_aljanstone").fullBlock(COBBLED_ALJANSTONE.get()).stairs(COBBLED_ALJANSTONE_STAIRS.get()).slab(COBBLED_ALJANSTONE_SLAB.get()).wall(COBBLED_ALJANSTONE_WALL.get()).build();
        blockFamily(modLoc("block/aljanstone_bricks"), "aljanstone_bricks").fullBlock(ALJANSTONE_BRICKS.get()).stairs(ALJANSTONE_BRICK_STAIRS.get()).slab(ALJANSTONE_BRICK_SLAB.get()).wall(ALJANSTONE_BRICK_WALL.get()).build();
        blockFamily(modLoc("block/smooth_aljanstone"), "smooth_aljanstone").fullBlock(SMOOTH_ALJANSTONE.get()).doubleSlab(SMOOTH_ALJANSTONE_SLAB.get(), modLoc("block/smooth_aljanstone_slab_side")).build();
        simpleBlock(CHISELED_ALJANSTONE_FABRICIO.get());
        simpleBlock(CHISELED_ALJANSTONE_JUNE.get());
        simpleBlock(CHISELED_ALJANSTONE_SOPHIE.get());
        simpleBlock(CHISELED_ALJANSTONE_LUCIA.get());
        simpleBlock(CHISELED_ALJANSTONE_CREEPER.get());

        // Sleepingstone
        blockFamily(modLoc("block/sleepingstone"), "sleepingstone").fullBlock(SLEEPINGSTONE.get()).stairs(SLEEPINGSTONE_STAIRS.get()).slab(SLEEPINGSTONE_SLAB.get()).wall(SLEEPINGSTONE_WALL.get()).build();
        blockFamily(modLoc("block/polished_sleepingstone"), "polished_sleepingstone").fullBlock(POLISHED_SLEEPINGSTONE.get()).stairs(POLISHED_SLEEPINGSTONE_STAIRS.get()).slab(POLISHED_SLEEPINGSTONE_SLAB.get())
                .wall(POLISHED_SLEEPINGSTONE_WALL.get()).build();
        blockFamily(modLoc("block/sleepingstone_bricks"), "sleepingstone_bricks").fullBlock(SLEEPINGSTONE_BRICKS.get()).stairs(SLEEPINGSTONE_BRICK_STAIRS.get()).slab(SLEEPINGSTONE_BRICK_SLAB.get()).wall(SLEEPINGSTONE_BRICK_WALL.get())
                .build();
        simpleBlock(INSOGRAVEL.get());
        simpleBlock(CHISELED_SLEEPINGSTONE_FABRICIO.get());
        simpleBlock(CHISELED_SLEEPINGSTONE_JUNE.get());
        simpleBlock(CHISELED_SLEEPINGSTONE_SOPHIE.get());
        simpleBlock(CHISELED_SLEEPINGSTONE_LUCIA.get());
        simpleBlock(CHISELED_SLEEPINGSTONE_CREEPER.get());
        // Aljan Portal Stand

        // Aljanwood
        blockFamily(modLoc("block/aljanwood_planks"), "aljanwood").log(ALJANWOOD_LOG.get(), ALJANWOOD_WOOD.get(), STRIPPED_ALJANWOOD_LOG.get(), STRIPPED_ALJANWOOD_WOOD.get()).fullBlock(ALJANWOOD_PLANKS.get())
                .stairs(ALJANWOOD_STAIRS.get()).slab(ALJANWOOD_SLAB.get()).fence(ALJANWOOD_FENCE.get()).fenceGate(ALJANWOOD_FENCE_GATE.get()).door(ALJANWOOD_DOOR.get()).trapdoor(ALJANWOOD_TRAPDOOR.get())
                .grapeVinePost(ALJANWOOD_GRAPE_VINE_POST.get()).pressurePlate(ALJANWOOD_PRESSURE_PLATE.get()).button(ALJANWOOD_BUTTON.get()).ladder(ALJANWOOD_LADDER.get()).sign(ALJANWOOD_SIGN.get(), ALJANWOOD_WALL_SIGN.get()).build();
        simplePlantWithPotted(ALJANWOOD_SAPLING.get(), POTTED_ALJANWOOD_SAPLING.get(), modLoc("block/aljanwood_sapling"));
        leaves(ALJANWOOD_LEAVES.get(), mcLoc("block/oak_leaves"));
        charjanTorch(CHARJAN_ALJANWOOD_TORCH.get(), CHARJAN_ALJANWOOD_WALL_TORCH.get(), "aljanwood");

        // Aljancap
        blockFamily(modLoc("block/aljancap_planks"), "aljancap").log(ALJANCAP_LOG.get(), ALJANCAP_WOOD.get(), STRIPPED_ALJANCAP_LOG.get(), STRIPPED_ALJANCAP_WOOD.get()).fullBlock(ALJANCAP_PLANKS.get()).stairs(ALJANCAP_STAIRS.get())
                .slab(ALJANCAP_SLAB.get()).fence(ALJANCAP_FENCE.get()).fenceGate(ALJANCAP_FENCE_GATE.get()).grapeVinePost(ALJANCAP_GRAPE_VINE_POST.get()).pressurePlate(ALJANCAP_PRESSURE_PLATE.get()).button(ALJANCAP_BUTTON.get())
                .ladder(ALJANCAP_LADDER.get()).door(ALJANCAP_DOOR.get()).trapdoor(ALJANCAP_TRAPDOOR.get()).sign(ALJANCAP_SIGN.get(), ALJANCAP_WALL_SIGN.get()).build();
        simplePlantWithPotted(ALJANCAP_SAPLING.get(), POTTED_ALJANCAP_SAPLING.get(), modLoc("block/aljancap_sapling"));
        leaves(ALJANCAP_LEAVES.get(), mcLoc("block/oak_leaves"));
        leaves(AMARACAP_LEAVES.get(), mcLoc("block/acacia_leaves"));
        charjanTorch(CHARJAN_ALJANCAP_TORCH.get(), CHARJAN_ALJANCAP_WALL_TORCH.get(), "aljancap");

        // Insomnian
        blockFamily(modLoc("block/insomnian_planks"), "insomnian").log(INSOMNIAN_LOG.get(), INSOMNIAN_WOOD.get(), STRIPPED_INSOMNIAN_LOG.get(), STRIPPED_INSOMNIAN_WOOD.get()).fullBlock(INSOMNIAN_PLANKS.get())
                .stairs(INSOMNIAN_STAIRS.get()).slab(INSOMNIAN_SLAB.get()).fence(INSOMNIAN_FENCE.get()).fenceGate(INSOMNIAN_FENCE_GATE.get()).grapeVinePost(INSOMNIAN_GRAPE_VINE_POST.get()).pressurePlate(INSOMNIAN_PRESSURE_PLATE.get())
                .button(INSOMNIAN_BUTTON.get()).ladder(INSOMNIAN_LADDER.get()).door(INSOMNIAN_DOOR.get()).trapdoor(INSOMNIAN_TRAPDOOR.get()).sign(INSOMNIAN_SIGN.get(), INSOMNIAN_WALL_SIGN.get()).build();
        simplePlantWithPotted(INSOMNIAN_SAPLING.get(), POTTED_INSOMNIAN_SAPLING.get(), modLoc("block/insomnian_sapling"));
        leaves(INSOMNIAN_LEAVES.get(), mcLoc("block/oak_leaves"));
        charjanTorch(CHARJAN_INSOMNIAN_TORCH.get(), CHARJAN_INSOMNIAN_WALL_TORCH.get(), "insomnian");

        // Todo: Back Math 1.8.0: Bountifully Expansive
        // Tabu
        simpleBlock(TABU.get());
        horizontalBlock(GLAZED_TABU.get(), modLoc("block/glazed_tabu"), modLoc("block/glazed_tabu"), modLoc("block/glazed_tabu"));
        blockFamily(modLoc("block/cubic_tabu"), "cubic_tabu").fullBlock(CUBIC_TABU.get()).stairs(CUBIC_TABU_STAIRS.get()).slab(CUBIC_TABU_SLAB.get()).build();
        blockFamily(modLoc("block/tabu_mosaic"), "tabu_mosaic").fullBlock(TABU_MOSAIC.get()).stairs(TABU_MOSAIC_STAIRS.get()).slab(TABU_MOSAIC_SLAB.get()).build();
        axisBlock((RotatedPillarBlock) TABU_PILLAR.get(), modLoc("block/tabu_pillar"), modLoc("block/tabu_pillar_top"));

        // Hillaried Stone
        blockFamily(modLoc("block/hillaried_stone"), "hillaried_stone").fullBlock(HILLARIED_STONE.get()).stairs(HILLARIED_STONE_STAIRS.get()).slab(HILLARIED_STONE_SLAB.get()).wall(HILLARIED_STONE_WALL.get()).build();
        axisBlock((RotatedPillarBlock) HILLARIED_STONE_PILLAR.get(), modLoc("block/hillaried_stone_pillar"), modLoc("block/hillaried_stone_pillar_top"));
        simpleBlock(CHISELED_HILLARIED_STONE.get());
        simpleBlock(CHISELED_HILLARIED_STONE_BUCKET.get());

        // Cork Oak
        blockFamily(modLoc("block/cork_oak_planks"), "cork_oak").log(CORK_OAK_LOG.get(), CORK_OAK_WOOD.get(), STRIPPED_CORK_OAK_LOG.get(), STRIPPED_CORK_OAK_WOOD.get()).fullBlock(CORK_OAK_PLANKS.get())
                .stairs(CORK_OAK_STAIRS.get()).slab(CORK_OAK_SLAB.get()).fence(CORK_OAK_FENCE.get()).fenceGate(CORK_OAK_FENCE_GATE.get()).door(CORK_OAK_DOOR.get()).trapdoor(CORK_OAK_TRAPDOOR.get())
                .grapeVinePost(CORK_OAK_GRAPE_VINE_POST.get()).pressurePlate(CORK_OAK_PRESSURE_PLATE.get()).button(CORK_OAK_BUTTON.get()).ladder(CORK_OAK_LADDER.get()).sign(CORK_OAK_SIGN.get(), CORK_OAK_WALL_SIGN.get()).build();
        simplePlantWithPotted(CORK_OAK_SAPLING.get(), POTTED_CORK_OAK_SAPLING.get(), modLoc("block/cork_oak_sapling"));
        leaves(CORK_OAK_LEAVES.get(), modLoc("block/cork_oak_leaves"));
        charjanTorch(CHARJAN_CORK_OAK_TORCH.get(), CHARJAN_CORK_OAK_WALL_TORCH.get(), "cork_oak");

        // Guava
        blockFamily(modLoc("block/guava_planks"), "guava").log(GUAVA_LOG.get(), GUAVA_WOOD.get(), STRIPPED_GUAVA_LOG.get(), STRIPPED_GUAVA_WOOD.get()).fullBlock(GUAVA_PLANKS.get())
                .stairs(GUAVA_STAIRS.get()).slab(GUAVA_SLAB.get()).fence(GUAVA_FENCE.get()).fenceGate(GUAVA_FENCE_GATE.get()).door(GUAVA_DOOR.get()).trapdoor(GUAVA_TRAPDOOR.get())
                .grapeVinePost(GUAVA_GRAPE_VINE_POST.get()).pressurePlate(GUAVA_PRESSURE_PLATE.get()).button(GUAVA_BUTTON.get()).ladder(GUAVA_LADDER.get()).sign(GUAVA_SIGN.get(), GUAVA_WALL_SIGN.get()).build();
        simplePlantWithPotted(GUAVA_SAPLING.get(), POTTED_GUAVA_SAPLING.get(), modLoc("block/guava_sapling"));
        simpleBlock(GUAVA_LEAVES.get());
        charjanTorch(CHARJAN_GUAVA_TORCH.get(), CHARJAN_GUAVA_WALL_TORCH.get(), "guava");

        // Jabuticaba
        blockFamily(modLoc("block/jabuticaba_planks"), "jabuticaba").log(JABUTICABA_LOG.get(), JABUTICABA_WOOD.get(), STRIPPED_JABUTICABA_LOG.get(), STRIPPED_JABUTICABA_WOOD.get()).fullBlock(JABUTICABA_PLANKS.get())
                .stairs(JABUTICABA_STAIRS.get()).slab(JABUTICABA_SLAB.get()).fence(JABUTICABA_FENCE.get()).fenceGate(JABUTICABA_FENCE_GATE.get()).door(JABUTICABA_DOOR.get()).trapdoor(JABUTICABA_TRAPDOOR.get())
                .grapeVinePost(JABUTICABA_GRAPE_VINE_POST.get()).pressurePlate(JABUTICABA_PRESSURE_PLATE.get()).button(JABUTICABA_BUTTON.get()).ladder(JABUTICABA_LADDER.get()).sign(JABUTICABA_SIGN.get(), JABUTICABA_WALL_SIGN.get())
                .build();
        simplePlantWithPotted(JABUTICABA_SAPLING.get(), POTTED_JABUTICABA_SAPLING.get(), modLoc("block/jabuticaba_sapling"));
        leaves(JABUTICABA_LEAVES.get(), modLoc("block/jabuticaba_leaves"));
        charjanTorch(CHARJAN_JABUTICABA_TORCH.get(), CHARJAN_JABUTICABA_WALL_TORCH.get(), "jabuticaba");

        // Goldenwood
        blockFamily(modLoc("block/goldenwood_planks"), "goldenwood").log(GOLDENWOOD_LOG.get(), GOLDENWOOD_WOOD.get(), STRIPPED_GOLDENWOOD_LOG.get(), STRIPPED_GOLDENWOOD_WOOD.get()).fullBlock(GOLDENWOOD_PLANKS.get())
                .stairs(GOLDENWOOD_STAIRS.get()).slab(GOLDENWOOD_SLAB.get()).fence(GOLDENWOOD_FENCE.get()).fenceGate(GOLDENWOOD_FENCE_GATE.get()).door(GOLDENWOOD_DOOR.get()).trapdoor(GOLDENWOOD_TRAPDOOR.get())
                .grapeVinePost(GOLDENWOOD_GRAPE_VINE_POST.get()).pressurePlate(GOLDENWOOD_PRESSURE_PLATE.get()).button(GOLDENWOOD_BUTTON.get()).ladder(GOLDENWOOD_LADDER.get()).sign(GOLDENWOOD_SIGN.get(), GOLDENWOOD_WALL_SIGN.get())
                .build();
        simplePlantWithPotted(GOLDENWOOD_SAPLING.get(), POTTED_GOLDENWOOD_SAPLING.get(), modLoc("block/goldenwood_sapling"));
        simplePlantWithPotted(ENCHANTED_GOLDENWOOD_SAPLING.get(), POTTED_ENCHANTED_GOLDENWOOD_SAPLING.get(), modLoc("block/enchanted_goldenwood_sapling"));
        simpleBlock(GOLDENWOOD_LEAVES.get());
        simpleBlock(ENCHANTED_GOLDENWOOD_LEAVES.get());
        charjanTorch(CHARJAN_GOLDENWOOD_TORCH.get(), CHARJAN_GOLDENWOOD_WALL_TORCH.get(), "goldenwood");

        // Miscellaneous
        simpleBlock(MID_HILLARY_BLOCK.get(), models().cubeTop("mid_hillary_block", modLoc("block/mid_hillary_block_side"), modLoc("block/milkllary_block_bottom")));
        // Leandro Toy
        // Teenager Alice Toy
        // Queen Lucy Pet Relic
        // Chocolate Naked Cake
        simplePlantWithPotted(ENDER_DRAGON_FRIED_EGG_FLOWER.get(), POTTED_ENDER_DRAGON_FRIED_EGG_FLOWER.get(), modLoc("block/ender_dragon_fried_egg_flower"));
        axisBlock((RotatedPillarBlock) ANGELIC_PILLAR.get(), modLoc("block/angelic_pillar"));
        blockFamily(modLoc("block/mossy_angelic_bricks"), "mossy_angelic_bricks").fullBlock(MOSSY_ANGELIC_BRICKS.get()).stairs(MOSSY_ANGELIC_BRICK_STAIRS.get()).slab(MOSSY_ANGELIC_BRICK_SLAB.get()).wall(MOSSY_ANGELIC_BRICK_WALL.get())
                .build();
        head(INSOMNIA_SOPHIE_HEAD.get());
        head(INSOMNIA_SOPHIE_WALL_HEAD.get());
        simpleBlock(EMERIOND_BLOCK.get());

        // Coldterm, Warmterm and Obsiditerm Related Blocks
        simpleBlock(COLDTERM_BLOCK.get());
        blockFamily(modLoc("block/coldterm_bricks"), "coldterm_bricks").fullBlock(COLDTERM_BRICKS.get()).stairs(COLDTERM_BRICK_STAIRS.get()).slab(COLDTERM_BRICK_SLAB.get()).wall(COLDTERM_BRICK_WALL.get()).build();
        simpleBlock(WARMTERM_BLOCK.get());
        blockFamily(modLoc("block/warmterm_bricks"), "warmterm_bricks").fullBlock(WARMTERM_BRICKS.get()).stairs(WARMTERM_BRICK_STAIRS.get()).slab(WARMTERM_BRICK_SLAB.get()).wall(WARMTERM_BRICK_WALL.get()).build();
        simpleBlock(CHISELED_OBSIDITERM.get());
        blockFamily(modLoc("block/obsiditerm_bricks"), "obsiditerm_bricks").fullBlock(OBSIDITERM_BRICKS.get()).stairs(OBSIDITERM_BRICK_STAIRS.get()).slab(OBSIDITERM_BRICK_SLAB.get()).wall(OBSIDITERM_BRICK_WALL.get()).build();

        // Aljan Content
        simpleBlock(ALJAMIC_SAND.get());
        simpleBlock(ALJAMIC_GLASS.get());
        paneBlock((PaneBlock) ALJAMIC_GLASS_PANE.get(), modLoc("block/aljamic_glass"), modLoc("block/aljamic_glass_pane_top"));
        simpleBlock(ALJAMIC_DIRT_PATH.get(), models().withExistingParent("aljamic_dirt_path", modLoc("block/template_pixel_short_block")).texture("top", modLoc("block/aljamic_dirt_path_top")).texture("side", modLoc(
                "block/aljamic_dirt_path_side")).texture("dirt", modLoc("block/aljamic_dirt")));
        simpleBlock(JANTICAL_BLOCK.get(), models().cubeBottomTop("jantical_block", modLoc("block/jantical_block_side"), modLoc("block/jantical_block_bottom"), modLoc("block/jantical_block_top")));
        chain(ALJANSTEEL_CHAIN.get());
        simpleBlock(ALJANSTEEL_BLOCK.get(), models().cubeTop("aljansteel_block", modLoc("block/aljansteel_block_side"), modLoc("block/aljansteel_block_end")));
        simpleBlock(RAW_ALJAMIC_COPPER_BLOCK.get());
        simpleBlock(RAW_ALJAMIC_TIN_BLOCK.get());
        axisBlock((RotatedPillarBlock) CARAMELED_HAY_BALE.get(), modLoc("block/carameled_hay_bale_side"), modLoc("block/carameled_hay_bale_top"));
        charjanTorch(BMBlocks.CHARJAN_CRYSTALLINE_BIRCH_TORCH.get(), BMBlocks.CHARJAN_CRYSTALLINE_BIRCH_WALL_TORCH.get(), "crystalline_birch");
        charjanTorch(BMBlocks.CHARJAN_HILLARY_TORCH.get(), BMBlocks.CHARJAN_HILLARY_WALL_TORCH.get(), "hillary");
        charjanTorch(BMBlocks.CHARJAN_DEVIL_TORCH.get(), BMBlocks.CHARJAN_DEVIL_WALL_TORCH.get(), "devil");
        charjanTorch(BMBlocks.CHARJAN_ANGELIC_TORCH.get(), BMBlocks.CHARJAN_ANGELIC_WALL_TORCH.get(), "angelic");
        charjanTorch(BMBlocks.CHARJAN_MID_TERM_TORCH.get(), BMBlocks.CHARJAN_MID_TERM_WALL_TORCH.get(), "mid_term");
        charjanTorch(BMBlocks.CHARJAN_ALJAMEED_TORCH.get(), BMBlocks.CHARJAN_ALJAMEED_WALL_TORCH.get(), "aljameed");
        blockFamily(modLoc("block/aljan_light_blue_stained_glass"), "aljan_light_blue").stainedGlass(ALJAN_LIGHT_BLUE_STAINED_GLASS.get(), ALJAN_LIGHT_BLUE_STAINED_GLASS_PANE.get()).build();
        blockFamily(modLoc("block/poison_brown_stained_glass"), "poison_brown").stainedGlass(POISON_BROWN_STAINED_GLASS.get(), POISON_BROWN_STAINED_GLASS_PANE.get()).build();
        blockFamily(modLoc("block/insomnian_stained_glass"), "insomnian").stainedGlass(INSOMNIAN_STAINED_GLASS.get(), INSOMNIAN_STAINED_GLASS_PANE.get()).build();

        // Avondalic Willow
        grassBlock(AVONDALIC_NYLIUM.get(), modLoc("block/avondalic_nylium"), modLoc("block/aljamic_dirt"));
        blockFamily(modLoc("block/avondalic_willow_planks"), "avondalic_willow").log(AVONDALIC_WILLOW_LOG.get(), AVONDALIC_WILLOW_WOOD.get(), STRIPPED_AVONDALIC_WILLOW_LOG.get(), STRIPPED_AVONDALIC_WILLOW_WOOD.get())
                .fullBlock(AVONDALIC_WILLOW_PLANKS.get()).stairs(AVONDALIC_WILLOW_STAIRS.get()).slab(AVONDALIC_WILLOW_SLAB.get()).fence(AVONDALIC_WILLOW_FENCE.get()).fenceGate(AVONDALIC_WILLOW_FENCE_GATE.get()).door(AVONDALIC_WILLOW_DOOR.get())
                .trapdoor(AVONDALIC_WILLOW_TRAPDOOR.get()).grapeVinePost(AVONDALIC_WILLOW_GRAPE_VINE_POST.get()).pressurePlate(AVONDALIC_WILLOW_PRESSURE_PLATE.get()).button(AVONDALIC_WILLOW_BUTTON.get()).ladder(AVONDALIC_WILLOW_LADDER.get())
                .sign(AVONDALIC_WILLOW_SIGN.get(), AVONDALIC_WILLOW_WALL_SIGN.get()).build();
        simplePlantWithPotted(AVONDALIC_WILLOW_SAPLING.get(), POTTED_AVONDALIC_WILLOW_SAPLING.get(), modLoc("block/avondalic_willow_sapling"));
        leaves(AVONDALIC_WILLOW_LEAVES.get(), mcLoc("block/acacia_leaves"));
        charjanTorch(CHARJAN_AVONDALIC_WILLOW_TORCH.get(), CHARJAN_AVONDALIC_WILLOW_WALL_TORCH.get(), "avondalic_willow");
    }
}
