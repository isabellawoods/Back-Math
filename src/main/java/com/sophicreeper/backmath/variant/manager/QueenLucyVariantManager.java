package com.sophicreeper.backmath.variant.manager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.sophicreeper.backmath.variant.queenlucy.QueenLucyVariant;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class QueenLucyVariantManager extends JsonReloadListener {
    private static final Gson GSON = createQueenLucyVariantSerializer().create();
    public static final Logger LOGGER = LogManager.getLogger();

    public QueenLucyVariantManager() {
        super(GSON, "mob_variant/queen_lucy");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceList, IResourceManager manager, IProfiler profiler) {
        ImmutableMap.Builder<ResourceLocation, QueenLucyVariant> builder = ImmutableMap.builder();

        resourceList.forEach(((location, element) -> {
            try {
                if (element.isJsonObject()) {
                    QueenLucyVariant variant = GSON.fromJson(element, QueenLucyVariant.class);
                    builder.put(location, variant);
                }
            } catch (Exception exception) {
                LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.queen_lucy_variant.parse_error", location)).getString(), exception);
            }
        }));
        QueenLucyVariant.DATA_DRIVEN_VARIANTS.putAll(builder.build());
        LOGGER.info(new TranslationTextComponent("console.backmath.queen_lucy_variant.loaded", builder.build().size()).getString());
    }

    public static GsonBuilder createQueenLucyVariantSerializer() {
        return new GsonBuilder().registerTypeAdapter(QueenLucyVariant.class, new QueenLucyVariant.Serializer());
    }
}
