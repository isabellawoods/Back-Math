package melonystudios.backmath.event;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.util.BMKeys;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = BackMath.MOD_ID)
public class BMClientEvents {
    @SubscribeEvent
    public static void registerKeybindings(RegisterKeyMappingsEvent event) {
        event.register(BMKeys.SHOW_TOOLTIPS_KEY);
    }
}
