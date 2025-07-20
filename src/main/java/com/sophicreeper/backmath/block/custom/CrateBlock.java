package com.sophicreeper.backmath.block.custom;

import com.sophicreeper.backmath.blockentity.custom.CrateBlockEntity;
import com.sophicreeper.backmath.item.AxolotlTest;
import com.sophicreeper.backmath.misc.BMStatistics;
import com.sophicreeper.backmath.util.BMKeyBindings;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.revaried.RVUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CrateBlock extends ContainerBlock {
    public CrateBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.0625, 0, 0.0625, 0.1875, 1, 0.9375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.8125, 0, 0.0625, 0.9375, 1, 0.9375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.1875, 0, 0.0625, 0.8125, 1, 0.1875), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.1875, 0, 0.8125, 0.8125, 1, 0.9375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.1875, 0, 0.1875, 0.8125, 0.0625, 0.8125), IBooleanFunction.OR);
        return shape;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hitResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else if (BMKeyBindings.isVanillaShiftDown()) {
            SoundType crateSoundType = this.getSoundType(state, world, pos, player);
            float[] pitches = new float[] {0.6F, 0.7F, 0.8F};
            world.playSound(null, pos, crateSoundType.getBreakSound(), SoundCategory.BLOCKS, (crateSoundType.getVolume() + 1) / 2, crateSoundType.getPitch() * 0.8F);
            world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 0.8F, pitches[world.random.nextInt(2)]);
            Minecraft.getInstance().particleEngine.destroy(pos, state);

            ItemStack stack = this.createCrateStack(new ItemStack(AxolotlTest.CRATE.get()), world, pos);
            if (player.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
                player.inventory.armor.set(EquipmentSlotType.CHEST.getIndex(), stack);
            } else if (player.getItemInHand(hand).isEmpty()) {
                player.inventory.items.set(player.inventory.selected, stack);
            } else if (!player.inventory.add(stack)) {
                player.drop(stack, false);
            }

            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            return ActionResultType.SUCCESS;
        } else {
            TileEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrateBlockEntity) {
                player.openMenu((CrateBlockEntity) blockEntity);
                player.awardStat(BMStatistics.OPEN_CRATE);
                PiglinTasks.angerNearbyPiglins(player, true);
            }

            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CrateBlockEntity) {
            CrateBlockEntity crateBlockEntity = (CrateBlockEntity) blockEntity;
            if (!world.isClientSide && player.isCreative() && !crateBlockEntity.isEmpty()) {
                ItemStack crateStack = this.createCrateStack(new ItemStack(AxolotlTest.CRATE.get()), world, pos);
                ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, crateStack);
                itemEntity.setDefaultPickUpDelay();
                world.addFreshEntity(itemEntity);
            } else {
                crateBlockEntity.unpackLootTable(player);
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity livEntity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            TileEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrateBlockEntity) {
                ((CrateBlockEntity) blockEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            TileEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof IInventory) world.updateNeighbourForOutputSignal(pos, this);
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CrateBlockEntity) ((CrateBlockEntity) blockEntity).recheckOpen();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity blockEntity = builder.getOptionalParameter(LootParameters.BLOCK_ENTITY);
        if (blockEntity instanceof CrateBlockEntity) {
            CrateBlockEntity crateBlockEntity = (CrateBlockEntity) blockEntity;
            builder = builder.withDynamicDrop(BMResourceLocations.CONTENTS, (context, consumer) -> {
                for (int i = 0; i < crateBlockEntity.getContainerSize(); ++i) consumer.accept(crateBlockEntity.getItem(i));
            });
        }

        return super.getDrops(state, builder);
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader world, BlockPos pos, BlockState state) {
        return this.createCrateStack(super.getCloneItemStack(world, pos, state), world, pos);
    }

    protected ItemStack createCrateStack(ItemStack stack, IBlockReader world, BlockPos pos) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CrateBlockEntity) {
            CrateBlockEntity crateBlockEntity = (CrateBlockEntity) blockEntity;
            CompoundNBT tag = crateBlockEntity.saveToNBT(new CompoundNBT());

            if (!tag.isEmpty()) stack.addTagElement("BlockEntityTag", tag);
            if (crateBlockEntity.hasCustomName()) stack.setHoverName(crateBlockEntity.getCustomName());
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void appendHoverText(ItemStack stack, IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        CompoundNBT blockEntityTag = stack.getTagElement("BlockEntityTag");
        if (blockEntityTag != null) {
            if (blockEntityTag.contains("LootTable", TagTypes.STRING)) tooltip.add(new TranslationTextComponent("tooltip.backmath.crate.has_unknown_loot").withStyle(TextFormatting.GRAY));

            if (blockEntityTag.contains("items", TagTypes.LIST)) {
                NonNullList<ItemStack> shulkerItems = NonNullList.withSize(18, ItemStack.EMPTY);
                RVUtils.loadAllItems(blockEntityTag, shulkerItems);
                int displayedItems = 0;
                int totalItems = 0;

                for (ItemStack crateStack : shulkerItems) {
                    if (!crateStack.isEmpty()) {
                        ++totalItems;
                        if (!BMKeyBindings.isShiftDown()) {
                            if (displayedItems <= 4) {
                                ++displayedItems;
                                IFormattableTextComponent component = crateStack.getDisplayName().copy();
                                ITextComponent countComponent = new TranslationTextComponent("tooltip.backmath.crate.item_count", crateStack.getCount()).withStyle(TextFormatting.GRAY);
                                component.append(countComponent);
                                tooltip.add(component);
                            }
                        } else {
                            IFormattableTextComponent component = crateStack.getDisplayName().copy();
                            ITextComponent countComponent = new TranslationTextComponent("tooltip.backmath.crate.item_count", crateStack.getCount()).withStyle(TextFormatting.GRAY);
                            component.append(countComponent);
                            tooltip.add(component);
                        }
                    }
                }

                if (totalItems - displayedItems > 0 && !BMKeyBindings.isShiftDown()) {
                    tooltip.add(new TranslationTextComponent("tooltip.backmath.crate.more", totalItems - displayedItems).withStyle(TextFormatting.DARK_GRAY).withStyle(TextFormatting.ITALIC));
                    tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift.crate", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY).withStyle(TextFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    @Nullable
    public TileEntity newBlockEntity(IBlockReader world) {
        return new CrateBlockEntity();
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, World world, BlockPos pos) {
        return Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
