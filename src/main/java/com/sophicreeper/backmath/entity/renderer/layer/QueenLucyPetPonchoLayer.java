package com.sophicreeper.backmath.entity.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sophicreeper.backmath.entity.custom.QueenLucyPetEntity;
import com.sophicreeper.backmath.entity.model.QueenLucyPetModel;
import com.sophicreeper.backmath.util.BMResourceLocations;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QueenLucyPetPonchoLayer extends LayerRenderer<QueenLucyPetEntity, QueenLucyPetModel> {
    public QueenLucyPetPonchoLayer(IEntityRenderer<QueenLucyPetEntity, QueenLucyPetModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, QueenLucyPetEntity lucyPet, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
        if (lucyPet.isTame() && !lucyPet.isInvisible()) {
            int ponchoColor = lucyPet.getPonchoColor();
            float red = (float) (ponchoColor >> 16 & 255) / 255;
            float green = (float) (ponchoColor >> 8 & 255) / 255;
            float blue = (float) (ponchoColor & 255) / 255;
            renderColoredCutoutModel(this.getParentModel(), BMResourceLocations.QUEEN_LUCY_PET_PONCHO, stack, buffer, packedLight, lucyPet, red, green, blue);
        }
    }
}
