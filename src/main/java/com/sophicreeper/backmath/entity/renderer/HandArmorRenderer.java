package com.sophicreeper.backmath.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.entity.outfit.OutfitProvider;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nullable;

public class HandArmorRenderer {
    public static void renderOutfitInArm(AbstractClientPlayerEntity player, HandSide side, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        ItemStack chestStack = player.getItemBySlot(EquipmentSlotType.CHEST);
        if (chestStack.getItem() instanceof OutfitProvider && chestStack.getItem().is(BMItemTags.OUTFITS)) {
            PlayerModel<AbstractClientPlayerEntity> outfitModel = new PlayerModel<>(0.01F, player.getModelName().equals("slim"));
            ModelRenderer rightArm = outfitModel.rightArm;
            ModelRenderer rightSleeve = outfitModel.rightSleeve;

            if (side == HandSide.LEFT) {
                rightArm = outfitModel.leftArm;
                rightSleeve = outfitModel.leftSleeve;
            }

            ResourceLocation outfitDefinition = ((OutfitProvider) chestStack.getItem()).getOutfitDefinition(chestStack);
            if (outfitDefinition == null) return;
            outfitModel.attackTime = 0;
            outfitModel.crouching = false;
            outfitModel.swimAmount = 0;
            outfitModel.setupAnim(player, 0, 0, 0, 0, 0);
            ResourceLocation outfitLocation = OutfitDefinition.getOutfitTexture(EquipmentSlotType.CHEST, outfitDefinition, outfitModel.slim);
            ResourceLocation emissiveLocation = OutfitDefinition.getEmissiveOutfitTexture(EquipmentSlotType.CHEST, outfitDefinition, outfitModel.slim);

            if (outfitLocation != null) {
                IVertexBuilder translucentBuffer = buffer.getBuffer(RenderType.entityTranslucent(outfitLocation));

                rightArm.xRot = 0;
                rightArm.render(stack, translucentBuffer, packedLight, BMUtils.getOverlayCoordinates(0));
                rightSleeve.xRot = 0;
                rightSleeve.render(stack, translucentBuffer, packedLight, BMUtils.getOverlayCoordinates(0));
            }

            if (emissiveLocation != null) {
                IVertexBuilder emissiveBuffer = buffer.getBuffer(RenderType.eyes(emissiveLocation));

                rightArm.xRot = 0;
                rightArm.render(stack, emissiveBuffer, BMUtils.EMISSIVE_LIGHT_VALUE, BMUtils.getOverlayCoordinates(0));
                rightSleeve.xRot = 0;
                rightSleeve.render(stack, emissiveBuffer, BMUtils.EMISSIVE_LIGHT_VALUE, BMUtils.getOverlayCoordinates(0));
            }
        }
    }

    public static void renderArmorInArm(AbstractClientPlayerEntity player, HandSide side, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight) {
        if (player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof ArmorItem && !player.getItemBySlot(EquipmentSlotType.CHEST).getItem().is(BMItemTags.OUTFITS)) {
            BipedModel<AbstractClientPlayerEntity> armorModel = new BipedModel<>(0.26F);
            ModelRenderer rightArm = armorModel.rightArm;
            if (side == HandSide.LEFT) rightArm = armorModel.leftArm;

            ItemStack chestStack = player.getItemBySlot(EquipmentSlotType.CHEST);
            ArmorItem item = (ArmorItem) chestStack.getItem();
            armorModel.attackTime = 0;
            armorModel.crouching = false;
            armorModel.swimAmount = 0;
            armorModel.setupAnim(player, 0, 0, 0, 0, 0);
            IVertexBuilder cutoutBuffer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(getArmorResource(player, chestStack, item.getSlot(), null)), false, chestStack.hasFoil());

            // dyeable armor (leather) currently don't render for some reason ~isa 24-1-25
            rightArm.xRot = 0;
            if (item.is(BMItemTags.FULLY_LIT_ITEMS)) packedLight = LightTexture.pack(15, 15);
            if (item instanceof IDyeableArmorItem) {
                IVertexBuilder cutoutOverlayBuffer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(getArmorResource(player, chestStack, item.getSlot(), "overlay")), false, chestStack.hasFoil());
                int color = ((IDyeableArmorItem) item).getColor(chestStack);
                float red = (float) (color >> 16 & 255) / 255;
                float green = (float) (color >> 8 & 255) / 255;
                float blue = (float) (color & 255) / 255;
                rightArm.render(stack, cutoutBuffer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1F);
                rightArm.render(stack, cutoutOverlayBuffer, packedLight, OverlayTexture.NO_OVERLAY);
            } else {
                rightArm.render(stack, cutoutBuffer, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
    }

    public static ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlotType slotType, @Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String material = item.getMaterial().getName();
        String namespace = "minecraft";
        int index = material.indexOf(':');
        if (index != -1) {
            namespace = material.substring(0, index);
            material = material.substring(index + 1);
        }
        String textureFormat = String.format("%s:textures/models/armor/%s_layer_%d%s.png", namespace, material, (slotType == EquipmentSlotType.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
        textureFormat = ForgeHooksClient.getArmorTexture(entity, stack, textureFormat, slotType, type);
        return new ResourceLocation(textureFormat);
    }
}
