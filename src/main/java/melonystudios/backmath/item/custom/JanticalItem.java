package melonystudios.backmath.item.custom;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.util.BMKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class JanticalItem extends Item {
    public JanticalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (!BMKeys.isShiftDown()) tooltip.add(Component.translatable("tooltip.backmath.hold_shift", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
        if (BMKeys.isShiftDown()) {
            tooltip.add(Component.translatable("tooltip.backmath.hold_shift", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.translatable("tooltip.backmath.empty"));
            tooltip.add(Component.translatable("tooltip."  + BackMath.MOD_ID + ".jantical"));
        }
    }
}
