package com.sophicreeper.backmath.data.variant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sophicreeper.backmath.variant.wansophie.WandererSophieVariant;
import cpw.mods.modlauncher.api.LamdbaExceptionUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class WandererSophieVariantProvider implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, Tuple<WandererSophieVariant, JsonObject>> toSerialize = new HashMap<>();
    private final DataGenerator generator;
    private final ExistingFileHelper helper;
    private final String modID;

    public WandererSophieVariantProvider(DataGenerator generator, ExistingFileHelper fileHelper, String modID) {
        this.generator = generator;
        this.helper = fileHelper;
        this.modID = modID;
    }

    @Override
    @Nonnull
    public String getName() {
        return "Wanderer Sophie Variants: " + this.modID;
    }

    protected abstract void addVariants();

    @Override
    public void run(DirectoryCache cache) {
        this.addVariants();
        String variantsPath = "data/" + this.modID + "/mob_variant/wanderer_sophie/";

        this.toSerialize.forEach(LamdbaExceptionUtils.rethrowBiConsumer((name, pair) -> {
            Path variantFile = this.generator.getOutputFolder().resolve(variantsPath + name + ".json");
            this.validateVariant(pair.getA());
            IDataProvider.save(GSON, cache, pair.getB(), variantFile);
        }));
    }

    public void add(String name, WandererSophieVariant variant) {
        this.toSerialize.put(name, new Tuple<>(variant, variant.writeJSON(variant)));
    }

    private void validateVariant(WandererSophieVariant variant) throws FileNotFoundException {
        boolean valid = this.helper.exists(variant.getTextureLocation(), ResourcePackType.CLIENT_RESOURCES, ".png", "textures");
        if (!valid) throw new FileNotFoundException(new TranslationTextComponent("error.backmath.wanderer_sophie_variant.no_texture", variant.getTextureLocation(), variant.getAssetID()).getString());
        if (variant.getEmissiveTexture() != null) {
            boolean valid1 = this.helper.exists(variant.getEmissiveTexture(), ResourcePackType.CLIENT_RESOURCES, ".png", "textures");
            if (!valid1) throw new FileNotFoundException(new TranslationTextComponent("error.backmath.wanderer_sophie_variant.no_texture", variant.getEmissiveTexture(), variant.getAssetID()).getString());
        }
    }
}
