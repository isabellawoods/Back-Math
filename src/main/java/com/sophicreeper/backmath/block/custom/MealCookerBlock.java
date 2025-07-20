package com.sophicreeper.backmath.block.custom;

import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMStatistics;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class MealCookerBlock extends HorizontalBlock {
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(3, 23, 3, 13, 23, 13),
            Block.box(3, 17, 13, 13, 24, 14),
            Block.box(3, 17, 2, 13, 24, 3),
            Block.box(13, 17, 3, 14, 24, 13),
            Block.box(2, 17, 3, 3, 24, 13),
            Block.box(3, 16, 3, 13, 17, 13),
            Block.box(0, 0, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public MealCookerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, @Nonnull PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack mainHand = player.getItemInHand(Hand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(Hand.OFF_HAND);

        if (mainHand.getItem() == AxolotlTest.HOT_SOPHIE.get() && offHand.getItem() == AxolotlTest.COLD_FABRICIO.get()) {
            player.addItem(new ItemStack(AxolotlTest.HOT_SOPHIE_COLD_FABRICIO_MEAL.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            mainHand.shrink(1);
            offHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == AxolotlTest.FRIED_SOPHIE.get() && offHand.getItem() == AxolotlTest.FRIED_EGG.get()) {
            player.addItem(new ItemStack(AxolotlTest.FRIED_SOPHIE_FRIED_EGG_MEAL.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            mainHand.shrink(1);
            offHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == AxolotlTest.COOKED_LUCIA.get() && offHand.getItem() == AxolotlTest.FRIED_EGG.get()) {
            player.addItem(new ItemStack(AxolotlTest.COOKED_LUCIA_FRIED_EGG_MEAL.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            mainHand.shrink(1);
            offHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == AxolotlTest.TOASTED_FABRICIO.get() && offHand.getItem() == AxolotlTest.FRIED_EGG.get()) {
            player.addItem(new ItemStack(AxolotlTest.TOASTED_FABRICIO_FRIED_EGG_MEAL.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            mainHand.shrink(1);
            offHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == AxolotlTest.ALJAME_TEA.get()) {
            player.addItem(new ItemStack(AxolotlTest.ALJAME_TEACUP.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            mainHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == Items.BREAD && offHand.getItem() == AxolotlTest.FRIED_EGG.get()) {
            player.addItem(new ItemStack(AxolotlTest.FRIED_EGG_BREAD.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            mainHand.shrink(1);
            offHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == Items.BREAD && offHand.getItem() == AxolotlTest.TURTLE_FRIED_EGG.get()) {
            player.addItem(new ItemStack(AxolotlTest.TURTLE_FRIED_EGG_BREAD.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            mainHand.shrink(1);
            offHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == Items.BREAD && offHand.getItem() == AxolotlTest.ENDER_DRAGON_FRIED_EGG.get()) {
            player.addItem(new ItemStack(AxolotlTest.ENDER_DRAGON_FRIED_EGG_BREAD.get()));
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            mainHand.shrink(1);
            offHand.shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem() == AxolotlTest.ANGELIC_SPOON.get()) {
            player.awardStat(BMStatistics.INTERACT_WITH_MEAL_COOKER);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            addPasta(mainHand, new ItemStack(AxolotlTest.PASTA.get()), player, true);
            mainHand.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(Hand.MAIN_HAND));
            return ActionResultType.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    private static void addPasta(ItemStack spoon, ItemStack pasta, @Nonnull PlayerEntity player, boolean preventDuplicates) {
        boolean isInCreative = player.abilities.instabuild;
        if (preventDuplicates && isInCreative) {
            if (!player.inventory.contains(pasta)) player.inventory.add(pasta);
        } else {
            if (!isInCreative) spoon.hurtAndBreak(1, player, (player1) -> player.broadcastBreakEvent(player.getUsedItemHand()));
            if (spoon.isEmpty()) {
            } else {
                if (!player.inventory.add(pasta)) player.drop(pasta, false);
            }
        }
    }
}
