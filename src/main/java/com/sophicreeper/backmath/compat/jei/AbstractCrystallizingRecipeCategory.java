package com.sophicreeper.backmath.compat.jei;

import com.sophicreeper.backmath.crafting.custom.AbstractCrystallizingRecipe;
import com.sophicreeper.backmath.util.BMResourceLocations;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public abstract class AbstractCrystallizingRecipeCategory<T extends AbstractCrystallizingRecipe> implements IRecipeCategory<T> {
    private final IDrawable background;
    private final IDrawable tabIcon;
    private final ITextComponent tabName;

    public AbstractCrystallizingRecipeCategory(ITextComponent tabName, Item item, IGuiHelper helper) {
        this.tabName = tabName;
        this.background = helper.createDrawable(BMResourceLocations.CRYSTALLIZER_BACKGROUND, 0, 0, 56, 48);
        this.tabIcon = helper.createDrawableIngredient(new ItemStack(item));
    }

    @Override
    @Nonnull
    public String getTitle() {
        return this.getTitleAsTextComponent().getString();
    }

    @Override
    @Nonnull
    public ITextComponent getTitleAsTextComponent() {
        return this.tabName;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return this.tabIcon;
    }

    @Override
    public boolean isHandled(T recipe) {
        return !recipe.isSpecial();
    }
}
