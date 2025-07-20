package com.sophicreeper.backmath.data.recipe;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.crystallizer.Molds;
import com.sophicreeper.backmath.crystallizer.advanced.AdvancedMolds;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class BMRecipeProvider extends RecipeProvider {
    public BMRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        this.addCrystallizingRecipes(consumer);
        this.addCrystallineCrystallizingRecipes(consumer);
    }

    private void addCrystallizingRecipes(Consumer<IFinishedRecipe> consumer) {
        // Empty mold
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.LIQUID_ALJAME_BUCKET.get()), Ingredient.of(new ItemStack(AxolotlTest.ALJAME.get(), 4)), Ingredient.of(Items.BUCKET), Molds.EMPTY, SoundEvents.BUCKET_FILL)
                .unlockedBy("has_aljame", has(AxolotlTest.ALJAME.get())).save(consumer);

        // Rod mold
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.HILLARY_ROD.get(), 4), Ingredient.of(AxolotlTest.HILLARY_BUCKET.get()), null, Molds.ROD)
                .unlockedBy("has_hillary_bucket", has(AxolotlTest.HILLARY_BUCKET.get())).save(consumer);

        // Singularity mold
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARITY.get(), 2), Ingredient.of(new ItemStack(AxolotlTest.MILKLLARY_BUCKET.get())), null, Molds.SINGULARITY)
                .unlockedBy("has_milkllary_bucket", has(AxolotlTest.MILKLLARY_BUCKET.get())).save(consumer, BackMath.backMath("crystallizing/milkllarity_from_bucket"));
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MID_TERM.get()), Ingredient.of(BMItemTags.INGOTS_MID_TERM), Molds.SINGULARITY)
                .unlockedBy("has_mid_term_ingot", has(BMItemTags.INGOTS_MID_TERM)).save(consumer);
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM.get()), Ingredient.of(BMItemTags.INGOTS_OBSIDIAN_INFUSED_MID_TERM), Molds.SINGULARITY)
                .unlockedBy("has_obsidian_infused_mid_term_ingot", has(BMItemTags.INGOTS_OBSIDIAN_INFUSED_MID_TERM)).save(consumer);
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARITY.get()), Ingredient.of(BMItemTags.INGOTS_MILKLLARY), Molds.SINGULARITY)
                .unlockedBy("has_milkllary_ingot", has(BMItemTags.INGOTS_MILKLLARY)).save(consumer, BackMath.backMath("crystallizing/milkllarity_from_ingot"));

        // Ingot mold
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MID_TERM_INGOT.get()), Ingredient.of(BMItemTags.SINGULARITIES_MID_TERM), Molds.INGOT)
                .unlockedBy("has_mid_term", has(BMItemTags.SINGULARITIES_MID_TERM)).save(consumer);
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM_INGOT.get()), Ingredient.of(BMItemTags.SINGULARITIES_OBSIDIAN_INFUSED_MID_TERM), Molds.INGOT)
                .unlockedBy("has_obsidian_infused_mid_term", has(BMItemTags.SINGULARITIES_OBSIDIAN_INFUSED_MID_TERM)).save(consumer);
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get(), 2), Ingredient.of(AxolotlTest.MILKLLARY_BUCKET.get()), Molds.INGOT)
                .unlockedBy("has_milkllary_bucket", has(AxolotlTest.MILKLLARY_BUCKET.get())).save(consumer, BackMath.backMath("crystallizing/milkllary_ingot_from_bucket"));
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get()), Ingredient.of(BMItemTags.SINGULARITIES_MILKLLARY), Molds.INGOT)
                .unlockedBy("has_milkllarity", has(BMItemTags.SINGULARITIES_MILKLLARY)).save(consumer, BackMath.backMath("crystallizing/milkllary_ingot_from_milkllarity"));
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.TWO_THIRDS_HILLARY_INGOT.get()), Ingredient.of(BMItemTags.INGOTS_MILKLLARY), Ingredient.of(AxolotlTest.HILLARY_BUCKET.get()), Molds.INGOT)
                .unlockedBy("has_milkllary", has(BMItemTags.INGOTS_MILKLLARY)).save(consumer);
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.HARDENED_AMARACAMEL_INGOT.get()), Ingredient.of(AxolotlTest.HARDENED_AMARACAMEL_BATTER.get()), Molds.INGOT)
                .unlockedBy("has_hardened_amaracamel_batter", has(AxolotlTest.HARDENED_AMARACAMEL_BATTER.get())).save(consumer);

        // Mold mold
        CrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.CRYSTALLINE_EMPTY_MOLD.get()), Ingredient.of(new ItemStack(AxolotlTest.CRYSTALLINE_ANGELIC.get(), 2)), Molds.MOLD)
                .unlockedBy("has_crystalline_angelic", has(BMItemTags.GEMS_CRYSTALLINE_ANGELIC)).save(consumer);
    }

    private void addCrystallineCrystallizingRecipes(Consumer<IFinishedRecipe> consumer) {
        // Empty mold
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.LIQUID_ALJAME_BUCKET.get()), Ingredient.of(new ItemStack(AxolotlTest.ALJAME.get(), 4)), Ingredient.of(Items.BUCKET), AdvancedMolds.EMPTY, SoundEvents.BUCKET_FILL)
                .unlockedBy("has_aljame", has(AxolotlTest.ALJAME.get())).save(consumer);

        // Rod mold
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.HILLARY_ROD.get(), 4), Ingredient.of(AxolotlTest.HILLARY_BUCKET.get()), null, AdvancedMolds.ROD)
                .unlockedBy("has_hillary_bucket", has(AxolotlTest.HILLARY_BUCKET.get())).save(consumer);

        // Singularity mold
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARITY.get(), 2), Ingredient.of(new ItemStack(AxolotlTest.MILKLLARY_BUCKET.get())), null, AdvancedMolds.SINGULARITY)
                .unlockedBy("has_milkllary_bucket", has(AxolotlTest.MILKLLARY_BUCKET.get())).save(consumer, BackMath.backMath("crystalline_crystallizing/milkllarity_from_bucket"));
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MID_TERM.get()), Ingredient.of(BMItemTags.INGOTS_MID_TERM), AdvancedMolds.SINGULARITY)
                .unlockedBy("has_mid_term_ingot", has(BMItemTags.INGOTS_MID_TERM)).save(consumer, BackMath.backMath("crystalline_crystallizing/mid_term_from_ingot"));
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM.get()), Ingredient.of(BMItemTags.INGOTS_OBSIDIAN_INFUSED_MID_TERM), AdvancedMolds.SINGULARITY)
                .unlockedBy("has_obsidian_infused_mid_term_ingot", has(BMItemTags.INGOTS_OBSIDIAN_INFUSED_MID_TERM)).save(consumer);
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARITY.get()), Ingredient.of(BMItemTags.INGOTS_MILKLLARY), AdvancedMolds.SINGULARITY)
                .unlockedBy("has_milkllary_ingot", has(BMItemTags.INGOTS_MILKLLARY)).save(consumer, BackMath.backMath("crystalline_crystallizing/milkllarity_from_ingot"));
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MID_TERM.get()), Ingredient.of(AxolotlTest.MOLTEN_MID_TERM_BUCKET.get()), AdvancedMolds.SINGULARITY)
                .unlockedBy("has_molten_mid_term_bucket", has(AxolotlTest.MOLTEN_MID_TERM_BUCKET.get())).save(consumer, BackMath.backMath("crystalline_crystallizing/mid_term_from_bucket"));

        // Ingot mold
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MID_TERM_INGOT.get()), Ingredient.of(BMItemTags.SINGULARITIES_MID_TERM), AdvancedMolds.INGOT)
                .unlockedBy("has_mid_term", has(BMItemTags.SINGULARITIES_MID_TERM)).save(consumer, BackMath.backMath("crystalline_crystallizing/mid_term_ingot_from_singularity"));
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM_INGOT.get()), Ingredient.of(BMItemTags.SINGULARITIES_OBSIDIAN_INFUSED_MID_TERM), AdvancedMolds.INGOT)
                .unlockedBy("has_obsidian_infused_mid_term", has(BMItemTags.SINGULARITIES_OBSIDIAN_INFUSED_MID_TERM)).save(consumer);
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get(), 2), Ingredient.of(AxolotlTest.MILKLLARY_BUCKET.get()), AdvancedMolds.INGOT)
                .unlockedBy("has_milkllary_bucket", has(AxolotlTest.MILKLLARY_BUCKET.get())).save(consumer, BackMath.backMath("crystalline_crystallizing/milkllary_ingot_from_bucket"));
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get()), Ingredient.of(BMItemTags.SINGULARITIES_MILKLLARY), AdvancedMolds.INGOT)
                .unlockedBy("has_milkllarity", has(BMItemTags.SINGULARITIES_MILKLLARY)).save(consumer, BackMath.backMath("crystalline_crystallizing/milkllary_ingot_from_milkllarity"));
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.TWO_THIRDS_HILLARY_INGOT.get()), Ingredient.of(BMItemTags.INGOTS_MILKLLARY), Ingredient.of(AxolotlTest.HILLARY_BUCKET.get()), AdvancedMolds.INGOT)
                .unlockedBy("has_milkllary", has(BMItemTags.INGOTS_MILKLLARY)).save(consumer);
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.HARDENED_AMARACAMEL_INGOT.get()), Ingredient.of(AxolotlTest.HARDENED_AMARACAMEL_BATTER.get()), AdvancedMolds.INGOT)
                .unlockedBy("has_hardened_amaracamel_batter", has(AxolotlTest.HARDENED_AMARACAMEL_BATTER.get())).save(consumer);
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.OBSIDIAN_INGOT.get()), Ingredient.of(Tags.Items.OBSIDIAN), AdvancedMolds.INGOT)
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN)).save(consumer);
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.MID_TERM_INGOT.get()), Ingredient.of(AxolotlTest.MOLTEN_MID_TERM_BUCKET.get()), AdvancedMolds.INGOT)
                .unlockedBy("has_molten_mid_term_bucket", has(AxolotlTest.MOLTEN_MID_TERM_BUCKET.get())).save(consumer, BackMath.backMath("crystalline_crystallizing/mid_term_ingot_from_bucket"));

        // Mold mold
        CrystallineCrystallizingRecipeBuilder.crystallizing(new ItemStack(AxolotlTest.CRYSTALLINE_EMPTY_MOLD.get()), Ingredient.of(new ItemStack(AxolotlTest.CRYSTALLINE_ANGELIC.get(), 2)), AdvancedMolds.MOLD)
                .unlockedBy("has_crystalline_angelic", has(BMItemTags.GEMS_CRYSTALLINE_ANGELIC)).save(consumer);
    }
}
