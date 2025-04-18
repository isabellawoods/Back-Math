package melonystudios.backmath;

import melonystudios.backmath.config.BMConfigs;
import melonystudios.backmath.proxy.ClientProxy;
import melonystudios.backmath.proxy.CommonProxy;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(BackMath.MOD_ID)
public class BackMath {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "backmath";
    public final CommonProxy proxy;

    public BackMath(IEventBus eventBus, ModContainer container) {
        this.proxy = new ClientProxy(eventBus);
        container.registerConfig(ModConfig.Type.COMMON, BMConfigs.SPEC, "melonystudios/backmath-common.toml");
    }

    public static ResourceLocation backMath(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static ResourceLocation texture(String name) {
        return backMath("textures/" + name + ".png");
    }

    public static ResourceLocation entityTexture(String name) {
        return texture("entity/" + name);
    }

    public static ResourceLocation textureLocation(ResourceLocation textureLocation) {
        return ResourceLocation.fromNamespaceAndPath(textureLocation.getNamespace(), "textures/" + textureLocation.getPath() + ".png");
    }
}
