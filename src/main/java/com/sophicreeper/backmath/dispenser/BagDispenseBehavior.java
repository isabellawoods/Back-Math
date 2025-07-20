package com.sophicreeper.backmath.dispenser;

import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.item.custom.BagItem;
import com.sophicreeper.backmath.loot.LootTableUtils;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import java.util.Collection;

public class BagDispenseBehavior extends DefaultDispenseItemBehavior {
    @Override
    @Nonnull
    protected ItemStack execute(IBlockSource source, ItemStack stack) {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        IPosition pos = DispenserBlock.getDispensePosition(source);
        Collection<ItemStack> lootTableDrops = this.getLootTableDrops(stack, source);
        if (lootTableDrops.isEmpty()) LogManager.getLogger().warn(new TranslationTextComponent("backmath.message_template", new TranslationTextComponent("error.backmath.bag.no_drops",
                    stack.getItem().getRegistryName(), getLootTable(stack))).getString());
        lootTableDrops.forEach(stack1 -> spawnItem(source.getLevel(), stack1, 6, direction, pos));
        stack.shrink(1);
        return stack;
    }

    protected ResourceLocation getLootTable(ItemStack stack) {
        if (stack.getItem() == AxolotlTest.AMARACAMEL_BATTER_BAG.get()) {
            return BMResourceLocations.AMARACAMEL_BATTER_BAG;
        } else if (stack.getItem() == AxolotlTest.BOOT_PACK.get()) {
            return BMResourceLocations.BOOT_PACK;
        } else if (stack.getItem() instanceof BagItem) {
            BagItem bagItem = (BagItem) stack.getItem();
            CompoundNBT tag = stack.getTag();
            if (tag != null && tag.contains("loot_table", TagTypes.STRING)) {
                ResourceLocation tableLoc = ResourceLocation.tryParse(tag.getString("loot_table"));
                if (tableLoc != null) return tableLoc;
            } else return bagItem.getDefaultLootTable();
        }
        return LootTables.EMPTY;
    }

    protected Collection<ItemStack> getLootTableDrops(ItemStack stack, IBlockSource source) {
        return LootTableUtils.giftFromDispenser(this.getLootTable(stack), source);
    }
}
