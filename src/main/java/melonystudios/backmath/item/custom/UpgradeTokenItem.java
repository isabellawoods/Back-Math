package melonystudios.backmath.item.custom;

import melonystudios.backmath.BackMath;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class UpgradeTokenItem extends Item {
    private final String upgradeToken;

    public UpgradeTokenItem(Properties properties, String upgradeToken) {
        super(properties);
        this.upgradeToken = upgradeToken;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("upgrade_token." + BackMath.MOD_ID + "." + this.upgradeToken).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".empty"));
        tooltip.add(Component.translatable("item." + BackMath.MOD_ID + ".upgrade_token.applies_to").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item." + BackMath.MOD_ID + ".upgrade_token_" + this.upgradeToken + ".applies_to").withStyle(ChatFormatting.GOLD));
    }
}
