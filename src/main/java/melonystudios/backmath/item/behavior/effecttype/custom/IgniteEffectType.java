package melonystudios.backmath.item.behavior.effecttype.custom;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.util.RVUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class IgniteEffectType extends ItemBehaviorEffectType {
    private final int ticks;

    public IgniteEffectType(int ticks) {
        this.ticks = ticks;
    }

    public IgniteEffectType() {
        this(100);
    }

    @Override
    public void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        if (!target.isInvulnerableTo(world.damageSources().inFire()) || !target.isInvulnerableTo(world.damageSources().onFire())) {
            Integer ticksOnFire = stack.get(BMDataComponents.TICKS_ON_FIRE);
            target.setRemainingFireTicks(Objects.requireNonNullElse(ticksOnFire, this.ticks));
        }
    }

    @Override
    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.addToTooltip(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.harmful_effect", Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.ablaze",
                StringUtil.formatTickDuration(this.ticks, context.tickRate())).withStyle(RVUtils.getFromRGB(0xE1A61E))).withStyle(ChatFormatting.DARK_GRAY));
        return tooltip;
    }
}
