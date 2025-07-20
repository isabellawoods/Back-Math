package com.sophicreeper.backmath.item.custom;

import com.sophicreeper.backmath.util.BMKeyBindings;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class CrystallizerItem extends BlockItem {
    public CrystallizerItem(RegistryObject<Block> block, Properties properties) {
        super(block.get(), properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (!BMKeyBindings.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
        if (BMKeyBindings.isShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.WHITE)).withStyle(TextFormatting.DARK_GRAY));
            tooltip.add(new TranslationTextComponent("tooltip.backmath.empty"));
            tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".desc"));
        }
    }
}
