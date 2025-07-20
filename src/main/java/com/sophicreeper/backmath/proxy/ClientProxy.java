package com.sophicreeper.backmath.proxy;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.block.BMFluids;
import com.sophicreeper.backmath.blockentity.BMBlockEntities;
import com.sophicreeper.backmath.blockentity.renderer.HeadBlockEntityRenderer;
import com.sophicreeper.backmath.blockentity.renderer.QueenLucyHeadBlockEntityRenderer;
import com.sophicreeper.backmath.blockentity.renderer.WandererSophieHeadBlockEntityRenderer;
import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.entity.renderer.*;
import com.sophicreeper.backmath.entity.renderer.aljan.*;
import com.sophicreeper.backmath.entity.renderer.layer.CrateLayer;
import com.sophicreeper.backmath.entity.renderer.layer.OutfitLayer;
import com.sophicreeper.backmath.entity.renderer.misc.BMBoatRenderer;
import com.sophicreeper.backmath.entity.renderer.misc.InsomniaArrowRenderer;
import com.sophicreeper.backmath.entity.renderer.misc.JanticBoltRenderer;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.item.custom.tool.bow.BMBowItem;
import com.sophicreeper.backmath.misc.AljanTextureUpdatePack;
import com.sophicreeper.backmath.misc.BMWoodTypes;
import com.sophicreeper.backmath.util.BMKeyBindings;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.world.dimension.renderer.AljanDimensionRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.sophicreeper.backmath.util.BMItemModelProperties.*;

