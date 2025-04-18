package melonystudios.backmath.item.custom;

import melonystudios.backmath.item.AxolotlTest;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class SleepishwaterBottleItem extends Item implements UseRemainders {
    public SleepishwaterBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    @Override
    @Nonnull
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livEntity) {
        super.finishUsingItem(stack, world, livEntity);
        if (livEntity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty()) {
            return this.getFoodUseRemainder(stack, new ItemStack(AxolotlTest.ALJAMIC_GLASS_BOTTLE.get()));
        } else {
            if (livEntity instanceof Player player && !player.getAbilities().instabuild) {
                ItemStack bottleStack = this.getFoodUseRemainder(stack, this.getFoodUseRemainder(stack, new ItemStack(AxolotlTest.ALJAMIC_GLASS_BOTTLE.get())));
                stack.shrink(1);
                if (!player.getInventory().add(bottleStack)) player.drop(bottleStack, false);
            }
            return stack;
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livEntity) {
        return 32;
    }

    @Override
    @Nonnull
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    @Nonnull
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }
}
