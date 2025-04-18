package melonystudios.backmath.item.behavior.effecttype;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

// there's a non-zero chance I make this data-driven like consumable behaviors in the future ~isa 24-1-25
public abstract class ItemBehaviorEffectType {
    public abstract void runBehavior(ItemStack stack, Player attacker, LivingEntity target, Level world);

    public List<Component> addToTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        return tooltip;
    }
}
