package melonystudios.backmath.item.custom.tool;

import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.custom.behavior.BMSwordItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ButterSwordItem extends BMSwordItem {
    public ButterSwordItem(Supplier<ItemBehavior> behavior, Tier tier, int attackDamage, float swingSpeed, Properties properties) {
        super(behavior, tier, attackDamage, swingSpeed, properties);
        this.cancelAttackBehavior = true;
    }

    @Override
    @Nonnull
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livEntity) {
        if (livEntity instanceof Player player) this.getBehavior().get().run(stack, player, livEntity, world);
        return super.finishUsingItem(stack, world, livEntity);
    }
}
