package com.sophicreeper.backmath.mixin.renderer;

import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.entity.outfit.OutfitProvider;
import com.sophicreeper.backmath.item.custom.tool.JanticRailgunItem;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class BMPlayerRendererMixin extends LivingRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
    public BMPlayerRendererMixin(EntityRendererManager manager, PlayerModel<AbstractClientPlayerEntity> model, float shadowSize) {
        super(manager, model, shadowSize);
    }

    @Inject(method = "setModelProperties", at = @At("TAIL"))
    private void hideLayersForOutfit(AbstractClientPlayerEntity player, CallbackInfo callback) {
        PlayerModel<AbstractClientPlayerEntity> model = this.getModel();

        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.getItem() instanceof OutfitProvider && stack.getItem().is(BMItemTags.OUTFITS)) {
                OutfitDefinition definition = OutfitDefinition.DATA_DRIVEN_OUTFITS.get(((OutfitProvider) stack.getItem()).getOutfitDefinition(stack));
                EquipmentSlotType slotType = ((OutfitProvider) stack.getItem()).getOutfitSlot(stack);
                if (OutfitDefinition.shouldHideLayer(slotType, definition, model.slim)) {
                    this.hideModelLayers(model, slotType);
                }
            }
        }
    }

    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void getArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedModel.ArmPose> callback) {
        ItemStack handStack = player.getItemInHand(hand);
        if (!handStack.isEmpty() && !player.swinging) {
            if (handStack.getItem().is(BMItemTags.RAILGUNS) && JanticRailgunItem.isCharged(handStack)) {
                callback.setReturnValue(BipedModel.ArmPose.CROSSBOW_HOLD);
            }
            if (handStack.getItem().is(BMItemTags.DUAL_WIELDED)) {
                callback.setReturnValue(BipedModel.ArmPose.CROSSBOW_CHARGE);
            }
        }
    }

    @Unique
    private void hideModelLayers(PlayerModel<AbstractClientPlayerEntity> model, EquipmentSlotType slotType) {
        switch (slotType) {
            case HEAD: {
                model.hat.visible = false;
                break;
            }
            case CHEST: {
                model.jacket.visible = false;
                model.rightSleeve.visible = false;
                model.leftSleeve.visible = false;
                break;
            }
            case LEGS:
            case FEET: {
                model.rightPants.visible = false;
                model.leftPants.visible = false;
                break;
            }
        }
    }
}
