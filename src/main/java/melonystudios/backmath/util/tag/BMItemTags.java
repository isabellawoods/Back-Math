package melonystudios.backmath.util.tag;

import melonystudios.backmath.BackMath;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/// All item tags used by {@link BackMath Back Math}.
/// <b><p>Namespaces:</b>
/// <li><code>backmath</code>: Back Math's default namespace. Used for tags related to <em>only</em> this mod.</li>
/// <li><code>c</code>: Common namespace. Used for tags used across mods, like the old <code>forge</code> and my <code>melony</code> namespaces.</li>
/// @see net.minecraft.tags.ItemTags Vanilla's item tags
/// @see net.neoforged.neoforge.common.Tags.Items NeoForge's and common item tags
public class BMItemTags {
    // NAMESPACE: Back Math
    // Grouping tags
    public static final TagKey<Item> FRUITS = backMath("fruits");
    public static final TagKey<Item> CUT_FRUITS = backMath("cut_fruits");
    public static final TagKey<Item> JUICES = backMath("juices");
    public static final TagKey<Item> POPSICLES = backMath("popsicles");
    public static final TagKey<Item> JAMS = backMath("jams");
    public static final TagKey<Item> CRATES = backMath("crates");
    public static final TagKey<Item> CRATE_FRUIT_SAPLINGS = backMath("crate_fruit_saplings");
    public static final TagKey<Item> CROWNS = backMath("crowns");
    public static final TagKey<Item> RAILGUNS = backMath("railguns");
    public static final TagKey<Item> MILKED_SWORDS = backMath("milked_swords");

    public static final TagKey<Item> CRYSTALLINE_BIRCH_LOGS = backMath("crystalline_birch_logs");
    public static final TagKey<Item> GOLDENWOOD_LOGS = backMath("goldenwood_logs");
    public static final TagKey<Item> GUAVA_LOGS = backMath("guava_logs");
    public static final TagKey<Item> JABUTICABA_LOGS = backMath("jabuticaba_logs");
    public static final TagKey<Item> CORK_OAK_LOGS = backMath("cork_oak_logs");
    public static final TagKey<Item> ALJANWOOD_LOGS = backMath("aljanwood_logs");
    public static final TagKey<Item> ALJANCAP_LOGS = backMath("aljancap_logs");
    public static final TagKey<Item> INSOMNIAN_LOGS = backMath("insomnian_logs");
    public static final TagKey<Item> AVONDALIC_WILLOW_LOGS = backMath("avondalic_willow_logs");

    // Crafting tags
    public static final TagKey<Item> ALJANSTONE_CRAFTING_MATERIALS = backMath("aljanstone_crafting_materials");
    public static final TagKey<Item> ALJAN_LOG_MATERIALS = backMath("aljan_log_materials");
    public static final TagKey<Item> ALJAN_STICK_MATERIALS = backMath("aljan_stick_materials");
    public static final TagKey<Item> HARDENED_AMARACAMEL_MATERIALS = backMath("hardened_amaracamel_materials");
    public static final TagKey<Item> MID_TERM_MATERIALS = backMath("mid_term_materials");
    public static final TagKey<Item> OIMT_MATERIALS = backMath("oimt_materials");
    public static final TagKey<Item> TABU_SMELTABLES = backMath("tabu_smeltables");
    public static final TagKey<Item> MORTAR_AND_PESTLES = backMath("mortar_and_pestles");

    // Mob-related tags
    public static final TagKey<Item> ARCHER_LUCIA_CAN_PICK_UP = backMath("archer_lucia_can_pick_up");
    public static final TagKey<Item> TERMIAN_RAIDERS_CAN_PICK_UP = backMath("termian_raiders_can_pick_up");
    public static final TagKey<Item> COLLECTOR_ALCALYTES_CAN_PICK_UP = backMath("collector_alcalytes_can_pick_up");
    public static final TagKey<Item> CHICKEN_JOCKEYS_CANNOT_PICK_UP = backMath("chicken_jockeys_cannot_pick_up");
    public static final TagKey<Item> QUEEN_LUCY_PET_FOOD = backMath("queen_lucy_pet_food");
    public static final TagKey<Item> QUEEN_LUCY_PET_POISONOUS_FOOD = backMath("queen_lucy_pet_poisonous_food");
    public static final TagKey<Item> QUEEN_LUCY_PET_TEMPT_ITEMS = backMath("queen_lucy_pet_tempt_items");
    public static final TagKey<Item> WANDERER_TERMIAN_TEMPT_ITEMS = backMath("wanderer_termian_tempt_items");
    public static final TagKey<Item> ARCHER_LUCIA_TEMPT_ITEMS = backMath("archer_lucia_tempt_items");
    public static final TagKey<Item> SHY_ALCALYTE_TEMPT_ITEMS = backMath("shy_alcalyte_tempt_items");

