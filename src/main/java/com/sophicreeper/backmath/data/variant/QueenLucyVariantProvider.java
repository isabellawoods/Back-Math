package com.sophicreeper.backmath.data.variant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sophicreeper.backmath.variant.queenlucy.QueenLucyVariant;
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

public abstract class QueenLucyVariantProvider implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, Tuple<QueenLucyVariant, JsonObject>> toSerialize = new HashMap<>();
    private final DataGenerator generator;
    private final ExistingFileHelper helper;
    private final String modID;

    public QueenLucyVariantProvider(DataGenerator generator, ExistingFileHelper fileHelper, String modID) {
        this.generator = generator;
        this.helper = fileHelper;
        this.modID = modID;
    }

    @Override
    @Nonnull
    public String getName() {
        return "Queen Lucy Variants: " + this.modID;
    }

    protected abstract void addVariants();

    @Override
    public void run(DirectoryCache cache) {
        this.addVariants();
        String variantsPath = "data/" + this.modID + "/mob_variant/queen_lucy/";

        this.toSerialize.forEach(LamdbaExceptionUtils.rethrowBiConsumer((name, pair) -> {
            Path variantFile = this.generator.getOutputFolder().resolve(variantsPath + name + ".json");
            this.validateVariant(pair.getA());
            IDataProvider.save(GSON, cache, pair.getB(), variantFile);
        }));
    }

    public void add(String name, QueenLucyVariant variant) {
        this.toSerialize.put(name, new Tuple<>(variant, variant.toJSON(variant)));
    }

    private void validateVariant(QueenLucyVariant variant) throws FileNotFoundException {
        boolean valid = this.helper.exists(variant.getTextureLocation(), ResourcePackType.CLIENT_RESOURCES, ".png", "textures");
        if (!valid) throw new FileNotFoundException(new TranslationTextComponent("error.backmath.queen_lucy_variant.no_texture", variant.getTextureLocation(), variant.getAssetID()).getString());
    }
}
