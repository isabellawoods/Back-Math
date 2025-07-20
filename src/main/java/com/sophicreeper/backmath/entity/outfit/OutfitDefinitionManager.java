package com.sophicreeper.backmath.entity.outfit;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class OutfitDefinitionManager extends JsonReloadListener {
    private static final Gson GSON = createDefinitionSerializer().create();
    public static final Logger LOGGER = LogManager.getLogger("backmath");

    public OutfitDefinitionManager() {
        super(GSON, "outfit_definition");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceList, IResourceManager manager, IProfiler profiler) {
        ImmutableMap.Builder<ResourceLocation, OutfitDefinition> builder = ImmutableMap.builder();

        resourceList.forEach(((location, element) -> {
            try {
                if (element.isJsonObject()) {
                    OutfitDefinition definition = GSON.fromJson(element, OutfitDefinition.class);
                    builder.put(location, definition);
                }
            } catch (Exception exception) {
                LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.outfit_definition.parse_error", location)).getString(), exception);
            }
        }));
        OutfitDefinition.DATA_DRIVEN_OUTFITS.putAll(builder.build());
        LOGGER.info(new TranslationTextComponent("console.backmath.outfit_definition.loaded", builder.build().size()).getString());
    }

    public static GsonBuilder createDefinitionSerializer() {
        return new GsonBuilder().registerTypeAdapter(OutfitDefinition.class, new OutfitDefinition.Serializer());
    }
}
