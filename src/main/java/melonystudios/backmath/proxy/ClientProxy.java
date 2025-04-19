package melonystudios.backmath.proxy;

import melonystudios.backmath.fluid.BMFluids;
import melonystudios.backmath.item.AxolotlTest;
import melonystudios.backmath.item.custom.tool.bow.BMBowItem;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static melonystudios.backmath.util.BMItemModelProperties.*;

public class ClientProxy extends CommonProxy {
    public ClientProxy(IEventBus eventBus) {
        super(eventBus);
        eventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Block & Fluid Render Lookups
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

        // Item Properties
        makeShield(AxolotlTest.DEVIL_SHIELD.get());
        makeShield(AxolotlTest.ANGELIC_SHIELD.get());
        makeShield(AxolotlTest.MID_TERM_SHIELD.get());
        makeShield(AxolotlTest.ALJAMEED_SHIELD.get());
        makeShield(AxolotlTest.MOONERING_SHIELD.get());

        makeBow((BMBowItem) AxolotlTest.DEVIL_BOW.get());
        makeBow((BMBowItem) AxolotlTest.ANGELIC_BOW.get());
        makeBow((BMBowItem) AxolotlTest.MID_TERM_BOW.get());

        makeCrossbow(AxolotlTest.DEVIL_CROSSBOW.get());
        makeCrossbow(AxolotlTest.ANGELIC_CROSSBOW.get());
        makeJanticRailgun(AxolotlTest.JANTIC_RAILGUN.get());
        makeCarewni(AxolotlTest.CAREWNI.get());
    }
}
