package com.sophicreeper.backmath.entity.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrateLayer<T extends LivingEntity, A extends BipedModel<T>> extends LayerRenderer<T, A> {
    public CrateLayer(IEntityRenderer<T, A> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, T livEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
        if (livEntity.getItemBySlot(EquipmentSlotType.CHEST).getItem().is(BMItemTags.CRATES)) {
            ItemStack chestStack = livEntity.getItemBySlot(EquipmentSlotType.CHEST);
            stack.pushPose();
            stack.scale(0.8F, 0.8F, 0.8F);
            stack.translate(0, 0.5, 0.6);
            stack.mulPose(new Quaternion(Vector3f.XP.rotationDegrees(180)));
            if (livEntity.isShiftKeyDown() || livEntity.isCrouching()) {
                stack.mulPose(new Quaternion(Vector3f.XP.rotation(0.5F)));
                stack.translate(0, 0, -0.2);
            }
            Minecraft.getInstance().getItemInHandRenderer().renderItem(livEntity, chestStack, ItemCameraTransforms.TransformType.NONE, false, stack, buffer, packedLight);
            stack.popPose();
        }
    }
}
