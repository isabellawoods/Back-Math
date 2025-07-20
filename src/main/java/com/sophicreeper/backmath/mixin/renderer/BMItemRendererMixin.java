package com.sophicreeper.backmath.mixin.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ItemRenderer.class)
public abstract class BMItemRendererMixin extends EntityRenderer<ItemEntity> {
    @Shadow
    @Final
    private net.minecraft.client.renderer.ItemRenderer itemRenderer;
    @Shadow
    @Final
    private Random random;
    @Shadow
    protected abstract int getRenderAmount(ItemStack stack);
    @Shadow(remap = false)
    public abstract boolean shouldBob();
    @Shadow(remap = false)
    public abstract boolean shouldSpreadItems();

    public BMItemRendererMixin(EntityRendererManager manager) {
        super(manager);
    }

    @Inject(method = "render(Lnet/minecraft/entity/item/ItemEntity;FFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V", at = @At("HEAD"), cancellable = true)
    public void render(ItemEntity entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, CallbackInfo callback) {
        if (entity.getItem().getItem().is(BMItemTags.FULLY_LIT_ITEMS)) {
            callback.cancel();
            stack.pushPose();
            ItemStack entityItem = entity.getItem();
            this.random.setSeed(entityItem.isEmpty() ? 187 : Item.getId(entityItem.getItem()) + entityItem.getDamageValue());
            IBakedModel model = this.itemRenderer.getModel(entityItem, entity.level, null);
            boolean threeDimensionalInGUI = model.isGui3d();
            int renderAmount = this.getRenderAmount(entityItem);
            float bobOffset = MathHelper.sin(((float) entity.getAge() + partialTicks) / 10 + entity.bobOffs) * 0.1F + 0.1F;
            float yScale = this.shouldBob() ? model.getTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y() : 0;
            stack.translate(0, bobOffset + 0.25F * yScale, 0);
            float spin = entity.getSpin(partialTicks);
            stack.mulPose(Vector3f.YP.rotation(spin));
            if (!threeDimensionalInGUI) {
                float x = -0 * (float) (renderAmount - 1) * 0.5F;
                float y = -0 * (float) (renderAmount - 1) * 0.5F;
                float z = -0.09375F * (float) (renderAmount - 1) * 0.5F;
                stack.translate(x, y, z);
            }

            for (int k = 0; k < renderAmount; ++k) {
                stack.pushPose();
                if (k > 0) {
                    if (threeDimensionalInGUI) {
                        float x = (this.random.nextFloat() * 2 - 1) * 0.15F;
                        float y = (this.random.nextFloat() * 2 - 1) * 0.15F;
                        float z = (this.random.nextFloat() * 2 - 1) * 0.15F;
                        stack.translate(shouldSpreadItems() ? x : 0, shouldSpreadItems() ? y : 0, shouldSpreadItems() ? z : 0);
                    } else {
                        float x = (this.random.nextFloat() * 2 - 1) * 0.15F * 0.5F;
                        float y = (this.random.nextFloat() * 2 - 1) * 0.15F * 0.5F;
                        stack.translate(shouldSpreadItems() ? x : 0, shouldSpreadItems() ? y : 0, 0);
                    }
                }

                this.itemRenderer.render(entityItem, ItemCameraTransforms.TransformType.GROUND, false, stack, buffer, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, model);
                stack.popPose();
                if (!threeDimensionalInGUI) stack.translate(0, 0, 0.09375F);
            }

            stack.popPose();
            super.render(entity, yaw, partialTicks, stack, buffer, packedLight);
        }
    }
}
