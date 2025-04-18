package melonystudios.backmath.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class AljamicGlassBottleItem extends Item {
    public AljamicGlassBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() == BlockHitResult.Type.MISS) {
            return InteractionResultHolder.pass(heldItem);
        } else {
            if (hitResult.getType() == BlockHitResult.Type.BLOCK) {
                BlockPos hitResultPos = hitResult.getBlockPos();
                if (!world.mayInteract(player, hitResultPos)) {
                    return InteractionResultHolder.pass(heldItem);
                }

                /*if (world.getFluidState(hitResultPos).is(BMFluidTags.SLEEPISHWATER)) {
                    world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1, 1);
                    return InteractionResultHolder.sidedSuccess(turnBottleIntoItem(heldItem, player, new ItemStack(AxolotlTest.SLEEPISHWATER_BOTTLE.get())), world.isClientSide);
                }*/
            }
            return super.use(world, player, hand);
        }
    }

    protected ItemStack turnBottleIntoItem(ItemStack bottleStack, Player player, ItemStack filledBottleStack) {
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.createFilledResult(bottleStack, player, filledBottleStack);
    }
}
