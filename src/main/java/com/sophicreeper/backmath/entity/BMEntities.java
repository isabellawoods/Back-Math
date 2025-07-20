package com.sophicreeper.backmath.entity;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.custom.*;
import com.sophicreeper.backmath.entity.custom.alcalyte.CollectorAlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.alcalyte.ShyAlcalyteEntity;
import com.sophicreeper.backmath.entity.custom.aljan.*;
import com.sophicreeper.backmath.entity.custom.misc.BMBoatEntity;
import com.sophicreeper.backmath.entity.custom.misc.ChocoGlueEntity;
import com.sophicreeper.backmath.entity.custom.misc.InsomniaArrowEntity;
import com.sophicreeper.backmath.entity.custom.misc.JanticBoltEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BMEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BackMath.MOD_ID);

    public static final RegistryObject<EntityType<WandererSophieEntity>> WANDERER_SOPHIE = ENTITIES.register("friend_sophie", () ->
            EntityType.Builder.<WandererSophieEntity>of(WandererSophieEntity::new, EntityClassification.AMBIENT).sized(0.6F, 1.8F).clientTrackingRange(32).build("friend_sophie"));

    public static final RegistryObject<EntityType<AngrySophieEntity>> ANGRY_SOPHIE = ENTITIES.register("angry_sophie", () ->
            EntityType.Builder.of(AngrySophieEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).clientTrackingRange(32).build("angry_sophie"));

    public static final RegistryObject<EntityType<InsomniaSophieEntity>> INSOMNIA_SOPHIE = ENTITIES.register("insomnia_sophie", () ->
            EntityType.Builder.of(InsomniaSophieEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).clientTrackingRange(32).build(BackMath.backMath("insomnia_sophie").toString()));

    public static final RegistryObject<EntityType<ArcherInsomniaSophieEntity>> ARCHER_INSOMNIA_SOPHIE = ENTITIES.register("archer_insomnia_sophie", () ->
            EntityType.Builder.of(ArcherInsomniaSophieEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).clientTrackingRange(32).build(BackMath.backMath("archer_insomnia_sophie").toString()));

    public static final RegistryObject<EntityType<QueenLucyEntity>> QUEEN_LUCY = ENTITIES.register("queen_sophie", () ->
            EntityType.Builder.<QueenLucyEntity>of(QueenLucyEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).fireImmune().clientTrackingRange(64).build(BackMath.backMath("queen_sophie").toString()));

    public static final RegistryObject<EntityType<WarriorSophieEntity>> WARRIOR_SOPHIE = ENTITIES.register("warrior_sophie", () ->
            EntityType.Builder.of(WarriorSophieEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).clientTrackingRange(32).build(BackMath.backMath("warrior_sophie").toString()));

    public static final RegistryObject<EntityType<ArcherLuciaEntity>> ARCHER_LUCIA = ENTITIES.register("archer_lucia", () ->
            EntityType.Builder.of(ArcherLuciaEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).clientTrackingRange(32).build(BackMath.backMath("archer_lucia").toString()));

    public static final RegistryObject<EntityType<KarateLuciaEntity>> KARATE_LUCIA = ENTITIES.register("karate_lucia", () ->
            EntityType.Builder.of(KarateLuciaEntity::new, EntityClassification.AMBIENT).sized(0.6F, 1.8F).clientTrackingRange(64).build(BackMath.backMath("karate_lucia").toString()));

    public static final RegistryObject<EntityType<ShyAlcalyteEntity>> SHY_ALCALYTE = ENTITIES.register("shy_fabricio", () ->
            EntityType.Builder.of(ShyAlcalyteEntity::new, EntityClassification.AMBIENT).sized(0.6F, 1.8F).clientTrackingRange(32).build(BackMath.backMath("shy_fabricio").toString()));

    public static final RegistryObject<EntityType<QueenLucyPetEntity>> QUEEN_LUCY_PET = ENTITIES.register("queen_sophie_pet", () ->
            EntityType.Builder.of(QueenLucyPetEntity::new, EntityClassification.CREATURE).clientTrackingRange(10).fireImmune().sized(0.3F, 0.9F).build(BackMath.backMath("queen_sophie_pet").toString()));

    // BM 1.7.0: Aljamic Wars Creatures and Enemies
    public static final RegistryObject<EntityType<InsomniaZombieEntity>> INSOMNIA_ZOMBIE = ENTITIES.register("insomnia_zombie", () ->
            EntityType.Builder.of(InsomniaZombieEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build(BackMath.backMath("insomnia_zombie").toString()));

    public static final RegistryObject<EntityType<ZombieFabricioEntity>> ZOMBIE_FABRICIO = ENTITIES.register("zombie_fabricio", () ->
            EntityType.Builder.of(ZombieFabricioEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build(BackMath.backMath("zombie_fabricio").toString()));

    public static final RegistryObject<EntityType<AljamicBonesEntity>> ALJAMIC_BONES = ENTITIES.register("aljamic_bones", () ->
            EntityType.Builder.of(AljamicBonesEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build(BackMath.backMath("aljamic_bones").toString()));

    public static final RegistryObject<EntityType<SleepishSkeletonEntity>> SLEEPISH_SKELETON = ENTITIES.register("sleepish_skeleton", () ->
            EntityType.Builder.of(SleepishSkeletonEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build(BackMath.backMath("sleepish_skeleton").toString()));

    public static final RegistryObject<EntityType<AmaracamelerEntity>> AMARACAMELER = ENTITIES.register("amaracameler", () ->
            EntityType.Builder.of(AmaracamelerEntity::new, EntityClassification.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10).build(BackMath.backMath("amaracameler").toString()));

    public static final RegistryObject<EntityType<MalaikaEntity>> MALAIKA = ENTITIES.register("malaika", () ->
            EntityType.Builder.of(MalaikaEntity::new, EntityClassification.MONSTER).sized(0.7F, 2.4F).clientTrackingRange(10).build(BackMath.backMath("malaika").toString()));

    public static final RegistryObject<EntityType<JanticleEntity>> JANTICLE = ENTITIES.register("janticle", () ->
            EntityType.Builder.<JanticleEntity>of(JanticleEntity::new, EntityClassification.MONSTER).sized(0.5F, 0.5F).clientTrackingRange(16).build(BackMath.backMath("janticle").toString()));

    // BM 1.8.0: Bountifully Expansive Creatures and Enemies
    public static final RegistryObject<EntityType<BMBoatEntity>> BACK_MATH_BOAT = ENTITIES.register("backmath_boat", () ->
            EntityType.Builder.<BMBoatEntity>of(BMBoatEntity::new, EntityClassification.MISC).sized(1.375F, 0.5F).build(BackMath.backMath("backmath_boat").toString()));

    public static final RegistryObject<EntityType<InsomniaArrowEntity>> INSOMNIA_ARROW = ENTITIES.register("insomnia_arrow", () ->
            EntityType.Builder.<InsomniaArrowEntity>of(InsomniaArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(BackMath.backMath("insomnia_arrow").toString()));

    public static final RegistryObject<EntityType<ChocoGlueEntity>> CHOCOGLUE_PROJECTILE = ENTITIES.register("chocoglue_proj", () ->
            EntityType.Builder.<ChocoGlueEntity>of(ChocoGlueEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).build(BackMath.backMath("chocoglue_proj").toString()));

    public static final RegistryObject<EntityType<JanticBoltEntity>> JANTIC_BOLT = ENTITIES.register("jantic_bolt", () ->
            EntityType.Builder.<JanticBoltEntity>of(JanticBoltEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).build(BackMath.backMath("jantic_bolt").toString()));

    // Alcalyte Union Members
    public static final RegistryObject<EntityType<CollectorAlcalyteEntity>> COLLECTOR_ALCALYTE = ENTITIES.register("collector_fabricio", () ->
            EntityType.Builder.of(CollectorAlcalyteEntity::new, EntityClassification.CREATURE).sized(0.6F, 1.8F).build(BackMath.backMath("collector_fabricio").toString()));
}
