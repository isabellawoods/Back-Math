package melonystudios.backmath.item.behavior.effecttype.custom;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DamageEntityEffectType extends ItemBehaviorEffectType {
    @Nullable
    private final DamageSource source;
    private final float amount;

    public DamageEntityEffectType(@Nullable DamageSource source, float amount) {
        this.source = source;
        this.amount = amount;
    }

    public DamageEntityEffectType() {
        this(null, 0);
    }

    @Override
    public void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        if (this.source != null) target.hurt(this.source, this.amount);
    }

    @Override
    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.addToTooltip(stack, context, tooltip, flag);
        if (this.source != null) {
            MutableComponent component = Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior." + this.source.getMsgId(), this.amount).withStyle(ChatFormatting.GRAY);
            tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.harmful_effect", component).withStyle(ChatFormatting.DARK_GRAY));
        }
        return tooltip;
    }
}
