package com.sophicreeper.backmath.crystallizer.advanced;

import com.sophicreeper.backmath.crystallizer.MoldUtils;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMKeyBindings;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMStatistics;
import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.util.tag.BMItemTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
import net.minecraftforge.common.Tags;

import static com.sophicreeper.backmath.crystallizer.CrystallizerBlock.playSound;

public class CrystallineCrystallizerBlock extends HorizontalBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.FACING;
    public static final EnumProperty<AdvancedMolds> MOLD = EnumProperty.create("mold", AdvancedMolds.class);

    public CrystallineCrystallizerBlock(Properties properties) {
        super(properties);
        this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(MOLD, AdvancedMolds.EMPTY);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
        ItemStack mainHand = player.getItemInHand(Hand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(Hand.OFF_HAND);

        // Sets the specific molds for the recipes below.
        if (mainHand.getItem().is(BMItemTags.MOLDS_EMPTY) && state.getValue(MOLD) != AdvancedMolds.EMPTY) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.EMPTY).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (BMKeyBindings.isVanillaShiftDown() && state.getValue(MOLD) != AdvancedMolds.EMPTY) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.EMPTY).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_INGOT) && state.getValue(MOLD) != AdvancedMolds.INGOT) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.INGOT).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_CRYSTALLIZED) && state.getValue(MOLD) != AdvancedMolds.CRYSTALLIZED) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.CRYSTALLIZED).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_SINGULARITY) && state.getValue(MOLD) != AdvancedMolds.SINGULARITY) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.SINGULARITY).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_MOLD) && state.getValue(MOLD) != AdvancedMolds.MOLD) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.MOLD).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_ROD) && state.getValue(MOLD) != AdvancedMolds.ROD) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.ROD).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_CRYSTALLINE_EMPTY) && state.getValue(MOLD) != AdvancedMolds.CRYSTALLINE_EMPTY) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.CRYSTALLINE_EMPTY).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }
        if (mainHand.getItem().is(BMItemTags.MOLDS_CRYSTALLINE_GEM) && state.getValue(MOLD) != AdvancedMolds.CRYSTALLINE_GEM) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, AdvancedMolds.CRYSTALLINE_GEM).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }

        // You can also use the Book of Advanced Molds to cycle through the mold types.
        if (mainHand.getItem() == AxolotlTest.ADVANCED_MOLDS_BOOK.get()) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, MoldUtils.getNextAdvanced(state.getValue(MOLD))).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }

        // But if you use the Book of the Regular Molds, it cannot change it to a higher tier mold.
        if (mainHand.getItem() == AxolotlTest.REGULAR_MOLDS_BOOK.get()) {
            world.setBlockAndUpdate(pos, BMBlocks.CRYSTALLINE_CRYSTALLIZER.get().defaultBlockState().setValue(MOLD, MoldUtils.getNextAdvancedWithoutCrystalline(state.getValue(MOLD))).setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)));
            playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CHANGE_MOLD);
            player.awardStat(BMStatistics.CHANGE_CRYSTALLINE_CRYSTALLIZER_MOLD);
            player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
            return ActionResultType.SUCCESS;
        }

        if (!mainHand.getItem().is(BMItemTags.CANNOT_CRAFT_WITH_AT_CRYSTALLIZER) || !mainHand.isEmpty()) {
            // MOLD: Empty
            if (state.getValue(MOLD) == AdvancedMolds.EMPTY) {
                // 4 Aljames + Bucket = Liquid Aljame Bucket
                if (mainHand.getItem() == AxolotlTest.ALJAME.get() && mainHand.getCount() >= 4 && offHand.getItem() == Items.BUCKET) {
                    playSound(world, pos, SoundEvents.BUCKET_FILL);
                    player.inventory.add(new ItemStack(AxolotlTest.LIQUID_ALJAME_BUCKET.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    player.awardStat(Stats.ITEM_USED.get(offHand.getItem()));
                    offHand.shrink(1);
                    mainHand.shrink(4);
                    return ActionResultType.SUCCESS;
                }
            } else {
                playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_FAIL_CRAFT);
            }
            // MOLD: Rod
            if (state.getValue(MOLD) == AdvancedMolds.ROD) {
                // Hillary Bucket = 4 Hillary Rods + Bucket
                if (mainHand.getItem() == AxolotlTest.HILLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.HILLARY_ROD.get(), 4));
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
            } else {
                playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_FAIL_CRAFT);
            }
            // MOLD: Singularity
            if (state.getValue(MOLD) == AdvancedMolds.SINGULARITY) {
                // Milkllary Bucket = 2 Milkllarities + Bucket
                if (mainHand.getItem() == AxolotlTest.MILKLLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARITY.get(), 2));
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
                // Mid-Term Ingot = Mid-Term (Singularity)
                if (mainHand.getItem().is(BMItemTags.INGOTS_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MID_TERM.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Obsidian Infused Mid-Term Ingot = Obsidian Infused Mid-Term (Singularity)
                if (mainHand.getItem().is(BMItemTags.INGOTS_OBSIDIAN_INFUSED_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Milkllary Ingot = Milkllarity
                if (mainHand.getItem().is(BMItemTags.INGOTS_MILKLLARY)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARITY.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Molten Mid-Term Bucket = Mid-Term + Bucket
                if (mainHand.getItem() == AxolotlTest.MOLTEN_MID_TERM_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MID_TERM.get()));
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
            } else {
                playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_FAIL_CRAFT);
            }
            // MOLD: Ingot
            if (state.getValue(MOLD) == AdvancedMolds.INGOT) {
                // Mid-Term (Singularity) = Mid-Term Ingot
                if (mainHand.getItem().is(BMItemTags.SINGULARITIES_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MID_TERM_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Obsidian Infused Mid-Term (Singularity) = Obsidian Infused Mid-Term Ingot
                if (mainHand.getItem().is(BMItemTags.SINGULARITIES_OBSIDIAN_INFUSED_MID_TERM)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.OBSIDIAN_INFUSED_MID_TERM_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Milkllary Bucket = 2 Milkllary Ingots = Bucket
                if (mainHand.getItem() == AxolotlTest.MILKLLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get(), 2));
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
                // Milkllarity = Milkllary Ingot
                if (mainHand.getItem().is(BMItemTags.SINGULARITIES_MILKLLARY)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MILKLLARY_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Milkllary Ingot + Hillary Bucket = Two-Thirds Hillary Ingot + Bucket
                if (mainHand.getItem().is(BMItemTags.INGOTS_MILKLLARY) && offHand.getItem() == AxolotlTest.HILLARY_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.TWO_THIRDS_HILLARY_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    player.awardStat(Stats.ITEM_USED.get(offHand.getItem()));
                    player.setItemSlot(EquipmentSlotType.OFFHAND, offHand.getContainerItem());
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Hardened Amaracamel Batter = Hardened Amaracamel Ingot
                if (mainHand.getItem() == AxolotlTest.HARDENED_AMARACAMEL_BATTER.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.HARDENED_AMARACAMEL_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Obsidian = Obsidian Ingot
                if (mainHand.getItem().is(Tags.Items.OBSIDIAN)) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.OBSIDIAN_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
                // Molten Mid-Term Bucket = Mid-Term Ingot = Bucket
                if (mainHand.getItem() == AxolotlTest.MOLTEN_MID_TERM_BUCKET.get()) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.MID_TERM_INGOT.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.setItemSlot(EquipmentSlotType.MAINHAND, mainHand.getContainerItem());
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    return ActionResultType.SUCCESS;
                }
            } else {
                playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_FAIL_CRAFT);
            }
            // MOLD: Mold
            if (state.getValue(MOLD) == AdvancedMolds.MOLD) {
                if (mainHand.getItem().is(BMItemTags.GEMS_CRYSTALLINE_ANGELIC) && mainHand.getCount() >= 2) {
                    playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_CRAFT);
                    player.inventory.add(new ItemStack(AxolotlTest.CRYSTALLINE_EMPTY_MOLD.get()));
                    player.awardStat(BMStatistics.CRAFT_IN_CRYSTALLINE_CRYSTALLIZER);
                    player.awardStat(Stats.ITEM_USED.get(mainHand.getItem()));
                    mainHand.shrink(2);
                    return ActionResultType.SUCCESS;
                }
            } else {
                playSound(world, pos, BMSounds.BLOCK_CRYSTALLIZER_FAIL_CRAFT);
            }
        }
        return ActionResultType.FAIL;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

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
}
