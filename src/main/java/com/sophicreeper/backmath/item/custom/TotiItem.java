package com.sophicreeper.backmath.item.custom;

import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.util.BMKeyBindings;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.util.BMUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TotiItem extends BlockItem {
    public TotiItem(Properties properties) {
        super(BMBlocks.TOTI.get(), properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent("tooltip.backmath.can_be_placed").withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.GRAY));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (!world.isClientSide && BMKeyBindings.isVanillaShiftDown()) {
            player.addItem(new ItemStack(AxolotlTest.TITO.get()));
            handStack.shrink(1);
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                BMUtils.playItemPickupSound(serverPlayer);
            }
            return ActionResult.success(handStack);
        }
        return super.use(world, player, hand);
    }
}
