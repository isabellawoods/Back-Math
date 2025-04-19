package melonystudios.backmath.item;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.custom.tool.BMTools;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BMSetFields {
    public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, BackMath.MOD_ID);

    // Helmets
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAT_TIARA_ARMOR = MATERIALS.register("cat_tiara", () -> new ArmorMaterial(makeDefenseMap(3, 8, 6, 3), 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(Items.LIGHT_BLUE_WOOL), defaultLayers("cat_tiara"), 3, 0.1F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DOG_TIARA_ARMOR = MATERIALS.register("dog_tiara", () -> new ArmorMaterial(makeDefenseMap(3, 0, 0, 0), 15,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.BROWN_WOOL), defaultLayers("dog_tiara"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> KARATE_HEADBAND_ARMOR = MATERIALS.register("karate_headband", () -> new ArmorMaterial(makeDefenseMap(1, 0, 0, 0), 63,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ItemTags.WOOL), defaultLayers("karate_headband"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CANDY_YELLOW_TURTLE_ARMOR = MATERIALS.register("candy_yellow_turtle", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 9,
            SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(Items.TURTLE_SCUTE), defaultLayers("candy_yellow_turtle"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CANDY_PINK_TURTLE_ARMOR = MATERIALS.register("candy_pink_turtle", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 9,
            SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(Items.TURTLE_SCUTE), defaultLayers("candy_pink_turtle"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ALJAMIC_BONE_ARMOR = MATERIALS.register("aljamic_bone", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 15,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.ALJAMEED_INGOT), defaultLayers("aljamic_bone"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GOLDEN_HALO_ARMOR = MATERIALS.register("golden_halo", () -> new ArmorMaterial(makeDefenseMap(2, 0, 0, 0), 13,
            SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(Tags.Items.INGOTS_GOLD), defaultLayers("golden_halo"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RED_YELLOW_GLASSES_ARMOR = MATERIALS.register("red_yellow", () -> glasses("red_yellow"));

    // Chestplates
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GOLDEN_PLATED_ARMOR = MATERIALS.register("golden_plated", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 15,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.CHRISTIAN_MID_TERM_INGOT), defaultLayers("golden_plated"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> QLS_CURRENT = MATERIALS.register("qls_current", () -> queenLucyShirt("qls_current"));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> QLS_ALT = MATERIALS.register("qls_alt", () -> queenLucyShirt("qls_alt"));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> QLS_RELIC = MATERIALS.register("qls_relic", () -> queenLucyShirt("qls_relic"));

    // (Mostly) Full Armor Sets
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DEVIL_ARMOR = MATERIALS.register("devil", () -> new ArmorMaterial(makeDefenseMap(3, 6, 5, 3), 11,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.DEVIL_INGOT), defaultLayers("devil"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ANGELIC_ARMOR = MATERIALS.register("angelic", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 9,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.ANGELIC_INGOT), defaultLayers("angelic"), 0, 0.01F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MILKLLARY_ARMOR = MATERIALS.register("milkllary", () -> new ArmorMaterial(makeDefenseMap(3, 6, 5, 3), 18,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.MILKLLARY_INGOT), defaultLayers("milkllary"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MID_TERM_ARMOR = MATERIALS.register("mid_term", () -> new ArmorMaterial(makeDefenseMap(8, 14, 12, 8), 34,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AxolotlTest.MID_TERM), defaultLayers("mid_term"), 11, 0.3F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> OBSIDIAN_INFUSED_MID_TERM_ARMOR = MATERIALS.register("obsidian_infused_mid_term", () -> new ArmorMaterial(makeDefenseMap(12, 18, 16, 12), 42,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM_INGOT), defaultLayers("obsidian_infused_mid_term"), 15, 0.5F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PLATEFORCED_MID_TERM_ARMOR = MATERIALS.register("plateforced_mid_term", () -> new ArmorMaterial(makeDefenseMap(6, 9, 8, 6), 17,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AxolotlTest.OBSIDIAN_INGOT), defaultLayers("plateforced_mid_term"), 4.5F, 0.4F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARCHER_TERMIAN_ARMOR = MATERIALS.register("archer_termian", () -> new ArmorMaterial(makeDefenseMap(2, 5, 4, 2), 18,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(AxolotlTest.MILKLLARY_INGOT), defaultLayers("archer_termian"), 0, 0.05F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARCHER_ALCALYTE_ARMOR = MATERIALS.register("archer_alcalyte", () -> new ArmorMaterial(makeDefenseMap(3, 4, 4, 3), 18,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(AxolotlTest.ALJAMEED_INGOT), defaultLayers("archer_alcalyte"), 0, 0.05F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HARDENED_AMARACAMEL_ARMOR = MATERIALS.register("hardened_amaracamel", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 12,
            SoundEvents.ARMOR_EQUIP_CHAIN, () -> Ingredient.of(AxolotlTest.HARDENED_AMARACAMEL_BATTER), defaultLayers("hardened_amaracamel"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> JANTSKIN_ARMOR = MATERIALS.register("jantskin", () -> new ArmorMaterial(makeDefenseMap(1, 3, 2, 1), 15,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(AxolotlTest.JANTSKIN), defaultLayers("jantskin"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ALJAMEED_ARMOR = MATERIALS.register("aljameed", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 15,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.ALJAMEED_INGOT), defaultLayers("aljameed"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MOONERING_ARMOR = MATERIALS.register("moonering", () -> new ArmorMaterial(makeDefenseMap(3, 8, 6, 3), 10,
            SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(AxolotlTest.MOONERING_INGOT), defaultLayers("moonering"), 2, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> JANTIQUIFIED_MOONERING_ARMOR = MATERIALS.register("jantiquified_moonering", () -> new ArmorMaterial(makeDefenseMap(4, 9, 7, 4), 12,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AxolotlTest.MOONERING_INGOT), defaultLayers("jantiquified_moonering"), 3, 0.05F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BAKUGOU_ARMOR = MATERIALS.register("bakugou", () -> new ArmorMaterial(makeDefenseMap(0, 0, 0, 0), 13,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, defaultLayers("bakugou"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> INSOMNIAC_TERMIAN_SLEEPWEAR_ARMOR = MATERIALS.register("insomniac_termian_sleepwear", () -> new ArmorMaterial(makeDefenseMap(0, 0, 0, 0), 15,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, defaultLayers("insomniac_termian_sleepwear"), 0, 0));

    // Warrior Helmets
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_DEVIL_ARMOR = MATERIALS.register("warrior_devil", () -> new ArmorMaterial(makeDefenseMap(3, 6, 5, 3), 11,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.DEVIL_INGOT), defaultLayers("warrior_devil"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_ANGELIC_ARMOR = MATERIALS.register("warrior_angelic", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 9,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.ANGELIC_INGOT), defaultLayers("warrior_angelic"), 0, 0.01F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_MILKLLARY_ARMOR = MATERIALS.register("warrior_milkllary", () -> new ArmorMaterial(makeDefenseMap(3, 6, 5, 3), 18,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.MILKLLARY_INGOT), defaultLayers("warrior_milkllary"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_MID_TERM_ARMOR = MATERIALS.register("warrior_mid_term", () -> new ArmorMaterial(makeDefenseMap(8, 14, 12, 8), 34,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AxolotlTest.MID_TERM), defaultLayers("warrior_mid_term"), 11, 0.3F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_OBSIDIAN_INFUSED_MID_TERM_ARMOR = MATERIALS.register("warrior_obsidian_infused_mid_term", () -> new ArmorMaterial(makeDefenseMap(12, 18, 16, 12), 42,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM_INGOT), defaultLayers("warrior_obsidian_infused_mid_term"), 15, 0.5F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_HARDENED_AMARACAMEL_ARMOR = MATERIALS.register("warrior_hardened_amaracamel", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 12,
            SoundEvents.ARMOR_EQUIP_CHAIN, () -> Ingredient.of(AxolotlTest.HARDENED_AMARACAMEL_BATTER), defaultLayers("warrior_hardened_amaracamel"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_ALJAMEED_ARMOR = MATERIALS.register("warrior_aljameed", () -> new ArmorMaterial(makeDefenseMap(2, 6, 5, 2), 15,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(AxolotlTest.ALJAMEED_INGOT), defaultLayers("warrior_aljameed"), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_MOONERING_ARMOR = MATERIALS.register("warrior_moonering", () -> new ArmorMaterial(makeDefenseMap(3, 8, 6, 3), 10,
            SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(AxolotlTest.MOONERING_INGOT), defaultLayers("warrior_moonering"), 2, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR_JANTIQUIFIED_MOONERING_ARMOR = MATERIALS.register("warrior_jantiquified_moonering", () -> new ArmorMaterial(makeDefenseMap(4, 9, 7, 4), 12,
            SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AxolotlTest.MOONERING_INGOT), defaultLayers("warrior_jantiquified_moonering"), 3, 0.05F));

    private static List<ArmorMaterial.Layer> defaultLayers(String name) {
        return List.of(new ArmorMaterial.Layer(BackMath.backMath(name)));
    }

    private static Map<ArmorItem.Type, Integer> makeDefenseMap(int helmet, int chestplate, int leggings, int boots) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.HELMET, helmet);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.BOOTS, boots);
        return map;
    }

    /// Makes an armor material for glasses.
    /// @param glassType The type of glass block used for these glasses, used in locating the texture of this armor piece, found at <code>backmath:textures/models/armor/\<glassType>_glasses_layer_<1-2>.png</code>
    private static ArmorMaterial glasses(String glassType) {
        return new ArmorMaterial(makeDefenseMap(0, 0, 0, 0), 13, SoundEvents.ARMOR_EQUIP_CHAIN, () -> Ingredient.of(Tags.Items.GLASS_BLOCKS),
                defaultLayers(glassType + "_glasses"), 0, 0);
    }

    /// Makes an armor material for a queen lucy shirt.
    /// @param shirtDesign The design of the shirt, used in locating the texture of this armor piece, found at <code>backmath:textures/models/armor/qls_\<shirtDesign>_layer<1-2>.png</code>
    private static ArmorMaterial queenLucyShirt(String shirtDesign) {
        return new ArmorMaterial(makeDefenseMap(0, 0, 0, 0), 8, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ItemTags.WOOL),
                defaultLayers("qls_" + shirtDesign), 0, 0);
    }

    // Single-Piece Tool Sets
    public static final BMTools BUTTER_SWORD_SET = new BMTools(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 200, 6, 2, 40, () -> Ingredient.of(Items.BREAD));
    public static final BMTools GOLDEN_PATTY_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 32, 12, 0, 22, () -> Ingredient.of(AxolotlTest.CHRISTIAN_MID_TERM_INGOT));
    public static final BMTools WATER_TALC_POWDER_SET = new BMTools(BlockTags.INCORRECT_FOR_STONE_TOOL, 720, 4, 1, 5, () -> Ingredient.of(Tags.Items.INGOTS_GOLD));
    public static final BMTools RAINBOW_PENCIL_SET = new BMTools(BlockTags.INCORRECT_FOR_IRON_TOOL, 720, 5, 43, 69, () -> Ingredient.EMPTY);
    public static final BMTools MECH_MECH_SET = new BMTools(BlockTags.INCORRECT_FOR_IRON_TOOL, 1561, 4, 0, 22, () -> Ingredient.of(AxolotlTest.DIAMOND_SHARD));
    public static final BMTools CAREWNI_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1758, 0, 13, 25, () -> Ingredient.of(AxolotlTest.CHRISTIAN_MID_TERM_INGOT));
    public static final BMTools PERSONA_SWORD_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1758, 0, 8, 13, () -> Ingredient.of(AxolotlTest.PERSONA_SHARD));
    public static final BMTools BREAD_SWORD_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 150, 0, 1, 13, () -> Ingredient.of(Items.BREAD));
    public static final BMTools TABU_SWORD_SET = new BMTools(BlockTags.INCORRECT_FOR_STONE_TOOL, 1250, 3, 2, 20, () -> Ingredient.EMPTY);
    public static final BMTools KARATE_TRAINING_STICK_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1561, 8, 3, 25, () -> Ingredient.of(Tags.Items.RODS_WOODEN));

    // Tool Sets
    public static final BMTools DEVIL_SET = new BMTools(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6, 2, 14, () -> Ingredient.of(AxolotlTest.DEVIL_INGOT));
    public static final BMTools ANGELIC_SET = new BMTools(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6, 2, 14, () -> Ingredient.of(AxolotlTest.ANGELIC_INGOT));
    public static final BMTools MILKLLARY_SET = new BMTools(BlockTags.INCORRECT_FOR_IRON_TOOL, 450, 7, 3, 21, () -> Ingredient.of(AxolotlTest.MILKLLARY_INGOT));
    public static final BMTools OLIVE_SET = new BMTools(BlockTags.INCORRECT_FOR_IRON_TOOL, 646, 4, 1, 69, () -> Ingredient.EMPTY);
    public static final BMTools DEVIL_SPAREY_SET = new BMTools(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 9, 18, 12, () -> Ingredient.of(AxolotlTest.DEVIL_INGOT));
    public static final BMTools SPAREY_SET = new BMTools(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 9, 18, 12, () -> Ingredient.of(AxolotlTest.CHRISTIAN_MID_TERM_INGOT));
    public static final BMTools MID_TERM_SET = new BMTools(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 4062, 12, 16, 17, () -> Ingredient.of(AxolotlTest.MID_TERM));
    public static final BMTools MID_TERM_SPAREY_SET = new BMTools(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 4062, 12, 61, 36, () -> Ingredient.of(AxolotlTest.MID_TERM));
    public static final BMTools OBSIDIAN_INFUSED_MID_TERM_SET = new BMTools(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 8126, 16, 58, 45, () -> Ingredient.of(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM));
    public static final BMTools OBSIDIAN_INFUSED_MID_TERM_SPAREY_SET = new BMTools(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9, 76, 12, () -> Ingredient.of(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM));
    public static final BMTools CRYSTALLINE_BIRCH_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2, 0, 15, () -> Ingredient.EMPTY);
    public static final BMTools GOLDENWOOD_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2, 0, 15, () -> Ingredient.EMPTY);
    public static final BMTools GUAVA_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2, 0, 15, () -> Ingredient.EMPTY);
    public static final BMTools JABUTICABA_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2, 0, 15, () -> Ingredient.EMPTY);
    public static final BMTools CORK_OAK_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2, 0, 15, () -> Ingredient.EMPTY);
    public static final BMTools ALJANWOOD_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 2, 0, 15, () -> Ingredient.EMPTY);
    public static final BMTools ALJANCAP_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 68, 2.2F, 0, 13, () -> Ingredient.EMPTY);
    public static final BMTools INSOMNIAN_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 72, 2.3F, 0, 13, () -> Ingredient.EMPTY);
    public static final BMTools AVONDALIC_WILLOW_SET = new BMTools(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 61, 2.2F, 0.4F, 16, () -> Ingredient.EMPTY);
    public static final BMTools ALJANSTONE_SET = new BMTools(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4, 1, 5, () -> Ingredient.EMPTY);
    public static final BMTools SLEEPINGSTONE_SET = new BMTools(BlockTags.INCORRECT_FOR_STONE_TOOL, 161, 4.5F, 1, 5, () -> Ingredient.EMPTY);
    public static final BMTools ALJAMEED_SET = new BMTools(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6, 2, 14, () -> Ingredient.of(AxolotlTest.ALJAMEED_INGOT));
    public static final BMTools ALJANSTEEL_SET = new BMTools(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 500, 6.5F, 3, 16, () -> Ingredient.of(AxolotlTest.ALJANSTEEL_INGOT));
    public static final BMTools MOONERING_SET = new BMTools(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8, 3, 10, () -> Ingredient.of(AxolotlTest.MOONERING_INGOT));
    public static final BMTools JANTIQUIFIED_MOONERING_SET = new BMTools(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 10, 4.5F, 14, () -> Ingredient.of(AxolotlTest.MOONERING_INGOT));
}
