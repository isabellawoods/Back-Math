package com.sophicreeper.backmath.item.custom;

import com.sophicreeper.backmath.BackMath;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class UpgradeTokenItem extends Item {
    private final String upgradeToken;

    public UpgradeTokenItem(Properties properties, String upgradeToken) {
        super(properties);
        this.upgradeToken = upgradeToken;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("upgrade_token." + BackMath.MOD_ID + "." + upgradeToken).withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".empty"));
        tooltip.add(new TranslationTextComponent("item." + BackMath.MOD_ID + ".upgrade_token.applies_to").withStyle(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item." + BackMath.MOD_ID + ".upgrade_token_" + upgradeToken + ".applies_to").withStyle(TextFormatting.BLUE));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
