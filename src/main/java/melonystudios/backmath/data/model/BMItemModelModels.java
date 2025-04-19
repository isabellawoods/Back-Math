package melonystudios.backmath.data.model;

import melonystudios.backmath.BackMath;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.LinkedHashMap;

public abstract class BMItemModelModels extends ItemModelProvider {
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> TRIM_MATERIALS = new LinkedHashMap<>();
    static {
        TRIM_MATERIALS.put(TrimMaterials.QUARTZ, 0.1F);
        TRIM_MATERIALS.put(TrimMaterials.IRON, 0.2F);
        TRIM_MATERIALS.put(TrimMaterials.NETHERITE, 0.3F);
        TRIM_MATERIALS.put(TrimMaterials.REDSTONE, 0.4F);
        TRIM_MATERIALS.put(TrimMaterials.COPPER, 0.5F);
        TRIM_MATERIALS.put(TrimMaterials.GOLD, 0.6F);
        TRIM_MATERIALS.put(TrimMaterials.EMERALD, 0.7F);
        TRIM_MATERIALS.put(TrimMaterials.DIAMOND, 0.8F);
        TRIM_MATERIALS.put(TrimMaterials.LAPIS, 0.9F);
        TRIM_MATERIALS.put(TrimMaterials.AMETHYST, 1.0F);
    }
    public final ModelFile generated = new ModelFile.UncheckedModelFile("item/generated");
    public final ModelFile handheld = new ModelFile.UncheckedModelFile("item/handheld");
    public final ModelFile handheld32x = new ModelFile.UncheckedModelFile(modLoc("item/handheld_32x"));

