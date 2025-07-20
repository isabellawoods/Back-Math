package com.sophicreeper.backmath.blockentity;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.blockentity.custom.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sophicreeper.backmath.block.BMBlocks.*;

public class BMBlockEntities {
    public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BackMath.MOD_ID);

    public static final RegistryObject<TileEntityType<BMBarrelBlockEntity>> BARREL = BLOCK_ENTITIES.register("bm_barrel", () -> TileEntityType.Builder.of(BMBarrelBlockEntity::new,
            ALJANWOOD_BARREL.get(), ALJANCAP_BARREL.get(), INSOMNIAN_BARREL.get(), AVONDALIC_WILLOW_BARREL.get()).build(null));
    public static final RegistryObject<TileEntityType<BMSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> TileEntityType.Builder.of(BMSignBlockEntity::new,
            CRYSTALLINE_BIRCH_SIGN.get(), CRYSTALLINE_BIRCH_WALL_SIGN.get(), GOLDENWOOD_SIGN.get(), GOLDENWOOD_WALL_SIGN.get(), GUAVA_SIGN.get(), GUAVA_WALL_SIGN.get(),
            JABUTICABA_SIGN.get(), JABUTICABA_WALL_SIGN.get(), CORK_OAK_SIGN.get(), CORK_OAK_WALL_SIGN.get(), ALJANWOOD_SIGN.get(), ALJANWOOD_WALL_SIGN.get(), ALJANCAP_SIGN.get(),
            ALJANCAP_WALL_SIGN.get(), INSOMNIAN_SIGN.get(), INSOMNIAN_WALL_SIGN.get(), AVONDALIC_WILLOW_SIGN.get(), AVONDALIC_WILLOW_WALL_SIGN.get()).build(null));
    public static final RegistryObject<TileEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITIES.register("crate", () -> TileEntityType.Builder.of(CrateBlockEntity::new, BMBlocks.CRATE.get()).build(null));
    public static final RegistryObject<TileEntityType<HeadBlockEntity>> HEAD = BLOCK_ENTITIES.register("head", () -> TileEntityType.Builder.of(HeadBlockEntity::new,
            ANGRY_SOPHIE_HEAD.get(), ANGRY_SOPHIE_WALL_HEAD.get(), INSOMNIA_SOPHIE_HEAD.get(), INSOMNIA_SOPHIE_WALL_HEAD.get(),
            ALJAMIC_BONES_SKULL.get(), ALJAMIC_BONES_WALL_SKULL.get(), SLEEPISH_SKELETON_SKULL.get(), SLEEPISH_SKELETON_WALL_SKULL.get(),
            ZOMBIE_FABRICIO_HEAD.get(), ZOMBIE_FABRICIO_WALL_HEAD.get()).build(null));
    public static final RegistryObject<TileEntityType<WandererSophieHeadBlockEntity>> WANDERER_SOPHIE_HEAD = BLOCK_ENTITIES.register("wanderer_sophie_head", () ->
            TileEntityType.Builder.of(WandererSophieHeadBlockEntity::new, BMBlocks.WANDERER_SOPHIE_HEAD.get(), WANDERER_SOPHIE_WALL_HEAD.get()).build(null));
    public static final RegistryObject<TileEntityType<QueenLucyHeadBlockEntity>> QUEEN_LUCY_HEAD = BLOCK_ENTITIES.register("queen_lucy_head", () ->
            TileEntityType.Builder.of(QueenLucyHeadBlockEntity::new, BMBlocks.QUEEN_LUCY_HEAD.get(), QUEEN_LUCY_WALL_HEAD.get()).build(null));
}
