package com.sophicreeper.backmath.data.tags;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.util.tag.BMBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BMBlockTagsProvider extends BlockTagsProvider {
    public BMBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper fileHelper) {
        super(generator, BackMath.MOD_ID, fileHelper);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Back Math - Block Tags";
    }

    @Override
    protected void addTags() {
        this.tag(BMBlockTags.STORAGE_BLOCKS_DEVIL).add(BMBlocks.DEVIL_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_ANGELIC).add(BMBlocks.ANGELIC_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_CHRISTIAN_MID_TERM).add(BMBlocks.CHRISTIAN_MID_TERM_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_WARMTERM).add(BMBlocks.WARMTERM_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_MID_TERM).add(BMBlocks.MID_TERM_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_COLDTERM).add(BMBlocks.COLDTERM_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_CHRISTIAN_MID_TERM_ANGELIC).add(BMBlocks.CHRISTIAN_MID_TERM_ANGELIC_ALLOY_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_CHRISTIAN_MID_TERM_DEVIL).add(BMBlocks.CHRISTIAN_MID_TERM_DEVIL_ALLOY_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_DEVIL_ANGELIC).add(BMBlocks.DEVIL_ANGELIC_ALLOY_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_OBSIDIAN_INFUSED_MID_TERM).add(BMBlocks.OBSIDIAN_INFUSED_MID_TERM_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_ALJAMEED).add(BMBlocks.ALJAMEED_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_ALJANSTEEL).add(BMBlocks.ALJANSTEEL_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_MOONERING).add(BMBlocks.MOONERING_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_CHARJAN_COAL).add(BMBlocks.CHARJAN_COAL_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_HARDENED_AMARACAMEL).add(BMBlocks.HARDENED_AMARACAMEL_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_MILKLLARY).add(BMBlocks.MILKLLARY_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_CRYSTALLINE_ANGELIC).add(BMBlocks.CRYSTALLINE_ANGELIC_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_RAW_DEVIL).add(BMBlocks.RAW_DEVIL_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_RAW_ANGELIC).add(BMBlocks.RAW_ANGELIC_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_RAW_MID_TERM).add(BMBlocks.RAW_MID_TERM_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_RAW_ALJAMIC_COPPER).add(BMBlocks.RAW_ALJAMIC_COPPER_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_RAW_ALJAMIC_TIN).add(BMBlocks.RAW_ALJAMIC_TIN_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_RAW_ALJAMEED).add(BMBlocks.RAW_ALJAMEED_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_RAW_MOONERING).add(BMBlocks.RAW_MOONER_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_MID_HILLARY).add(BMBlocks.MID_HILLARY_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_JANTICAL).add(BMBlocks.JANTICAL_BLOCK.get());
        this.tag(BMBlockTags.STORAGE_BLOCKS_EMERIOND).add(BMBlocks.EMERIOND_BLOCK.get());

        this.tag(BMBlockTags.ORES_DEVIL).add(BMBlocks.DEVIL_ORE.get()).add(BMBlocks.DEEPSLATE_DEVIL_ORE.get()).add(BMBlocks.NETHER_DEVIL_ORE.get());
        this.tag(BMBlockTags.ORES_ANGELIC).add(BMBlocks.ANGELIC_ORE.get()).add(BMBlocks.DEEPSLATE_ANGELIC_ORE.get());
        this.tag(BMBlockTags.ORES_MID_TERM).add(BMBlocks.MID_TERM_ORE.get()).add(BMBlocks.OBSIDIAN_MID_TERM_ORE.get());
        this.tag(BMBlockTags.ORES_CRYSTALLINE_ANGELIC).add(BMBlocks.CRYSTALLINE_ANGELIC_ORE.get());
        this.tag(BMBlockTags.ORES_ALJAMEED).add(BMBlocks.ALJAMEED_ORE.get()).add(BMBlocks.SLEEPINGSTONE_ALJAMEED_ORE.get());
        this.tag(BMBlockTags.ORES_MOONERING).add(BMBlocks.MOONERING_ORE.get()).add(BMBlocks.SLEEPINGSTONE_MOONERING_ORE.get());
        this.tag(BMBlockTags.ORES_JANTICAL).add(BMBlocks.JANTIC_ORE.get()).add(BMBlocks.SLEEPINGSTONE_JANTIC_ORE.get());
        this.tag(BMBlockTags.ORES_ALJAMIC_COPPER).add(BMBlocks.ALJAMIC_COPPER_ORE.get()).add(BMBlocks.SLEEPINGSTONE_ALJAMIC_COPPER_ORE.get());
        this.tag(BMBlockTags.ORES_COPPER).add(BMBlocks.ALJAMIC_COPPER_ORE.get()).add(BMBlocks.SLEEPINGSTONE_ALJAMIC_COPPER_ORE.get());
        this.tag(BMBlockTags.ORES_ALJAMIC_TIN).add(BMBlocks.ALJAMIC_TIN_ORE.get()).add(BMBlocks.SLEEPINGSTONE_ALJAMIC_TIN_ORE.get());
        this.tag(BMBlockTags.ORES_TIN).add(BMBlocks.ALJAMIC_TIN_ORE.get()).add(BMBlocks.SLEEPINGSTONE_ALJAMIC_TIN_ORE.get());
        this.tag(BMBlockTags.BASE_STONE_ALJAN).add(BMBlocks.ALJANSTONE.get()).add(BMBlocks.SLEEPINGSTONE.get()).add(BMBlocks.INSOGRAVEL.get());

        this.tag(BMBlockTags.GLASS_DEVIL).add(BMBlocks.RED_YELLOW_STAINED_GLASS.get());
        this.tag(BMBlockTags.GLASS_ALJAN_LIGHT_BLUE).add(BMBlocks.ALJAN_LIGHT_BLUE_STAINED_GLASS.get());
        this.tag(BMBlockTags.GLASS_POISON_BROWN).add(BMBlocks.POISON_BROWN_STAINED_GLASS.get());
        this.tag(BMBlockTags.GLASS_INSOMNIAN).add(BMBlocks.INSOMNIAN_STAINED_GLASS.get());
        this.tag(BMBlockTags.GLASS_PANES_DEVIL).add(BMBlocks.RED_YELLOW_STAINED_GLASS_PANE.get());
        this.tag(BMBlockTags.GLASS_PANES_ALJAN_LIGHT_BLUE).add(BMBlocks.ALJAN_LIGHT_BLUE_STAINED_GLASS_PANE.get());
        this.tag(BMBlockTags.GLASS_PANES_POISON_BROWN).add(BMBlocks.POISON_BROWN_STAINED_GLASS_PANE.get());
        this.tag(BMBlockTags.GLASS_PANES_INSOMNIAN).add(BMBlocks.INSOMNIAN_STAINED_GLASS_PANE.get());
        this.tag(Tags.Blocks.STAINED_GLASS).add(BMBlocks.RED_YELLOW_STAINED_GLASS.get()).add(BMBlocks.ALJAN_LIGHT_BLUE_STAINED_GLASS.get()).add(BMBlocks.POISON_BROWN_STAINED_GLASS.get())
                .add(BMBlocks.INSOMNIAN_STAINED_GLASS.get());
        this.tag(Tags.Blocks.STAINED_GLASS_PANES).add(BMBlocks.RED_YELLOW_STAINED_GLASS_PANE.get()).add(BMBlocks.ALJAN_LIGHT_BLUE_STAINED_GLASS_PANE.get()).add(BMBlocks.POISON_BROWN_STAINED_GLASS_PANE.get())
                .add(BMBlocks.INSOMNIAN_STAINED_GLASS_PANE.get());

        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_DEVIL);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_RAW_DEVIL);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_ANGELIC);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_RAW_ANGELIC);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_CHRISTIAN_MID_TERM);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_CHRISTIAN_MID_TERM_ANGELIC);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_CHRISTIAN_MID_TERM_DEVIL);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_DEVIL_ANGELIC);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_WARMTERM);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_MID_TERM);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_COLDTERM);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_OBSIDIAN_INFUSED_MID_TERM);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_RAW_ALJAMIC_COPPER);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_RAW_ALJAMIC_TIN);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_ALJAMEED);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_RAW_ALJAMEED);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_ALJANSTEEL);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_MOONERING);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_RAW_MOONERING);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_CHARJAN_COAL);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_HARDENED_AMARACAMEL);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_MILKLLARY);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_CRYSTALLINE_ANGELIC);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_RAW_MID_TERM);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_MID_HILLARY);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_JANTICAL);
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(BMBlockTags.STORAGE_BLOCKS_EMERIOND);

        this.tag(Tags.Blocks.ORES).addTag(BMBlockTags.ORES_DEVIL).addTag(BMBlockTags.ORES_ANGELIC).addTag(BMBlockTags.ORES_MID_TERM).addTag(BMBlockTags
                .ORES_CRYSTALLINE_ANGELIC).addTag(BMBlockTags.ORES_ALJAMEED).addTag(BMBlockTags.ORES_MOONERING).addTag(BMBlockTags.ORES_JANTICAL).addTag(BMBlockTags
                .ORES_ALJAMIC_COPPER).addTag(BMBlockTags.ORES_COPPER).addTag(BMBlockTags.ORES_ALJAMIC_TIN).addTag(BMBlockTags.ORES_TIN);

        this.tag(Tags.Blocks.GLASS_COLORLESS).add(BMBlocks.ALJAMIC_GLASS.get());
        this.tag(Tags.Blocks.GLASS_PANES_COLORLESS).add(BMBlocks.ALJAMIC_GLASS_PANE.get());
        this.tag(Tags.Blocks.GLASS).addTag(BMBlockTags.GLASS_DEVIL);
        this.tag(Tags.Blocks.GLASS_PANES).addTag(BMBlockTags.GLASS_PANES_DEVIL);

        this.tag(Tags.Blocks.STONE).add(BMBlocks.TABU.get());
        this.tag(Tags.Blocks.SAND).add(BMBlocks.ALJAMIC_SAND.get());
        this.tag(Tags.Blocks.DIRT).add(BMBlocks.ALJAMIC_GRASS_BLOCK.get()).add(BMBlocks.AVONDALIC_NYLIUM.get()).add(BMBlocks.ALJAMIC_DIRT.get());
        this.tag(Tags.Blocks.COBBLESTONE).add(BMBlocks.COBBLED_ALJANSTONE.get());
        this.tag(Tags.Blocks.BARRELS_WOODEN).add(BMBlocks.ALJANWOOD_BARREL.get(), BMBlocks.ALJANCAP_BARREL.get(), BMBlocks.INSOMNIAN_BARREL.get(), BMBlocks.AVONDALIC_WILLOW_BARREL.get());

        // Melony Tags
        this.tag(BMBlockTags.CAMPFIRE_SIGNAL_FIRE_BLOCKS).add(BMBlocks.CARAMELED_HAY_BALE.get());
        this.tag(BMBlockTags.NATURAL_FARMLAND).add(Blocks.FARMLAND).add(BMBlocks.ALJAMIC_FARMLAND.get());
        this.tag(BMBlockTags.FARMLAND).addTag(BMBlockTags.NATURAL_FARMLAND);
        this.tag(BMBlockTags.FARMLAND_TRANSPARENT).addTag(Tags.Blocks.FENCE_GATES).addTag(BlockTags.FENCE_GATES).add(Blocks.MOVING_PISTON);
        this.tag(BMBlockTags.MAKES_GRASS_BLOCKS_SNOWY).add(Blocks.SNOW_BLOCK, Blocks.SNOW);
        this.tag(BMBlockTags.HELPS_ON_ZOMBIE_CONVERSION).addTag(BlockTags.BEDS).add(Blocks.IRON_BARS);
        this.tag(BMBlockTags.BARRELS).add(BMBlocks.ALJANWOOD_BARREL.get(), BMBlocks.ALJANCAP_BARREL.get(), BMBlocks.INSOMNIAN_BARREL.get(), BMBlocks.AVONDALIC_WILLOW_BARREL.get());

        // Back Math Tags
        this.tag(BMBlockTags.INFINIBURN_ALJAN).add(BMBlocks.CHARJAN_COAL_BLOCK.get()).add(Blocks.NETHERRACK).add(Blocks.MAGMA_BLOCK);

        this.tag(BMBlockTags.WILD_CROPS_PLANTABLE_ON).addTag(BMBlockTags.ALJAN_GROUND).add(BMBlocks.ALJAMIC_FARMLAND.get());
        this.tag(BMBlockTags.ALJAN_CROP_PLANTABLE_ON).add(BMBlocks.ALJAMIC_FARMLAND.get());
        this.tag(BMBlockTags.TURTLE_FRIED_EGG_FLOWER_PLANTABLE_ON).addTag(BMBlockTags.NATURAL_FARMLAND).addTag(Tags.Blocks.SAND).addTag(Tags.Blocks.DIRT);
        this.tag(BMBlockTags.ENDER_DRAGON_FRIED_EGG_FLOWER_PLANTABLE_ON).addOptional(new ResourceLocation("variants:ender_nylium")).addTag(BMBlockTags.FARMLAND).addTag(Tags.Blocks.DIRT).add(Blocks.END_STONE)
                .add(Blocks.END_STONE_BRICKS);
        this.tag(BMBlockTags.MINEABLE_KNIVES).addTag(BlockTags.SAPLINGS).addTag(BlockTags.FLOWERS).add(Blocks.GRASS).add(Blocks.TALL_GRASS).add(Blocks.FERN).add(Blocks.LARGE_FERN).add(Blocks.SEAGRASS).add(Blocks.TALL_SEAGRASS)
                .add(Blocks.KELP).add(Blocks.KELP_PLANT).add(Blocks.BAMBOO_SAPLING).add(Blocks.BAMBOO).add(Blocks.CRIMSON_ROOTS).add(Blocks.WARPED_ROOTS).add(Blocks.CRIMSON_FUNGUS).add(Blocks.WARPED_FUNGUS).add(Blocks.NETHER_SPROUTS)
                .add(Blocks.WHEAT).add(Blocks.CARROTS).add(Blocks.POTATOES).add(Blocks.BEETROOTS).add(Blocks.NETHER_WART).add(Blocks.SWEET_BERRY_BUSH).add(BMBlocks.CARAMELED_WHEAT.get()).add(BMBlocks.WILD_CARAMELED_WHEAT.get())
                .add(BMBlocks.ALJAMIC_ONIONS.get()).add(BMBlocks.WILD_ALJAMIC_ONIONS.get());
        this.tag(BMBlockTags.ALJAN_CARVER_REPLACEABLES).addTag(BMBlockTags.BASE_STONE_ALJAN).addTag(BMBlockTags.ALJAN_GROUND).add(BMBlocks.ALJAMIC_SAND.get()).add(Blocks.GRASS_BLOCK).add(Blocks.DIRT);
        this.tag(BMBlockTags.ALJAN_TELEPORTER_PASSTHROUGH).addTag(BlockTags.LEAVES).add(BMBlocks.ALJAN_TULIP.get(), BMBlocks.POISON_ROSE.get(), BMBlocks.INSOMNIAN_TULIP.get(), Blocks.GRASS, Blocks.SNOW);
        this.tag(BMBlockTags.ALJAN_GROUND).add(BMBlocks.ALJAMIC_GRASS_BLOCK.get()).add(BMBlocks.AVONDALIC_NYLIUM.get()).add(BMBlocks.ALJAMIC_DIRT.get());
        this.tag(BMBlockTags.MALAIKA_SPAWNABLE_ON).addTag(Tags.Blocks.END_STONES).addOptionalTag(new ResourceLocation("variants", "has_ender_nylium"));
        this.tag(BMBlockTags.TERMIANS_SPAWNABLE_ON).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.LOGS).addTag(BlockTags.PLANKS).addTag(Tags.Blocks.DIRT);
        this.tag(BMBlockTags.OBSIDIAN_REPLACEABLES).add(Blocks.OBSIDIAN).add(Blocks.CRYING_OBSIDIAN);
        this.tag(BMBlockTags.AIR_REPLACEABLES).add(Blocks.AIR).add(Blocks.CAVE_AIR);
        this.tag(BMBlockTags.ALCALYTES_CAN_HARVEST).add(BMBlocks.WILD_ALJAMIC_ONIONS.get(), BMBlocks.WILD_CARAMELED_WHEAT.get());
        this.tag(BMBlockTags.QUEEN_LUCY_PET_PREFERRED_BLOCKS);

        // Logs & Woods
        this.tag(BMBlockTags.CRYSTALLINE_BIRCH_LOGS).add(BMBlocks.CRYSTALLINE_BIRCH_LOG.get()).add(BMBlocks.CRYSTALLINE_BIRCH_WOOD.get()).add(BMBlocks.STRIPPED_CRYSTALLINE_BIRCH_LOG.get()).add(BMBlocks.STRIPPED_CRYSTALLINE_BIRCH_WOOD.get());
        this.tag(BMBlockTags.CORK_OAK_LOGS).add(BMBlocks.CORK_OAK_LOG.get()).add(BMBlocks.CORK_OAK_WOOD.get()).add(BMBlocks.STRIPPED_CORK_OAK_LOG.get()).add(BMBlocks.STRIPPED_CORK_OAK_WOOD.get());
        this.tag(BMBlockTags.GOLDENWOOD_LOGS).add(BMBlocks.GOLDENWOOD_LOG.get()).add(BMBlocks.GOLDENWOOD_WOOD.get()).add(BMBlocks.STRIPPED_GOLDENWOOD_LOG.get()).add(BMBlocks.STRIPPED_GOLDENWOOD_WOOD.get());
        this.tag(BMBlockTags.GUAVA_LOGS).add(BMBlocks.GUAVA_LOG.get()).add(BMBlocks.GUAVA_WOOD.get()).add(BMBlocks.STRIPPED_GUAVA_LOG.get()).add(BMBlocks.STRIPPED_GUAVA_WOOD.get());
        this.tag(BMBlockTags.JABUTICABA_LOGS).add(BMBlocks.JABUTICABA_LOG.get()).add(BMBlocks.JABUTICABA_WOOD.get()).add(BMBlocks.STRIPPED_JABUTICABA_LOG.get()).add(BMBlocks.STRIPPED_JABUTICABA_WOOD.get());
        this.tag(BMBlockTags.ALJANWOOD_LOGS).add(BMBlocks.ALJANWOOD_LOG.get()).add(BMBlocks.ALJANWOOD_WOOD.get()).add(BMBlocks.STRIPPED_ALJANWOOD_LOG.get()).add(BMBlocks.STRIPPED_ALJANWOOD_WOOD.get());
        this.tag(BMBlockTags.ALJANCAP_LOGS).add(BMBlocks.ALJANCAP_LOG.get()).add(BMBlocks.ALJANCAP_WOOD.get()).add(BMBlocks.STRIPPED_ALJANCAP_LOG.get()).add(BMBlocks.STRIPPED_ALJANCAP_WOOD.get());
        this.tag(BMBlockTags.INSOMNIAN_LOGS).add(BMBlocks.INSOMNIAN_LOG.get()).add(BMBlocks.INSOMNIAN_WOOD.get()).add(BMBlocks.STRIPPED_INSOMNIAN_LOG.get()).add(BMBlocks.STRIPPED_INSOMNIAN_WOOD.get());
        this.tag(BMBlockTags.AVONDALIC_WILLOW_LOGS).add(BMBlocks.AVONDALIC_WILLOW_LOG.get()).add(BMBlocks.AVONDALIC_WILLOW_WOOD.get()).add(BMBlocks.STRIPPED_AVONDALIC_WILLOW_LOG.get()).add(BMBlocks.STRIPPED_AVONDALIC_WILLOW_WOOD.get());

        this.tag(BMBlockTags.STRIPPED_WOODS).add(Blocks.STRIPPED_OAK_WOOD).add(Blocks.STRIPPED_SPRUCE_WOOD).add(Blocks.STRIPPED_BIRCH_WOOD).add(Blocks.STRIPPED_JUNGLE_WOOD)
                .add(Blocks.STRIPPED_ACACIA_WOOD).add(Blocks.STRIPPED_DARK_OAK_WOOD).add(Blocks.STRIPPED_CRIMSON_HYPHAE).add(Blocks.STRIPPED_WARPED_HYPHAE).add(BMBlocks.STRIPPED_CRYSTALLINE_BIRCH_WOOD.get())
                .add(BMBlocks.STRIPPED_GOLDENWOOD_WOOD.get()).add(BMBlocks.STRIPPED_GUAVA_WOOD.get()).add(BMBlocks.STRIPPED_JABUTICABA_WOOD.get()).add(BMBlocks.STRIPPED_CORK_OAK_WOOD.get())
                .add(BMBlocks.STRIPPED_ALJANWOOD_WOOD.get()).add(BMBlocks.STRIPPED_ALJANCAP_WOOD.get()).add(BMBlocks.STRIPPED_INSOMNIAN_WOOD.get()).add(BMBlocks.STRIPPED_AVONDALIC_WILLOW_WOOD.get());

        this.tag(BMBlockTags.GRAPE_VINE_POSTS).add(BMBlocks.OAK_GRAPE_VINE_POST.get()).add(BMBlocks.SPRUCE_GRAPE_VINE_POST.get()).add(BMBlocks.BIRCH_GRAPE_VINE_POST.get())
                .add(BMBlocks.JUNGLE_GRAPE_VINE_POST.get()).add(BMBlocks.ACACIA_GRAPE_VINE_POST.get()).add(BMBlocks.DARK_OAK_GRAPE_VINE_POST.get()).add(BMBlocks.CRIMSON_GRAPE_VINE_POST.get())
                .add(BMBlocks.WARPED_GRAPE_VINE_POST.get()).add(BMBlocks.CRYSTALLINE_BIRCH_GRAPE_VINE_POST.get()).add(BMBlocks.CORK_OAK_GRAPE_VINE_POST.get()).add(BMBlocks.GOLDENWOOD_GRAPE_VINE_POST.get())
                .add(BMBlocks.GUAVA_GRAPE_VINE_POST.get()).add(BMBlocks.JABUTICABA_GRAPE_VINE_POST.get()).add(BMBlocks.ALJANWOOD_GRAPE_VINE_POST.get()).add(BMBlocks.ALJANCAP_GRAPE_VINE_POST.get())
                .add(BMBlocks.INSOMNIAN_GRAPE_VINE_POST.get()).add(BMBlocks.AVONDALIC_WILLOW_GRAPE_VINE_POST.get());

        this.tag(BMBlockTags.SQUIDS).add(BMBlocks.TITO.get()).add(BMBlocks.TOTI.get());
        this.tag(BMBlockTags.TOYS).add(BMBlocks.INNOVATOR_TOY.get()).add(BMBlocks.ALICE_TOY.get()).add(BMBlocks.ALAN_TOY.get()).add(BMBlocks.TEENAGER_ALICE_TOY.get())
                .add(BMBlocks.LEANDRO_TOY.get()).add(BMBlocks.TYLER_TOY.get()).add(BMBlocks.MALENA_TOY.get());

        this.tag(BMBlockTags.BOUNTIFULLY_EXPANSIVE_IDEA).add(BMBlocks.CHOCOLATE_NAKED_CAKE.get());
        this.tag(BMBlockTags.SOPHIE_IDEA).addTag(BMBlockTags.BOUNTIFULLY_EXPANSIVE_IDEA);


        // TODO: MINECRAFT TAGS


        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(BMBlocks.HILLARIED_STONE.get()).add(BMBlocks.TABU.get());
        this.tag(BlockTags.GUARDED_BY_PIGLINS).add(BMBlocks.CRYSTALLINE_BIRCH_LEAVES.get()).add(BMBlocks.CRYSTALLINE_CRYSTALLIZER.get()).add(BMBlocks.QUEEN_LUCY_RELIC.get()).add(BMBlocks.CRYSTALLINE_BIRCH_SAPLING.get()).add(
                BMBlocks.POTTED_CRYSTALLINE_BIRCH_SAPLING.get()).add(BMBlocks.QUEEN_LUCY_PET_RELIC.get(), BMBlocks.ALJANWOOD_BARREL.get(), BMBlocks.ALJANCAP_BARREL.get(), BMBlocks.INSOMNIAN_BARREL.get(),
                BMBlocks.AVONDALIC_WILLOW_BARREL.get(), BMBlocks.CRATE.get());
        this.tag(BlockTags.DRAGON_IMMUNE).add(BMBlocks.ENDER_DRAGON_FRIED_EGG_FLOWER.get()).add(BMBlocks.POTTED_ENDER_DRAGON_FRIED_EGG_FLOWER.get());

        this.tag(BlockTags.BAMBOO_PLANTABLE_ON).addTag(BMBlockTags.ALJAN_GROUND);
        this.tag(BlockTags.ENDERMAN_HOLDABLE).addTag(BMBlockTags.ALJAN_GROUND);
        this.tag(BlockTags.MUSHROOM_GROW_BLOCK).addTag(BMBlockTags.ALJAN_GROUND);
        this.tag(BlockTags.WOOL).add(BMBlocks.RED_YELLOW_WOOL.get());
        this.tag(BlockTags.CARPETS).add(BMBlocks.RED_YELLOW_CARPET.get());
        this.tag(BlockTags.CROPS).add(BMBlocks.CARAMELED_WHEAT.get()).add(BMBlocks.WILD_CARAMELED_WHEAT.get()).add(BMBlocks.ALJAMIC_ONIONS.get()).add(BMBlocks.WILD_ALJAMIC_ONIONS.get());
        this.tag(BlockTags.IMPERMEABLE).add(BMBlocks.RED_YELLOW_STAINED_GLASS.get()).add(BMBlocks.ALJAN_LIGHT_BLUE_STAINED_GLASS.get()).add(BMBlocks.POISON_BROWN_STAINED_GLASS.get()).add(BMBlocks.INSOMNIAN_STAINED_GLASS.get())
                .add(BMBlocks.ALJAMIC_GLASS.get());
        this.tag(BlockTags.PIGLIN_REPELLENTS).add(BMBlocks.MID_TERM_SOUL_LANTERN.get());
        this.tag(BlockTags.SAND).add(BMBlocks.ALJAMIC_SAND.get());
        this.tag(BlockTags.SMALL_FLOWERS).add(BMBlocks.RED_YELLOW_ALLIUM.get()).add(BMBlocks.FRIED_EGG_FLOWER.get()).add(BMBlocks.TURTLE_FRIED_EGG_FLOWER.get()).add(BMBlocks.ENDER_DRAGON_FRIED_EGG_FLOWER.get())
                .add(BMBlocks.ALJAN_TULIP.get()).add(BMBlocks.POISON_ROSE.get()).add(BMBlocks.INSOMNIAN_TULIP.get());
        this.tag(BlockTags.VALID_SPAWN).addTag(BMBlockTags.ALJAN_GROUND).add(BMBlocks.ALJAMIC_SAND.get());

        this.tag(BlockTags.STAIRS).add(BMBlocks.DEVIL_STAIRS.get()).add(BMBlocks.DEVIL_BRICK_STAIRS.get()).add(BMBlocks.ANGELIC_BRICK_STAIRS.get()).add(BMBlocks.MOSSY_ANGELIC_BRICK_STAIRS.get())
                .add(BMBlocks.WARMTERM_BRICK_STAIRS.get()).add(BMBlocks.COLDTERM_BRICK_STAIRS.get()).add(BMBlocks.OBSIDITERM_BRICK_STAIRS.get()).add(BMBlocks.HILLARIED_STONE_STAIRS.get()).add(BMBlocks.CUBIC_TABU_STAIRS.get())
                .add(BMBlocks.TABU_MOSAIC_STAIRS.get()).add(BMBlocks.COBBLED_ALJANSTONE_STAIRS.get()).add(BMBlocks.ALJANSTONE_STAIRS.get()).add(BMBlocks.ALJANSTONE_BRICK_STAIRS.get()).add(BMBlocks.SLEEPINGSTONE_STAIRS.get())
                .add(BMBlocks.POLISHED_SLEEPINGSTONE_STAIRS.get()).add(BMBlocks.SLEEPINGSTONE_BRICK_STAIRS.get());
        this.tag(BlockTags.SLABS).add(BMBlocks.DEVIL_SLAB.get()).add(BMBlocks.DEVIL_BRICK_SLAB.get()).add(BMBlocks.ANGELIC_BRICK_SLAB.get()).add(BMBlocks.MOSSY_ANGELIC_BRICK_SLAB.get())
                .add(BMBlocks.WARMTERM_BRICK_SLAB.get()).add(BMBlocks.COLDTERM_BRICK_SLAB.get()).add(BMBlocks.OBSIDITERM_BRICK_SLAB.get()).add(BMBlocks.HILLARIED_STONE_SLAB.get()).add(BMBlocks.CUBIC_TABU_SLAB.get())
                .add(BMBlocks.TABU_MOSAIC_SLAB.get()).add(BMBlocks.COBBLED_ALJANSTONE_SLAB.get()).add(BMBlocks.ALJANSTONE_SLAB.get()).add(BMBlocks.ALJANSTONE_BRICK_SLAB.get()).add(BMBlocks.SMOOTH_ALJANSTONE_SLAB.get())
                .add(BMBlocks.SLEEPINGSTONE_SLAB.get()).add(BMBlocks.POLISHED_SLEEPINGSTONE_SLAB.get()).add(BMBlocks.SLEEPINGSTONE_BRICK_SLAB.get());
        this.tag(BlockTags.WALLS).add(BMBlocks.DEVIL_WALL.get()).add(BMBlocks.DEVIL_BRICK_WALL.get()).add(BMBlocks.ANGELIC_BRICK_WALL.get()).add(BMBlocks.MOSSY_ANGELIC_BRICK_WALL.get())
                .add(BMBlocks.WARMTERM_BRICK_WALL.get()).add(BMBlocks.COLDTERM_BRICK_WALL.get()).add(BMBlocks.OBSIDITERM_BRICK_WALL.get()).add(BMBlocks.HILLARIED_STONE_WALL.get()).add(BMBlocks.COBBLED_ALJANSTONE_WALL.get())
                .add(BMBlocks.ALJANSTONE_WALL.get()).add(BMBlocks.ALJANSTONE_BRICK_WALL.get()).add(BMBlocks.SLEEPINGSTONE_WALL.get()).add(BMBlocks.POLISHED_SLEEPINGSTONE_WALL.get()).add(BMBlocks.SLEEPINGSTONE_BRICK_WALL.get());
        this.tag(BlockTags.FENCES).add(BMBlocks.DEVIL_FENCE.get());
        this.tag(BlockTags.DOORS).add(BMBlocks.DEVIL_DOOR.get()).add(BMBlocks.ANGELIC_DOOR.get()).add(BMBlocks.MID_TERM_DOOR.get()).add(BMBlocks.OBSIDIAN_INFUSED_MID_TERM_DOOR.get());
        this.tag(BlockTags.TRAPDOORS).add(BMBlocks.DEVIL_TRAPDOOR.get()).add(BMBlocks.ANGELIC_TRAPDOOR.get()).add(BMBlocks.MID_TERM_TRAPDOOR.get()).add(BMBlocks.OBSIDIAN_INFUSED_MID_TERM_TRAPDOOR.get());
        this.tag(BlockTags.PRESSURE_PLATES).add(BMBlocks.DEVIL_PRESSURE_PLATE.get()).add(BMBlocks.ANGELIC_PRESSURE_PLATE.get()).add(BMBlocks.ALJAMEED_PRESSURE_PLATE.get()).add(BMBlocks.MOONERING_PRESSURE_PLATE.get());

        // Wood Related
        this.tag(BlockTags.LOGS_THAT_BURN).addTag(BMBlockTags.CRYSTALLINE_BIRCH_LOGS).addTag(BMBlockTags.GOLDENWOOD_LOGS).addTag(BMBlockTags.GUAVA_LOGS).addTag(BMBlockTags.JABUTICABA_LOGS)
                .addTag(BMBlockTags.CORK_OAK_LOGS).addTag(BMBlockTags.ALJANWOOD_LOGS).addTag(BMBlockTags.ALJANCAP_LOGS).addTag(BMBlockTags.INSOMNIAN_LOGS).addTag(BMBlockTags.AVONDALIC_WILLOW_LOGS);
        this.tag(BlockTags.PLANKS).add(BMBlocks.CRYSTALLINE_BIRCH_PLANKS.get()).add(BMBlocks.GOLDENWOOD_PLANKS.get()).add(BMBlocks.GUAVA_PLANKS.get()).add(BMBlocks.JABUTICABA_PLANKS.get()).add(BMBlocks.CORK_OAK_PLANKS.get())
                .add(BMBlocks.ALJANWOOD_PLANKS.get()).add(BMBlocks.ALJANCAP_PLANKS.get()).add(BMBlocks.INSOMNIAN_PLANKS.get()).add(BMBlocks.AVONDALIC_WILLOW_PLANKS.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(BMBlocks.CRYSTALLINE_BIRCH_STAIRS.get()).add(BMBlocks.GOLDENWOOD_STAIRS.get()).add(BMBlocks.GUAVA_STAIRS.get()).add(BMBlocks.JABUTICABA_STAIRS.get()).add(BMBlocks.CORK_OAK_STAIRS.get())
                .add(BMBlocks.ALJANWOOD_STAIRS.get()).add(BMBlocks.ALJANCAP_STAIRS.get()).add(BMBlocks.INSOMNIAN_STAIRS.get()).add(BMBlocks.AVONDALIC_WILLOW_STAIRS.get());
        this.tag(BlockTags.WOODEN_SLABS).add(BMBlocks.CRYSTALLINE_BIRCH_SLAB.get()).add(BMBlocks.GOLDENWOOD_SLAB.get()).add(BMBlocks.GUAVA_SLAB.get()).add(BMBlocks.JABUTICABA_SLAB.get()).add(BMBlocks.CORK_OAK_SLAB.get())
                .add(BMBlocks.ALJANWOOD_SLAB.get()).add(BMBlocks.ALJANCAP_SLAB.get()).add(BMBlocks.INSOMNIAN_SLAB.get()).add(BMBlocks.AVONDALIC_WILLOW_SLAB.get());
        this.tag(BlockTags.WOODEN_FENCES).add(BMBlocks.CRYSTALLINE_BIRCH_FENCE.get()).add(BMBlocks.GOLDENWOOD_FENCE.get()).add(BMBlocks.GUAVA_FENCE.get()).add(BMBlocks.JABUTICABA_FENCE.get()).add(BMBlocks.CORK_OAK_FENCE.get())
                .add(BMBlocks.ALJANWOOD_FENCE.get()).add(BMBlocks.ALJANCAP_FENCE.get()).add(BMBlocks.INSOMNIAN_FENCE.get()).add(BMBlocks.AVONDALIC_WILLOW_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(BMBlocks.DEVIL_FENCE_GATE.get()).add(BMBlocks.CRYSTALLINE_BIRCH_FENCE_GATE.get()).add(BMBlocks.GOLDENWOOD_FENCE_GATE.get()).add(BMBlocks.GUAVA_FENCE_GATE.get())
                .add(BMBlocks.JABUTICABA_FENCE_GATE.get()).add(BMBlocks.CORK_OAK_FENCE_GATE.get()).add(BMBlocks.ALJANWOOD_FENCE_GATE.get()).add(BMBlocks.ALJANCAP_FENCE_GATE.get()).add(BMBlocks.INSOMNIAN_FENCE_GATE.get())
                .add(BMBlocks.AVONDALIC_WILLOW_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_DOORS).add(BMBlocks.CRYSTALLINE_BIRCH_DOOR.get()).add(BMBlocks.GOLDENWOOD_DOOR.get()).add(BMBlocks.GUAVA_DOOR.get()).add(BMBlocks.JABUTICABA_DOOR.get()).add(BMBlocks.CORK_OAK_DOOR.get())
                .add(BMBlocks.ALJANWOOD_DOOR.get(), BMBlocks.ALJANCAP_DOOR.get(), BMBlocks.INSOMNIAN_DOOR.get()).add(BMBlocks.AVONDALIC_WILLOW_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(BMBlocks.CRYSTALLINE_BIRCH_TRAPDOOR.get()).add(BMBlocks.GOLDENWOOD_TRAPDOOR.get()).add(BMBlocks.GUAVA_TRAPDOOR.get()).add(BMBlocks.JABUTICABA_TRAPDOOR.get())
                .add(BMBlocks.CORK_OAK_TRAPDOOR.get()).add(BMBlocks.ALJANWOOD_TRAPDOOR.get(), BMBlocks.ALJANCAP_TRAPDOOR.get(), BMBlocks.INSOMNIAN_TRAPDOOR.get()).add(BMBlocks.AVONDALIC_WILLOW_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BMBlocks.CRYSTALLINE_BIRCH_PRESSURE_PLATE.get()).add(BMBlocks.GOLDENWOOD_PRESSURE_PLATE.get()).add(BMBlocks.GUAVA_PRESSURE_PLATE.get())
                .add(BMBlocks.JABUTICABA_PRESSURE_PLATE.get()).add(BMBlocks.CORK_OAK_PRESSURE_PLATE.get()).add(BMBlocks.ALJANWOOD_PRESSURE_PLATE.get()).add(BMBlocks.ALJANCAP_PRESSURE_PLATE.get()).add(BMBlocks.INSOMNIAN_PRESSURE_PLATE.get())
                .add(BMBlocks.AVONDALIC_WILLOW_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(BMBlocks.CRYSTALLINE_BIRCH_BUTTON.get()).add(BMBlocks.GOLDENWOOD_BUTTON.get()).add(BMBlocks.GUAVA_BUTTON.get()).add(BMBlocks.JABUTICABA_BUTTON.get())
                .add(BMBlocks.CORK_OAK_BUTTON.get()).add(BMBlocks.ALJANWOOD_BUTTON.get()).add(BMBlocks.ALJANCAP_BUTTON.get()).add(BMBlocks.INSOMNIAN_BUTTON.get()).add(BMBlocks.AVONDALIC_WILLOW_BUTTON.get());

        this.tag(BlockTags.CLIMBABLE).add(BMBlocks.CRYSTALLINE_BIRCH_LADDER.get()).add(BMBlocks.GOLDENWOOD_LADDER.get()).add(BMBlocks.GUAVA_LADDER.get()).add(BMBlocks.JABUTICABA_LADDER.get()).add(BMBlocks.CORK_OAK_LADDER.get())
                .add(BMBlocks.ALJANWOOD_LADDER.get()).add(BMBlocks.ALJANCAP_LADDER.get()).add(BMBlocks.INSOMNIAN_LADDER.get()).add(BMBlocks.AVONDALIC_WILLOW_LADDER.get());

        this.tag(BlockTags.FLOWER_POTS).add(BMBlocks.POTTED_GUARANA_OAK_SAPLING.get()).add(BMBlocks.POTTED_MANGO_OAK_SAPLING.get()).add(BMBlocks.POTTED_MANGAED_MANGO_OAK_SAPLING.get())
                .add(BMBlocks.POTTED_GRAPE_VINE_SAPLING.get()).add(BMBlocks.POTTED_LEMON_OAK_SAPLING.get()).add(BMBlocks.POTTED_PINEAPPLE_OAK_SAPLING.get()).add(BMBlocks.POTTED_ORANGE_OAK_SAPLING.get())
                .add(BMBlocks.POTTED_BANANA_JUNGLE_SAPLING.get()).add(BMBlocks.POTTED_GUAVA_SAPLING.get()).add(BMBlocks.POTTED_JABUTICABA_SAPLING.get()).add(BMBlocks.POTTED_ALJAME_BIRCH_SAPLING.get())
                .add(BMBlocks.POTTED_CRYSTALLINE_BIRCH_SAPLING.get()).add(BMBlocks.POTTED_GOLDENWOOD_SAPLING.get()).add(BMBlocks.POTTED_ENCHANTED_GOLDENWOOD_SAPLING.get()).add(BMBlocks.POTTED_CORK_OAK_SAPLING.get())
                .add(BMBlocks.POTTED_ALJANWOOD_SAPLING.get()).add(BMBlocks.POTTED_ALJANCAP_SAPLING.get()).add(BMBlocks.POTTED_INSOMNIAN_SAPLING.get()).add(BMBlocks.POTTED_AVONDALIC_WILLOW_SAPLING.get())
                .add(BMBlocks.POTTED_RED_YELLOW_ALLIUM.get()).add(BMBlocks.POTTED_FRIED_EGG_FLOWER.get()).add(BMBlocks.POTTED_TURTLE_FRIED_EGG_FLOWER.get()).add(BMBlocks.POTTED_ENDER_DRAGON_FRIED_EGG_FLOWER.get())
                .add(BMBlocks.POTTED_ALJAN_TULIP.get()).add(BMBlocks.POTTED_POISON_ROSE.get()).add(BMBlocks.POTTED_INSOMNIAN_TULIP.get()).add(BMBlocks.POTTED_ALJANSHROOM.get()).add(BMBlocks.POTTED_SLEEPSHROOM.get())
                .add(BMBlocks.POTTED_SLEEPYSHROOM.get());
        this.tag(BlockTags.SAPLINGS).add(BMBlocks.GUARANA_OAK_SAPLING.get()).add(BMBlocks.MANGO_OAK_SAPLING.get()).add(BMBlocks.MANGAED_MANGO_OAK_SAPLING.get())
                .add(BMBlocks.GRAPE_VINE_SAPLING.get()).add(BMBlocks.LEMON_OAK_SAPLING.get()).add(BMBlocks.PINEAPPLE_OAK_SAPLING.get()).add(BMBlocks.ORANGE_OAK_SAPLING.get()).add(BMBlocks.BANANA_JUNGLE_SAPLING.get())
                .add(BMBlocks.GUAVA_SAPLING.get()).add(BMBlocks.JABUTICABA_SAPLING.get()).add(BMBlocks.ALJAME_BIRCH_SAPLING.get()).add(BMBlocks.CRYSTALLINE_BIRCH_SAPLING.get()).add(BMBlocks.GOLDENWOOD_SAPLING.get())
                .add(BMBlocks.ENCHANTED_GOLDENWOOD_SAPLING.get()).add(BMBlocks.CORK_OAK_SAPLING.get()).add(BMBlocks.ALJANWOOD_SAPLING.get()).add(BMBlocks.ALJANCAP_SAPLING.get()).add(BMBlocks.INSOMNIAN_SAPLING.get())
                .add(BMBlocks.AVONDALIC_WILLOW_SAPLING.get());
        this.tag(BlockTags.LEAVES).add(BMBlocks.GUARANA_OAK_LEAVES.get()).add(BMBlocks.MANGO_OAK_LEAVES.get()).add(BMBlocks.MANGAED_MANGO_OAK_LEAVES.get())
                .add(BMBlocks.GRAPE_VINE_LEAVES.get()).add(BMBlocks.LEMON_OAK_LEAVES.get()).add(BMBlocks.PINEAPPLE_OAK_LEAVES.get(), BMBlocks.ORANGE_OAK_LEAVES.get(), BMBlocks.BANANA_JUNGLE_LEAVES.get()).add(BMBlocks.GUAVA_LEAVES.get())
                .add(BMBlocks.JABUTICABA_LEAVES.get(), BMBlocks.ALJAME_BIRCH_LEAVES.get()).add(BMBlocks.CRYSTALLINE_BIRCH_LEAVES.get()).add(BMBlocks.GOLDENWOOD_LEAVES.get()).add(BMBlocks.ENCHANTED_GOLDENWOOD_LEAVES.get()).add(BMBlocks.CORK_OAK_LEAVES.get())
                .add(BMBlocks.ALJANWOOD_LEAVES.get()).add(BMBlocks.ALJANCAP_LEAVES.get()).add(BMBlocks.AMARACAP_LEAVES.get()).add(BMBlocks.INSOMNIAN_LEAVES.get()).add(BMBlocks.AVONDALIC_WILLOW_LEAVES.get());
        this.tag(BlockTags.WALL_POST_OVERRIDE).add(BMBlocks.HILLARY_TORCH.get()).add(BMBlocks.CHARJAN_WOOD_TORCH.get()).add(BMBlocks.CHARJAN_CRYSTALLINE_BIRCH_TORCH.get()).add(BMBlocks.CHARJAN_GOLDENWOOD_TORCH.get())
                .add(BMBlocks.CHARJAN_GUAVA_TORCH.get()).add(BMBlocks.CHARJAN_JABUTICABA_TORCH.get()).add(BMBlocks.CHARJAN_CORK_OAK_TORCH.get()).add(BMBlocks.CHARJAN_ALJANWOOD_TORCH.get()).add(BMBlocks.CHARJAN_ALJANCAP_TORCH.get())
                .add(BMBlocks.CHARJAN_INSOMNIAN_TORCH.get()).add(BMBlocks.CHARJAN_AVONDALIC_WILLOW_TORCH.get()).add(BMBlocks.CHARJAN_HILLARY_TORCH.get()).add(BMBlocks.CHARJAN_DEVIL_TORCH.get()).add(BMBlocks.CHARJAN_ANGELIC_TORCH.get())
                .add(BMBlocks.CHARJAN_MID_TERM_TORCH.get()).add(BMBlocks.CHARJAN_ALJAMEED_TORCH.get());
    }
}
