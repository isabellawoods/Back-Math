package com.sophicreeper.backmath.entity.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.config.BMConfigs;
import com.sophicreeper.backmath.entity.misc.HasBust;
import com.sophicreeper.backmath.entity.model.BMPlayerModel;
import com.sophicreeper.backmath.entity.model.StretchableModelRenderer;
import com.sophicreeper.backmath.entity.outfit.OutfitDefinition;
import com.sophicreeper.backmath.misc.BMBreastPhysics;
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
import org.lwjgl.opengl.GL11;

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

    public ResourceLocation getArmorResource(T entity, ItemStack stack, EquipmentSlotType slot, String type) {
        if (stack.getItem().is(BMItemTags.OUTFITS)) {
            String materialName = stack.getItem() instanceof ArmorItem ? ((ArmorItem) stack.getItem()).getMaterial().getName() : (stack.getItem().is(BMItemTags.CRATES) ? "backmath:crate" : "");
            if (materialName.isEmpty()) return new ResourceLocation("");
            return OutfitDefinition.getOutfitTexture(slot, ResourceLocation.tryParse(materialName), false);
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
            formattedString = ForgeHooksClient.getArmorTexture(entity, stack, formattedString, slot, type);
            return new ResourceLocation(formattedString);
        } else {
            return new ResourceLocation("");
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
        if ((ModList.get().isLoaded("femalegender") || Minecraft.getInstance().getLaunchedVersion().contains("melony-studios-dev")) && BMConfigs.COMMON_CONFIGS.renderBreasts.get() && entity instanceof HasBust) {
            BMBreastPhysics physics = ((HasBust) entity).getBreastPhysics();
            BMPlayerModel<T> model = this.renderer.getModel();
            float bustSize = ((HasBust) entity).getBustSize();

            if (bustSize >= 0.02F) {
                GL11.glColor3f(1, 1, 1);
                float preBounce = physics.getPreBounce();
                float breastBounce = physics.getBreastBounce();
                float total = MathHelper.lerp(partialTicks, preBounce, breastBounce);
                boolean isChestOccupied = entity.getItemBySlot(EquipmentSlotType.CHEST).getItem().is(BMItemTags.ELYTRA) && !entity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty();
                boolean canSeeFriendlyInvisibles = false;

                if (entity.getTeam() != null) canSeeFriendlyInvisibles = entity.getTeam().canSeeFriendlyInvisibles();

                this.pushMatrix(matrixStack, model.body);
                float rotationMultiplier;

                matrixStack.translate(0, total / 32, 0);

                matrixStack.translate(0, 0.05624999850988388, -0.125);
                matrixStack.translate(0, 0.009999999776482582, 0);
                rotationMultiplier = -total / 12;

                float totalRotation = bustSize + rotationMultiplier;
                if (totalRotation > bustSize + 0.2F) totalRotation = bustSize + 0.2F;
                if (totalRotation > 1) totalRotation = 1;
                if (isChestOccupied) matrixStack.translate(0, 0, 0.009999999776482582);

                matrixStack.mulPose(new Quaternion(-35 * totalRotation, 0, 0, true));

                if (!isChestOccupied) {
                    float rotationOffset = -MathHelper.cos(ageInTicks * 0.09F) * 0.45F + 0.45F + 1;
                    matrixStack.mulPose(new Quaternion(rotationOffset, 0, 0, true));
                }

                float transparency = this.getTransparency(entity);
                int packedOverlay = LivingRenderer.getOverlayCoords(entity, 0);
                RenderType translucentType = RenderType.entityTranslucent(this.renderer.getTextureLocation(entity));
                IVertexBuilder translucentBuffer = buffer.getBuffer(translucentType);
                matrixStack.scale(0.9995F, 1, 1);

                if (canSeeFriendlyInvisibles && entity.isInvisible() || !entity.isInvisible()) {
                    renderBox(this.chest, matrixStack, translucentBuffer, packedLight, packedOverlay, 1, 1, 1, transparency);
                    matrixStack.translate(0, 0, -0.014999999664723873);
                    matrixStack.scale(1.05F, 1.05F, 1.05F);
                    renderBox(this.jacket, matrixStack, translucentBuffer, packedLight, packedOverlay, 1, 1, 1, transparency);
                }

                if (!entity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
                    ItemStack chestStack = entity.getItemBySlot(EquipmentSlotType.CHEST);
                    ResourceLocation armorTexture = this.getArmorResource(entity, chestStack, EquipmentSlotType.CHEST, null);
                    if (chestStack.getItem().is(BMItemTags.FULLY_LIT_ITEMS)) packedLight = LightTexture.pack(15, 15);
                    if (armorTexture != null && chestStack.getItem().is(BMItemTags.OUTFITS)) {
                        matrixStack.pushPose();

                        RenderType armorType = RenderType.entityTranslucent(armorTexture);
                        IVertexBuilder armorBuffer = buffer.getBuffer(armorType);
                        matrixStack.scale(0.9997F, 1.03F, 1.02F);
                        renderBox(this.outfitChest, matrixStack, armorBuffer, packedLight, packedOverlay, 1, 1, 1, transparency);

                        matrixStack.translate(0, 0, -0.014999999664723873);
                        matrixStack.scale(1.06F, 1.07F, 1.06F);
                        renderBox(this.outfitJacket, matrixStack, armorBuffer, packedLight, packedOverlay, 1, 1, 1, transparency);
                        matrixStack.popPose();
                    } else if (armorTexture != null && chestStack.getItem() instanceof ArmorItem) {
                        matrixStack.pushPose();
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

                            matrixStack.translate(0, 0.014999999664723873, -0.014999999664723873);
                            matrixStack.scale(1.05F, 1, 1);
                            RenderType armorType = RenderType.entityTranslucent(armorTexture);
                            IVertexBuilder armorBuffer = buffer.getBuffer(armorType);
                            renderBox(this.chestplate, matrixStack, armorBuffer, packedLight, packedOverlay, red, green, blue, transparency);
                            if (chestStack.hasFoil()) {
                                RenderType glintType = RenderType.entityGlint();
                                IVertexBuilder glintBuffer = buffer.getBuffer(glintType);
                                renderBox(this.chestplate, matrixStack, glintBuffer, packedLight, packedOverlay, 1, 1, 1, transparency);
                            }
                            matrixStack.popPose();
                        }
                    }
                }
                matrixStack.popPose();
            }
            GL11.glColor3f(1, 1, 1);
        }
    }

    public void pushMatrix(MatrixStack stack, ModelRenderer renderer) {
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

    public float getTransparency(LivingEntity entity) {
        float alpha = 1;
        boolean isInvisible = entity.isInvisible() && !entity.isInvisibleTo(Minecraft.getInstance().player);
        if (isInvisible) {
            alpha = 0.15F;
        } else if (entity.isInvisible()) {
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
