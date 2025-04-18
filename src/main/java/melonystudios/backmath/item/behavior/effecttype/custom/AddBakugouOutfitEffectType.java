package melonystudios.backmath.item.behavior.effecttype.custom;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.util.BMUtils;
import melonystudios.backmath.util.RVUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AddBakugouOutfitEffectType extends ItemBehaviorEffectType {
    @Override
    public void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        BMUtils.addBakugouOutfit(target);
    }

    @Override
    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.addToTooltip(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.neutral_effect", Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.bakugou_outfit").withStyle(RVUtils.getFromRGB(0xFF8A50)))
                .withStyle(RVUtils.getFromRGB(0xCD473B)));
        return tooltip;
    }
}
