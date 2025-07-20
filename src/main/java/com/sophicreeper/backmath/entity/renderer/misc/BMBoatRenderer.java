package com.sophicreeper.backmath.entity.renderer.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.custom.misc.BMBoatEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

public class BMBoatRenderer extends EntityRenderer<BMBoatEntity> {
    private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[] {BackMath.entityTexture("boat/crystalline_birch"), BackMath.entityTexture("boat/goldenwood"), BackMath.entityTexture("boat/guava"),
            BackMath.entityTexture("boat/jabuticaba"), BackMath.entityTexture("boat/cork_oak"), BackMath.entityTexture("boat/aljanwood"), BackMath.entityTexture("boat/aljancap"), BackMath.entityTexture("boat/insomnian"),
            BackMath.entityTexture("boat/avondalic_willow")};
    protected final BoatModel model = new BoatModel();

    public BMBoatRenderer(EntityRendererManager manager) {
        super(manager);
        this.shadowRadius = 0.8F;
    }

    @Override
    public void render(BMBoatEntity boat, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        stack.pushPose();
        stack.translate(0, 0.375D, 0);
        stack.mulPose(Vector3f.YP.rotationDegrees(180 - yaw));
        float hurtTime = (float) boat.getHurtTime() - partialTicks;
        float damage = boat.getDamage() - partialTicks;
        if (damage < 0) damage = 0;

        if (hurtTime > 0) {
            stack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(hurtTime) * hurtTime * damage / 10 * (float) boat.getHurtDir()));
        }

        float bubbleAngle = boat.getBubbleAngle(partialTicks);
        if (!MathHelper.equal(bubbleAngle, 0)) {
            stack.mulPose(new Quaternion(new Vector3f(1, 0, 1), boat.getBubbleAngle(partialTicks), true));
        }

        stack.scale(-1, -1, 1);
        stack.mulPose(Vector3f.YP.rotationDegrees(90));
        this.model.setupAnim(boat, partialTicks, 0, -0.1F, 0, 0);
        IVertexBuilder vertexBuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(boat)));
        this.model.renderToBuffer(stack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        if (!boat.isUnderWater()) {
            IVertexBuilder waterMaskBuilder = buffer.getBuffer(RenderType.waterMask());
            this.model.waterPatch().render(stack, waterMaskBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        }

        stack.popPose();
        super.render(boat, yaw, partialTicks, stack, buffer, packedLight);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(BMBoatEntity boat) {
        switch (boat.getWoodType()) {
            case "crystalline_birch": return BOAT_TEXTURES[0];
            case "goldenwood": return BOAT_TEXTURES[1];
            case "guava": return BOAT_TEXTURES[2];
            case "jabuticaba": return BOAT_TEXTURES[3];
            case "cork_oak": return BOAT_TEXTURES[4];
            case "aljancap": return BOAT_TEXTURES[6];
            case "insomnian": return BOAT_TEXTURES[7];
            case "avondalic_willow": return BOAT_TEXTURES[8];
            case "aljanwood": default: return BOAT_TEXTURES[5];
        }
    }
}
