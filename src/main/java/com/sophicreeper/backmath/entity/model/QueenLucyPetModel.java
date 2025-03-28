package com.sophicreeper.backmath.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sophicreeper.backmath.entity.custom.QueenLucyPetEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/// Made with Blockbench 4.5.2. <p>
/// Exported for Minecraft version 1.15 - 1.16 with MCP mappings. <p>
/// Paste this class into your mod and generate all required imports. <p>
@OnlyIn(Dist.CLIENT)
public class QueenLucyPetModel extends BipedModel<QueenLucyPetEntity> {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer arms;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;
	private final ModelRenderer legs;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer wings;
	private final ModelRenderer rightWing;
	private final ModelRenderer rightWingR1;
	private final ModelRenderer leftWing;
	private final ModelRenderer leftWingR1;

	public QueenLucyPetModel() {
		super(0.25F, 0, 32, 32);
		this.texWidth = 32;
		this.texHeight = 32;

		this.head = new ModelRenderer(this);
		this.head.setPos(0, 12, 0);
		this.setRotationAngle(this.head, 0, 0, 0);
		this.head.texOffs(0, 0).addBox(-2, -4, -2, 4, 4, 4, 0, false);
		this.head.texOffs(0, 16).addBox(-1, -6, -1, 2, 2, 0, 0, false);
		this.head.texOffs(15, 14).addBox(-1, -6, -1, 0, 2, 2, 0, false);
		this.head.texOffs(8, 16).addBox(-1, -6, 1, 2, 2, 0, 0, false);
		this.head.texOffs(3, 14).addBox(1, -6, -1, 0, 2, 2, 0, false);

		this.body = new ModelRenderer(this);
		this.body.setPos(0, 24, 0);
		this.body.texOffs(8, 8).addBox(-2, -12, -1, 4, 6, 2, 0, false);

		this.arms = new ModelRenderer(this);
		this.arms.setPos(0, -11, 0);
		this.body.addChild(this.arms);

		this.rightArm = new ModelRenderer(this);
		this.rightArm.setPos(-2.5F, 0, 0);
		this.arms.addChild(this.rightArm);
		this.rightArm.texOffs(20, 8).addBox(-0.5F, -1, -1, 1, 6, 2, 0, false);

		this.leftArm = new ModelRenderer(this);
		this.leftArm.setPos(2.5F, 0, 0);
		this.arms.addChild(this.leftArm);
		this.leftArm.texOffs(20, 8).addBox(-0.5F, -1, -1, 1, 6, 2, 0, true);

		this.legs = new ModelRenderer(this);
		this.legs.setPos(0, -6, 0);
		this.body.addChild(this.legs);

		this.rightLeg = new ModelRenderer(this);
		this.rightLeg.setPos(-1, 0, 0);
		this.legs.addChild(this.rightLeg);
		this.rightLeg.texOffs(0, 8).addBox(-1, 0, -1, 2, 6, 2, 0, false);

		this.leftLeg = new ModelRenderer(this);
		this.leftLeg.setPos(1, 0, 0);
		this.legs.addChild(this.leftLeg);
		this.leftLeg.texOffs(0, 8).addBox(-1, 0, -1, 2, 6, 2, 0, true);

		this.wings = new ModelRenderer(this);
		this.wings.setPos(0, -9, 0);
		this.body.addChild(this.wings);

		this.rightWing = new ModelRenderer(this);
		this.rightWing.setPos(0, 0, 0);
		this.wings.addChild(this.rightWing);

		this.rightWingR1 = new ModelRenderer(this);
		this.rightWingR1.setPos(0, 0, 0);
		this.rightWing.addChild(this.rightWingR1);
		this.setRotationAngle(this.rightWingR1, 0, 0.4363F, 0);
		this.rightWingR1.texOffs(0, 20).addBox(-7, -3, 1, 7, 6, 0, 0, false);

		this.leftWing = new ModelRenderer(this);
		this.leftWing.setPos(0, 0, 0);
		this.wings.addChild(this.leftWing);

		this.leftWingR1 = new ModelRenderer(this);
		this.leftWingR1.setPos(0, 0, 0);
		this.leftWing.addChild(this.leftWingR1);
		this.setRotationAngle(this.leftWingR1, 0, -0.4363F, 0);
		this.leftWingR1.texOffs(0, 26).addBox(0, -3, 1, 7, 6, 0, 0, true);
	}

	@Nonnull
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Nonnull
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightArm, this.leftArm, this.rightWing, this.leftWing);
	}

	/// Sets this queen lucy pet's model rotation angles.
	@Override
	public void setupAnim(QueenLucyPetEntity lucy, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.head.yRot = headYaw * 0.017453292f;
		this.head.xRot = headPitch * 0.017453292f;

		this.body.yRot = 0;

		this.rightArm.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2 * limbSwingAmount * 0.5F;
		this.leftArm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2 * limbSwingAmount * 0.5F;
		this.rightArm.zRot = 0;
		this.leftArm.zRot = 0;
		this.rightLeg.yRot = 0;
		this.leftLeg.yRot = 0;
		this.rightLeg.zRot = 0;
		this.leftLeg.zRot = 0;

		ModelRenderer renderer;
		if (this.riding || lucy.isOrderedToSit()) {
			renderer = this.rightArm;
			renderer.xRot -= 0.62831855F;
			renderer = this.leftArm;
			renderer.xRot -= 0.62831855F;
			this.rightArm.xRot += (-(float) Math.PI / 5F);
			this.leftArm.xRot += (-(float) Math.PI / 5F);
			this.rightLeg.xRot = -1.4137167F;
			this.rightLeg.yRot = 0.31415927F;
			this.rightLeg.zRot = 0.07853982F;
			this.leftLeg.xRot = -1.4137167F;
			this.leftLeg.yRot = -0.31415927F;
			this.leftLeg.zRot = -0.07853982F;
		}

		this.setupAttackAnimation(lucy, ageInTicks);

		this.rightArm.yRot = 0;
		this.leftArm.yRot = 0;

		ModelHelper.bobArms(this.rightArm, this.leftArm, ageInTicks);

		this.rightLeg.z = 0.1F;
		this.leftLeg.z = 0.1F;
		this.rightLeg.y = 0;
		this.leftLeg.y = 0;
		this.head.y = 12;
		this.body.y = 24;
		this.rightArm.y = 0;
		this.leftArm.y = 0;
		super.setupAnim(lucy, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
	}

	@Override
	public void renderToBuffer(MatrixStack stack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(stack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		this.body.render(stack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer renderer, float x, float y, float z) {
		renderer.xRot = x;
		renderer.yRot = y;
		renderer.zRot = z;
	}
}