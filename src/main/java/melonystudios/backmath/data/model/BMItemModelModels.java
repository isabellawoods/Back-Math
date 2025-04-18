package melonystudios.backmath.data.model;

import melonystudios.backmath.BackMath;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

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

    public BMItemModelModels(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, BackMath.MOD_ID, fileHelper);
    }

    public void standard(ModelFile parent, String name) {
        this.getBuilder(name).parent(parent).texture("layer0", "item/" + name);
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
    public void trimmedArmorItem(ModelFile parent, DeferredItem<Item> item) {
        if (item.get() instanceof ArmorItem armorItem) {
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
                this.withExistingParent(item.getId().getPath(), parent.getLocation()).override()
                        .model(new ModelFile.UncheckedModelFile(currentTrimLocation.getNamespace()  + ":item/" + currentTrimLocation.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0", BackMath.backMath("item/" + item.getId().getPath()));
            });
        }
    }
}
