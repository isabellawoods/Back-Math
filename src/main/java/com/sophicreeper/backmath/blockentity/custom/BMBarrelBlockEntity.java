package com.sophicreeper.backmath.blockentity.custom;

import com.sophicreeper.backmath.block.custom.BMBarrelBlock;
import com.sophicreeper.backmath.blockentity.BMBlockEntities;
import com.sophicreeper.backmath.util.RVUtils;
import com.sophicreeper.backmath.util.tag.BMBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class BMBarrelBlockEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private int openCount;

    private BMBarrelBlockEntity(TileEntityType<?> type) {
        super(type);
    }

    public BMBarrelBlockEntity() {
        this(BMBlockEntities.BM_BARREL.get());
    }

    @Override
    @Nonnull
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);
        if (!this.trySaveLootTable(tag)) RVUtils.saveAllItems(tag, this.items);
        return tag;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) RVUtils.loadAllItems(tag, this.items);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    @Nonnull
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stackList) {
        this.items = stackList;
    }

    @Override
    @Nonnull
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.backmath.bm_barrel");
    }

    @Override
    @Nonnull
    protected Container createMenu(int id, PlayerInventory inventory) {
        return ChestContainer.threeRows(id, inventory, this);
    }

    @Override
    public void startOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.openCount < 0) this.openCount = 0;

            ++this.openCount;
            BlockState state = this.getBlockState();
            boolean isOpen = state.getValue(BMBarrelBlock.OPEN);
            if (!isOpen) {
                this.playSound(state, SoundEvents.BARREL_OPEN);
                this.updateBlockState(state, true);
            }

            this.scheduleRecheck();
        }
    }

    private void scheduleRecheck() {
        this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
    }

    public void recheckOpen() {
        int x = this.worldPosition.getX();
        int y = this.worldPosition.getY();
        int z = this.worldPosition.getZ();
        this.openCount = ChestTileEntity.getOpenCount(this.level, this, x, y, z);
        if (this.openCount > 0) {
            this.scheduleRecheck();
        } else {
            BlockState state = this.getBlockState();
            if (!state.is(BMBlockTags.BARRELS)) {
                this.setRemoved();
                return;
            }

            boolean isOpen = state.getValue(BMBarrelBlock.OPEN);
            if (isOpen) {
                this.playSound(state, SoundEvents.BARREL_CLOSE);
                this.updateBlockState(state, false);
            }
        }
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        if (!player.isSpectator()) --this.openCount;
    }

    private void updateBlockState(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(BMBarrelBlock.OPEN, open), 3);
    }

    private void playSound(BlockState state, SoundEvent sound) {
        Vector3i facing = state.getValue(BMBarrelBlock.FACING).getNormal();
        double x = (double) this.worldPosition.getX() + 0.5D + (double) facing.getX() / 2;
        double y = (double) this.worldPosition.getY() + 0.5D + (double) facing.getY() / 2;
        double z = (double) this.worldPosition.getZ() + 0.5D + (double) facing.getZ() / 2;
        this.level.playSound(null, x, y, z, sound, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}
