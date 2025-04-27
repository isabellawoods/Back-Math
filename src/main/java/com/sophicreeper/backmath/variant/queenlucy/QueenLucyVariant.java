package com.sophicreeper.backmath.variant.queenlucy;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class QueenLucyVariant extends ForgeRegistryEntry<QueenLucyVariant> {
    public static final Codec<QueenLucyVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(QueenLucyVariant::getAssetID),
            ResourceLocation.CODEC.fieldOf("texture_location").forGetter(QueenLucyVariant::getTextureLocation),
            ResourceLocation.CODEC.optionalFieldOf("emissive_texture", null).forGetter(QueenLucyVariant::getEmissiveTexture))
            .apply(instance, QueenLucyVariant::new));
    public static Map<ResourceLocation, QueenLucyVariant> DATA_DRIVEN_VARIANTS = new HashMap<>();
    private final ResourceLocation assetID;
    private final ResourceLocation textureLocation;
    @Nullable
    private final ResourceLocation emissiveTexture;
    @Nullable
    private final ServerBossInfo bossbarInformation;
    @Nullable
    private String descriptionID;

    public QueenLucyVariant(ResourceLocation assetID, ResourceLocation textureLocation, @Nullable ResourceLocation emissiveTexture, @Nullable ServerBossInfo bossbarInformation) {
        this.assetID = assetID;
        this.textureLocation = textureLocation;
        this.emissiveTexture = emissiveTexture;
        this.bossbarInformation = bossbarInformation;
    }

    public QueenLucyVariant(ResourceLocation assetID, ResourceLocation textureLocation, @Nullable ResourceLocation emissiveTexture) {
        this(assetID, textureLocation, emissiveTexture, null);
    }

    public QueenLucyVariant(ResourceLocation assetID, ResourceLocation textureLocation) {
        this(assetID, textureLocation, null, null);
    }

    public ResourceLocation getAssetID() {
        return this.assetID;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    @Nullable
    public ResourceLocation getEmissiveTexture() {
        return this.emissiveTexture;
    }

    @Nullable
    public ServerBossInfo getBossbarInformation() {
        return this.bossbarInformation;
    }

    protected String getOrMakeTranslation() {
        if (this.descriptionID == null) this.descriptionID = Util.makeDescriptionId("queen_lucy_variant", this.getAssetID());
        return this.descriptionID;
    }

    public String getTranslation() {
        return this.getOrMakeTranslation();
    }

    @Nullable
    public static QueenLucyVariant getVariantFromStack(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains("variant", TagTypes.STRING)) {
            ResourceLocation location = ResourceLocation.tryParse(stack.getTag().getString("variant"));
            return QueenLucyVariant.DATA_DRIVEN_VARIANTS.get(location);
        }
        return null;
    }

    public JsonObject toJSON(QueenLucyVariant variant) {
        JsonObject object = new JsonObject();
        JsonObject bossbarObject = new JsonObject();

        if (variant.bossbarInformation != null) {
            ServerBossInfo bar = variant.bossbarInformation;
            if (!bar.isVisible()) bossbarObject.addProperty("visible", false);
            if (bar.getColor() != BossInfo.Color.BLUE) bossbarObject.addProperty("color", bar.getColor().getName());
            if (bar.getOverlay() != BossInfo.Overlay.NOTCHED_6) bossbarObject.addProperty("overlay", bar.getOverlay().getName());
            if (bar.shouldDarkenScreen()) bossbarObject.addProperty("darkens_screen", true);
            if (bar.shouldPlayBossMusic()) bossbarObject.addProperty("plays_boss_music", true);
            if (bar.shouldCreateWorldFog()) bossbarObject.addProperty("creates_fog", true);
            bossbarObject.add("name", ITextComponent.Serializer.toJsonTree(bar.getName()));
        }

        object.addProperty("asset_id", variant.assetID.toString());
        object.addProperty("texture_location", variant.textureLocation.toString());
        if (variant.emissiveTexture != null) object.addProperty("emissive_texture", variant.emissiveTexture.toString());
        if (variant.bossbarInformation != null) object.add("bossbar_information", bossbarObject);
        return object;
    }

    public static class Serializer implements JsonSerializer<QueenLucyVariant>, JsonDeserializer<QueenLucyVariant> {
        @Override
        public QueenLucyVariant deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = element.getAsJsonObject();
            ResourceLocation assetID = ResourceLocation.tryParse(JSONUtils.getAsString(object, "asset_id"));
            ResourceLocation textureLocation = ResourceLocation.tryParse(JSONUtils.getAsString(object, "texture_location"));
            ResourceLocation emissiveTexture = object.has("emissive_texture") && object.get("emissive_texture").isJsonPrimitive() ? ResourceLocation.tryParse(object.get("emissive_texture").getAsString()) : null;
            ServerBossInfo bossInfo = null;

            if (object.has("bossbar_information") && object.get("bossbar_information").isJsonObject()) {
                JsonObject barObject = object.get("bossbar_information").getAsJsonObject();
                boolean visible = optionalBoolean(barObject, "visible", true);
                BossInfo.Color color = BossInfo.Color.byName(optionalString(barObject, "color", "blue"));
                BossInfo.Overlay overlay = BossInfo.Overlay.byName(optionalString(barObject, "color", "notched_6"));
                boolean darkensScreen = optionalBoolean(barObject, "darkens_screen", false);
                boolean playsBossMusic = optionalBoolean(barObject, "plays_boss_music", false);
                boolean createsFog = optionalBoolean(barObject, "creates_fog", false);
                ITextComponent name = ITextComponent.Serializer.fromJson(JSONUtils.getAsJsonObject(barObject, "name"));

                if (name != null) {
                    bossInfo = new ServerBossInfo(name, color, overlay);
                    bossInfo.setDarkenScreen(darkensScreen);
                    bossInfo.setPlayBossMusic(playsBossMusic);
                    bossInfo.setCreateWorldFog(createsFog);
                    bossInfo.setVisible(visible);
                }
            }
            return new QueenLucyVariant(assetID, textureLocation, emissiveTexture, bossInfo);
        }

        @Override
        public JsonElement serialize(QueenLucyVariant variant, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            JsonObject bossbarObject = new JsonObject();

            if (variant.bossbarInformation != null) {
                ServerBossInfo bar = variant.bossbarInformation;
                if (!bar.isVisible()) bossbarObject.addProperty("visible", false);
                if (bar.getColor() != BossInfo.Color.BLUE) bossbarObject.addProperty("color", bar.getColor().getName());
                if (bar.getOverlay() != BossInfo.Overlay.NOTCHED_6) bossbarObject.addProperty("overlay", bar.getOverlay().getName());
                if (bar.shouldDarkenScreen()) bossbarObject.addProperty("darkens_screen", true);
                if (bar.shouldPlayBossMusic()) bossbarObject.addProperty("plays_boss_music", true);
                if (bar.shouldCreateWorldFog()) bossbarObject.addProperty("creates_fog", true);
                bossbarObject.add("name", ITextComponent.Serializer.toJsonTree(bar.getName()));
            }

            object.addProperty("asset_id", variant.assetID.toString());
            object.addProperty("texture_location", variant.textureLocation.toString());
            if (variant.emissiveTexture != null) object.addProperty("emissive_texture", variant.emissiveTexture.toString());
            if (variant.bossbarInformation != null) object.add("bossbar_information", bossbarObject);
            return object;
        }

        private boolean optionalBoolean(JsonObject object, String name, boolean fallback) {
            if (object.has(name) && object.get(name).isJsonPrimitive()) return object.get(name).getAsBoolean();
            return fallback;
        }

        private String optionalString(JsonObject object, String name, String fallback) {
            if (object.has(name) && object.get(name).isJsonPrimitive()) return object.get(name).getAsString();
            return fallback;
        }
    }
}
