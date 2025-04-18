package melonystudios.backmath.proxy;

import melonystudios.backmath.fluid.BMFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {
    public ClientProxy(IEventBus eventBus) {
        super(eventBus);
        eventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BMFluids.HILLARY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.FLOWING_HILLARY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.MILKLLARY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.FLOWING_MILKLLARY.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.LIQUID_ALJAME.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.FLOWING_LIQUID_ALJAME.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.SLEEPISHWATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.FLOWING_SLEEPISHWATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.LIQUID_MANGA.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BMFluids.FLOWING_LIQUID_MANGA.get(), RenderType.translucent());
    }
}
