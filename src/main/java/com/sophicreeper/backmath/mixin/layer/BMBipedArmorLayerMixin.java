package com.sophicreeper.backmath.mixin.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(BipedArmorLayer.class)
public abstract class BMBipedArmorLayerMixin<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
    @Shadow
    protected abstract void setPartVisibility(A model, EquipmentSlotType slot);
    @Shadow(remap = false)
    public abstract ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlotType slotType, @Nullable String type);

    public BMBipedArmorLayerMixin(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    private void renderArmorPiece(MatrixStack stack, IRenderTypeBuffer buffer, T entity, EquipmentSlotType slot, int packedLight, A model, CallbackInfo callback) {
        ItemStack armorStack = entity.getItemBySlot(slot);

        if (armorStack.getItem().is(BMItemTags.OUTFITS)) callback.cancel();

        if (armorStack.getItem() instanceof ArmorItem && armorStack.getItem().is(BMItemTags.FULLY_LIT_ITEMS) && !armorStack.getItem().is(BMItemTags.OUTFITS)) {
            callback.cancel();
            ArmorItem armorItem = (ArmorItem) armorStack.getItem();
            if (armorItem.getSlot() == slot) {
                model = ForgeHooksClient.getArmorModel(entity, armorStack, slot, model);
                this.getParentModel().copyPropertiesTo(model);
                this.setPartVisibility(model, slot);
                boolean enchantmentGlint = armorStack.hasFoil();
                if (armorItem instanceof IDyeableArmorItem) {
                    int color = ((IDyeableArmorItem) armorItem).getColor(armorStack);
                    float red = (float) (color >> 16 & 255) / 255;
                    float green = (float) (color >> 8 & 255) / 255;
                    float blue = (float) (color & 255) / 255;
                    this.renderEmissiveModel(stack, buffer, armorStack, enchantmentGlint, model, red, green, blue, this.getArmorResource(entity, armorStack, slot, null));
                    this.renderEmissiveModel(stack, buffer, armorStack, enchantmentGlint, model, 1, 1, 1, this.getArmorResource(entity, armorStack, slot, "overlay"));
                } else {
                    this.renderEmissiveModel(stack, buffer, armorStack, enchantmentGlint, model, 1, 1, 1, this.getArmorResource(entity, armorStack, slot, null));
                }
            }
        }
    }

    @Unique
    private void renderEmissiveModel(MatrixStack stack, IRenderTypeBuffer buffer, ItemStack handStack, boolean usesSecondLayer, A model, float red, float green, float blue, ResourceLocation armorResource) {
        if (handStack.getItem().is(BMItemTags.FULLY_LIT_ITEMS)) {
            IVertexBuilder builder = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(armorResource), false, usesSecondLayer);
            model.renderToBuffer(stack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, red, green, blue, 0.5F);
        }
    }
}
