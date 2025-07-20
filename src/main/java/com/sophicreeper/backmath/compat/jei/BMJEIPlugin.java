package com.sophicreeper.backmath.compat.jei;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.crafting.BMRecipeSerializers;
import com.sophicreeper.backmath.crafting.custom.CrystallizingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class BMJEIPlugin implements IModPlugin {
    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return BackMath.backMath("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(BMAnvilRecipeMaker.getAnvilRecipes(registration.getVanillaRecipeFactory()), VanillaRecipeCategoryUid.ANVIL);
        registration.addRecipes(recipeManager.getAllRecipesFor(BMRecipeSerializers.CRYSTALLIZING_RECIPE).stream().filter(recipe -> recipe instanceof CrystallizingRecipe).collect(Collectors.toList()), BackMath.backMath("crystallizing"));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrystallizingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
}
