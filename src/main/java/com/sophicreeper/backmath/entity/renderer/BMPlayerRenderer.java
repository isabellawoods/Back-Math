package com.sophicreeper.backmath.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sophicreeper.backmath.entity.model.BMArmorModel;
import com.sophicreeper.backmath.entity.model.BMPlayerModel;
import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.entity.outfit.OutfitWearer;
import com.sophicreeper.backmath.entity.renderer.layer.BMArmorLayer;
import com.sophicreeper.backmath.entity.renderer.layer.CrateLayer;
import com.sophicreeper.backmath.entity.renderer.layer.OutfitLayer;
import com.sophicreeper.backmath.entity.outfit.OutfitProvider;
import com.sophicreeper.backmath.item.custom.tool.JanticRailgunItem;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
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

import static com.sophicreeper.backmath.entity.outfit.OutfitDefinition.shouldHideLayer;

@OnlyIn(Dist.CLIENT)
public class BMPlayerRenderer<T extends CreatureEntity> extends BipedRenderer<T, BMPlayerModel<T>> {
    public boolean enableDefaultElytra = true;

    public BMPlayerRenderer(EntityRendererManager manager, float shadowSize, boolean slimArms) {
        this(manager, new BMPlayerModel<>(0, 0, 64, 64, slimArms), shadowSize, slimArms);
    }

