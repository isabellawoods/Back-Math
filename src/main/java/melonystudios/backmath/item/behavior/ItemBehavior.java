package melonystudios.backmath.item.behavior;

import com.mojang.logging.LogUtils;
import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.misc.BMRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class ItemBehavior {
    public final List<Supplier<ItemBehaviorEffectType>> effects;
    private Integer durabilityBarColor;
    private boolean hasGlint;

    public ItemBehavior(List<Supplier<ItemBehaviorEffectType>> effects) {
        this.effects = effects;
    }

    public void run(ItemStack stack, Player attacker, LivingEntity target, Level world) {
        for (Supplier<ItemBehaviorEffectType> type : this.effects) {
            if (type.get() != null) type.get().runBehavior(stack, attacker, target, world);
            else LogUtils.getLogger().error(Component.translatable("error.backmath.no_item_behavior_effect_type", BMRegistries.ITEM_BEHAVIOR.getKey(this)).getString());
        }
    }

    public ItemBehavior durabilityBarColor(Integer color) {
        this.durabilityBarColor = color;
        return this;
    }

    public ItemBehavior glint(boolean glint) {
        this.hasGlint = glint;
        return this;
    }

    public Integer getDurabilityBarColor(ItemStack stack, int oldColor) {
        Integer colorOverride = stack.get(BMDataComponents.DURABILITY_BAR_COLOR);
        return colorOverride != null ? colorOverride : this.durabilityBarColor != null ? this.durabilityBarColor : oldColor;
    }

    public boolean hasGlint(ItemStack stack) {
        Boolean glintOverride = stack.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        return glintOverride != null ? glintOverride : this.hasGlint;
    }
}
