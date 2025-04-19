package melonystudios.backmath.proxy;

import melonystudios.backmath.block.BMBlocks;
import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.fluid.BMFluidTypes;
import melonystudios.backmath.fluid.BMFluids;
import melonystudios.backmath.item.AxolotlTest;
import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.behavior.effecttype.BMItemBehaviorEffectTypes;
import melonystudios.backmath.item.BMSetFields;
import melonystudios.backmath.item.tab.BMCreativeTabs;
import melonystudios.backmath.misc.BMSounds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonProxy {
    CommonProxy(IEventBus eventBus) {
        // Items
        AxolotlTest.ITEMS.register(eventBus);
        BMDataComponents.COMPONENTS.register(eventBus);
        BMSetFields.MATERIALS.register(eventBus);
        BMCreativeTabs.TABS.register(eventBus);
        BMItemBehaviors.BEHAVIORS.register(eventBus);
        BMItemBehaviorEffectTypes.TYPES.register(eventBus);

        // Blocks & Fluids
        BMBlocks.BLOCKS.register(eventBus);
        BMFluids.FLUIDS.register(eventBus);
        BMFluidTypes.FLUID_TYPES.register(eventBus);

        // Miscellaneous
        BMSounds.SOUNDS.register(eventBus);

        eventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}
}
