package com.sophicreeper.backmath.crafting.custom;

import com.google.gson.JsonObject;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.crafting.BMRecipeSerializers;
import com.sophicreeper.backmath.crystallizer.MoldUtils;
import com.sophicreeper.backmath.crystallizer.Molds;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.revaried.JSONDeserializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrystallizingRecipe extends AbstractCrystallizingRecipe {
    private final Molds mold;

    public CrystallizingRecipe(IRecipeType<?> type, ResourceLocation id, Molds mold, Ingredient mainHand, Ingredient offHand, ItemStack resultStack, SoundEvent sound) {
        super(type, id, mainHand, offHand, resultStack, sound);
        this.mold = mold;
    }

    @Override
    @Nonnull
    public ItemStack getToastSymbol() {
        return new ItemStack(AxolotlTest.CRYSTALLIZER.get());
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return BMRecipeSerializers.CRYSTALLIZING.get();
    }

    public static class CrystallizingRecipeType implements IRecipeType<CrystallizingRecipe> {
        @Override
        public String toString() {
            return BackMath.backMath("crystallizing").toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrystallizingRecipe> {
        @Override
        @Nonnull
        public CrystallizingRecipe fromJson(ResourceLocation recipeID, JsonObject object) {
            Molds mold = MoldUtils.getMoldFromString(JSONUtils.getAsString(object, "mold"));
            Ingredient mainHand;
            Ingredient offHand = null;

            if (JSONUtils.isArrayNode(object, "main_hand")) mainHand = Ingredient.fromJson(JSONUtils.getAsJsonArray(object, "main_hand"));
            else mainHand = Ingredient.fromJson(JSONUtils.getAsJsonObject(object, "main_hand"));

            if (object.has("off_hand")) {
                if (JSONUtils.isArrayNode(object, "off_hand")) offHand = Ingredient.fromJson(JSONUtils.getAsJsonArray(object, "off_hand"));
                else offHand = Ingredient.fromJson(JSONUtils.getAsJsonObject(object, "off_hand"));
            }
            ItemStack resultStack = JSONDeserializer.loadStack("result", object);
            String soundString = JSONUtils.getAsString(object, "sound", "backmath:block.crystallizer.craft");
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundString));

            return new CrystallizingRecipe(BMRecipeSerializers.CRYSTALLIZING_RECIPE, recipeID, mold, mainHand, offHand, resultStack, sound);
        }

        @Override
        @Nullable
        public CrystallizingRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
            Molds mold = MoldUtils.getMoldFromString(buffer.readUtf());
            Ingredient mainHand = Ingredient.fromNetwork(buffer);
            Ingredient offHand = Ingredient.fromNetwork(buffer);
            ItemStack resultStack = buffer.readItem();
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(buffer.readUtf()));
            if (sound == null) sound = BMSounds.BLOCK_CRYSTALLIZER_CRAFT;
            return new CrystallizingRecipe(BMRecipeSerializers.CRYSTALLIZING_RECIPE, recipeID, mold, mainHand, offHand, resultStack, sound);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, CrystallizingRecipe recipe) {
            buffer.writeUtf(recipe.mold.getSerializedName());
            recipe.mainHand.toNetwork(buffer);
            if (!recipe.offHand.isEmpty()) recipe.offHand.toNetwork(buffer);
            buffer.writeItem(recipe.resultStack);
            buffer.writeResourceLocation(recipe.sound.getRegistryName());
        }
    }
}