    public BMItemModelModels(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, BackMath.MOD_ID, fileHelper);
    }

    public void standard(ModelFile parent, String name) {
        this.getBuilder(name).parent(parent).texture("layer0", "item/" + name);
    }

    public void toolSet(ModelFile parent, String material, boolean milkedSword, String... toolNames) {
        if (milkedSword) {
            if (toolNames[0].equals("_blade")) {
                getBuilder("milked_" + material + toolNames[0]).parent(parent).texture("layer0", "item/" + material + toolNames[0]).texture("layer1", "item/milked_aljameed_blade_base");
            } else if (toolNames[0].contains("sparey")) {
                getBuilder("milked_" + material + toolNames[0]).parent(parent).texture("layer0", "item/" + material + toolNames[0]).texture("layer1", "item/milked_sparey_base");
            } else {
                getBuilder("milked_" + material + toolNames[0]).parent(parent).texture("layer0", "item/" + material + toolNames[0]).texture("layer1", "item/milked" + toolNames[0] + "_base");
            }
        }
        getBuilder(material + toolNames[0]).parent(parent).texture("layer0", "item/" + material + toolNames[0]);
        getBuilder(material + toolNames[1]).parent(parent).texture("layer0", "item/" + material + toolNames[1]);
        getBuilder(material + toolNames[2]).parent(parent).texture("layer0", "item/" + material + toolNames[2]);
        getBuilder(material + toolNames[3]).parent(parent).texture("layer0", "item/" + material + toolNames[3]);
        getBuilder(material + toolNames[4]).parent(parent).texture("layer0", "item/" + material + toolNames[4]);
    }

    public void toolSet(ModelFile parent, String material, boolean milkedSword) {
        toolSet(parent, material, milkedSword, "_sword", "_pickaxe", "_shovel", "_axe", "_hoe");
    }

    public void armorSet(ModelFile parent, String material, boolean isBreastplate, boolean hasWarriorHelmet) {
        trimmedArmorItem(parent, BuiltInRegistries.ITEM.get(BackMath.backMath(material + "_helmet")));
        if (hasWarriorHelmet) trimmedArmorItem(parent, BuiltInRegistries.ITEM.get(BackMath.backMath(material + "_warrior_helmet")));
        trimmedArmorItem(parent, BuiltInRegistries.ITEM.get(BackMath.backMath(material + (isBreastplate ? "_breastplate" : "_chestplate"))));
        trimmedArmorItem(parent, BuiltInRegistries.ITEM.get(BackMath.backMath(material + "_leggings")));
        trimmedArmorItem(parent, BuiltInRegistries.ITEM.get(BackMath.backMath(material + "_boots")));
    }

    public void queenLucyShirts(ModelFile parent, String... shirtDesigns) {
        for (String design : shirtDesigns) trimmedArmorItem(parent, BuiltInRegistries.ITEM.get(BackMath.backMath("queen_lucy_shirt_" + design)));
    }

    public void oneTextureCrowns(String... crowns) {
        for (String crown : crowns) {
            getBuilder(crown + "_crown").parent(getExistingFile(modLoc("item/template_crown"))).texture("crown", "item/crown/" + crown + "_crown");
        }
    }

    public void twoTextureCrowns(String... crowns) {
        for (String crown : crowns) {
            getBuilder(crown + "_crown").parent(getExistingFile(modLoc("item/template_two_texture_crown"))).texture("crown_a", "item/crown/" + crown + "_crown_a").texture("crown_b", "item/crown/" + crown + "_crown_b");
        }
    }

    public void swordWithMilked(ModelFile parent, String sword, boolean sparey) {
        getBuilder(sword).parent(parent).texture("layer0", "item/" + sword);
        getBuilder("milked_" + sword).parent(parent).texture("layer0", "item/" + sword).texture("layer1", "item/milked_" + (sparey ? "sparey" : "sword") + "_base");
    }

    public void vanillaMilkedSword(ModelFile parent, String sword) {
        getBuilder("milked_" + sword).parent(parent).texture("layer0", mcLoc("item/" + sword)).texture("layer1", modLoc("item/milked_sword_base"));
    }

    public void crossbow(String name) {
        ModelFile templateCrossbow = getExistingFile(modLoc("item/template_crossbow"));
        standard(templateCrossbow, name + "_pulling_0");
        standard(templateCrossbow, name + "_pulling_1");
        standard(templateCrossbow, name + "_pulling_2");
        standard(templateCrossbow, name + "_arrow");
        standard(templateCrossbow, name + "_firework");

        getBuilder(name).parent(templateCrossbow).texture("layer0", modLoc("item/" + name + "_standby"))
                .override().predicate(pulling(), 1).model(getExistingFile(modLoc("item/" + name + "_pulling_0"))).end()
                .override().predicate(pulling(), 1).predicate(pullProgress(), 0.58F).model(getExistingFile(modLoc("item/" + name + "_pulling_1"))).end()
                .override().predicate(pulling(), 1).predicate(pullProgress(), 1).model(getExistingFile(modLoc("item/" + name + "_pulling_2"))).end()
                .override().predicate(charged(), 1).model(getExistingFile(modLoc("item/" + name + "_arrow"))).end()
                .override().predicate(charged(), 1).predicate(fireworkRocketLoaded(), 1).model(getExistingFile(modLoc("item/" + name + "_arrow"))).end();
    }

    public void janticRailgun(String name) {
        ModelFile templateCrossbow = getExistingFile(modLoc("item/template_crossbow"));
        standard(templateCrossbow, name + "_pulling_0");
        standard(templateCrossbow, name + "_pulling_1");
        standard(templateCrossbow, name + "_pulling_2");
        standard(templateCrossbow, name + "_loaded");

        getBuilder(name).parent(templateCrossbow).texture("layer0", modLoc("item/" + name + "_standby"))
                .override().predicate(pulling(), 1).model(getExistingFile(modLoc("item/" + name + "_pulling_0"))).end()
                .override().predicate(pulling(), 1).predicate(pullProgress(), 0.58F).model(getExistingFile(modLoc("item/" + name + "_pulling_1"))).end()
                .override().predicate(pulling(), 1).predicate(pullProgress(), 1).model(getExistingFile(modLoc("item/" + name + "_pulling_2"))).end()
                .override().predicate(charged(), 1).model(getExistingFile(modLoc("item/" + name + "_loaded"))).end();
    }

    public void bow(String name) {
        ModelFile templateBow = getExistingFile(modLoc("item/template_bow"));
        standard(templateBow, name + "_pulling_0");
        standard(templateBow, name + "_pulling_1");
        standard(templateBow, name + "_pulling_2");

        getBuilder(name).parent(templateBow).texture("layer0", modLoc("item/" + name))
                .override().predicate(pulling(), 1).model(getExistingFile(modLoc("item/" + name + "_pulling_0"))).end()
                .override().predicate(pulling(), 1).predicate(pullProgress(), 0.65F).model(getExistingFile(modLoc("item/" + name + "_pulling_1"))).end()
                .override().predicate(pulling(), 1).predicate(pullProgress(), 0.9F).model(getExistingFile(modLoc("item/" + name + "_pulling_2"))).end();
    }

    public void shield(String name, ResourceLocation shieldTexture, ResourceLocation particleTexture) {
        getBuilder(name + "_blocking").parent(getExistingFile(modLoc("item/template_shield_blocking"))).texture("shield", shieldTexture).texture("particle", particleTexture);

        getBuilder(name).parent(getExistingFile(modLoc("item/template_shield"))).texture("shield", shieldTexture).texture("particle", particleTexture).override().predicate(ResourceLocation.withDefaultNamespace("blocking"), 1).model(getExistingFile(modLoc(
                "item/" + name + "_blocking"))).end();
    }

    public void queenLucySummonerStaff(String name) {
        getBuilder(name + "_inventory").parent(this.handheld).texture("layer0", "item/" + name);

        withExistingParent(name, this.generated.getLocation()).customLoader(SeparateTransformsModelBuilder::begin).base(nested().parent(getExistingFile(modLoc("item/" + name + "_in_hand"))))
                .perspective(ItemDisplayContext.GUI, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemDisplayContext.GROUND, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemDisplayContext.FIXED, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory")))).end();
    }

    public void dualWieldedSword(String name) {
        getBuilder(name + "_inventory").parent(this.handheld).texture("layer0", "item/" + name + "_inventory");

        if (name.equals("carewni")) {
            getBuilder(name + "_in_hand_trans").parent(this.handheld32x).texture("layer0", "item/" + name + "_trans");
            getBuilder(name + "_in_hand").parent(this.handheld32x).texture("layer0", "item/" + name).override().predicate(BackMath.backMath("june_check"), 1).model(getExistingFile(modLoc("item/carewni_in_hand_trans"))).end();
        } else {
            getBuilder(name + "_in_hand").parent(this.handheld32x).texture("layer0", "item/" + name);
        }

        withExistingParent(name, this.handheld.getLocation()).customLoader(SeparateTransformsModelBuilder::begin).base(nested().parent(getExistingFile(modLoc("item/" + name + "_in_hand"))))
                .perspective(ItemDisplayContext.GUI, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemDisplayContext.GROUND, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemDisplayContext.FIXED, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory")))).end();
    }

    public void fruitLeaves(String name, String fruit, ResourceLocation leavesTexture) {
        getBuilder(name).parent(getExistingFile(modLoc("item/template_fruit_leaves"))).texture("leaves", leavesTexture).texture("fruit", modLoc("block/" + fruit));
    }

    public void block(String name) {
        withExistingParent(name, modLoc("block/" + name));
    }

    public void block(String name, String extras) {
        withExistingParent(name, modLoc("block/" + name + extras));
    }

    public void notebook(String name, String bottom, String screen, String keyboard) {
        getBuilder(name).parent(getExistingFile(modLoc("item/template_notebook"))).texture("bottom", "block/" + bottom).texture("screen", "block/" + screen).texture("keyboard", "block/" + keyboard);
    }

    public void stuffedNotebook(String name, String bottom, String screen, String keyboard, String inner) {
        getBuilder(name).parent(getExistingFile(modLoc("item/template_stuffed_cookie_notebook"))).texture("bottom", "block/" + bottom).texture("screen", "block/" + screen).texture("keyboard", "block/" + keyboard)
                .texture("inner", "block/" + inner);
    }

    /// @author El_Redstoniano. Copied from [Kaupenjoe's NeoForge 1.21 tutorials](https://github.com/Tutorials-By-Kaupenjoe/NeoForge-Tutorial-1.21.X/blob/main/src/main/java/net/kaupenjoe/tutorialmod/datagen/ModItemModelProvider.java).
    public void trimmedArmorItem(ModelFile parent, Item item) {
        if (item instanceof ArmorItem armorItem) {
            TRIM_MATERIALS.forEach((material, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String itemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + material.location().getPath();
                String currentTrimName = itemPath + "_" + material.location().getPath() + "_trim";
                ResourceLocation itemLocation = ResourceLocation.parse(itemPath);
                ResourceLocation trimLocation = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation currentTrimLocation = ResourceLocation.parse(currentTrimName);

                // This is used for making the existing file helper acknowledge that this texture exist, so this will avoid an IllegalArgumentException
                this.existingFileHelper.trackGenerated(trimLocation, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armor item files
                this.getBuilder(currentTrimName).parent(parent)
                        .texture("layer0", itemLocation.getNamespace() + ":item/" + itemLocation.getPath())
                        .texture("layer1", trimLocation);

                // Non-trimmed armor item file (normal variant)
                this.withExistingParent(BuiltInRegistries.ITEM.getKey(item).getPath(), parent.getLocation()).override()
                        .model(new ModelFile.UncheckedModelFile(currentTrimLocation.getNamespace()  + ":item/" + currentTrimLocation.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0", BackMath.backMath("item/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
            });
        }
    }

    public ResourceLocation pulling() {
        return ResourceLocation.withDefaultNamespace("pulling");
    }

    public ResourceLocation pullProgress() {
        return ResourceLocation.withDefaultNamespace("pull");
    }

    public ResourceLocation charged() {
        return ResourceLocation.withDefaultNamespace("charged");
    }

    public ResourceLocation fireworkRocketLoaded() {
        return ResourceLocation.withDefaultNamespace("firework");
    }
}
