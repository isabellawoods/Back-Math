package melonystudios.backmath.item.custom.armor;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.custom.behavior.BMArmorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class QueenLucyShirtItem extends BMArmorItem {
    private final String shirtDesign;

    public QueenLucyShirtItem(Holder<ArmorMaterial> material, Type slot, Properties properties, String shirtDesign) {
        super(material, slot, properties);
        this.shirtDesign = shirtDesign;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("item." + BackMath.MOD_ID + ".queen_lucy_shirt.design").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item." + BackMath.MOD_ID + ".queen_lucy_shirt_" + this.shirtDesign + ".desc").withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
