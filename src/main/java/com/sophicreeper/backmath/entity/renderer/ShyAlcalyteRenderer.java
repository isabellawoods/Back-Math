package com.sophicreeper.backmath.entity.renderer;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.custom.alcalyte.ShyAlcalyteEntity;
import com.sophicreeper.backmath.entity.renderer.layer.BreastLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ShyAlcalyteRenderer extends BMPlayerRenderer<ShyAlcalyteEntity> {
    public ShyAlcalyteRenderer(EntityRendererManager manager) {
        super(manager, 0.5F, false);
        this.addLayer(new BreastLayer<>(this));
    }

    @Nonnull
    public ResourceLocation getTextureLocation(ShyAlcalyteEntity alcalyte) {
        return BackMath.entityTexture("alcalyte/shy_alcalyte");
    }
}
