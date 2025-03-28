package com.sophicreeper.backmath.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BMCommonConfigs {
    // World Generation
    // Ore Generation
    public final ForgeConfigSpec.BooleanValue devilOreGen;
    public final ForgeConfigSpec.BooleanValue netherDevilOreGen;
    public final ForgeConfigSpec.BooleanValue angelicOreGen;
    public final ForgeConfigSpec.BooleanValue abundantAngelicOreGen;
    public final ForgeConfigSpec.BooleanValue crystallineAngelicOreGen;
    public final ForgeConfigSpec.BooleanValue midTermOreGen;
    public final ForgeConfigSpec.BooleanValue aljameedOreGen;
    public final ForgeConfigSpec.BooleanValue mooneringOreGen;
    public final ForgeConfigSpec.BooleanValue janticOreGen;
    public final ForgeConfigSpec.BooleanValue abundantJanticOreGen;
    public final ForgeConfigSpec.BooleanValue aljamicCopperGen;
    public final ForgeConfigSpec.BooleanValue aljamicTinGen;

    // Miscellaneous
    public final ForgeConfigSpec.BooleanValue aljanDungeonsInAljan;
    public final ForgeConfigSpec.BooleanValue angerDungeonsInBackFields;

    // Carver Generation
    public final ForgeConfigSpec.BooleanValue enableAljanCarverGeneration;
    public final ForgeConfigSpec.BooleanValue enableAljanCavesAndRavines;
    public final ForgeConfigSpec.BooleanValue enableAljanLargeCaves;
    public final ForgeConfigSpec.BooleanValue enableUnderwaterAljanCaves;

    // Plants
    public final ForgeConfigSpec.BooleanValue grapeVinesInTaigas;
    public final ForgeConfigSpec.BooleanValue bananaJunglesInJungles;
    public final ForgeConfigSpec.BooleanValue turtleFriedEggFlowersInBeaches;
    public final ForgeConfigSpec.BooleanValue enderDragonFriedEggFlowersInTheEnd;

    // Biome Generation
    public final ForgeConfigSpec.BooleanValue backFieldGen;
    public final ForgeConfigSpec.BooleanValue originalBackFieldsGen;
    public final ForgeConfigSpec.BooleanValue modifiedBackFieldsGen;
    public final ForgeConfigSpec.BooleanValue angelicWoodsGen;

    // Mob Spawning
    public final ForgeConfigSpec.BooleanValue wandererSophieSpawn;
    public final ForgeConfigSpec.BooleanValue archerLuciaSpawn;
    public final ForgeConfigSpec.BooleanValue karateLuciaSpawn;
    public final ForgeConfigSpec.BooleanValue angrySophieSpawn;
    public final ForgeConfigSpec.BooleanValue insomniaSophieSpawn;
    public final ForgeConfigSpec.BooleanValue archerInsomniaSophieSpawn;
    public final ForgeConfigSpec.BooleanValue shyAlcalyteSpawn;
    public final ForgeConfigSpec.BooleanValue insomniaZombieSpawn;
    public final ForgeConfigSpec.BooleanValue zombieFabricioSpawn;
    public final ForgeConfigSpec.BooleanValue amaracamelerSpawn;
    public final ForgeConfigSpec.BooleanValue malaikaSpawn;
    public final ForgeConfigSpec.BooleanValue aljamicBonesSpawn;
    public final ForgeConfigSpec.BooleanValue sleepishSkeletonSpawn;
    public final ForgeConfigSpec.BooleanValue groundMobSpawningBackFields;
    public final ForgeConfigSpec.BooleanValue groundMobSpawningAljan;

    // Gameplay Aspects
    public final ForgeConfigSpec.BooleanValue safeAljan;
    public final ForgeConfigSpec.BooleanValue standingAljanTeleport;
    public final ForgeConfigSpec.BooleanValue aljanEnabledThroughStand;
    public final ForgeConfigSpec.BooleanValue enableMobAIChanges;
    public final ForgeConfigSpec.DoubleValue alcalyteArmorChance;

    // Gameplay Aspects - Items
    // Peace Tea
    public final ForgeConfigSpec.BooleanValue peaceTeaInvisibilityToggle;
    public final ForgeConfigSpec.BooleanValue peaceTeaGlowingToggle;

    // Gameplay Aspects - Structure Generation
    public final ForgeConfigSpec.BooleanValue sophieTowerGeneration;
    public final ForgeConfigSpec.IntValue sophieTowerAvgDistance;
    public final ForgeConfigSpec.IntValue sophieTowerMinDistance;
    public final ForgeConfigSpec.IntValue sophieTowerSeed;
    public final ForgeConfigSpec.BooleanValue sophieTowerTST;
    public final ForgeConfigSpec.IntValue sophieTowerYOffset;

    public final ForgeConfigSpec.BooleanValue fabricioHideoutDungeonGeneration;
    public final ForgeConfigSpec.IntValue fabricioHideoutDungeonAvgDistance;
    public final ForgeConfigSpec.IntValue fabricioHideoutDungeonMinDistance;
    public final ForgeConfigSpec.IntValue fabricioHideoutDungeonSeed;
    public final ForgeConfigSpec.BooleanValue fabricioHideoutDungeonTST;
    public final ForgeConfigSpec.IntValue fabricioHideoutDungeonYOffset;

    // Gameplay Aspects - Rendering
    public final ForgeConfigSpec.BooleanValue enableAljanFog;
    public final ForgeConfigSpec.DoubleValue aljanFogDensity;
    public final ForgeConfigSpec.BooleanValue changeAljanFogColorAtNight;
    public final ForgeConfigSpec.BooleanValue renderBreasts;

    // Bows
    public final ForgeConfigSpec.BooleanValue bowDamageCounter;
    public final ForgeConfigSpec.BooleanValue devilBowFCA;
    public final ForgeConfigSpec.BooleanValue devilBowCBD;
    public final ForgeConfigSpec.IntValue devilBowAAD;
    public final ForgeConfigSpec.IntValue devilBowFIT;
    public final ForgeConfigSpec.IntValue devilBowFRD;
    public final ForgeConfigSpec.BooleanValue angelicBowFCA;
    public final ForgeConfigSpec.BooleanValue angelicBowCBD;
    public final ForgeConfigSpec.IntValue angelicBowAAD;
    public final ForgeConfigSpec.IntValue angelicBowFIT;
    public final ForgeConfigSpec.IntValue angelicBowFRD;
    public final ForgeConfigSpec.BooleanValue midTermBowFCA;
    public final ForgeConfigSpec.BooleanValue midTermBowCBD;
    public final ForgeConfigSpec.IntValue midTermBowAAD;
    public final ForgeConfigSpec.IntValue midTermBowFIT;
    public final ForgeConfigSpec.IntValue midTermBowFRD;

    // Debug
    public final ForgeConfigSpec.BooleanValue logStructureLocationMessages;
    public final ForgeConfigSpec.BooleanValue logAljanTeleporterDebugging;

    // Items
    public final ForgeConfigSpec.BooleanValue populateWandererSophieHeadVariants;
    public final ForgeConfigSpec.BooleanValue populateQueenLucyHeadVariants;
    public final ForgeConfigSpec.BooleanValue milkedSwordsEnabled;
    public final ForgeConfigSpec.IntValue midTermCustomDurabilityBar;
    public final ForgeConfigSpec.IntValue spareyCustomDurabilityBar;
    public final ForgeConfigSpec.DoubleValue midTermLongswordReachIncrease;
    public final ForgeConfigSpec.DoubleValue carewniReachIncrease;
    public final ForgeConfigSpec.DoubleValue mechMechReachDecrease;

    public BMCommonConfigs(ForgeConfigSpec.Builder builder) {
        builder.comment("Welcome to the Back Math configuration file! Made on April 08th and 09th, 2022 and made working on April 21st and 22nd, 2022.");
        builder.push("oreGeneration");
        builder.comment("Overworld Ore Generation");
        this.devilOreGen = builder.comment("Allow Devil Ore to generate in the Overworld?").define("devilOreGeneration", true);
        this.netherDevilOreGen = builder.comment("Allow Nether Devil Ore to generate in the Nether?").define("netherDevilOreGeneration", true);
        this.angelicOreGen = builder.comment("Allow Angelic Ore to generate in the Overworld?").define("angelicOreGeneration", true);
        this.abundantAngelicOreGen = builder.comment("Allow Angelic Ore to generate more frequently in Angelic Woods?").define("abundantAngelicOreGeneration", true);
        this.crystallineAngelicOreGen = builder.comment("Allow Crystalline Angelic Ore to generate in Angelic Woods?").define("crystallineAngelicOreGeneration", true);
        this.midTermOreGen = builder.comment("Allow Mid-Term Ore to generate in the End?").define("midTermOreGeneration", false);
        builder.comment("Aljan Ore Generation");
        this.aljameedOreGen = builder.comment("Allow Aljameed Ore to generate in the Aljan?").define("aljameedOreGeneration", true);
        this.mooneringOreGen = builder.comment("Allow Moonering Ore to generate in the Aljan?").define("mooneringOreGeneration", true);
        this.janticOreGen = builder.comment("Allow Jantic Ore to generate in the Aljan?").define("janticOreGen", true);
        this.abundantJanticOreGen = builder.comment("Allow Jantic Ore to generate more frequently in Aljamic Highlands?").define("abundantJanticOreGeneration", true);
        this.aljamicCopperGen = builder.comment("Allow Aljamic Copper Ore to generate in the Aljan?").define("aljamicCopperOreGeneration", true);
        this.aljamicTinGen = builder.comment("Allow Aljamic Tin Ore to generate in the Aljan?").define("aljamicTinOreGeneration", true);
        builder.pop();

        builder.push("featureGeneration");
        this.grapeVinesInTaigas = builder.comment("Allow Grape Vine trees to generate in any biome categorized as a \"taiga\"?").define("grapeVinesInTaiga", true);
        this.bananaJunglesInJungles = builder.comment("Allow Banana Jungle trees to generate in any biome categorized as a \"jungle\"?").define("bananaJunglesInJungles", true);
        this.turtleFriedEggFlowersInBeaches = builder.comment("Allow Turtle Fried Egg Flowers to generate in any biome categorized as a \"beach\"?").define("turtleFriedEggFlowersInBeaches", true);
        this.enderDragonFriedEggFlowersInTheEnd = builder.comment("Allow Ender Dragon Fried Egg Flowers to generate in any biome categorized as \"End\"?").define("enderDragonFriedEggFlowersInTheEnd", true);
        this.aljanDungeonsInAljan = builder.comment("Allow Aljan Dungeons to spawn randomly in Aljan caves?").define("aljanDungeonsInAljan", true);
        this.angerDungeonsInBackFields = builder.comment("Allow Anger Dungeons to spawn randomly in Back Field caves?").define("angerDungeonsInBackFields", true);
        builder.pop();

        builder.push("biomeGeneration");
        this.backFieldGen = builder.comment("Allow the (truly) original Back Field to generate in the Overworld?").define("backFieldGeneration", false);
        this.originalBackFieldsGen = builder.comment("Allow the Original Back Fields to generate in the Overworld?").define("originalBackFieldsGeneration", true);
        this.modifiedBackFieldsGen = builder.comment("Allow the Modified Back Fields to generate in the Overworld?").define("modifiedBackFieldsGeneration", true);
        this.angelicWoodsGen = builder.comment("Allow the Angelic Woods to generate in the Overworld?").define("angelicWoodsGeneration", true);
        builder.pop();

        builder.push("carverGeneration");
        this.enableAljanCarverGeneration = builder.comment("Allow carvers (caves & ravines) to generate in the Aljan?").define("enableAljanCarverGeneration", true);
        this.enableAljanCavesAndRavines = builder.comment("Allows Caves and Ravines to generate in the Aljan?").define("enableAljanCavesAndRavines", true);
        this.enableAljanLargeCaves = builder.comment("Allows large caves to generate in the Aljan?").define("enableAljanLargeCaves", true);
        this.enableUnderwaterAljanCaves = builder.comment("Allow Underwater Caves to generate in the Aljan?").define("enableUnderwaterAljanCaves", true);
        builder.pop();

        builder.push("mobSpawning");
        this.wandererSophieSpawn = builder.comment("Allow Wanderer Sophies to spawn in Back Fields biomes?").define("wandererSophieSpawning", true);
        this.angrySophieSpawn = builder.comment("Allow Angry Sophies to spawn in Back Fields biomes?").define("angrySophieSpawn", true);
        this.shyAlcalyteSpawn = builder.comment("Allow Shy Alcalytes to spawn in Back Fields biomes?").define("shyAlcalyteSpawning", true);
        this.archerLuciaSpawn = builder.comment("Allow Archer Lucias to spawn in Back Fields biomes?").define("archerLuciaSpawning", true);
        this.karateLuciaSpawn = builder.comment("Allow Karate Lucias to spawn in Back Fields biomes?").define("karateLuciaSpawning", true);
        this.insomniaSophieSpawn = builder.comment("Allow Insomnia Sophies to spawn in Back Fields biomes?").define("insomniaSophieSpawning", true);
        this.archerInsomniaSophieSpawn = builder.comment("Allow Archer Insomnia Sophies to spawn in Back Fields biomes?").define("archerInsomniaSophieSpawning", true);
        builder.comment("Aljan Mob Spawning:");
        this.insomniaZombieSpawn = builder.comment("Allow Insomnia Zombies to spawn in the Aljan?").define("insomniaZombieSpawning", true);
        this.zombieFabricioSpawn = builder.comment("Allow Zombie Fabricios to spawn in the Aljan?").define("zombieFabricioSpawning", true);
        this.aljamicBonesSpawn = builder.comment("Allow Aljamic Bones to spawn in the Aljan?").define("aljamicBonesSpawning", true);
        this.sleepishSkeletonSpawn = builder.comment("Allow Sleepish Skeletons to spawn in the Aljan?").define("sleepishSkeletonSpawning", true);
        this.amaracamelerSpawn = builder.comment("Allow Amaracamelers to spawn in Amaracamel Sticks?").define("amaracamelerSpawning", true);
        this.malaikaSpawn = builder.comment("Allow Malaikas to spawn in the Aljan?").define("malaikaSpawn", false);
        builder.comment("Mob Spawning:");
        this.groundMobSpawningBackFields = builder.comment("Prohibits all Back Fields mobs to spawn anywhere \"invalid\", which includes in the air. Disable for old behavior.").define("groundMobSpawning.backFieldsMobs", true);
        this.groundMobSpawningAljan = builder.comment("Prohibits all Aljan mobs to spawn anywhere \"invalid\", which includes in the air.").define("groundMobSpawning.aljanMobs", true);
        builder.pop();

        builder.push("items");
        this.populateWandererSophieHeadVariants = builder.comment("If the creative tab should add all wanderer sophie head variants.").define("populateWandererSophieHeadVariants", true);
        this.populateQueenLucyHeadVariants = builder.comment("If the creative tab should add all queen lucy head variants.").define("populateQueenLucyHeadVariants", true);
        this.milkedSwordsEnabled = builder.comment("Allow Milked Swords to give you Milk Buckets when hitting a mob?").define("milkedSwordsEnabled", true);
        this.midTermCustomDurabilityBar = builder.comment("Defines the color of the durability bar of mid-term items.").defineInRange("midTermCustomDurabilityBar", 0x1DC2D1, 0x000000, 0xFFFFFF);
        this.spareyCustomDurabilityBar = builder.comment("Defines the color of the durability bar of the sparey tool set.").defineInRange("spareyCustomDurabilityBar", 0x85C284, 0x000000, 0xFFFFFF);
        this.midTermLongswordReachIncrease = builder.comment("How much extra Reach Distance a Mid-Term Longsword gives you.").defineInRange("midTermLongswordReachIncrease", 2.5D, -3, 64);
        this.carewniReachIncrease = builder.comment("How much extra Reach Distance a Carewni gives you.").defineInRange("carewniReachIncrease", 2.5D, -3, 64);
        this.mechMechReachDecrease = builder.comment("How much extra Reach Distance a Mech-Mech gives you.").defineInRange("mechMechReachDecrease", -1D, -3, 64);
        builder.pop();

        builder.push("gameplayAspects");
        this.aljanEnabledThroughStand = builder.comment("Allows the Aljan to be accessed through the portal stand. Disabling this will NOT disable the dimension itself.").define("aljanEnabledThroughStand", true);
        this.safeAljan = builder.comment("Makes it so that Aljan Portal Stands that are generated by teleporting have a jantical in it.").define("safeAljan", false);
        this.standingAljanTeleport = builder.comment("Allows any entity touching the portal stand to be teleported over to the Aljan.").define("standingAljanTeleport", false);
        this.enableMobAIChanges = builder.comment("Enables the new Back Math mob AI changes. Currently, this toggle doesn't do anything.").define("enableMobAIChanges", false);
        this.alcalyteArmorChance = builder.comment("The chance of alcalytes (like collector alcalytes) wearing armor.").defineInRange("alcalyteArmorChance", 0.75, 0, 1);
        this.peaceTeaInvisibilityToggle = builder.comment("Allows Peace Teas to give Invisibility to the affected entity.").define("peaceTea.invisibilityToggle", false);
        this.peaceTeaGlowingToggle = builder.comment("Allows Peace Teas to give Glowing to the affected entity.").define("peaceTea.glowingToggle", false);
        this.enableAljanFog = builder.comment("Enables a thick fog in the Aljan at all times.").define("aljanAmbience.enableAljanFog", true);
        this.aljanFogDensity = builder.comment("How thick should the Aljan fog be.").defineInRange("aljanAmbience.aljanFogDensity", 0.02, 0, 10);
        this.changeAljanFogColorAtNight = builder.comment("Should the sky/fog color change at nighttime in the Aljan? ").define("aljanAmbience.changeAljanFogColorAtNight", true);
        this.renderBreasts = builder.comment("Whether breasts should render on mobs that have them. Will only render if Female Gender Mod is installed.").define("renderBreasts", false);
        builder.comment("Back Math Bow Configurations:");
        this.bowDamageCounter = builder.comment("Shows a damage counter when shooting an arrow above the hotbar.").define("bowDamageCounter", false);
        builder.comment("Devil Bow:");
        this.devilBowFCA = builder.comment("Should a Devil Bow force its arrow to be critical?").define("devilBow.forcedCriticalArrow", false);
        this.devilBowCBD = builder.comment("Should a Devil Bow not lose durability when firing arrows?").define("devilBow.canBeDamaged", true);
        this.devilBowAAD = builder.comment("Should a Devil Bow deal additional damage? Set to 0 for no additional damage.").defineInRange("devilBow.additionalArrowDamage", 0, 0, 32767);
        this.devilBowFIT = builder.comment("For how many ticks should a Devil Bow set mobs on fire? Set to 0 for no fire.").defineInRange("devilBow.fireInTicks", 100, 0, 32767);
        this.devilBowFRD = builder.comment("How long should a Devil Bow be held up to fire? Defaults to 72.000 ticks (1 hour).").defineInRange("devilBow.firerateDelay", 72000, 0, 72000);

        builder.comment("Angelic Bow:");
        this.angelicBowFCA = builder.comment("Should an Angelic Bow force its arrows to be critical?").define("angelicBow.forcedCriticalArrow", false);
        this.angelicBowCBD = builder.comment("Should an Angelic Bow not lose durability when firing arrows?").define("angelicBow.canBeDamaged", true);
        this.angelicBowAAD = builder.comment("Should an Angelic Bow deal additional damage? Set to 0 for no additional damage.").defineInRange("angelicBow.additionalArrowDamage", 0, 0, 32767);
        this.angelicBowFIT = builder.comment("For how many ticks should an Angelic Bow set mobs on fire? Set to 0 for no fire.").defineInRange("angelicBow.fireInTicks", 0, 0, 32767);
        this.angelicBowFRD = builder.comment("How long should an Angelic Bow be held up to fire? Defaults to 72.000 ticks (1 hour).").defineInRange("angelicBow.firerateDelay", 72000, 0, 72000);

        builder.comment("Mid-Term Bow:");
        this.midTermBowFCA = builder.comment("Should a Mid-Term Bow force its arrow to be critical?").define("midTermBow.forcedCriticalArrow", true);
        this.midTermBowCBD = builder.comment("Should a Mid-Term Bow not lose durability when firing arrows?").define("midTermBow.canBeDamaged", false);
        this.midTermBowAAD = builder.comment("Should a Mid-Term Bow deal additional damage? Set to 0 for no additional damage.").defineInRange("midTermBow.additionalArrowDamage", 0, 0, 32767);
        this.midTermBowFIT = builder.comment("For how many ticks should a Mid-Term Bow set mobs on fire? Set to 0 for no fire.)").defineInRange("midTermBow.fireInTicks", 200, 0, 32767);
        this.midTermBowFRD = builder.comment("How long should a Mid-Term Bow be held up to fire? Defaults to 72.000 ticks (1 hour).").defineInRange("midTermBow.firerateDelay", 10, 1, 72000);
        builder.pop();

        builder.push("structures");
        builder.comment("Sophie Towers");
        this.sophieTowerGeneration = builder.comment("Allow Sophie Towers to generate in Back Fields?").define("sophieTower.canGenerate", true);
        this.sophieTowerAvgDistance = builder.comment("Average distance in chunks between Sophie Towers.").defineInRange("sophieTower.averageDistance", 12, 1, 2000);
        this.sophieTowerMinDistance = builder.comment("Minimum distance in chunks between Sophie Towers. Must be less than the average distance.").defineInRange("sophieTower.minDistance", 7, 0, 1999);
        this.sophieTowerSeed = builder.comment("Sophie Tower's generation seed. Needed so no two structures spawn on each other.").defineInRange("sophieTower.seed", 1073741823, Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.sophieTowerTST = builder.comment("Whether Sophie Towers should terraform the surrounding terrain.").define("sophieTower.terraformSurroundingTerrain", true);
        this.sophieTowerYOffset = builder.comment("The Y offset of Sophie Towers.").defineInRange("sophieTower.yOffset", 1, -64, 192);

        builder.comment("Fabricio's Hideout Dungeons");
        this.fabricioHideoutDungeonGeneration = builder.comment("Allow Fabricio's Hideout Dungeons to generate in the Overworld?").define("fabricioHideoutDungeon.canGenerate", true);
        this.fabricioHideoutDungeonAvgDistance = builder.comment("Average distance in chunks between Fabricio's Hideout Dungeons.").defineInRange("fabricioHideoutDungeon.averageDistance", 15, 1, 2000);
        this.fabricioHideoutDungeonMinDistance = builder.comment("Minimum distance in chunks between Fabricio's Hideout Dungeons. Must be less than the average distance.").defineInRange("fabricioHideoutDungeon.minDistance", 10, 0, 1999);
        this.fabricioHideoutDungeonSeed = builder.comment("Fabricio's Hideout Dungeon's generation seed. Needed so no two structures spawn on each other.").defineInRange("fabricioHideoutDungeon.seed", 27482834, 0, Integer.MAX_VALUE);
        this.fabricioHideoutDungeonTST = builder.comment("Whether Fabricio's Hideout Dungeons terraform the surrounding terrain. Currently, it terraforms the surface terrain.").define("fabricioHideoutDungeon.terraformSurroundingTerrain", false);
        this.fabricioHideoutDungeonYOffset = builder.comment("The Y offset of Fabricio's Hideout Dungeons.").defineInRange("fabricioHideoutDungeon.yOffset", -48, -64, 192);

        builder.comment("Debug Code");
        this.logStructureLocationMessages = builder.comment("[DEBUG] Logs structure locations for Sophie Towers and Fabricio's Hideout Dungeons.").define("logStructureLocationMessages", false);
        this.logAljanTeleporterDebugging = builder.comment("[DEBUG] Enables a debug log for Aljan teleportation (TheAljanTeleporter).").define("logAljanTeleporterDebugging", false);
        builder.pop();
    }
}
