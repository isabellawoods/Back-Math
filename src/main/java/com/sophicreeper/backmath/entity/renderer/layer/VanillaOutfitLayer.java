package com.sophicreeper.backmath.entity.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.entity.model.BMOutfitModel;
import com.sophicreeper.backmath.entity.model.BMPlayerModel;
import com.sophicreeper.backmath.item.custom.armor.OutfitItem;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VanillaOutfitLayer<T extends AbstractClientPlayerEntity, A extends PlayerModel<T>> extends LayerRenderer<T, A> {
    private final PlayerModel<T> originalModel;

    public VanillaOutfitLayer(IEntityRenderer<T, A> renderer, PlayerModel<T> originalModel) {
        super(renderer);
        this.originalModel = originalModel;
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, T mob, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
        this.renderOutfitPart(stack, buffer, mob, EquipmentSlotType.HEAD, packedLight, this.originalModel);
        this.renderOutfitPart(stack, buffer, mob, EquipmentSlotType.CHEST, packedLight, this.originalModel);
        this.renderOutfitPart(stack, buffer, mob, EquipmentSlotType.LEGS, packedLight, this.originalModel);
        this.renderOutfitPart(stack, buffer, mob, EquipmentSlotType.FEET, packedLight, this.originalModel);
    }

    private void renderOutfitPart(MatrixStack stack, IRenderTypeBuffer buffer, T mob, EquipmentSlotType slotType, int packedLight, PlayerModel<T> model) {
        if (!mob.getItemBySlot(slotType).isEmpty() && mob.getItemBySlot(slotType).getItem() instanceof OutfitItem) {
            A outfitModel = this.getParentModel();
            outfitModel.setAllVisible(true);
            OutfitItem item = (OutfitItem) mob.getItemBySlot(slotType).getItem();
            IVertexBuilder translucentBuffer = buffer.getBuffer(RenderType.entityTranslucent(parseOutfitLocation(item.getMaterial().getName(), slotType)));
            outfitModel.renderToBuffer(stack, translucentBuffer, packedLight, LivingRenderer.getOverlayCoords(mob, 0), 1, 1, 1, 1);
        }
    }

    private ResourceLocation parseOutfitLocation(String name, EquipmentSlotType slotType) {
        ResourceLocation location = new ResourceLocation(name);
        ResourceLocation chestLocation = new ResourceLocation(location.getNamespace(), "textures/models/outfit/" + location.getPath() + "_" + slotType.getName() + "_" + (this.getParentModel().slim ? "slim" : "classic") + ".png");
        ResourceLocation defaultLocation = new ResourceLocation(location.getNamespace(), "textures/models/outfit/" + location.getPath() + "_" + slotType.getName() + ".png");

        return slotType == EquipmentSlotType.CHEST ? chestLocation : defaultLocation;
    }
}
