package com.sophicreeper.backmath.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

public class BMKeyBindings {
    private static final long MINECRAFT_WINDOW = Minecraft.getInstance().getWindow().getWindow();
    public static final KeyBinding SHOW_TOOLTIPS_KEY = new KeyBinding("key.melony_studios.show_tooltips", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.melony_studios");

    @OnlyIn(Dist.CLIENT)
    public static boolean isShiftDown() {
        return InputMappings.isKeyDown(MINECRAFT_WINDOW, SHOW_TOOLTIPS_KEY.getKey().getValue());
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isVanillaShiftDown() {
        return Minecraft.getInstance().options.keyShift.isDown();
    }

    public static TranslationTextComponent getTranslation(KeyBinding keyBind) {
        return new TranslationTextComponent(keyBind.saveString());
    }
}
