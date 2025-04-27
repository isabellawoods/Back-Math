package com.sophicreeper.backmath.entity.outfit;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class OutfitDefinition {
    public static final Codec<OutfitDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(OutfitDefinition::assetID),
            OutfitSlot.CODEC.optionalFieldOf("head", null).forGetter(OutfitDefinition::headSlot),
            OutfitSlot.CODEC.optionalFieldOf("chest", null).forGetter(OutfitDefinition::chestSlot),
            OutfitSlot.CODEC.optionalFieldOf("legs", null).forGetter(OutfitDefinition::legsSlot),
            OutfitSlot.CODEC.optionalFieldOf("feet", null).forGetter(OutfitDefinition::feetSlot)).apply(instance, OutfitDefinition::new));
    public static Map<ResourceLocation, OutfitDefinition> DATA_DRIVEN_OUTFITS = new HashMap<>();
    public static Map<Pair<EquipmentSlotType, ResourceLocation>, ResourceLocation> TEXTURE_CACHE = new HashMap<>();
    public static Map<Pair<EquipmentSlotType, ResourceLocation>, ResourceLocation> EMISSIVE_TEXTURE_CACHE = new HashMap<>();
    private final ResourceLocation assetID;
    @Nullable
    private final OutfitSlot headSlot;
    @Nullable
    private final OutfitSlot chestSlot;
    @Nullable
    private final OutfitSlot legsSlot;
    @Nullable
    private final OutfitSlot feetSlot;

    public OutfitDefinition(ResourceLocation assetID, @Nullable OutfitSlot headSlot, @Nullable OutfitSlot chestSlot, @Nullable OutfitSlot legsSlot, @Nullable OutfitSlot feetSlot) {
        this.assetID = assetID;
        this.headSlot = headSlot;
        this.chestSlot = chestSlot;
        this.legsSlot = legsSlot;
        this.feetSlot = feetSlot;
    }

    public ResourceLocation assetID() {
        return this.assetID;
    }

    @Nullable
    public OutfitSlot headSlot() {
        return this.headSlot;
    }

    @Nullable
    public OutfitSlot chestSlot() {
        return this.chestSlot;
    }

    @Nullable
    public OutfitSlot legsSlot() {
        return this.legsSlot;
    }

    @Nullable
    public OutfitSlot feetSlot() {
        return this.feetSlot;
    }

    @Nullable
    public static OutfitSlot byEquipmentSlot(OutfitDefinition definition, EquipmentSlotType slotType) {
        switch (slotType) {
            case HEAD: return definition.headSlot;
            case CHEST: return definition.chestSlot;
            case LEGS: return definition.legsSlot;
            case FEET: return definition.feetSlot;
            default: return null;
        }
    }

    public static ResourceLocation getOutfitTexture(EquipmentSlotType slotType, ResourceLocation materialName, boolean slimArms) {
        Pair<EquipmentSlotType, ResourceLocation> pair = new Pair<>(slotType, materialName);
        if (TEXTURE_CACHE.containsKey(pair)) return TEXTURE_CACHE.get(pair);
        if (materialName.toString().equals("minecraft:")) return null;

        if (DATA_DRIVEN_OUTFITS.containsKey(materialName)) {
            OutfitDefinition definition = DATA_DRIVEN_OUTFITS.get(materialName);
            ResourceLocation location;
            switch (slotType) {
                case HEAD: {
                    if (definition.headSlot != null) {
                        location = new ResourceLocation(definition.headSlot.texture().getNamespace(), "textures/" + definition.headSlot.texture().getPath() + ".png");
                        TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
                case CHEST: {
                    if (definition.chestSlot != null) {
                        location = new ResourceLocation(definition.chestSlot.texture().getNamespace(), "textures/" + definition.chestSlot.texture().getPath() + (slimArms ? "_slim" : "_classic") + ".png");
                        TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
                case LEGS: {
                    if (definition.legsSlot != null) {
                        location = new ResourceLocation(definition.legsSlot.texture().getNamespace(), "textures/" + definition.legsSlot.texture().getPath() + ".png");
                        TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
                case FEET: {
                    if (definition.feetSlot != null) {
                        location = new ResourceLocation(definition.feetSlot.texture().getNamespace(), "textures/" + definition.feetSlot.texture().getPath() + ".png");
                        TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public static ResourceLocation getEmissiveOutfitTexture(EquipmentSlotType slotType, ResourceLocation materialName, boolean slimArms) {
        Pair<EquipmentSlotType, ResourceLocation> pair = new Pair<>(slotType, materialName);
        if (EMISSIVE_TEXTURE_CACHE.containsKey(pair)) return EMISSIVE_TEXTURE_CACHE.get(pair);
        if (materialName.toString().equals("minecraft:")) return null;

        if (DATA_DRIVEN_OUTFITS.containsKey(materialName)) {
            OutfitDefinition definition = DATA_DRIVEN_OUTFITS.get(materialName);
            ResourceLocation location;
            switch (slotType) {
                case HEAD: {
                    if (definition.headSlot != null && definition.headSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.headSlot.emissiveTexture().getNamespace(), "textures/" + definition.headSlot.emissiveTexture().getPath() + ".png");
                        EMISSIVE_TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
                case CHEST: {
                    if (definition.chestSlot != null && definition.chestSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.chestSlot.emissiveTexture().getNamespace(), "textures/" + definition.chestSlot.emissiveTexture().getPath() + (slimArms ? "_slim" : "_classic") + ".png");
                        EMISSIVE_TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
                case LEGS: {
                    if (definition.legsSlot != null && definition.legsSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.legsSlot.emissiveTexture().getNamespace(), "textures/" + definition.legsSlot.emissiveTexture().getPath() + ".png");
                        EMISSIVE_TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
                case FEET: {
                    if (definition.feetSlot != null && definition.feetSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.feetSlot.emissiveTexture().getNamespace(), "textures/" + definition.feetSlot.emissiveTexture().getPath() + ".png");
                        EMISSIVE_TEXTURE_CACHE.put(pair, location);
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public JsonObject toJSON(OutfitDefinition definition) {
        JsonObject object = new JsonObject();
        object.addProperty("asset_id", definition.assetID.toString());
        if (definition.headSlot != null) object.add("head", OutfitSlot.toJSON(definition.headSlot));
        if (definition.chestSlot != null) object.add("chest", OutfitSlot.toJSON(definition.chestSlot));
        if (definition.legsSlot != null) object.add("legs", OutfitSlot.toJSON(definition.legsSlot));
        if (definition.feetSlot != null) object.add("feet", OutfitSlot.toJSON(definition.feetSlot));
        return object;
    }

    @Override
    public String toString() {
        return "OutfitDefinition[asset_id=" + this.assetID + ", head=" + this.headSlot + ", chest=" + this.chestSlot + ", legs=" + this.legsSlot + ", feet=" + this.feetSlot + "]";
    }

    public static class Serializer implements JsonDeserializer<OutfitDefinition>, JsonSerializer<OutfitDefinition> {
        @Override
        public OutfitDefinition deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                OutfitSlot headSlot = null;
                OutfitSlot chestSlot = null;
                OutfitSlot legsSlot = null;
                OutfitSlot feetSlot = null;
                if (object.has("head") && object.get("head").isJsonObject()) headSlot = OutfitSlot.fromJSON(object.get("head").getAsJsonObject());
                if (object.has("chest") && object.get("chest").isJsonObject()) chestSlot = OutfitSlot.fromJSON(object.get("chest").getAsJsonObject());
                if (object.has("legs") && object.get("legs").isJsonObject()) legsSlot = OutfitSlot.fromJSON(object.get("legs").getAsJsonObject());
                if (object.has("feet") && object.get("feet").isJsonObject()) feetSlot = OutfitSlot.fromJSON(object.get("feet").getAsJsonObject());

                ResourceLocation assetID = ResourceLocation.tryParse(JSONUtils.getAsString(object, "asset_id"));
                return new OutfitDefinition(assetID, headSlot, chestSlot, legsSlot, feetSlot);
            }
            return null;
        }

        @Override
        public JsonElement serialize(OutfitDefinition definition, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("asset_id", definition.assetID.toString());
            if (definition.headSlot != null) object.add("head", OutfitSlot.toJSON(definition.headSlot));
            if (definition.chestSlot != null) object.add("chest", OutfitSlot.toJSON(definition.chestSlot));
            if (definition.legsSlot != null) object.add("legs", OutfitSlot.toJSON(definition.legsSlot));
            if (definition.feetSlot != null) object.add("feet", OutfitSlot.toJSON(definition.feetSlot));
            return object;
        }
    }
}
