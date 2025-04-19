package melonystudios.backmath.item.behavior.effecttype.custom;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.component.custom.MilkedSwordItem;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.util.RVUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GiveMilkedSwordItemEffectType extends ItemBehaviorEffectType {
    private final ItemStack bucketStack;
    private final boolean milkedSwordsEnabled = true;

    public GiveMilkedSwordItemEffectType(ItemStack bucketStack) {
        this.bucketStack = bucketStack;
    }

    public GiveMilkedSwordItemEffectType() {
        this(new ItemStack(Items.MILK_BUCKET));
    }

    @Override
    public void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        if (this.milkedSwordsEnabled) {
            MilkedSwordItem swordItem = stack.get(BMDataComponents.MILKED_SWORD_ITEM);
            ItemStack bucketStack = this.bucketStack.copy();
            if (swordItem != null && swordItem.milkedStack() != null) bucketStack = swordItem.milkedStack().copy();
            if (!attacker.getInventory().add(bucketStack)) attacker.drop(bucketStack, false);
        }
    }

    @Override
    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.addToTooltip(stack, context, tooltip, flag);
        if (this.bucketStack != null) {
            MutableComponent component = Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.give_milked_sword_item", this.bucketStack.getCount(), this.bucketStack.getHoverName()).withStyle(RVUtils.getFromRGB(0xBDDCE4));
            tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.neutral_effect", component).withStyle(RVUtils.getFromRGB(0x7C979E)));
        }
        return tooltip;
    }
}
