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

import javax.annotation.Nonnull;

public class EnderDragonFriedEggBagItem extends Item {
    public EnderDragonFriedEggBagItem(Properties properties) {
        super(properties);
        DispenserBlock.registerDispenseBehavior(this, new BagDispenseBehavior());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, @Nonnull PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        player.addItemStackToInventory(new ItemStack(AxolotlTest.EMPTY_ENDER_DRAGON_FRIED_EGG_BAG.get()));
        player.addItemStackToInventory(new ItemStack(AxolotlTest.ENDER_DRAGON_FRIED_EGG.get()));
        heldItem.shrink(1);
        return super.onItemRightClick(world, player, hand);
    }
}
