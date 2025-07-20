package com.sophicreeper.backmath.compat.jei;

import com.google.common.collect.Lists;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.crafting.custom.CrystallizingRecipe;
import com.sophicreeper.backmath.item.AxolotlTest;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class CrystallizingRecipeCategory extends AbstractCrystallizingRecipeCategory<CrystallizingRecipe> {
    public CrystallizingRecipeCategory(IGuiHelper helper) {
        super(new TranslationTextComponent("container.backmath.crystallizer.jei"), AxolotlTest.CRYSTALLIZER.get(), helper);
    }

    @Override
    @Nonnull
    public ResourceLocation getUid() {
        return BackMath.backMath("crystallizing");
    }

    @Override
    @Nonnull
    public Class<? extends CrystallizingRecipe> getRecipeClass() {
        return CrystallizingRecipe.class;
    }

    @Override
    public void setIngredients(CrystallizingRecipe recipe, IIngredients ingredients) {
        Ingredient offHand = recipe.offHand();
        if (offHand == null) {
            ingredients.setInputIngredients(Lists.newArrayList(recipe.mainHand(), Ingredient.EMPTY));
        } else {
            ingredients.setInputIngredients(Lists.newArrayList(recipe.mainHand(), recipe.offHand()));
        }
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout layout, CrystallizingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup stackGroup = layout.getItemStacks();
        stackGroup.init(0, true, 8, 8);
        stackGroup.init(1, true, 28, 8);
        stackGroup.init(2, false, 48, 8);
        stackGroup.set(ingredients);
    }
}
