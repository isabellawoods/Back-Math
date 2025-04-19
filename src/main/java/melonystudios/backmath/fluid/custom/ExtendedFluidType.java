package melonystudios.backmath.fluid.custom;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/**
 * Basic implementation of {@link FluidType} that supports specifying still and flowing textures in the constructor.
 *
 * @author Choonster (<a href="https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.19.x/LICENSE.txt">MIT License</a>)
 * Change by: Kaupenjoe
 * Added overlayTexture and tintColor as well. Also converts tint color into fog color
 */
public class ExtendedFluidType extends FluidType implements IClientFluidTypeExtensions {
    public final IClientFluidTypeExtensions extension = new IClientFluidTypeExtensions() {
        @NotNull
        public ResourceLocation getStillTexture() {
            return stillTexture;
        }

        @NotNull
        public ResourceLocation getFlowingTexture() {
            return flowingTexture;
        }

        public int getTintColor() {
            return tintColor;
        }

        public ResourceLocation getOverlayTexture() {
            return overlayTexture;
        }

        @Override
        @NotNull
        public Vector3f modifyFogColor(Camera camera, float partialTicks, ClientLevel world, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
            return IClientFluidTypeExtensions.super.modifyFogColor(camera, partialTicks, world, renderDistance, darkenWorldAmount, fogColor);
        }

        @Override
        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTicks, float nearDistance, float farDistance, FogShape shape) {
            RenderSystem.setShaderFogStart(5);
            RenderSystem.setShaderFogEnd(10);
        }
    };
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final int tintColor;
    private final Vector3f fogColor;

    public ExtendedFluidType(final ResourceLocation stillTexture, final ResourceLocation flowingTexture, final ResourceLocation overlayTexture, final int tintColor, final Vector3f fogColor, final Properties properties) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
    }

    @NotNull
    public ResourceLocation getStillTexture() {
        return this.stillTexture;
    }

    @NotNull
    public ResourceLocation getFlowingTexture() {
        return this.flowingTexture;
    }

    public int getTintColor() {
        return this.tintColor;
    }

    public ResourceLocation getOverlayTexture() {
        return this.overlayTexture;
    }

    public Vector3f getFogColor() {
        return this.fogColor;
    }
}
