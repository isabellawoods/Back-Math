package com.sophicreeper.backmath.data.recipe;

import com.google.gson.JsonObject;
import com.sophicreeper.backmath.crafting.BMRecipeSerializers;
import com.sophicreeper.backmath.crafting.custom.AbstractCrystallizingRecipe;
import com.sophicreeper.backmath.crafting.custom.CrystallizingRecipe;
import com.sophicreeper.backmath.crystallizer.Molds;
import com.sophicreeper.backmath.misc.BMSounds;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CrystallizingRecipeBuilder {
    private final ItemStack resultStack;
    private final Ingredient mainHand;
    @Nullable
    private final Ingredient offHand;
    private final Molds mold;
    @Nullable
    private final SoundEvent sound;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final CrystallizingRecipe.Serializer serializer;

    private CrystallizingRecipeBuilder(ItemStack resultStack, Ingredient mainHand, @Nullable Ingredient offHand, Molds mold, @Nullable SoundEvent sound, CrystallizingRecipe.Serializer serializer) {
        this.resultStack = resultStack;
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.mold = mold;
        this.sound = sound;
        this.serializer = serializer;
    }

    public static CrystallizingRecipeBuilder crystallizing(ItemStack resultStack, Ingredient mainHand, Ingredient offHand, Molds mold, @Nullable SoundEvent sound) {
        return new CrystallizingRecipeBuilder(resultStack, mainHand, offHand, mold, sound, BMRecipeSerializers.CRYSTALLIZING.get());
    }

    public static CrystallizingRecipeBuilder crystallizing(ItemStack resultStack, Ingredient mainHand, Ingredient offHand, Molds mold) {
        return crystallizing(resultStack, mainHand, offHand, mold, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
    }

    public static CrystallizingRecipeBuilder crystallizing(ItemStack resultStack, Ingredient mainHand, Molds mold, @Nullable SoundEvent sound) {
        return crystallizing(resultStack, mainHand, null, mold, sound);
    }

    public static CrystallizingRecipeBuilder crystallizing(ItemStack resultStack, Ingredient mainHand, Molds mold) {
        return crystallizing(resultStack, mainHand, null, mold, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
    }

    public CrystallizingRecipeBuilder unlockedBy(String name, ICriterionInstance instance) {
        this.advancement.addCriterion(name, instance);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> recipe) {
        ResourceLocation itemID = ForgeRegistries.ITEMS.getKey(this.resultStack.getItem());
        this.save(recipe, new ResourceLocation(itemID.getNamespace(), "crystallizing/" + itemID.getPath()));
    }

    public void save(Consumer<IFinishedRecipe> recipe, ResourceLocation saveLocation) {
        this.ensureValid(saveLocation);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(saveLocation)).rewards(AdvancementRewards.Builder.recipe(saveLocation))
                .requirements(IRequirementsStrategy.OR);
        recipe.accept(new Result(saveLocation, this.resultStack, this.mainHand, this.offHand, this.mold, this.sound, this.advancement,
                new ResourceLocation(saveLocation.getNamespace(), "recipes/" + this.resultStack.getItem().getItemCategory().getRecipeFolderName() + "/" + saveLocation.getPath()), this.serializer));
    }

    private void ensureValid(ResourceLocation saveLocation) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining crystallizing recipe " + saveLocation);
        }
    }

    private static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack resultStack;
        private final Ingredient mainHand;
        @Nullable
        private final Ingredient offHand;
        private final Molds mold;
        @Nullable
        private final SoundEvent sound;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementID;
        private final IRecipeSerializer<? extends AbstractCrystallizingRecipe> serializer;

        private Result(ResourceLocation id, ItemStack resultStack, Ingredient mainHand, @Nullable Ingredient offHand, Molds mold, @Nullable SoundEvent sound, Advancement.Builder advancement, ResourceLocation advancementID, IRecipeSerializer<? extends AbstractCrystallizingRecipe> serializer) {
            this.id = id;
            this.resultStack = resultStack;
            this.mainHand = mainHand;
            this.offHand = offHand;
            this.mold = mold;
            this.sound = sound;
            this.advancement = advancement;
            this.advancementID = advancementID;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject object) {
            object.addProperty("mold", this.mold.getSerializedName());
            object.add("main_hand", this.mainHand.toJson());
            if (this.offHand != null) object.add("off_hand", this.offHand.toJson());

            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("id", this.resultStack.getItem().getRegistryName().toString());
            if (this.resultStack.getCount() != 1) resultObject.addProperty("count", this.resultStack.getCount());
            if (this.resultStack.getTag() != null) resultObject.addProperty("tags", this.resultStack.getTag().toString());
            object.add("result", resultObject);

            if (this.sound != null) object.addProperty("sound", this.sound.getRegistryName().toString());
        }

        @Override
        @Nonnull
        public IRecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Override
        @Nonnull
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementID;
        }
    }
}
