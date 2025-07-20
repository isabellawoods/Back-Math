package com.sophicreeper.backmath.entity.outfit;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

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

    /// Gets an outfit slot from a definition based on an equipment slot.
    /// @param definition The outfit definition to get the slots from.
    /// @param slotType An equipment slot to get the slot.
    /// @throws IllegalArgumentException If the equipment slot is a non-armor slot (one of {@link EquipmentSlotType#HEAD HEAD}, {@link EquipmentSlotType#CHEST CHEST}, {@link EquipmentSlotType#LEGS LEGS} or {@link EquipmentSlotType#FEET FEET}).
    @Nullable
    public static OutfitSlot byEquipmentSlot(OutfitDefinition definition, EquipmentSlotType slotType) {
        switch (slotType) {
            case HEAD: return definition.headSlot;
            case CHEST: return definition.chestSlot;
            case LEGS: return definition.legsSlot;
            case FEET: return definition.feetSlot;
            default: throw new IllegalArgumentException(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent(
                    "error.backmath.outfit_definition.wrong_equipment_slot", slotType.getName())).getString());
        }
    }

    /// Whether to hide the skin layers for the player or entity when wearing this outfit on this slot.
    /// @param slotType Which slot the outfit is being rendered in.
    /// @param definition The outfit definition. Used to get the "<code>hides_skin_layers</code>" boolean field on the slot.
    /// @param slimArms Whether the entity has slim arms, used to find the correct texture.
    public static boolean shouldHideLayer(EquipmentSlotType slotType, OutfitDefinition definition, boolean slimArms) {
        ResourceLocation outfitLocation = getOutfitTexture(slotType, definition.assetID(), slimArms);
        OutfitSlot slot = byEquipmentSlot(definition, slotType);
        return outfitLocation != null && slot != null && slot.hidesSkinLayers();
    }

    /// Gets the default outfit texture for an entity.
    /// @param slotType The equipment slot of the outfit, used to get the texture.
    /// @param materialName A resource location of the outfit definition.
    /// @param slimArms Whether the entity has slim arms, used to get the texture.
    /// @return A resource location of the outfit texture to be rendered, or null if <code>materialName</code> is null or set to "<code>minecraft:</code>".
    public static ResourceLocation getOutfitTexture(EquipmentSlotType slotType, @Nullable ResourceLocation materialName, boolean slimArms) {
        if (materialName == null || materialName.toString().equals("minecraft:")) return null;

        if (DATA_DRIVEN_OUTFITS.containsKey(materialName)) {
            OutfitDefinition definition = DATA_DRIVEN_OUTFITS.get(materialName);
            ResourceLocation location;
            switch (slotType) {
                case HEAD: {
                    if (definition.headSlot != null) {
                        location = new ResourceLocation(definition.headSlot.texture().getNamespace(), "textures/" + definition.headSlot.texture().getPath() + ".png");
                        return location;
                    }
                    break;
                }
                case CHEST: {
                    if (definition.chestSlot != null) {
                        location = new ResourceLocation(definition.chestSlot.texture().getNamespace(), "textures/" + definition.chestSlot.texture().getPath() + (slimArms ? "_slim" : "_classic") + ".png");
                        return location;
                    }
                    break;
                }
                case LEGS: {
                    if (definition.legsSlot != null) {
                        location = new ResourceLocation(definition.legsSlot.texture().getNamespace(), "textures/" + definition.legsSlot.texture().getPath() + ".png");
                        return location;
                    }
                    break;
                }
                case FEET: {
                    if (definition.feetSlot != null) {
                        location = new ResourceLocation(definition.feetSlot.texture().getNamespace(), "textures/" + definition.feetSlot.texture().getPath() + ".png");
                        return location;
                    }
                    break;
                }
            }
        }
        return null;
    }

    /// Gets the emissive outfit texture for an entity.
    /// @param slotType The equipment slot of the outfit, used to get the texture.
    /// @param materialName A resource location of the outfit definition.
    /// @param slimArms Whether the entity has slim arms, used to get the texture.
    /// @return A resource location of the emissive outfit texture to be rendered, or null if <code>materialName</code> is null or set to "<code>minecraft:</code>".
    public static ResourceLocation getEmissiveOutfitTexture(EquipmentSlotType slotType, @Nullable ResourceLocation materialName, boolean slimArms) {
        if (materialName == null || materialName.toString().equals("minecraft:")) return null;

        if (DATA_DRIVEN_OUTFITS.containsKey(materialName)) {
            OutfitDefinition definition = DATA_DRIVEN_OUTFITS.get(materialName);
            ResourceLocation location;
            switch (slotType) {
                case HEAD: {
                    if (definition.headSlot != null && definition.headSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.headSlot.emissiveTexture().getNamespace(), "textures/" + definition.headSlot.emissiveTexture().getPath() + ".png");
                        return location;
                    }
                    break;
                }
                case CHEST: {
                    if (definition.chestSlot != null && definition.chestSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.chestSlot.emissiveTexture().getNamespace(), "textures/" + definition.chestSlot.emissiveTexture().getPath() + (slimArms ? "_slim" : "_classic") + ".png");
                        return location;
                    }
                    break;
                }
                case LEGS: {
                    if (definition.legsSlot != null && definition.legsSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.legsSlot.emissiveTexture().getNamespace(), "textures/" + definition.legsSlot.emissiveTexture().getPath() + ".png");
                        return location;
                    }
                    break;
                }
                case FEET: {
                    if (definition.feetSlot != null && definition.feetSlot.emissiveTexture() != null) {
                        location = new ResourceLocation(definition.feetSlot.emissiveTexture().getNamespace(), "textures/" + definition.feetSlot.emissiveTexture().getPath() + ".png");
                        return location;
                    }
                    break;
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
