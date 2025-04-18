package melonystudios.backmath.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class BMKeys {
    public static final long MINECRAFT_WINDOW = Minecraft.getInstance().getWindow().getWindow();
    public static final KeyMapping SHOW_TOOLTIPS_KEY = new KeyMapping("key.melony_studios.show_tooltips", KeyConflictContext.GUI, KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.melony_studios");

    @OnlyIn(Dist.CLIENT)
    public static boolean isShiftDown() {
        return InputConstants.isKeyDown(MINECRAFT_WINDOW, SHOW_TOOLTIPS_KEY.getKey().getValue());
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isVanillaShiftDown() {
        return Minecraft.getInstance().options.keyShift.isDown();
    }
}
