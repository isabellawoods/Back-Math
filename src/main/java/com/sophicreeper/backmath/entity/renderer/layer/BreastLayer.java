package com.sophicreeper.backmath.entity.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.config.BMConfigs;
import com.sophicreeper.backmath.entity.misc.HasBreasts;
import com.sophicreeper.backmath.entity.model.BMPlayerModel;
import com.sophicreeper.backmath.entity.model.StretchableModelRenderer;
import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.entity.outfit.OutfitProvider;
import com.sophicreeper.backmath.entity.outfit.OutfitWearer;
import com.sophicreeper.backmath.misc.BMBreastPhysics;
import com.sophicreeper.backmath.util.BMUtils;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class BreastLayer<T extends LivingEntity> extends LayerRenderer<T, BMPlayerModel<T>> {
    private final StretchableModelRenderer.ModelBox chest = new StretchableModelRenderer.ModelBox(64, 64, 16, 17, -4, 0, 0, 8, 5, 4, 0, false);
    private final StretchableModelRenderer.ModelBox jacket = new StretchableModelRenderer.ModelBox(64, 64, 17, 34, -4, 0, 0, 8, 5, 3, 0, false);
    private final StretchableModelRenderer.ModelBox outfitChest = new StretchableModelRenderer.ModelBox(64, 64, 16, 17, -4, 0, 0, 8, 5, 4, 0, false);
    private final StretchableModelRenderer.ModelBox outfitJacket = new StretchableModelRenderer.ModelBox(64, 64, 17, 34, -4, 0, 0, 8, 5, 3, 0, false);
    private final StretchableModelRenderer.ModelBox chestplate = new StretchableModelRenderer.ModelBox(64, 32, 17, 19, -4, 0, 0, 8, 5, 3, 0, false);
    private final IEntityRenderer<T, BMPlayerModel<T>> renderer;

    public BreastLayer(IEntityRenderer<T, BMPlayerModel<T>> renderer) {
        super(renderer);
        this.renderer = renderer;
    }

    @Nullable
    public ResourceLocation getArmorResource(T livEntity, ItemStack stack, EquipmentSlotType slot, String type) {
        if (stack.getItem().is(BMItemTags.OUTFITS)) {
            ResourceLocation materialName = stack.getItem() instanceof OutfitProvider ? ((OutfitProvider) stack.getItem()).getOutfitDefinition(stack) : null;
            if (materialName == null) return null;
            return OutfitDefinition.getOutfitTexture(slot, materialName, this.renderer.getModel().slimArms());
        } else if (!(stack.getItem().is(BMItemTags.ELYTRA)) && stack.getItem() instanceof ArmorItem) {
            ArmorItem item = (ArmorItem) stack.getItem();
            String materialName = item.getMaterial().getName();
            String namespace = "minecraft";
            int index = materialName.indexOf(58);
            if (index != -1) {
                namespace = materialName.substring(0, index);
                materialName = materialName.substring(index + 1);
            }

            String formattedString = String.format("%s:textures/models/armor/%s_layer_%d%s.png", namespace, materialName, 1, type == null ? "" : String.format("_%s", type));
            formattedString = ForgeHooksClient.getArmorTexture(livEntity, stack, formattedString, slot, type);
            return new ResourceLocation(formattedString);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
        if ((ModList.get().isLoaded("wildfire_gender") || ModList.get().isLoaded("femalegender") || Minecraft.getInstance().getLaunchedVersion().contains("melony-studios-dev")) &&
                BMConfigs.COMMON_CONFIGS.renderBreasts.get() && entity instanceof HasBreasts) {
            BMBreastPhysics physics = ((HasBreasts) entity).getBreastPhysics();
            BMPlayerModel<T> model = this.renderer.getModel();
            float bustSize = ((HasBreasts) entity).getBustSize();

            if (bustSize >= 0.02F) {
                RenderSystem.color4f(1, 1, 1, 1);
                float preBounce = physics.getPreBounce();
                float breastBounce = physics.getBreastBounce();
                float total = MathHelper.lerp(partialTicks, preBounce, breastBounce);
                boolean isChestOccupied = !entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().is(BMItemTags.ELYTRA) && !entity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty();
                boolean canSeeFriendlyInvisibles = false;

                if (entity.getTeam() != null) canSeeFriendlyInvisibles = entity.getTeam().canSeeFriendlyInvisibles();

                this.pushStack(stack, model.body);
                float rotationMultiplier;

                stack.translate(0, total / 32, 0);

                stack.translate(0, 0.05624999850988388, -0.125);
                stack.translate(0, 0.009999999776482582, 0);
                rotationMultiplier = -total / 12;

                float totalRotation = bustSize + rotationMultiplier;
                if (totalRotation > bustSize + 0.2F) totalRotation = bustSize + 0.2F;
                if (totalRotation > 1) totalRotation = 1;
                if (isChestOccupied) stack.translate(0, 0, 0.009999999776482582);

                stack.mulPose(new Quaternion(-35 * totalRotation, 0, 0, true));

                if (!isChestOccupied) {
                    float rotationOffset = -MathHelper.cos(ageInTicks * 0.09F) * (0.45F + (entity.getHealth() <= 4 ? 2 : 0)) + 0.45F + 1;
                    stack.mulPose(new Quaternion(rotationOffset, 0, 0, true));
                }

                float transparency = this.getTransparency(entity);
                int modelOverlay = LivingRenderer.getOverlayCoords(entity, 0);
                int armorOverlay = BMUtils.getOverlayCoordinates(0);
                IVertexBuilder translucentBuffer = buffer.getBuffer(RenderType.entityTranslucent(this.renderer.getTextureLocation(entity)));
                stack.scale(0.9995F, 1, 1);

                if (canSeeFriendlyInvisibles && entity.isInvisible() || !entity.isInvisible()) {
                    renderBox(this.chest, stack, translucentBuffer, packedLight, modelOverlay, 1, 1, 1, transparency);
                    stack.translate(0, 0, -0.014999999664723873);
                    stack.scale(1.05F, 1.05F, 1.05F);
                    renderBox(this.jacket, stack, translucentBuffer, packedLight, modelOverlay, 1, 1, 1, transparency);
                }

                ItemStack chestStack = entity.getItemBySlot(EquipmentSlotType.CHEST);
                ResourceLocation armorTexture = this.getArmorResource(entity, chestStack, EquipmentSlotType.CHEST, null);
                boolean wearingOutfit = entity instanceof OutfitWearer && ((OutfitWearer) entity).isWearingOutfit();
                if (wearingOutfit) {
                    this.renderOutfitOnBreasts(stack, buffer, entity, armorTexture, packedLight, armorOverlay, transparency);
                }

                if (!entity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
                    if (chestStack.getItem().is(BMItemTags.FULLY_LIT_ITEMS)) packedLight = LightTexture.pack(15, 15);
                    if (armorTexture != null && chestStack.getItem().is(BMItemTags.OUTFITS) && !wearingOutfit) {
                        this.renderOutfitOnBreasts(stack, buffer, entity, armorTexture, packedLight, armorOverlay, transparency);
                    } else if (armorTexture != null && chestStack.getItem() instanceof ArmorItem) {
                        stack.pushPose();
                        float red = 1;
                        float green = 1;
                        float blue = 1;
                        if (!chestStack.getItem().is(BMItemTags.ELYTRA)) {
                            ArmorItem armorItem = (ArmorItem) chestStack.getItem();
                            if (armorItem instanceof IDyeableArmorItem) {
                                int armorColor = ((IDyeableArmorItem) armorItem).getColor(chestStack);
                                red = (float) (armorColor >> 16 & 255) / 255;
                                green = (float) (armorColor >> 8 & 255) / 255;
                                blue = (float) (armorColor & 255) / 255;
                            }

                            stack.translate(0, 0.014999999664723873, -0.014999999664723873);
                            stack.scale(1.05F, 1, 1);
                            RenderType armorType = RenderType.entityTranslucent(armorTexture);
                            IVertexBuilder armorBuffer = buffer.getBuffer(armorType);
                            renderBox(this.chestplate, stack, armorBuffer, packedLight, armorOverlay, red, green, blue, transparency);
                            if (chestStack.hasFoil()) {
                                RenderType glintType = RenderType.entityGlint();
                                IVertexBuilder glintBuffer = buffer.getBuffer(glintType);
                                renderBox(this.chestplate, stack, glintBuffer, packedLight, armorOverlay, 1, 1, 1, transparency);
                            }
                            stack.popPose();
                        }
                    }
                }
                stack.popPose();
            }
            RenderSystem.color4f(1, 1, 1, 1);
        }
    }

    private void renderOutfitOnBreasts(MatrixStack stack, IRenderTypeBuffer buffer, T entity, ResourceLocation armorTexture, int packedLight, int armorOverlay, float transparency) {
        if (entity instanceof OutfitWearer && ((OutfitWearer) entity).isWearingOutfit()) {
            armorTexture = OutfitDefinition.getOutfitTexture(EquipmentSlotType.CHEST, ResourceLocation.tryParse(((OutfitWearer) entity).getOutfitDefinition()), this.renderer.getModel().slimArms());
        }
        if (armorTexture == null) return;
        stack.pushPose();
        RenderType armorType = RenderType.entityTranslucent(armorTexture);
        IVertexBuilder armorBuffer = buffer.getBuffer(armorType);

        stack.scale(1, 1.032F, 1.032F);
        renderBox(this.outfitChest, stack, armorBuffer, packedLight, armorOverlay, 1, 1, 1, transparency);

        stack.translate(0, -0.016, -0.012);
        stack.scale(1.06F, 1.06F, 1.06F);
        renderBox(this.outfitJacket, stack, armorBuffer, packedLight, armorOverlay, 1, 1, 1, transparency);
        stack.popPose();
    }

    public void pushStack(MatrixStack stack, ModelRenderer renderer) {
        float x = renderer.x;
        float y = renderer.y;
        float z = renderer.z;
        float xRotation = renderer.xRot;
        float yRotation = renderer.yRot;
        float zRotation = renderer.zRot;
        stack.pushPose();
        stack.translate(x * 0.0625F, y * 0.0625F, z * 0.0625F);

        if (zRotation != 0) stack.mulPose(new Quaternion(0, 0, zRotation, false));
        if (yRotation != 0) stack.mulPose(new Quaternion(0, yRotation, 0, false));
        if (xRotation != 0) stack.mulPose(new Quaternion(xRotation, 0, 0, false));
    }

    public float getTransparency(LivingEntity livEntity) {
        float alpha = 1;
        boolean isInvisible = livEntity.isInvisible() && !livEntity.isInvisibleTo(Minecraft.getInstance().player);
        if (isInvisible) {
            alpha = 0.15F;
        } else if (livEntity.isInvisible()) {
            alpha = 0;
        }

        return alpha;
    }

    public static void renderBox(StretchableModelRenderer.ModelBox box, MatrixStack stack, IVertexBuilder builder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        stack.pushPose();
        Matrix4f lastPose = stack.last().pose();
        Matrix3f normalPose = stack.last().normal();
        StretchableModelRenderer.TexturedQuad[] quads = box.quads;

        for (StretchableModelRenderer.TexturedQuad quad : quads) {
            Vector3f quadCopy = quad.normal.copy();
            quadCopy.transform(normalPose);
            float x = quadCopy.x();
            float y = quadCopy.y();
            float z = quadCopy.z();

            for (int i = 0; i < 4; ++i) {
                StretchableModelRenderer.PositionTextureVertex textureVertex = quad.positions[i];
                float x1 = textureVertex.pos.x() / 16;
                float y1 = textureVertex.pos.y() / 16;
                float z1 = textureVertex.pos.z() / 16;
                Vector4f vec4F = new Vector4f(x1, y1, z1, 1);
                vec4F.transform(lastPose);
                builder.vertex(vec4F.x(), vec4F.y(), vec4F.z(), red, green, blue, alpha, textureVertex.posX, textureVertex.posY, packedOverlay, packedLight, x, y, z);
            }
        }

        stack.popPose();
    }
}
