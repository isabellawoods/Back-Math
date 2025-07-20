package com.sophicreeper.backmath.item.custom;

import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.revaried.RVUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public interface UseRemainders {
    default ItemStack getFoodUseRemainder(ItemStack stack) {
        return getFoodUseRemainder(stack, new ItemStack(Items.GLASS_BOTTLE));
    }

    default ItemStack getFoodUseRemainder(ItemStack stack, ItemStack defaultStack) {
        ItemStack containerStack = defaultStack.copy();
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("use_remainder", TagTypes.STRING)) {
            ResourceLocation itemLocation = ResourceLocation.tryParse(tag.getString("use_remainder"));
            if (itemLocation != null && ForgeRegistries.ITEMS.containsKey(itemLocation)) {
                ItemStack tagStack = new ItemStack(ForgeRegistries.ITEMS.getValue(itemLocation));
                tag.remove("use_remainder");
                tag.put("use_remainder", RVUtils.saveStack(tagStack, new CompoundNBT()));
                if (!tagStack.isEmpty()) return tagStack;
            }
        }
        if (tag != null && tag.contains("use_remainder", TagTypes.COMPOUND)) {
            ItemStack tagStack = RVUtils.loadStack(tag.getCompound("use_remainder"));
            if (!tagStack.isEmpty()) return tagStack;
        }
        return containerStack;
    }
}
