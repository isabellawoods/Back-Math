package com.sophicreeper.backmath.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.custom.QueenLucyPetEntity;
import com.sophicreeper.backmath.entity.model.QueenLucyPetModel;
import com.sophicreeper.backmath.entity.renderer.layer.QueenLucyPetEmissiveLayer;
import com.sophicreeper.backmath.entity.renderer.layer.QueenLucyPetPonchoLayer;
import com.sophicreeper.backmath.item.custom.tool.JanticRailgunItem;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import com.sophicreeper.backmath.variant.queenlucypet.QueenLucyPetVariant;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class QueenLucyPetRenderer extends BipedRenderer<QueenLucyPetEntity, QueenLucyPetModel> {
    public QueenLucyPetRenderer(EntityRendererManager manager) {
        super(manager, new QueenLucyPetModel(), 0.25F);
        this.addLayer(new QueenLucyPetEmissiveLayer(this));
        this.addLayer(new QueenLucyPetPonchoLayer(this));
        // I'd rather not render the armor than have it render 2x the size of the mob.
        // this.addLayer(new BipedArmorLayer<>(this, new BMBipedModel<>(0.25F, 0, 64, 32), new BMBipedModel<>(0.5F, 0, 64, 32)));
        // Elytra also renders 2x the mob size.
        // this.addLayer(new ElytraLayer<>(this));
    }

    @Nonnull
    public ResourceLocation getTextureLocation(QueenLucyPetEntity lucy) {
        try {
            QueenLucyPetVariant variant = QueenLucyPetVariant.DATA_DRIVEN_VARIANTS.get(new ResourceLocation(lucy.getVariant()));
            return new ResourceLocation(variant.getTextureLocation().getNamespace(), "textures/" + variant.getTextureLocation().getPath() + ".png");
        } catch (NullPointerException exception) {
            return BackMath.entityTexture("queen_lucy_pet/qsp_current");
        }
    }

    @Override
    public void render(QueenLucyPetEntity lucy, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        this.setModelVisibilities(lucy);
        super.render(lucy, yaw, partialTicks, stack, buffer, packedLight);
    }

    @Override
    protected void setupRotations(QueenLucyPetEntity lucy, MatrixStack stack, float ageInTicks, float yaw, float partialTicks) {
        if (lucy.isFallFlying()) {
            super.setupRotations(lucy, stack, ageInTicks, yaw, partialTicks);
            float fallFlyingTicks = (float) lucy.getFallFlyingTicks() + partialTicks;
            float f = MathHelper.clamp(fallFlyingTicks * fallFlyingTicks / 100, 0, 1);
            if (!lucy.isAutoSpinAttack()) stack.mulPose(Vector3f.XP.rotationDegrees(f * (-90 - lucy.xRot)));

            Vector3d viewVector = lucy.getViewVector(partialTicks);
            Vector3d deltaMovement = lucy.getDeltaMovement();
            double deltaMovementSqr = Entity.getHorizontalDistanceSqr(deltaMovement);
            double viewVectorSqr = Entity.getHorizontalDistanceSqr(viewVector);
            if (deltaMovementSqr > 0 && viewVectorSqr > 0) {
                double d1 = (deltaMovement.x * viewVector.x + deltaMovement.z * viewVector.z) / Math.sqrt(deltaMovementSqr * viewVectorSqr);
                double d2 = deltaMovement.x * viewVector.z - deltaMovement.z * viewVector.x;
                stack.mulPose(Vector3f.YP.rotation((float) (Math.signum(d2) * Math.acos(d1))));
            }
        } else {
            super.setupRotations(lucy, stack, ageInTicks, yaw, partialTicks);
        }
    }

    private BipedModel.ArmPose getArmPose(QueenLucyPetEntity lucy, Hand hand) {
        ItemStack handStack = lucy.getItemInHand(hand);
        boolean acceptableCrossbows = handStack.getItem().is(BMItemTags.CROSSBOWS);

        if (handStack.isEmpty()) {
            return BipedModel.ArmPose.EMPTY;
        } else {
            if (lucy.getUsedItemHand() == hand && lucy.getUseItemRemainingTicks() > 0) {
                UseAction useAction = handStack.getUseAnimation();
                if (useAction == UseAction.BLOCK) {
                    return BipedModel.ArmPose.BLOCK;
                }

                if (useAction == UseAction.BOW) {
                    return BipedModel.ArmPose.BOW_AND_ARROW;
                }

                if (useAction == UseAction.SPEAR) {
                    return BipedModel.ArmPose.THROW_SPEAR;
                }

                if (useAction == UseAction.CROSSBOW && hand == lucy.getUsedItemHand()) {
                    return BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else if (!lucy.swinging && acceptableCrossbows && (CrossbowItem.isCharged(handStack) || JanticRailgunItem.isCharged(handStack))) {
                return BipedModel.ArmPose.CROSSBOW_HOLD;
            }

            if (!lucy.swinging && handStack.getItem().is(BMItemTags.DUAL_WIELDED)) {
                return BipedModel.ArmPose.CROSSBOW_CHARGE;
            }

            return BipedModel.ArmPose.ITEM;
        }
    }

    private void setModelVisibilities(QueenLucyPetEntity lucy) {
        QueenLucyPetModel lucyModel = this.getModel();
        lucyModel.setAllVisible(true);

        BipedModel.ArmPose mainHandPose = this.getArmPose(lucy, Hand.MAIN_HAND);
        BipedModel.ArmPose offHandPose = this.getArmPose(lucy, Hand.OFF_HAND);

        if (mainHandPose.isTwoHanded()) {
            offHandPose = lucy.getOffhandItem().isEmpty() ? BipedModel.ArmPose.EMPTY : BipedModel.ArmPose.ITEM;
        }

        if (lucy.getMainArm() == HandSide.RIGHT) {
            lucyModel.rightArmPose = mainHandPose;
            lucyModel.leftArmPose = offHandPose;
        } else {
            lucyModel.rightArmPose = offHandPose;
            lucyModel.leftArmPose = mainHandPose;
        }
    }
}
