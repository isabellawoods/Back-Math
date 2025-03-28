package com.sophicreeper.backmath.block.custom.variants;

import com.sophicreeper.backmath.util.RVUtils;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CustomBeamGlassBlock extends AbstractGlassBlock implements IBeaconBeamColorProvider {
    private final int beamColor;

    public CustomBeamGlassBlock(int beamColor, Properties properties) {
        super(properties);
        this.beamColor = beamColor;
    }

    @Nullable
    @Override
    public float[] getBeaconColorMultiplier(BlockState state, IWorldReader world, BlockPos pos, BlockPos beaconPos) {
        int red = (this.beamColor & 16711680) >> 16;
        int green = (this.beamColor & '\uff00') >> 8;
        int blue = (this.beamColor & 255);

        float[] textureDiffuseColors = new float[] {(float) red / 255, (float) green / 255, (float) blue / 255};

        if (this.getBlock() instanceof IBeaconBeamColorProvider) return textureDiffuseColors;
        return null;
    }

    @Override
    @Nonnull
    public DyeColor getColor() {
        return DyeColor.WHITE;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (flag.isAdvanced() && ModList.get().isLoaded("variants")) tooltip.add(new TranslationTextComponent("tooltip.variants.glass_beam_color", new StringTextComponent(String.format("#%06X", this.beamColor)).withStyle(RVUtils.getFromRGB(this.beamColor)))
                .withStyle(TextFormatting.GRAY));
    }
}
