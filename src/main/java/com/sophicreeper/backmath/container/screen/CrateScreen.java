package com.sophicreeper.backmath.container.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.container.custom.CrateContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class CrateScreen extends ContainerScreen<CrateContainer> implements IHasContainer<CrateContainer> {
    private final int containerRows;

    public CrateScreen(CrateContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.passEvents = false;
        this.containerRows = 2;
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);
        if (this.minecraft != null) this.minecraft.getTextureManager().bind(BackMath.texture("gui/container/crate"));
        int centerX = (this.width - this.imageWidth) / 2;
        int centerY = (this.height - this.imageHeight) / 2;
        this.blit(stack, centerX, centerY, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
        this.blit(stack, centerX, centerY + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}
