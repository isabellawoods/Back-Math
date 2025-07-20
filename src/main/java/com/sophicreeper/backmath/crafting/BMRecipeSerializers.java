package com.sophicreeper.backmath.crafting;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.crafting.custom.CrystallineCrystallizingRecipe;
import com.sophicreeper.backmath.crafting.custom.CrystallizingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BMRecipeSerializers {
    public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BackMath.MOD_ID);

    public static IRecipeType<CrystallizingRecipe> CRYSTALLIZING_RECIPE = new CrystallizingRecipe.CrystallizingRecipeType();
    public static IRecipeType<CrystallineCrystallizingRecipe> CRYSTALLINE_CRYSTALLIZING_RECIPE = new CrystallineCrystallizingRecipe.CrystallineCrystallizingRecipeType();

    public static final RegistryObject<CrystallizingRecipe.Serializer> CRYSTALLIZING = SERIALIZERS.register("crystallizing", CrystallizingRecipe.Serializer::new);
    public static final RegistryObject<CrystallineCrystallizingRecipe.Serializer> CRYSTALLINE_CRYSTALLIZING = SERIALIZERS.register("crystalline_crystallizing", CrystallineCrystallizingRecipe.Serializer::new);
}