    // Utilitarian tags
    public static final TagKey<Item> CANNOT_CRAFT_WITH_AT_CRYSTALLIZER = backMath("cannot_craft_with_at_crystallizer");
    public static final TagKey<Item> JANTIC_RAILGUN_PROJECTILES = backMath("jantic_railgun_projectiles");
    public static final TagKey<Item> CHESTPLATE_MINING_ITEMS = backMath("popsicles");
    public static final TagKey<Item> ZOMBIE_ALCALYTES_CURES = backMath("zombie_alcalyte_cures");
    public static final TagKey<Item> OUTFITS = backMath("popsicles");
    // "allowed_in_<main|off>hand" -> equipment tables are now in vanilla;
    // "provides_effect/<effect>" -> can be made into a "provides_effect" data component;

    // Miscellaneous
    public static final TagKey<Item> SOPHIE_IDEA = backMath("sophie_idea");
    public static final TagKey<Item> ALPHA_IDEA = backMath("sophie_idea/alpha");
    public static final TagKey<Item> SOPHIES_TAKE_OVER_IDEA = backMath("sophie_idea/sophies_take_over");
    public static final TagKey<Item> ALJAMIC_WARS_IDEA = backMath("sophie_idea/aljamic_wars");
    public static final TagKey<Item> BOUNTIFULLY_EXPANSIVE_IDEA = backMath("sophie_idea/bountifully_expansive");
    public static final TagKey<Item> ONE_POINT_NINE_IDEA = backMath("sophie_idea/one_point_nine");
    public static final TagKey<Item> MOLDS = backMath("molds");
    public static final TagKey<Item> EMPTY_MOLDS = backMath("molds/empty");
    public static final TagKey<Item> EMPTY_CRYSTALLINE_MOLDS = backMath("molds/empty_crystalline");
    public static final TagKey<Item> SINGULARITY_MOLDS = backMath("molds/singularity");
    public static final TagKey<Item> CRYSTALLIZED_MOLDS = backMath("molds/crystallized");
    public static final TagKey<Item> INGOT_MOLDS = backMath("molds/ingot");
    public static final TagKey<Item> GEM_MOLDS = backMath("molds/gem");
    public static final TagKey<Item> ROD_MOLDS = backMath("molds/rod");
    public static final TagKey<Item> MOLD_MOLDS = backMath("molds/mold");

    // NAMESPACE: C -- includes both "forge" and "melony" namespaces.
    public static final TagKey<Item> MILK_CONTAINERS = common("milk_containers");
    public static final TagKey<Item> CLEARS_MOB_EFFECTS = common("clears_mob_effects");
    public static final TagKey<Item> BONE_MEAL = common("bone_meal");
    public static final TagKey<Item> DUAL_WIELDED = common("dual_wielded");
    public static final TagKey<Item> FULLY_LIT_ITEMS = common("fully_lit_items");
    public static final TagKey<Item> KNIVES = common("tools/knives");
    public static final TagKey<Item> ELYTRA = common("elytra");

    public static final TagKey<Item> RED_YELLOW_DYES = common("dyes/red_yellow");
    public static final TagKey<Item> ALJAN_LIGHT_BLUE_DYES = common("dyes/aljan_light_blue");
    public static final TagKey<Item> POISON_BROWN_DYES = common("dyes/poison_brown");
    public static final TagKey<Item> INSOMNIAN_DYES = common("dyes/insomnian");

