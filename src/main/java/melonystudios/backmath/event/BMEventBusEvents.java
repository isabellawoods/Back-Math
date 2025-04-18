package melonystudios.backmath.event;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.block.ExtendedFluidType;
import melonystudios.backmath.data.BMDataMapsProvider;
import melonystudios.backmath.data.model.BMBlockStateProviderV2;
import melonystudios.backmath.data.model.BMItemModelProvider;
import melonystudios.backmath.data.tag.BMBlockTagsProvider;
import melonystudios.backmath.data.tag.BMItemTagsProvider;
import melonystudios.backmath.fluid.BMFluidTypes;
import melonystudios.backmath.misc.BMRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BackMath.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class BMEventBusEvents {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput output = generator.getPackOutput();

        if (event.includeClient()) {
            generator.addProvider(true, new BMItemModelProvider(output, fileHelper));
            generator.addProvider(true, new BMBlockStateProviderV2(output, fileHelper));
        }

        if (event.includeServer()) {
            BMBlockTagsProvider blockTagsProvider = new BMBlockTagsProvider(output, lookupProvider, fileHelper);
            generator.addProvider(true, blockTagsProvider);
            generator.addProvider(true, new BMItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), fileHelper));
            generator.addProvider(true, new BMDataMapsProvider(output, lookupProvider));
        }
    }

    @SubscribeEvent
    public static void registerExtensions(RegisterClientExtensionsEvent event) {
        List<FluidType> types = List.of(BMFluidTypes.HILLARY.get(), BMFluidTypes.MILKLLARY.get(), BMFluidTypes.LIQUID_ALJAME.get(), BMFluidTypes.LIQUID_MANGA.get(), BMFluidTypes.LIQUEFIED_MONSTER.get(),
                BMFluidTypes.SLEEPISHWATER.get());
        for (FluidType type : types) event.registerFluidType(((ExtendedFluidType) type).extension, type);
    }

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(BMRegistries.ITEM_BEHAVIOR);
        event.register(BMRegistries.ITEM_BEHAVIOR_EFFECT_TYPE);
    }
}
