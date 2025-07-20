package com.sophicreeper.backmath.data;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.data.models.BMBlockStateProviderV2;
import com.sophicreeper.backmath.data.models.BMItemModelProvider;
import com.sophicreeper.backmath.data.outfit.BMOutfitDefinitionProvider;
import com.sophicreeper.backmath.data.recipe.BMRecipeProvider;
import com.sophicreeper.backmath.data.tags.*;
import com.sophicreeper.backmath.data.variant.BMQueenLucyPetVariantsProvider;
import com.sophicreeper.backmath.data.variant.BMQueenLucyVariantsProvider;
import com.sophicreeper.backmath.data.variant.BMWandererSophieVariantsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BackMath.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class BMDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(new BMBlockStateProviderV2(generator, fileHelper));
        generator.addProvider(new BMItemModelProvider(generator, fileHelper));

        BMBlockTagsProvider bmBlockTags = new BMBlockTagsProvider(generator, fileHelper);
        generator.addProvider(bmBlockTags);
        generator.addProvider(new BMItemTagsProvider(generator, bmBlockTags, fileHelper));
        generator.addProvider(new BMFluidTagsProvider(generator, fileHelper));
        generator.addProvider(new BMEntityTypeTagsProvider(generator, fileHelper));
        generator.addProvider(new BMEnchantmentTagsProvider(generator, fileHelper));
        generator.addProvider(new BMRecipeProvider(generator));
        generator.addProvider(new BMWandererSophieVariantsProvider(generator, fileHelper));
        generator.addProvider(new BMQueenLucyVariantsProvider(generator, fileHelper));
        generator.addProvider(new BMQueenLucyPetVariantsProvider(generator, fileHelper));
        generator.addProvider(new BMOutfitDefinitionProvider(generator));
        generator.addProvider(new BMLootModifierProvider(generator));
    }
}
