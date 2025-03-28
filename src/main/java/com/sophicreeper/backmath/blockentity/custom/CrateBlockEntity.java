package com.sophicreeper.backmath.blockentity.custom;

import com.sophicreeper.backmath.block.custom.CrateBlock;
import com.sophicreeper.backmath.blockentity.BMBlockEntities;
import com.sophicreeper.backmath.container.custom.CrateContainer;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.RVUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class CrateBlockEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(18, ItemStack.EMPTY);
    private int openCount;

    private CrateBlockEntity(TileEntityType<?> type) {
        super(type);
    }

    public CrateBlockEntity() {
        this(BMBlockEntities.CRATE.get());
    }

    public CompoundNBT saveToNBT(CompoundNBT tag) {
        if (!this.trySaveLootTable(tag)) RVUtils.saveAllItems(tag, this.items, false);
        return tag;
    }

    public CrateBlockEntity loadFromNBT(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains("BlockEntityTag", TagTypes.COMPOUND)) {
            CompoundNBT blockEntityTag = stack.getTag().getCompound("BlockEntityTag");
            this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            if (!this.tryLoadLootTable(blockEntityTag)) RVUtils.loadAllItems(blockEntityTag, this.items);
        }
        return this;
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
        return 18;
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
        return new TranslationTextComponent("container.backmath.crate");
    }

    @Override
    @Nonnull
    protected Container createMenu(int id, PlayerInventory inventory) {
        // return new ChestContainer(ContainerType.GENERIC_9x2, id, inventory, this, 2);
        return new CrateContainer(id, inventory, this);
    }

    @Override
    public void startOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.openCount < 0) this.openCount = 0;
            ++this.openCount;
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
            if (!(state.getBlock() instanceof CrateBlock)) this.setRemoved();
        }
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        if (!player.isSpectator()) --this.openCount;
    }
}
