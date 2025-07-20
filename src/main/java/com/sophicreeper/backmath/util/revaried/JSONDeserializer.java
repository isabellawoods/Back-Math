package com.sophicreeper.backmath.util.revaried;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sophicreeper.backmath.BackMath;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

public class JSONDeserializer {
    public static ItemStack loadStack(String objectName, JsonObject object) throws JsonSyntaxException {
        ResourceLocation identifier;
        int count = 1;
        CompoundNBT tags = new CompoundNBT();

        JsonObject stackObject = object.get(objectName).getAsJsonObject();
        if (stackObject == null) throw new JsonSyntaxException(new TranslationTextComponent("item_parser.variants.missing_parent_object", objectName).getString());

        // "id" tag
        if (stackObject.has("id")) {
            JsonElement idElement = stackObject.get("id");
            if (idElement.isJsonPrimitive()) {
                ResourceLocation id = ResourceLocation.tryParse(idElement.getAsString());
                if (id != null && ForgeRegistries.ITEMS.containsKey(id)) {
                    identifier = id;
                } else {
                    throw new JsonSyntaxException(new TranslationTextComponent("item_parser.variants.invalid_item", id).getString());
                }
            } else {
                throw new JsonSyntaxException(new TranslationTextComponent("item_parser.variants.invalid_id", getTranslatedType(idElement)).getString());
            }
        } else {
            throw new JsonSyntaxException(new TranslationTextComponent("item_parser.variants.missing_id", objectName).getString());
        }

        // "count" tag
        if (stackObject.has("count")) {
            JsonElement countElement = stackObject.get("count");
            if (countElement.isJsonPrimitive() && countElement.getAsJsonPrimitive().isNumber()) {
                int subCount = countElement.getAsInt();
                if (subCount >= 0) {
                    count = subCount;
                } else {
                    BackMath.LOGGER.warn(new TranslationTextComponent("item_parser.variants.negative_count", subCount));
                }
            } else {
                throw new JsonSyntaxException(new TranslationTextComponent("item_parser.variants.invalid_count", getTranslatedType(countElement)).getString());
            }
        }

        // "tags" tag
        if (stackObject.has("tags")) {
            JsonElement tagsElement = stackObject.get("tags");
            if (tagsElement.isJsonObject() || tagsElement.isJsonPrimitive()) {
                try {
                    tags = JsonToNBT.parseTag(tagsElement.toString());
                } catch (CommandSyntaxException exception) {
                    throw new JsonSyntaxException(new TranslationTextComponent("item_parser.variants.tag_parse_failed", tagsElement).getString());
                }
            } else {
                throw new JsonSyntaxException(new TranslationTextComponent("item_parser.variants.invalid_tags", getTranslatedType(tagsElement)).getString());
            }
        }

        CompoundNBT tag = new CompoundNBT();
        tag.putString("id", identifier.toString());
        tag.putInt("count", count);
        if (!tags.isEmpty()) tag.put("tags", tags);

        return RVUtils.loadStack(tag);
    }

    public static TranslationTextComponent getTranslatedType(JsonElement element) {
        String abbreviation = StringUtils.abbreviateMiddle(String.valueOf(element), new TranslationTextComponent("exception.variants.ellipsis").getString(), 10);
        String template = "exception.variants.json_primitive.";

        if (element == null) {
            return new TranslationTextComponent(template + "null");
        } else if (element.isJsonNull()) {
            return new TranslationTextComponent(template + "json_null");
        } else if (element.isJsonArray()) {
            return new TranslationTextComponent(template + "array", abbreviation);
        } else if (element.isJsonObject()) {
            return new TranslationTextComponent(template + "object", abbreviation);
        } else {
            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isNumber()) return new TranslationTextComponent(template + "number", abbreviation);
                if (primitive.isBoolean()) return new TranslationTextComponent(template + "boolean", abbreviation);
            }

            return new TranslationTextComponent(template + "entire_object", element);
        }
    }
}
