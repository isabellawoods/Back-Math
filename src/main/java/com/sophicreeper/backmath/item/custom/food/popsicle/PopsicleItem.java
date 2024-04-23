package com.sophicreeper.backmath.item.custom.food.popsicle;

import com.sophicreeper.backmath.item.tab.SophiesCursedFoods;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class PopsicleItem extends Item {
    public PopsicleItem(Food food) {
        super(new Properties().tab(SophiesCursedFoods.TAB).food(food));
    }

    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity livEntity) {
        ItemStack stack1 = super.finishUsingItem(stack, world, livEntity);
        return livEntity instanceof PlayerEntity && ((PlayerEntity) livEntity).abilities.instabuild ? stack1 : new ItemStack(Items.STICK);
    }
}
