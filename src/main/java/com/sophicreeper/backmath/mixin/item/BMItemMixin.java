package com.sophicreeper.backmath.mixin.item;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.outfit.OutfitProvider;
import com.sophicreeper.backmath.util.BMKeyBindings;
import com.sophicreeper.backmath.util.RVUtils;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class BMItemMixin implements OutfitProvider {
    @OnlyIn(Dist.CLIENT)
    @Inject(method = "appendHoverText", at = @At("HEAD")) // I can't live without Revaried's item tag tooltip
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag, CallbackInfo callback) {
        if (flag.isAdvanced() && stack.getTag() != null && Minecraft.getInstance().getLaunchedVersion().contains("melony-studios-dev")) {
            if (!BMKeyBindings.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
            if (BMKeyBindings.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.WHITE)).withStyle(TextFormatting.DARK_GRAY));
            if (BMKeyBindings.isShiftDown()) RVUtils.addItemTagsTooltip(stack, tooltip, flag);
        }
    }

    @Override
    public ResourceLocation getOutfitDefinition(ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        return tag != null && tag.contains("outfit", TagTypes.STRING) ? new ResourceLocation(tag.getString("outfit")) : null;
    }
}