public class ClientProxy extends CommonProxy {
    public ClientProxy() {
        super();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Block & Fluid Render Lookups
        RenderTypeLookup.setRenderLayer(BMBlocks.FRIED_EGG_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ANGELIC_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ANGELIC_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUARANA_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MANGO_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUARANA_OAK_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MANGO_OAK_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.HILLARY_LANTERN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MID_TERM_HILLARY_LANTERN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MID_TERM_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.RED_YELLOW_ALLIUM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_FRIED_EGG_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_RED_YELLOW_ALLIUM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.RED_YELLOW_STAINED_GLASS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.RED_YELLOW_STAINED_GLASS_PANE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_GUARANA_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_MANGO_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.HILLARY_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.HILLARY_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GRAPE_VINE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_GRAPE_VINE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GRAPE_VINE_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.DEVIL_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.DEVIL_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ANGRY_SOPHIE_HEAD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ANGRY_SOPHIE_WALL_HEAD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAME_BIRCH_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAME_BIRCH_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ALJAME_BIRCH_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_ANGELIC_ORE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.LEMON_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_LEMON_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.QUEEN_LUCY_RELIC.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.QUEEN_LUCY_HEAD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.QUEEN_LUCY_WALL_HEAD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.PINEAPPLE_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.PINEAPPLE_OAK_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_PINEAPPLE_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_SAPLING.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_PLANKS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_TRAPDOOR.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_DOOR.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_SLAB.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_FENCE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_FENCE_GATE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_PRESSURE_PLATE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_BUTTON.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.MID_TERM_LANTERN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MID_TERM_SOUL_LANTERN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_TURTLE_FRIED_EGG_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.TURTLE_FRIED_EGG_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_CRYSTALLINE_BIRCH_SAPLING.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANWOOD_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ALJANWOOD_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANWOOD_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANWOOD_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANWOOD_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.OAK_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.SPRUCE_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.BIRCH_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.JUNGLE_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ACACIA_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.DARK_OAK_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRIMSON_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.WARPED_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.OBSIDIAN_INFUSED_MID_TERM_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.OBSIDIAN_INFUSED_MID_TERM_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MID_TERM_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAN_TULIP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POISON_ROSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANSHROOM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.SLEEPSHROOM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ALJANSHROOM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_SLEEPSHROOM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ALJAN_TULIP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_POISON_ROSE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_TULIP.get(), ClientProxy::getDoubleLayer);
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_INSOMNIAN_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_INSOMNIAN_TULIP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANCAP_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANCAP_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ALJANCAP_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANCAP_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANWOOD_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANCAP_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAMIC_GRASS_BLOCK.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(BMBlocks.SLEEPYSHROOM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_SLEEPYSHROOM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ORANGE_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.BANANA_JUNGLE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ORANGE_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_BANANA_JUNGLE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_STAIRS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_WOOD_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_WOOD_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ALJANWOOD_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ALJANWOOD_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ALJANCAP_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ALJANCAP_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_INSOMNIAN_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_INSOMNIAN_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CARAMELED_WHEAT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.WILD_CARAMELED_WHEAT.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAMIC_ONIONS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.DEVIL_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ANGELIC_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHRISTIAN_MID_TERM_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MILKLLARY_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MID_HILLARY_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MID_TERM_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.OBSIDIAN_INFUSED_MID_TERM_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAMEED_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MOONERING_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_ANGELIC_BLOCK.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.STICKY_AMARACAMEL_BLOCK.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUAVA_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUAVA_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUAVA_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_GUAVA_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUAVA_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.MANGAED_MANGO_OAK_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUAVA_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAMIC_GLASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAMIC_GLASS_PANE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.QUEEN_LUCY_PET_RELIC.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.WILD_ALJAMIC_ONIONS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GUAVA_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANSTEEL_CHAIN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GOLDENWOOD_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_GOLDENWOOD_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ENCHANTED_GOLDENWOOD_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ENCHANTED_GOLDENWOOD_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GOLDENWOOD_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ENCHANTED_GOLDENWOOD_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GOLDENWOOD_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GOLDENWOOD_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.GOLDENWOOD_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.JABUTICABA_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.JABUTICABA_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_JABUTICABA_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.JABUTICABA_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_WILLOW_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_WILLOW_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.STRIPPED_CRYSTALLINE_BIRCH_LOG.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.STRIPPED_CRYSTALLINE_BIRCH_WOOD.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_NYLIUM.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_WILLOW_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_LOG.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_WOOD.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.JABUTICABA_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.JABUTICABA_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_WILLOW_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_WILLOW_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_AVONDALIC_WILLOW_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_WILLOW_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.AVONDALIC_WILLOW_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.JABUTICABA_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CORK_OAK_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CORK_OAK_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CORK_OAK_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CORK_OAK_GRAPE_VINE_POST.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CORK_OAK_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_GRAPE_VINE_POST.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.MANGAED_MANGO_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_MANGAED_MANGO_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ENDER_DRAGON_FRIED_EGG_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_ENDER_DRAGON_FRIED_EGG_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_LADDER.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.GOLDENWOOD_LADDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIA_SOPHIE_HEAD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIA_SOPHIE_WALL_HEAD.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_CRYSTALLINE_BIRCH_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_CRYSTALLINE_BIRCH_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_GOLDENWOOD_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_GOLDENWOOD_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_GUAVA_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_GUAVA_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_JABUTICABA_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_JABUTICABA_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_CORK_OAK_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_CORK_OAK_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_AVONDALIC_WILLOW_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_AVONDALIC_WILLOW_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_HILLARY_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_HILLARY_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_DEVIL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_DEVIL_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ANGELIC_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ANGELIC_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_MID_TERM_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_MID_TERM_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ALJAMEED_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CHARJAN_ALJAMEED_WALL_TORCH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CORK_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.POTTED_CORK_OAK_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLIZER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_CRYSTALLIZER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAN_LIGHT_BLUE_STAINED_GLASS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJAN_LIGHT_BLUE_STAINED_GLASS_PANE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.POISON_BROWN_STAINED_GLASS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.POISON_BROWN_STAINED_GLASS_PANE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_STAINED_GLASS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_STAINED_GLASS_PANE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANCAP_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.ALJANCAP_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.INSOMNIAN_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_SIGN.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMBlocks.CRYSTALLINE_BIRCH_WALL_SIGN.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.HILLARY.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.MILKLLARY.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.FLOWING_HILLARY.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.FLOWING_MILKLLARY.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.LIQUID_ALJAME.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.FLOWING_LIQUID_ALJAME.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.SLEEPISHWATER.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.FLOWING_SLEEPISHWATER.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.LIQUID_MANGA.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BMFluids.FLOWING_LIQUID_MANGA.get(), RenderType.translucent());

        // Key Bindings
        ClientRegistry.registerKeyBinding(BMKeyBindings.SHOW_TOOLTIPS_KEY);

        // Block Entity Renderers
        ClientRegistry.bindTileEntityRenderer(BMBlockEntities.HEAD.get(), HeadBlockEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(BMBlockEntities.SIGN.get(), SignTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(BMBlockEntities.WANDERER_SOPHIE_HEAD.get(), WandererSophieHeadBlockEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(BMBlockEntities.QUEEN_LUCY_HEAD.get(), QueenLucyHeadBlockEntityRenderer::new);

        // Wood Types for signs
        Atlases.addWoodType(BMWoodTypes.CRYSTALLINE_BIRCH);
        Atlases.addWoodType(BMWoodTypes.GOLDENWOOD);
        Atlases.addWoodType(BMWoodTypes.GUAVA);
        Atlases.addWoodType(BMWoodTypes.JABUTICABA);
        Atlases.addWoodType(BMWoodTypes.CORK_OAK);
        Atlases.addWoodType(BMWoodTypes.ALJANWOOD);
        Atlases.addWoodType(BMWoodTypes.ALJANCAP);
        Atlases.addWoodType(BMWoodTypes.INSOMNIAN);
        Atlases.addWoodType(BMWoodTypes.AVONDALIC_WILLOW);

        // Entity Renderers
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.WANDERER_SOPHIE.get(), WandererSophieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.ARCHER_LUCIA.get(), ArcherLuciaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.ANGRY_SOPHIE.get(), AngrySophieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.SHY_ALCALYTE.get(), ShyAlcalyteRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.KARATE_LUCIA.get(), KarateLuciaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.INSOMNIA_SOPHIE.get(), InsomniaSophieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.QUEEN_LUCY.get(), QueenLucyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.WARRIOR_SOPHIE.get(), WarriorSophieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.QUEEN_LUCY_PET.get(), QueenLucyPetRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.ARCHER_INSOMNIA_SOPHIE.get(), ArcherInsomniaSophieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.INSOMNIA_ZOMBIE.get(), InsomniaZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.ZOMBIE_FABRICIO.get(), ZombieFabricioRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.AMARACAMELER.get(), AmaracamelerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.MALAIKA.get(), MalaikaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.JANTICLE.get(), JanticleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.ALJAMIC_BONES.get(), AljamicBonesRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.SLEEPISH_SKELETON.get(), SleepishSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.BACK_MATH_BOAT.get(), BMBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.INSOMNIA_ARROW.get(), InsomniaArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.JANTIC_BOLT.get(), JanticBoltRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BMEntities.COLLECTOR_ALCALYTE.get(), CollectorAlcalyteRenderer::new);

        // Aljan Sky
        DimensionRenderInfo.EFFECTS.put(BackMath.backMath("the_aljan"), new AljanDimensionRenderer());

        // Item Properties
        makeShield(AxolotlTest.DEVIL_SHIELD.get());
        makeShield(AxolotlTest.ANGELIC_SHIELD.get());
        makeShield(AxolotlTest.MID_TERM_SHIELD.get());
        makeShield(AxolotlTest.ALJAMEED_SHIELD.get());
        makeShield(AxolotlTest.MOONERING_SHIELD.get());

        makeBow((BMBowItem) AxolotlTest.DEVIL_BOW.get());
        makeBow((BMBowItem) AxolotlTest.ANGELIC_BOW.get());
        makeBow((BMBowItem) AxolotlTest.MID_TERM_BOW.get());

        makeCrossbow(AxolotlTest.DEVIL_CROSSBOW.get());
        makeCrossbow(AxolotlTest.ANGELIC_CROSSBOW.get());
        makeJanticRailgun(AxolotlTest.JANTIC_RAILGUN.get());
        makeCarewni(AxolotlTest.CAREWNI.get());

        addMobOutfitLayers();
        for (PlayerRenderer renderer : Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values()) {
            renderer.addLayer(new OutfitLayer<>(renderer, renderer.getModel().slim));
            renderer.addLayer(new CrateLayer<>(renderer));
        }
    }

    private static void addMobOutfitLayers() {
        for (EntityRenderer<?> renderer : Minecraft.getInstance().getEntityRenderDispatcher().renderers.values()) {
            if (renderer instanceof ZombieRenderer) {
                ((ZombieRenderer) renderer).addLayer(new OutfitLayer<>(((ZombieRenderer) renderer), false));
            }
            if (renderer instanceof DrownedRenderer) {
                ((DrownedRenderer) renderer).addLayer(new OutfitLayer<>(((DrownedRenderer) renderer), false));
            }
            if (renderer instanceof PiglinRenderer) {
                ((PiglinRenderer) renderer).addLayer(new OutfitLayer<>(((PiglinRenderer) renderer), false));
            }
        }
    }

    // Double Layer Render Lookup
    public static boolean getDoubleLayer(RenderType layerToCheck) {
        return layerToCheck == RenderType.cutout() || layerToCheck == RenderType.translucent();
    }

    // Copied from teamtwilight/twilightforest.
    public static void addAljanTextureUpdatePack() {
        // noinspection ConstantConditions
        if (Minecraft.getInstance() == null)
            // Normally Minecraft Client is never null except when generating through runData
            return;

        Minecraft.getInstance().getResourcePackRepository().addPackFinder((consumer, factory) -> consumer.accept(ResourcePackInfo.create(
                BackMath.backMath(BMUtils.ALJAN_TEXTURE_UPDATE_ID).toString(), false, () -> new AljanTextureUpdatePack(ModList.get()
                        .getModFileById(BackMath.MOD_ID).getFile()), factory, ResourcePackInfo.Priority.TOP, IPackNameDecorator.BUILT_IN)));
    }
}
