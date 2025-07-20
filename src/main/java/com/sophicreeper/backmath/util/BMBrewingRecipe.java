package com.sophicreeper.backmath.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nonnull;

public class BMBrewingRecipe implements IBrewingRecipe {
    private final Potion bottleInput;
    private final Item itemInput;
    private final ItemStack output;

    public BMBrewingRecipe(Potion potion, Item ingredient, Potion output) {
        this.bottleInput = potion;
        this.itemInput = ingredient;
        this.output = PotionUtils.setPotion(new ItemStack(Items.POTION), output);
    }

    /// Checks the item where the potion would go.
    @Override
    public boolean isInput(ItemStack inputStack) {
        return PotionUtils.getPotion(inputStack).equals(this.bottleInput);
    }

    /// Checks the item where the nether wart would go.
    @Override
    public boolean isIngredient(ItemStack ingredientStack) {
        return ingredientStack.getItem().equals(this.itemInput);
    }

    /// Gets the output potion. Very important to call copy because <code>ItemStack</code>s are mutable.
    @Override
    @Nonnull
    public ItemStack getOutput(ItemStack inputStack, ItemStack ingredientStack) {
        if (this.isInput(inputStack) && this.isIngredient(ingredientStack)) {
            return this.output.copy();
        } else {
            return ItemStack.EMPTY;
        }
    }
}
