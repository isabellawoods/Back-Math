package com.sophicreeper.backmath.compat.jei;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BMAnvilRecipeMaker {
    public static final Logger LOGGER = LogManager.getLogger("backmath/BMAnvilRecipeMaker");

    public static List<Object> getAnvilRecipes(IVanillaRecipeFactory recipeFactory) {
        List<Object> recipes = new ArrayList<>();
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            getBMRepairRecipes(recipes, recipeFactory);
        } catch (RuntimeException exception) {
            LOGGER.error(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.anvil_repair_recipe_maker.failed")).getString(), exception);
        }
        stopwatch.stop();
        LOGGER.debug(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.anvil_repair_recipe_maker.successful", stopwatch)).getString());
        return recipes;
    }

    private static void getBMRepairRecipes(List<Object> recipes, IVanillaRecipeFactory recipeFactory) {
        Map<Ingredient, List<ItemStack>> items = Maps.newHashMap();

        items.put(Ingredient.of(BMItemTags.INGOTS_DEVIL), Lists.newArrayList(new ItemStack(AxolotlTest.MILKED_DEVIL_SWORD.get()), new ItemStack(AxolotlTest.DEVIL_SWORD.get()), new ItemStack(AxolotlTest.DEVIL_PICKAXE.get()),
                new ItemStack(AxolotlTest.DEVIL_PICKAXE.get()), new ItemStack(AxolotlTest.DEVIL_SHOVEL.get()), new ItemStack(AxolotlTest.DEVIL_AXE.get()), new ItemStack(AxolotlTest.DEVIL_HOE.get()), new ItemStack(AxolotlTest.DEVIL_WARRIOR_HELMET.get()),
                new ItemStack(AxolotlTest.DEVIL_HELMET.get()), new ItemStack(AxolotlTest.DEVIL_CHESTPLATE.get()), new ItemStack(AxolotlTest.DEVIL_LEGGINGS.get()), new ItemStack(AxolotlTest.DEVIL_BOOTS.get()), new ItemStack(AxolotlTest.MILKED_DEVIL_SPAREY.get()),
                new ItemStack(AxolotlTest.DEVIL_SPAREY.get()), new ItemStack(AxolotlTest.DEVIL_KNIFE.get())));

        items.put(Ingredient.of(BMItemTags.INGOTS_ANGELIC), Lists.newArrayList(new ItemStack(AxolotlTest.MILKED_ANGELIC_SWORD.get()), new ItemStack(AxolotlTest.ANGELIC_SWORD.get()), new ItemStack(AxolotlTest.ANGELIC_PICKAXE.get()),
                new ItemStack(AxolotlTest.ANGELIC_PICKAXE.get()), new ItemStack(AxolotlTest.ANGELIC_SHOVEL.get()), new ItemStack(AxolotlTest.ANGELIC_AXE.get()), new ItemStack(AxolotlTest.ANGELIC_HOE.get()), new ItemStack(AxolotlTest.ANGELIC_WARRIOR_HELMET.get()),
                new ItemStack(AxolotlTest.ANGELIC_HELMET.get()), new ItemStack(AxolotlTest.ANGELIC_CHESTPLATE.get()), new ItemStack(AxolotlTest.ANGELIC_LEGGINGS.get()), new ItemStack(AxolotlTest.ANGELIC_BOOTS.get()), new ItemStack(AxolotlTest.ANGELIC_KNIFE.get()),
                new ItemStack(AxolotlTest.ANGELIC_SPOON.get())));

        for (Map.Entry<Ingredient, List<ItemStack>> entry : items.entrySet()) {
            List<ItemStack> repairMaterials = Lists.newArrayList(entry.getKey().getItems());

            for (ItemStack repairIngredients : entry.getValue()) {
                ItemStack damagedStack1 = repairIngredients.copy();
                damagedStack1.setDamageValue(damagedStack1.getMaxDamage());
                ItemStack damagedStack2 = repairIngredients.copy();
                damagedStack2.setDamageValue(damagedStack2.getMaxDamage() * 3 / 4);
                ItemStack damagedStack3 = repairIngredients.copy();
                damagedStack3.setDamageValue(damagedStack3.getMaxDamage() * 2 / 4);

                if (!repairMaterials.isEmpty()) {
                    Object repairWithMaterial = recipeFactory.createAnvilRecipe(damagedStack1, repairMaterials, Collections.singletonList(damagedStack2));
                    recipes.add(repairWithMaterial);
                }
                Object repairWithSame = recipeFactory.createAnvilRecipe(damagedStack2, Collections.singletonList(damagedStack2), Collections.singletonList(damagedStack3));
                recipes.add(repairWithSame);
            }
        }
    }
}
