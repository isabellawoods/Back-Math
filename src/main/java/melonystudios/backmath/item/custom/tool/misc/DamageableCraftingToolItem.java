package melonystudios.backmath.item.custom.tool.misc;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DamageableCraftingToolItem extends Item {
    public DamageableCraftingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack containerStack = stack.copy();
        containerStack.setDamageValue(containerStack.getDamageValue() - 1);
        if (containerStack.getDamageValue() <= 0) {
            return ItemStack.EMPTY;
        } else {
            return containerStack;
        }
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }
}