    // Mineral Tags
    // Raw Materials
    public static final TagKey<Item> DEVIL_RAW_MATERIALS = common("raw_materials/devil");
    public static final TagKey<Item> ANGELIC_RAW_MATERIALS = common("raw_materials/angelic");
    public static final TagKey<Item> MID_TERM_RAW_MATERIALS = common("raw_materials/mid_term");
    public static final TagKey<Item> ALJAMIC_COPPER_RAW_MATERIALS = common("raw_materials/aljamic_copper");
    public static final TagKey<Item> ALJAMIC_TIN_RAW_MATERIALS = common("raw_materials/aljamic_tin");
    public static final TagKey<Item> TIN_RAW_MATERIALS = common("raw_materials/tin");
    public static final TagKey<Item> ALJAMEED_RAW_MATERIALS = common("raw_materials/aljameed");
    public static final TagKey<Item> MOONERING_RAW_MATERIALS = common("raw_materials/moonering");

    // Ingots
    public static final TagKey<Item> DEVIL_INGOTS = common("ingots/devil");
    public static final TagKey<Item> ANGELIC_INGOTS = common("ingots/angelic");
    public static final TagKey<Item> CHRISTIAN_MID_TERM_INGOTS = common("ingots/christian_mid_term");
    public static final TagKey<Item> CHRISTIAN_MID_TERM_DEVIL_INGOTS = common("ingots/christian_mid_term_devil");
    public static final TagKey<Item> CHRISTIAN_MID_TERM_ANGELIC_INGOTS = common("ingots/christian_mid_term_angelic");
    public static final TagKey<Item> WARMTERM_INGOTS = common("ingots/warmterm");
    public static final TagKey<Item> MID_TERM_INGOTS = common("ingots/mid_term");
    public static final TagKey<Item> COLDTERM_INGOTS = common("ingots/coldterm");
    public static final TagKey<Item> OIMT_INGOTS = common("ingots/obsidian_infused_mid_term");
    public static final TagKey<Item> HARDENED_AMARACAMEL_INGOTS = common("ingots/hardened_amaracamel");
    public static final TagKey<Item> MILKLLARY_INGOTS = common("ingots/milkllary");
    public static final TagKey<Item> TWO_THIRDS_HILLARY_INGOTS = common("ingots/two_thirds_hillary");
    public static final TagKey<Item> ALJAMIC_COPPER_INGOTS = common("ingots/aljamic_copper");
    public static final TagKey<Item> ALJAMIC_TIN_INGOTS = common("ingots/aljamic_tin");
    public static final TagKey<Item> TIN_INGOTS = common("ingots/tin");
    public static final TagKey<Item> ALJAMEED_INGOTS = common("ingots/aljameed");
    public static final TagKey<Item> ALJANSTEEL_INGOTS = common("ingots/aljansteel");
    public static final TagKey<Item> MOONERING_INGOTS = common("ingots/moonering");
    public static final TagKey<Item> OBSIDIAN_INGOTS = common("ingots/obsidian");

    // Gems
    public static final TagKey<Item> CRYSTALLINE_ANGELIC_GEMS = common("gems/crystalline_angelic");
    public static final TagKey<Item> JANTICAL_GEMS = common("gems/jantical");
    public static final TagKey<Item> PERSONA_GEMS = common("gems/persona");
    public static final TagKey<Item> EMERIOND_GEMS = common("gems/emeriond");

    // Nuggets
    public static final TagKey<Item> DEVIL_NUGGETS = common("nuggets/devil");
    public static final TagKey<Item> ANGELIC_NUGGETS = common("nuggets/angelic");
    public static final TagKey<Item> CHRISTIAN_MID_TERM_NUGGETS = common("nuggets/christian_mid_term");
    public static final TagKey<Item> MID_TERM_NUGGETS = common("nuggets/mid_term");
    public static final TagKey<Item> OIMT_NUGGETS = common("nuggets/obsidian_infused_mid_term");
    public static final TagKey<Item> HARDENED_AMARACAMEL_NUGGETS = common("nuggets/hardened_amaracamel");
    public static final TagKey<Item> MILKLLARY_NUGGETS = common("nuggets/milkllary");
    public static final TagKey<Item> MILKLLARITY_NUGGETS = common("nuggets/milkllarity");
    public static final TagKey<Item> TWO_THIRDS_HILLARY_NUGGETS = common("nuggets/two_thirds_hillary");
    public static final TagKey<Item> CRYSTALLINE_ANGELIC_NUGGETS = common("nuggets/crystalline_angelic");
    public static final TagKey<Item> ALJAMEED_NUGGETS = common("nuggets/aljameed");
    public static final TagKey<Item> ALJANSTEEL_NUGGETS = common("nuggets/aljansteel");
    public static final TagKey<Item> MOONERING_NUGGETS = common("nuggets/moonering");
    public static final TagKey<Item> JANTICAL_NUGGETS = common("nuggets/jantical");
    public static final TagKey<Item> DIAMOND_NUGGETS = common("nuggets/diamond");

