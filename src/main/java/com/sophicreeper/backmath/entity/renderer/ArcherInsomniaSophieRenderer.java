package com.sophicreeper.backmath.entity.renderer;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.custom.ArcherInsomniaSophie;
import com.sophicreeper.backmath.entity.model.BMBipedModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArcherInsomniaSophieRenderer extends BMBipedRenderer<ArcherInsomniaSophie> {
    public ArcherInsomniaSophieRenderer(EntityRendererManager manager) {
        super(manager, 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new BMBipedModel<>(0.5F, 0, 64, 32), new BMBipedModel<>(1, 0, 64, 32)));
    }

    public ResourceLocation getTextureLocation(ArcherInsomniaSophie archerInsomniaSophie) {
        return BackMath.resourceLoc("textures/entity/insomnia_sophie.png");
    }
}
