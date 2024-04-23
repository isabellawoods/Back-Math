package com.sophicreeper.backmath.item.custom.food;

import com.sophicreeper.backmath.block.dispenser.BagDispenseBehavior;
import com.sophicreeper.backmath.item.AxolotlTest;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LagustaBagItem extends Item {
    public LagustaBagItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, new BagDispenseBehavior());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        player.addItem(new ItemStack(AxolotlTest.EMPTY_LAGUSTA_BAG.get()));
        player.addItem(new ItemStack(AxolotlTest.LAGUSTA.get()));
        heldItem.shrink(1);
        return super.use(world, player, hand);
    }
}
