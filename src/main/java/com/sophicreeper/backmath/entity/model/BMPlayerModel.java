package com.sophicreeper.backmath.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.entity.custom.termian.TermianMemberEntity;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class BMPlayerModel<T extends LivingEntity> extends BipedModel<T> {
    public final ModelRenderer leftSleeve;
    public final ModelRenderer rightSleeve;
    public final ModelRenderer leftPants;
    public final ModelRenderer rightPants;
    public final ModelRenderer jacket;
    private final ModelRenderer cape;
    private final boolean slimArms;

    public BMPlayerModel(float modelSize, float yOffset, int textureWidth, int textureHeight, boolean slimArms) {
        super(RenderType::entityTranslucent, modelSize, yOffset, textureWidth, textureHeight);
        this.slimArms = slimArms;

        this.cape = new ModelRenderer(this, 0, 0);
        this.cape.setTexSize(64, 32);
        this.cape.addBox(-5, 0, -1, 10, 16, 1, modelSize);

        if (slimArms) {
            this.leftArm = new ModelRenderer(this, 32, 48);
            this.leftArm.addBox(-1, -2, -2, 3, 12, 4, modelSize);
            this.leftArm.setPos(5, 2.5F + yOffset, 0);
            this.rightArm = new ModelRenderer(this, 40, 16);
            this.rightArm.addBox(-2, -2, -2, 3, 12, 4, modelSize);
            this.rightArm.setPos(-5, 2.5F + yOffset, 0);
            this.leftSleeve = new ModelRenderer(this, 48, 48);
            this.leftSleeve.addBox(-1, -2, -2, 3, 12, 4, modelSize + 0.25F);
            this.leftSleeve.setPos(5, 2.5F + yOffset, 0);
            this.rightSleeve = new ModelRenderer(this, 40, 32);
            this.rightSleeve.addBox(-2, -2, -2, 3, 12, 4, modelSize + 0.25F);
            this.rightSleeve.setPos(-5, 2.5F + yOffset, 10);
        } else {
            this.leftArm = new ModelRenderer(this, 32, 48);
            this.leftArm.addBox(-1, -2, -2, 4, 12, 4, modelSize);
            this.leftArm.setPos(5, 2 + yOffset, 0);
            this.leftSleeve = new ModelRenderer(this, 48, 48);
            this.leftSleeve.addBox(-1, -2, -2, 4, 12, 4, modelSize + 0.25F);
            this.leftSleeve.setPos(5, 2 + yOffset, 0);
            this.rightSleeve = new ModelRenderer(this, 40, 32);
            this.rightSleeve.addBox(-3, -2, -2, 4, 12, 4, modelSize + 0.25F);
            this.rightSleeve.setPos(-5, 2 + yOffset, 10);
        }

        this.leftLeg = new ModelRenderer(this, 16, 48);
        this.leftLeg.addBox(-2, 0, -2, 4, 12, 4, modelSize);
        this.leftLeg.setPos(1.9F, 12 + yOffset, 0);
        this.leftPants = new ModelRenderer(this, 0, 48);
        this.leftPants.addBox(-2, 0, -2, 4, 12, 4, modelSize + 0.25F);
        this.leftPants.setPos(1.9F, 12 + yOffset, 0);
        this.rightPants = new ModelRenderer(this, 0, 32);
        this.rightPants.addBox(-2, 0, -2, 4, 12, 4, modelSize + 0.25F);
        this.rightPants.setPos(-1.9F, 12 + yOffset, 0);
        this.jacket = new ModelRenderer(this, 16, 32);
        this.jacket.addBox(-4, 0, -2, 8, 12, 4, modelSize + 0.25F);
        this.jacket.setPos(0, 0 + yOffset, 0);
    }

    public boolean slimArms() {
        return this.slimArms;
    }

    @Nonnull
    protected Iterable<ModelRenderer> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.rightPants, this.leftPants, this.rightSleeve, this.leftSleeve, this.jacket));
    }

    public void renderCape(MatrixStack stack, IVertexBuilder buffer, int packedLight, int packedOverlay) {
        this.cape.render(stack, buffer, packedLight, packedOverlay);
    }

    // Sets this entity's model rotation angles.
    @Override
    public void setupAnim(T mob, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(mob, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftSleeve.copyFrom(this.leftArm);
        this.jacket.copyFrom(this.body);

        if (mob.getItemBySlot(EquipmentSlotType.CHEST).isEmpty() || mob.getItemBySlot(EquipmentSlotType.CHEST).getItem().is(BMItemTags.OUTFITS)) {
            if (mob.isCrouching()) {
                this.cape.z = 1.4F;
                this.cape.y = 1.85F;
            } else {
                this.cape.z = 0;
                this.cape.y = 0;
            }
        } else if (mob.isCrouching()) {
            this.cape.z = 0.3F;
            this.cape.y = 0.8F;
        } else {
            this.cape.z = -1.1F;
            this.cape.y = -0.85F;
        }

        if (mob instanceof TermianMemberEntity) {
            TermianMemberEntity memberEntity = (TermianMemberEntity) mob;
            TermianMemberEntity.ArmPose armPose = memberEntity.getArmPose();
            if (armPose == TermianMemberEntity.ArmPose.CASTING_SPELL) {
                this.rightArm.z = 0;
                this.rightArm.x = -5;
                this.rightSleeve.z = 0;
                this.rightSleeve.x = -5;
                this.leftArm.z = 0;
                this.leftArm.x = 5;
                this.leftSleeve.z = 0;
                this.leftSleeve.x = 5;
                this.rightArm.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
                this.rightSleeve.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
                this.leftArm.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
                this.leftSleeve.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
                this.rightArm.zRot = 2.3561945F;
                this.rightSleeve.zRot = 2.3561945F;
                this.leftArm.zRot = -2.3561945F;
                this.leftSleeve.zRot = -2.3561945F;
                this.rightArm.yRot = 0;
                this.rightSleeve.yRot = 0;
                this.leftArm.yRot = 0;
                this.leftSleeve.yRot = 0;
            }
        }
    }

    @Override
    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
        this.cape.visible = visible;
    }

    @Override
    public void translateToHand(HandSide hand, MatrixStack stack) {
        ModelRenderer renderer = this.getArm(hand);
        if (this.slimArms) {
            float f = 0.5F * (float) (hand == HandSide.RIGHT ? 1 : -1);
            renderer.x += f;
            renderer.translateAndRotate(stack);
            renderer.x -= f;
        } else {
            renderer.translateAndRotate(stack);
        }
    }
}