    public BMPlayerRenderer(EntityRendererManager manager, BMPlayerModel<T> model, float shadowSize, boolean slimArms) {
        super(manager, model, shadowSize);
        this.addLayer(new OutfitLayer<>(this, slimArms));
        this.addLayer(new BMArmorLayer<>(this, new BMArmorModel<>(0.5F, 0, 64, 32), new BMArmorModel<>(1, 0, 64, 32)));
        this.addLayer(new CrateLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        if (this.enableDefaultElytra) this.addLayer(new ElytraLayer<>(this));
    }

    @Override
    public void render(T mob, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        this.setModelVisibilities(mob);
        super.render(mob, yaw, partialTicks, stack, buffer, packedLight);
    }

    @Override
    protected void scale(T mob, MatrixStack stack, float partialTicks) {
        stack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected void setupRotations(T mob, MatrixStack stack, float ageInTicks, float yaw, float partialTicks) {
        float swimAmount = mob.getSwimAmount(partialTicks);
        if (mob.isFallFlying()) {
            super.setupRotations(mob, stack, ageInTicks, yaw, partialTicks);
            float fallFlyingTicks = (float) mob.getFallFlyingTicks() + partialTicks;
            float f = MathHelper.clamp(fallFlyingTicks * fallFlyingTicks / 100, 0, 1);
            if (!mob.isAutoSpinAttack()) stack.mulPose(Vector3f.XP.rotationDegrees(f * (-90 - mob.xRot)));

            Vector3d viewVector = mob.getViewVector(partialTicks);
            Vector3d deltaMovement = mob.getDeltaMovement();
            double deltaMovementSqr = Entity.getHorizontalDistanceSqr(deltaMovement);
            double viewVectorSqr = Entity.getHorizontalDistanceSqr(viewVector);
            if (deltaMovementSqr > 0 && viewVectorSqr > 0) {
                double d1 = (deltaMovement.x * viewVector.x + deltaMovement.z * viewVector.z) / Math.sqrt(deltaMovementSqr * viewVectorSqr);
                double d2 = deltaMovement.x * viewVector.z - deltaMovement.z * viewVector.x;
                stack.mulPose(Vector3f.YP.rotation((float) (Math.signum(d2) * Math.acos(d1))));
            }
        } else if (swimAmount > 0) {
            super.setupRotations(mob, stack, ageInTicks, yaw, partialTicks);
            float f1 = mob.isInWater() ? -90 - mob.xRot : -90;
            float f2 = MathHelper.lerp(swimAmount, 0, f1);
            stack.mulPose(Vector3f.XP.rotationDegrees(f2));
            if (mob.isVisuallySwimming()) {
                stack.translate(0, -1, 0.3F);
            }
        } else {
            super.setupRotations(mob, stack, ageInTicks, yaw, partialTicks);
        }
    }

    private BipedModel.ArmPose getArmPose(T mob, Hand hand) {
        ItemStack handStack = mob.getItemInHand(hand);
        boolean acceptableCrossbows = handStack.getItem().is(BMItemTags.CROSSBOWS);

        if (handStack.isEmpty()) {
            return BipedModel.ArmPose.EMPTY;
        } else {
            if (mob.getUsedItemHand() == hand && mob.getUseItemRemainingTicks() > 0) {
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

                if (useAction == UseAction.CROSSBOW && hand == mob.getUsedItemHand()) {
                    return BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else if (!mob.swinging && acceptableCrossbows && (CrossbowItem.isCharged(handStack) || JanticRailgunItem.isCharged(handStack))) {
                return BipedModel.ArmPose.CROSSBOW_HOLD;
            }

            if (!mob.swinging && handStack.getItem().is(BMItemTags.DUAL_WIELDED)) {
                return BipedModel.ArmPose.CROSSBOW_CHARGE;
            }

            return BipedModel.ArmPose.ITEM;
        }
    }

    private void setModelVisibilities(T mob) {
        BMPlayerModel<T> mobModel = this.getModel();
        mobModel.setAllVisible(true);

        if (mob instanceof OutfitWearer && ((OutfitWearer) mob).isWearingOutfit()) {
            OutfitDefinition definition = OutfitDefinition.DATA_DRIVEN_OUTFITS.get(ResourceLocation.tryParse(((OutfitWearer) mob).getOutfitDefinition()));
            if (definition == null) return;
            if (shouldHideLayer(EquipmentSlotType.HEAD, definition, mobModel.slimArms())) this.hideModelLayers(mobModel, EquipmentSlotType.HEAD);
            if (shouldHideLayer(EquipmentSlotType.CHEST, definition, mobModel.slimArms())) this.hideModelLayers(mobModel, EquipmentSlotType.CHEST);
            if (shouldHideLayer(EquipmentSlotType.LEGS, definition, mobModel.slimArms())) this.hideModelLayers(mobModel, EquipmentSlotType.LEGS);
            if (shouldHideLayer(EquipmentSlotType.FEET, definition, mobModel.slimArms())) this.hideModelLayers(mobModel, EquipmentSlotType.FEET);
        } else {
            for (ItemStack stack : mob.getArmorSlots()) {
                if (stack.getItem() instanceof OutfitProvider && stack.getItem().is(BMItemTags.OUTFITS)) {
                    OutfitDefinition definition = OutfitDefinition.DATA_DRIVEN_OUTFITS.get(((OutfitProvider) stack.getItem()).getOutfitDefinition(stack));
                    EquipmentSlotType slotType = ((OutfitProvider) stack.getItem()).getOutfitSlot(stack);
                    if (shouldHideLayer(slotType, definition, mobModel.slimArms())) {
                        this.hideModelLayers(mobModel, slotType);
                    }
                }
            }
        }

        BipedModel.ArmPose mainHandPose = this.getArmPose(mob, Hand.MAIN_HAND);
        BipedModel.ArmPose offHandPose = this.getArmPose(mob, Hand.OFF_HAND);
        mobModel.crouching = mob.isCrouching();

        if (mainHandPose.isTwoHanded()) {
            offHandPose = mob.getOffhandItem().isEmpty() ? BipedModel.ArmPose.EMPTY : BipedModel.ArmPose.ITEM;
        }

        if (mob.getMainArm() == HandSide.RIGHT) {
            mobModel.rightArmPose = mainHandPose;
            mobModel.leftArmPose = offHandPose;
        } else {
            mobModel.rightArmPose = offHandPose;
            mobModel.leftArmPose = mainHandPose;
        }
    }

    private void hideModelLayers(BMPlayerModel<T> mobModel, EquipmentSlotType slotType) {
        switch (slotType) {
            case HEAD: {
                mobModel.hat.visible = false;
                break;
            }
            case CHEST: {
                mobModel.jacket.visible = false;
                mobModel.rightSleeve.visible = false;
                mobModel.leftSleeve.visible = false;
                break;
            }
            case LEGS: case FEET: {
                mobModel.rightPants.visible = false;
                mobModel.leftPants.visible = false;
                break;
            }
        }
    }
}
