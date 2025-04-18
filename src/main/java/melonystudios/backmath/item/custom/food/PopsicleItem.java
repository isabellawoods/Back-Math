package melonystudios.backmath.item.custom.food;

import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.ItemBehaviorParameters;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PopsicleItem extends BMFoodItem {
    public PopsicleItem(Properties properties) {
        super(ItemBehaviorParameters.POPSICLE, properties);
    }

    public PopsicleItem(Supplier<ItemBehavior> behavior, Properties properties) {
        super(ItemBehaviorParameters.POPSICLE, behavior, properties);
    }

    @Override
    @Nonnull
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livEntity) {
        ItemStack superStack = super.finishUsingItem(stack, world, livEntity);
        return livEntity instanceof Player && ((Player) livEntity).getAbilities().instabuild ? superStack : this.getFoodUseRemainder(stack, new ItemStack(Items.STICK));
    }
}
