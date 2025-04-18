package melonystudios.backmath.item.behavior.effecttype.custom;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.util.RVUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class AddExperienceEffectType extends ItemBehaviorEffectType {
    private final int defaultAmount;

    public AddExperienceEffectType(int defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    @Override
    public void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        Integer storedExperience = stack.get(BMDataComponents.STORED_EXPERIENCE);
        attacker.giveExperiencePoints(Objects.requireNonNullElse(storedExperience, this.defaultAmount));
    }

    @Override
    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.addToTooltip(stack, context, tooltip, flag);
        Integer storedExperience = stack.get(BMDataComponents.STORED_EXPERIENCE);
        MutableComponent component = Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.experience", Objects.requireNonNullElse(storedExperience, this.defaultAmount)).withStyle(RVUtils.getFromRGB(8453920));
        tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".behavior.beneficial_effect", component).withStyle(RVUtils.getFromRGB(0x588039)));
        return tooltip;
    }
}
