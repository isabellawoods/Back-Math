package com.sophicreeper.backmath.entity.outfit;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public class OutfitSlot {
    public static final Codec<OutfitSlot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(OutfitSlot::texture),
            ResourceLocation.CODEC.optionalFieldOf("emissive_texture", null).forGetter(OutfitSlot::emissiveTexture),
            Codec.INT.optionalFieldOf("color", null).forGetter(OutfitSlot::color)).apply(instance, OutfitSlot::new));
    private final ResourceLocation texture;
    @Nullable
    private final ResourceLocation emissiveTexture;
    @Nullable
    private final Integer color;

    public OutfitSlot(ResourceLocation texture, @Nullable ResourceLocation emissiveTexture, @Nullable Integer color) {
        this.texture = texture;
        this.emissiveTexture = emissiveTexture;
        this.color = color;
    }

    public OutfitSlot(ResourceLocation texture, @Nullable ResourceLocation emissiveTexture) {
        this(texture, emissiveTexture, null);
    }

    public OutfitSlot(ResourceLocation texture) {
        this(texture, null, null);
    }

    public ResourceLocation texture() {
        return this.texture;
    }

    @Nullable
    public ResourceLocation emissiveTexture() {
        return this.emissiveTexture;
    }

    @Nullable
    public Integer color() {
        return this.color;
    }

    public static OutfitSlot fromJSON(JsonObject object) {
        ResourceLocation texture = ResourceLocation.tryParse(JSONUtils.getAsString(object, "texture"));
        ResourceLocation emissiveTexture = object.has("emissive_texture") && object.get("emissive_texture").isJsonPrimitive() ? ResourceLocation.tryParse(object.get("emissive_texture").getAsString()) : null;
        Integer color = object.has("color") && object.get("color").isJsonPrimitive() ? JSONUtils.getAsInt(object, "color") : null;
        return new OutfitSlot(texture, emissiveTexture, color);
    }

    public static JsonElement toJSON(OutfitSlot slot) {
        JsonObject object = new JsonObject();
        object.addProperty("texture", slot.texture.toString());
        if (slot.emissiveTexture != null) object.addProperty("emissive_texture", slot.emissiveTexture.toString());
        if (slot.color != null) object.addProperty("color", slot.color);
        return object;
    }

    @Override
    public String toString() {
        return "OutfitSlot[texture=" + this.texture + ", emissive_texture=" + this.emissiveTexture + ", color=" + this.color + "]";
    }

    public static class Serializer implements JsonDeserializer<OutfitSlot>, JsonSerializer<OutfitSlot> {
        @Override
        public OutfitSlot deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();
            ResourceLocation texture = ResourceLocation.tryParse(JSONUtils.getAsString(object, "texture"));
            ResourceLocation emissiveTexture = object.has("emissive_texture") && object.get("emissive_texture").isJsonPrimitive() ? ResourceLocation.tryParse(object.get("emissive_texture").getAsString()) : null;
            Integer color = object.has("color") && object.get("color").isJsonPrimitive() ? JSONUtils.getAsInt(object, "color") : null;
            return new OutfitSlot(texture, emissiveTexture, color);
        }

        @Override
        public JsonElement serialize(OutfitSlot slot, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("texture", slot.texture.toString());
            if (slot.emissiveTexture != null) object.addProperty("emissive_texture", slot.emissiveTexture.toString());
            if (slot.color != null) object.addProperty("color", slot.color);
            return object;
        }
    }
}
