package com.sophicreeper.backmath.crafting.custom;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractCrystallizingRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<?> type;
    protected final ResourceLocation id;
    protected final Ingredient mainHand;
    @Nullable
    protected final Ingredient offHand;
    protected final ItemStack resultStack;
    @Nullable
    protected final SoundEvent sound;

    public AbstractCrystallizingRecipe(IRecipeType<?> type, ResourceLocation id, Ingredient mainHand, @Nullable Ingredient offHand, ItemStack resultStack, @Nullable SoundEvent sound) {
        this.type = type;
        this.id = id;
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.resultStack = resultStack;
        this.sound = sound;
    }

    @Override
    public boolean matches(IInventory inventory, World world) {
        return this.mainHand().test(inventory.getItem(0)) && (this.offHand == null || this.offHand.test(inventory.getItem(1)));
    }

    @Override
    @Nonnull
    public ItemStack assemble(IInventory inventory) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.mainHand());
        if (this.offHand() != null) list.add(this.offHand());
        return list;
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return this.type;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return this.id;
    }

    public Ingredient mainHand() {
        return this.mainHand;
    }

    @Nullable
    public Ingredient offHand() {
        return this.offHand;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem() {
        return this.resultStack;
    }

    @Nullable
    public SoundEvent crystallizingSound() {
        return this.sound;
    }
}
