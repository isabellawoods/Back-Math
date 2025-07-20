package com.sophicreeper.backmath.block.custom;

import com.google.common.collect.Lists;
import com.sophicreeper.backmath.loot.LootTableUtils;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class FruitLeavesBlock extends LeavesBlock {
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty DOWN = SixWayBlock.DOWN;
    public static final List<BooleanProperty> DIRECTIONS = Lists.newArrayList(NORTH, SOUTH, EAST, WEST, DOWN);
    private final ResourceLocation pickingTable;

    public FruitLeavesBlock(ResourceLocation pickingTable, Properties properties) {
        super(properties);
        this.pickingTable = pickingTable;
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false)
                .setValue(WEST, false).setValue(DOWN, false).setValue(PERSISTENT, false).setValue(DISTANCE, 7));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
        BooleanProperty clickedSide = null;
        if (hitResult.getDirection() == Direction.NORTH) clickedSide = NORTH;
        else if (hitResult.getDirection() == Direction.SOUTH) clickedSide = SOUTH;
        else if (hitResult.getDirection() == Direction.EAST) clickedSide = EAST;
        else if (hitResult.getDirection() == Direction.WEST) clickedSide = WEST;
        else if (hitResult.getDirection() == Direction.DOWN) clickedSide = DOWN;

        if (clickedSide != null && state.getValue(clickedSide)) {
            Collection<ItemStack> tableDrops = getLootTableDrops(this.pickingTable, state, pos, world, player);
            world.setBlockAndUpdate(pos, state.setValue(clickedSide, false));
            world.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1, 0.8F + world.random.nextFloat() * 0.4F);
            tableDrops.forEach(stack -> popResource(world, pos, stack));
            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        return super.use(state, world, pos, player, hand, hitResult);
    }

    public static Collection<ItemStack> getLootTableDrops(ResourceLocation pickingTable, BlockState state, BlockPos pos, World world, @Nullable PlayerEntity player) {
        return LootTableUtils.pickFruits(pickingTable, world, state, pos, player);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        super.randomTick(state, world, pos, rand);
        growFruit(state, world, pos, rand);
    }

    public void growFruit(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (rand.nextInt(3) == 0) {
            BooleanProperty property = DIRECTIONS.get(rand.nextInt(5));
            if (!state.getValue(property)) world.setBlockAndUpdate(pos, state.setValue(property, true));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DOWN, NORTH, SOUTH, EAST, WEST, DISTANCE, PERSISTENT);
    }
}
