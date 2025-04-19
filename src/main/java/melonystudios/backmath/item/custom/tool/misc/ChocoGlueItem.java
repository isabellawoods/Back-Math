package melonystudios.backmath.item.custom.tool.misc;

import melonystudios.backmath.misc.BMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ChocoGlueItem extends Item {
    public ChocoGlueItem(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), BMSounds.CHOCOGLUE_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            /*ChocoGlueEntity chocoGlue = new ChocoGlueEntity(world, player);
            chocoGlue.setItem(handStack);
            chocoGlue.shootFromRotation(player, player.xRot, player.yRot, 0, 1.5F, 1);
            world.addFreshEntity(chocoGlue);*/
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) handStack.shrink(1);
        return InteractionResultHolder.sidedSuccess(handStack, world.isClientSide());
    }
}
