package com.sophicreeper.backmath.data.models;

import com.sophicreeper.backmath.BackMath;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparatePerspectiveModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class BMItemModelModels extends ItemModelProvider {
    private final ModelFile generated = getExistingFile(mcLoc("item/generated"));
    private final ModelFile handheld = getExistingFile(mcLoc("item/handheld"));
    private final ModelFile handheld32x = getExistingFile(modLoc("item/handheld_32x"));

    public BMItemModelModels(DataGenerator generator, String modID, ExistingFileHelper fileHelper) {
        super(generator, modID, fileHelper);
    }

    public void standard(ModelFile parent, String name) {
        getBuilder(name).parent(parent).texture("layer0", "item/" + name);
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
        getBuilder(material + "_helmet").parent(parent).texture("layer0", "item/" + material + "_helmet");
        if (hasWarriorHelmet) getBuilder(material + "_warrior_helmet").parent(parent).texture("layer0", "item/" + material + "_warrior_helmet");
        getBuilder(material + (isBreastplate ? "_breastplate" : "_chestplate")).parent(parent).texture("layer0", "item/" + material + (isBreastplate ? "_breastplate" : "_chestplate"));
        getBuilder(material + "_leggings").parent(parent).texture("layer0", "item/" + material + "_leggings");
        getBuilder(material + "_boots").parent(parent).texture("layer0", "item/" + material + "_boots");
    }

    public void queenLucyShirts(ModelFile parent, String... shirtDesigns) {
        for (String design : shirtDesigns) getBuilder("queen_lucy_shirt_" + design).parent(parent).texture("layer0", "item/queen_lucy_shirt_" + design);
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

        getBuilder(name).parent(getExistingFile(modLoc("item/template_shield"))).texture("shield", shieldTexture).texture("particle", particleTexture).override().predicate(new ResourceLocation("blocking"), 1).model(getExistingFile(modLoc(
                "item/" + name + "_blocking"))).end();
    }

    public void queenLucySummonerStaff(String name) {
        getBuilder(name + "_inventory").parent(this.handheld).texture("layer0", "item/" + name);

        withExistingParent(name, this.generated.getLocation()).customLoader(SeparatePerspectiveModelBuilder::begin).base(nested().parent(getExistingFile(modLoc("item/" + name + "_in_hand"))))
                .perspective(ItemCameraTransforms.TransformType.GUI, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.GROUND, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.FIXED, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory")))).end();
    }

    public void dualWieldedSword(String name) {
        getBuilder(name + "_inventory").parent(this.handheld).texture("layer0", "item/" + name + "_inventory");
        getBuilder(name + "_in_hand").parent(this.handheld32x).texture("layer0", "item/" + name);

        withExistingParent(name, this.handheld.getLocation()).customLoader(SeparatePerspectiveModelBuilder::begin).base(nested().parent(getExistingFile(modLoc("item/" + name + "_in_hand"))))
                .perspective(ItemCameraTransforms.TransformType.GUI, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.GROUND, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.FIXED, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory")))).end();
    }

    public void carewni(String name) {
        getBuilder(name + "_inventory").parent(this.handheld).texture("layer0", "item/" + name + "_inventory");
        getBuilder(name + "_in_hand").parent(this.handheld32x).texture("layer0", "item/" + name);
        getBuilder(name + "_trans_inventory").parent(this.handheld).texture("layer0", "item/" + name + "_trans_inventory");
        getBuilder(name + "_trans_in_hand").parent(this.handheld32x).texture("layer0", "item/" + name + "_trans");

        withExistingParent(name + "_trans", this.handheld.getLocation()).customLoader(SeparatePerspectiveModelBuilder::begin).base(nested().parent(getExistingFile(modLoc("item/" + name + "_trans_in_hand"))))
                .perspective(ItemCameraTransforms.TransformType.GUI, this.nested().parent(getExistingFile(modLoc("item/" + name + "_trans_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.GROUND, this.nested().parent(getExistingFile(modLoc("item/" + name + "_trans_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.FIXED, this.nested().parent(getExistingFile(modLoc("item/" + name + "_trans_inventory")))).end();

        withExistingParent(name, this.handheld.getLocation()).customLoader(SeparatePerspectiveModelBuilder::begin).base(nested().parent(getExistingFile(modLoc("item/" + name + "_in_hand"))))
                .perspective(ItemCameraTransforms.TransformType.GUI, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.GROUND, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory"))))
                .perspective(ItemCameraTransforms.TransformType.FIXED, this.nested().parent(getExistingFile(modLoc("item/" + name + "_inventory")))).end()
                .override().predicate(BackMath.backMath("june_check"), 1).model(getExistingFile(modLoc("item/" + name + "_trans")));
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

    public void blockItem(String name) {
        getBuilder(name).parent(this.generated).texture("layer0", "block/" + name);
    }

    public void blockItem(String name, String extras) {
        getBuilder(name).parent(this.generated).texture("layer0", "block/" + name + extras);
    }

    public void glassPane(String name) {
        getBuilder(name + "_pane").parent(this.generated).texture("layer0", "block/" + name);
    }

    public void grapeVinePost(String woodName) {
        withExistingParent(woodName + "_grape_vine_post", modLoc("block/" + woodName + "_grape_vine_post_age0"));
    }

    public void notebook(String name, String bottom, String screen, String keyboard) {
        getBuilder(name).parent(getExistingFile(modLoc("item/template_notebook"))).texture("bottom", "block/" + bottom).texture("screen", "block/" + screen).texture("keyboard", "block/" + keyboard);
    }

    public void stuffedNotebook(String name, String bottom, String screen, String keyboard, String inner) {
        getBuilder(name).parent(getExistingFile(modLoc("item/template_stuffed_cookie_notebook"))).texture("bottom", "block/" + bottom).texture("screen", "block/" + screen).texture("keyboard", "block/" + keyboard)
                .texture("inner", "block/" + inner);
    }

    public ResourceLocation pulling() {
        return new ResourceLocation("pulling");
    }

    public ResourceLocation pullProgress() {
        return new ResourceLocation("pull");
    }

    public ResourceLocation charged() {
        return new ResourceLocation("charged");
    }

    public ResourceLocation fireworkRocketLoaded() {
        return new ResourceLocation("firework");
    }
}
