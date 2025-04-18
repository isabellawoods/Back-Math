package melonystudios.backmath.item.custom;

import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.component.custom.UseRemainder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface UseRemainders {
    default ItemStack getFoodUseRemainder(ItemStack stack) {
        return this.getFoodUseRemainder(stack, new ItemStack(Items.GLASS_BOTTLE));
    }

    default ItemStack getFoodUseRemainder(ItemStack stack, ItemStack defaultStack) {
        UseRemainder remainder = stack.get(BMDataComponents.USE_REMAINDER);
        return remainder != null && remainder.remainderStack() != null ? remainder.remainderStack() : defaultStack;
    }
}
