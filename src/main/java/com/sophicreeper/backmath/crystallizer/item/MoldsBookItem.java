package com.sophicreeper.backmath.crystallizer.item;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.dispenser.CrystallizerMoldDispenseBehavior;
import com.sophicreeper.backmath.util.BMKeyBindings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MoldsBookItem extends Item {
    public MoldsBookItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, new CrystallizerMoldDispenseBehavior("book"));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (!BMKeyBindings.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
        if (BMKeyBindings.isShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.WHITE)).withStyle(TextFormatting.DARK_GRAY));
            tooltip.add(new TranslationTextComponent("tooltip.backmath.empty"));
            tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + "." + this.getRegistryName().getPath()));
        }
    }
}