    // Singularities
    public static final TagKey<Item> SINGULARITIES = common("singularities");
    public static final TagKey<Item> MID_TERM_SINGULARITIES = common("singularities/mid_term");
    public static final TagKey<Item> OIMT_SINGULARITIES = common("singularities/obsidian_infused_mid_term");
    public static final TagKey<Item> MILKLLARY_SINGULARITIES = common("singularities/milkllary");
    public static final TagKey<Item> CHRISTIANITY_SINGULARITIES = common("singularities/christianity");
    public static final TagKey<Item> EMOTIONAL_SINGULARITIES = common("singularities/emotional");
    public static final TagKey<Item> HEAT_SINGULARITIES = common("singularities/heat");
    public static final TagKey<Item> MANGAED_MANGO_SINGULARITIES = common("singularities/mangaed_mango");
    public static final TagKey<Item> AMARACAMEL_SINGULARITIES = common("singularities/amaracamel");

    // Dusts
    public static final TagKey<Item> DEVIL_DUSTS = common("dusts/devil");
    public static final TagKey<Item> ANGELIC_DUSTS = common("dusts/angelic");
    public static final TagKey<Item> CHRISTIAN_MID_TERM_DUSTS = common("dusts/christian_mid_term");
    public static final TagKey<Item> CHRISTIAN_MID_TERM_DEVIL_DUSTS = common("dusts/christian_mid_term_devil");
    public static final TagKey<Item> CHRISTIAN_MID_TERM_ANGELIC_DUSTS = common("dusts/christian_mid_term_angelic");
    public static final TagKey<Item> MID_TERM_DUSTS = common("dusts/mid_term");
    public static final TagKey<Item> OIMT_DUSTS = common("dusts/obsidian_infused_mid_term");
    public static final TagKey<Item> HARDENED_AMARACAMEL_DUSTS = common("dusts/hardened_amaracamel");
    public static final TagKey<Item> HILLARY_DUSTS = common("dusts/hillary");
    public static final TagKey<Item> MILKLLARY_DUSTS = common("dusts/milkllary");
    public static final TagKey<Item> TWO_THIRDS_HILLARY_DUSTS = common("dusts/two_thirds_hillary");
    public static final TagKey<Item> ALJAMEED_DUSTS = common("dusts/aljameed");
    public static final TagKey<Item> ALJANSTEEL_DUSTS = common("dusts/aljansteel");
    public static final TagKey<Item> MOONERING_DUSTS = common("dusts/moonering");
    public static final TagKey<Item> WATER_DUSTS = common("dusts/water");

    // Rods
    public static final TagKey<Item> DEVIL_RODS = common("rods/devil");
    public static final TagKey<Item> ANGELIC_RODS = common("rods/angelic");
    public static final TagKey<Item> MID_TERM_RODS = common("rods/mid_term");
    public static final TagKey<Item> ALJAMEED_RODS = common("rods/aljameed");
    public static final TagKey<Item> HILLARY_RODS = common("rods/hillary");
    public static final TagKey<Item> CRYSTALLINE_BIRCH_RODS = common("rods/crystalline_birch");
    public static final TagKey<Item> GOLDENWOOD_RODS = common("rods/goldenwood");
    public static final TagKey<Item> GUAVA_RODS = common("rods/guava");
    public static final TagKey<Item> JABUTICABA_RODS = common("rods/jabuticaba");
    public static final TagKey<Item> CORK_OAK_RODS = common("rods/cork_oak");
    public static final TagKey<Item> ALJANWOOD_RODS = common("rods/aljanwood");
    public static final TagKey<Item> ALJANCAP_RODS = common("rods/aljancap");
    public static final TagKey<Item> INSOMNIAN_RODS = common("rods/insomnian");
    public static final TagKey<Item> AVONDALIC_WILLOW_RODS = common("rods/avondalic_willow");
    public static final TagKey<Item> GOLD_RODS = common("rods/gold");

    private static TagKey<Item> backMath(String name) {
        return TagKey.create(Registries.ITEM, BackMath.backMath(name));
    }

    private static TagKey<Item> common(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
