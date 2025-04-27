package com.sophicreeper.backmath.item.custom.food;

import com.sophicreeper.backmath.item.behavior.ItemBehavior;
import com.sophicreeper.backmath.item.behavior.ItemBehaviorParameters;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

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
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity livEntity) {
        ItemStack superStack = super.finishUsingItem(stack, world, livEntity);
        return livEntity instanceof PlayerEntity && ((PlayerEntity) livEntity).abilities.instabuild ? superStack : this.getFoodUseRemainder(stack, new ItemStack(Items.STICK));
    }
}
