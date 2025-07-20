package com.sophicreeper.backmath.crystallizer;

import com.google.common.collect.Lists;
import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.crafting.BMRecipeSerializers;
import com.sophicreeper.backmath.crafting.custom.CrystallizingRecipe;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.misc.BMStatistics;
import com.sophicreeper.backmath.util.BMKeyBindings;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class CrystallizerBlock extends HorizontalBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.FACING;
    public static final EnumProperty<Molds> MOLD = EnumProperty.create("mold", Molds.class);
    private List<CrystallizingRecipe> recipes = Lists.newArrayList();

    public CrystallizerBlock(Properties properties) {
        super(properties);
        this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(MOLD, Molds.EMPTY);
    }

    @Override
    @Nonnull
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
        ItemStack mainHand = player.getItemInHand(Hand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(Hand.OFF_HAND);

        // Sets the specific molds for the recipes below.
        if (mainHand.getItem().is(BMItemTags.MOLDS_EMPTY) && state.getValue(MOLD) != Molds.EMPTY) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, Molds.EMPTY).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (BMKeyBindings.isVanillaShiftDown() && state.getValue(MOLD) != Molds.EMPTY) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, Molds.EMPTY).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_INGOT) && state.getValue(MOLD) != Molds.INGOT) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, Molds.INGOT).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_CRYSTALLIZED) && state.getValue(MOLD) != Molds.CRYSTALLIZED) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, Molds.CRYSTALLIZED).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_SINGULARITY) && state.getValue(MOLD) != Molds.SINGULARITY) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, Molds.SINGULARITY).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_MOLD) && state.getValue(MOLD) != Molds.MOLD) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, Molds.MOLD).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_ROD) && state.getValue(MOLD) != Molds.ROD) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, Molds.ROD).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }

        // You can also use the Book of the Regular Molds to cycle through the mold types.
        if (mainHand.getItem() == AxolotlTest.REGULAR_MOLDS_BOOK.get()) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, MoldUtils.getNext(state.getValue(MOLD))).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }

        if (!mainHand.getItem().is(BMItemTags.CANNOT_CRAFT_WITH_AT_CRYSTALLIZER) || !mainHand.isEmpty()) {
            boolean recipeFailed = false;
            this.setupRecipeList(world, mainHand, offHand);
            // MOLD: Empty
            if (state.getValue(MOLD) == Molds.EMPTY) {
                // 4 Aljames + Bucket = Liquid Aljame Bucket
                if (mainHand.getItem() == AxolotlTest.ALJAME.get() && mainHand.getCount() >= 4 && offHand.getItem() == Items.BUCKET) {
                    playSound(world, pos, SoundEvents.BUCKET_FILL);
                    player.inventory.add(new ItemStack(AxolotlTest.LIQUID_ALJAME_BUCKET.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    player.awardStat(Stats.ITEM_USED.get(offHand.getItem()));
                    offHand.shrink(1);
                    mainHand.shrink(4);
                    return ActionResultType.SUCCESS;
                }
            } else {
                recipeFailed = true;
            }
            // MOLD: Rod
            if (state.getValue(MOLD) == Molds.ROD) {
                // Hillary Bucket = 4 Hillary Rods + Bucket
                if (mainHand.getItem() == AxolotlTest.HILLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.HILLARY_ROD.get(), 4));
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
            } else {
                recipeFailed = true;
            }
            // MOLD: Singularity
            if (state.getValue(MOLD) == Molds.SINGULARITY) {
                // Milkllary Bucket = Milkllarity + Bucket
                if (mainHand.getItem() == AxolotlTest.MILKLLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARITY.get(), 2));
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
                // Mid-Term Ingot = Mid-Term (Singularity)
                if (mainHand.getItem().is(BMItemTags.INGOTS_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MID_TERM.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Obsidian Infused Mid-Term Ingot = Obsidian Infused Mid-Term (Singularity)
                if (mainHand.getItem().is(BMItemTags.INGOTS_OBSIDIAN_INFUSED_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Milkllary Ingot = Milkllarity
                if (mainHand.getItem().is(BMItemTags.INGOTS_MILKLLARY)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARITY.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            } else {
                recipeFailed = true;
            }
            // MOLD: Ingot
            if (state.getValue(MOLD) == Molds.INGOT) {
                // Mid-Term = Mid-Term Ingot
                if (mainHand.getItem().is(BMItemTags.SINGULARITIES_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MID_TERM_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Obsidian Infused Mid-Term (Singularity) = Obsidian Infused Mid-Term Ingot
                if (mainHand.getItem().is(BMItemTags.SINGULARITIES_OBSIDIAN_INFUSED_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Milkllary Bucket = 2 Milkllary Ingots = Bucket
                if (mainHand.getItem() == AxolotlTest.MILKLLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get(), 2));
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
                // Milkllarity = Milkllary Ingot
                if (mainHand.getItem().is(BMItemTags.SINGULARITIES_MILKLLARY)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Milkllary Ingot + Hillary Bucket = Two-Thirds Hillary Ingot + Bucket
                if (mainHand.getItem().is(BMItemTags.INGOTS_MILKLLARY) && offHand.getItem() == AxolotlTest.HILLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.TWO_THIRDS_HILLARY_INGOT.get()));
                    player.setItemSlot(EquipmentSlotType.OFFHAND, offHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    player.awardStat(Stats.ITEM_USED.get(offHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Hardened Amaracamel Batter = Hardened Amaracamel Ingot
                if (mainHand.getItem() == AxolotlTest.HARDENED_AMARACAMEL_BATTER.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.HARDENED_AMARACAMEL_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            } else {
                recipeFailed = true;
            }
            // MOLD: Mold
            if (state.getValue(MOLD) == Molds.MOLD) {
                if (mainHand.getItem().is(BMItemTags.GEMS_CRYSTALLINE_ANGELIC) && mainHand.getCount() >= 2) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.CRYSTALLINE_EMPTY_MOLD.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(2);
                    return ActionResultType.SUCCESS;
                }
            } else {
                recipeFailed = true;
            }
            if (recipeFailed) playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_FAIL_CRAFT);
        }
        return ActionResultType.FAIL;
    }

    private void setupRecipeList(World world, ItemStack mainHand, ItemStack offHand) {
        Inventory inventory = new Inventory(mainHand, offHand);
        this.recipes.clear();
        this.recipes = world.getRecipeManager().getRecipesFor(BMRecipeSerializers.CRYSTALLIZING_RECIPE, inventory, world);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Nonnull
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, World world, BlockPos pos) {
        return state.getValue(MOLD).ordinal();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, MOLD);
    }

    public static void playSound(World world, BlockPos pos, SoundEvent craftingSound) {
        world.playSound(null, pos, craftingSound, SoundCategory.BLOCKS, 1, 1);
    }
}
