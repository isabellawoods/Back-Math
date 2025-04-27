package com.sophicreeper.backmath.variant.manager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.sophicreeper.backmath.variant.queenlucypet.QueenLucyPetVariant;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class QueenLucyPetVariantManager extends JsonReloadListener {
    private static final Gson GSON = createQueenLucyPetVariantSerializer().create();
    public static final Logger LOGGER = LogManager.getLogger();

    public QueenLucyPetVariantManager() {
        super(GSON, "mob_variant/queen_lucy_pet");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceList, IResourceManager manager, IProfiler profiler) {
        ImmutableMap.Builder<ResourceLocation, QueenLucyPetVariant> builder = ImmutableMap.builder();

        resourceList.forEach(((location, element) -> {
            try {
                if (element.isJsonObject()) {
                    QueenLucyPetVariant variant = GSON.fromJson(element, QueenLucyPetVariant.class);
                    builder.put(location, variant);
                }
            } catch (Exception exception) {
                LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.queen_lucy_pet_variant.parse_error", location)).getString(), exception);
            }
        }));
        QueenLucyPetVariant.DATA_DRIVEN_VARIANTS.putAll(builder.build());
        LOGGER.info(new TranslationTextComponent("console.backmath.queen_lucy_pet_variant.loaded", builder.build().size()).getString());
    }

    public static GsonBuilder createQueenLucyPetVariantSerializer() {
        return new GsonBuilder().registerTypeAdapter(QueenLucyPetVariant.class, new QueenLucyPetVariant.Serializer());
    }
}
