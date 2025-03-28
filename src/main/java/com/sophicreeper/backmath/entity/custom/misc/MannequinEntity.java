package com.sophicreeper.backmath.entity.custom.misc;

import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.RVUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MannequinEntity extends LivingEntity {
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);

    public MannequinEntity(EntityType<? extends MannequinEntity> type, World world) {
        super(type, world);
        this.maxUpStep = 0;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);

        ListNBT armorList = new ListNBT();
        for (ItemStack stack : this.armorItems) {
            CompoundNBT stackTag = new CompoundNBT();
            if (!stack.isEmpty()) RVUtils.saveStack(stack, stackTag);
            armorList.add(stackTag);
        }
        tag.put("armor", armorList);

        tag.put("main_hand", RVUtils.saveStack(this.handItems.get(0), new CompoundNBT()));
        tag.put("off_hand", RVUtils.saveStack(this.handItems.get(1), new CompoundNBT()));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("armor", TagTypes.LIST)) {
            ListNBT armorList = tag.getList("armor", TagTypes.COMPOUND);
            for (int i = 0; i < armorList.size(); ++i) this.armorItems.set(i, RVUtils.loadStack(armorList.getCompound(i)));
        }

        if (tag.contains("main_hand", TagTypes.COMPOUND)) this.handItems.set(0, RVUtils.loadStack(tag.getCompound("main_hand")));
        if (tag.contains("off_hand", TagTypes.COMPOUND)) this.handItems.set(1, RVUtils.loadStack(tag.getCompound("off_hand")));
    }

    @Override
    public boolean canTakeItem(ItemStack stack) {
        return this.getItemBySlot(MobEntity.getEquipmentSlotForItem(stack)).isEmpty();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {}

    @Override
    @Nonnull
    public Iterable<ItemStack> getHandSlots() {
        return this.handItems;
    }

    @Override
    @Nonnull
    public Iterable<ItemStack> getArmorSlots() {
        return this.armorItems;
    }

    @Override
    @Nonnull
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    @Override
    @Nonnull
    public ItemStack getItemBySlot(EquipmentSlotType slotType) {
        switch (slotType.getType()) {
            case HAND: return this.handItems.get(slotType.getIndex());
            case ARMOR: return this.armorItems.get(slotType.getIndex());
            default: return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlotType slotType, ItemStack stack) {
        switch (slotType.getType()) {
            case HAND:
                this.playEquipSound(stack);
                this.handItems.set(slotType.getIndex(), stack);
                break;
            case ARMOR:
                this.playEquipSound(stack);
                this.armorItems.set(slotType.getIndex(), stack);
        }
    }

    @Override
    public boolean setSlot(int slot, ItemStack stack) {
        EquipmentSlotType slotType;

        if (slot == 98) {
            slotType = EquipmentSlotType.MAINHAND;
        } else if (slot == 99) {
            slotType = EquipmentSlotType.OFFHAND;
        } else if (slot == 100 + EquipmentSlotType.HEAD.getIndex()) {
            slotType = EquipmentSlotType.HEAD;
        } else if (slot == 100 + EquipmentSlotType.CHEST.getIndex()) {
            slotType = EquipmentSlotType.CHEST;
        } else if (slot == 100 + EquipmentSlotType.LEGS.getIndex()) {
            slotType = EquipmentSlotType.LEGS;
        } else {
            if (slot != 100 + EquipmentSlotType.FEET.getIndex()) return false;
            slotType = EquipmentSlotType.FEET;
        }

        if (!stack.isEmpty() && !MobEntity.isValidSlotForItem(slotType, stack) && slotType != EquipmentSlotType.HEAD) {
            return false;
        } else {
            this.setItemSlot(slotType, stack);
            return true;
        }
    }

    @Override
    @Nonnull
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec3D, Hand hand) {
        ItemStack handStack = player.getItemInHand(hand);

        if (handStack.getItem() != Items.NAME_TAG) {
            if (player.isSpectator()) {
                return ActionResultType.SUCCESS;
            } else if (player.level.isClientSide) {
                return ActionResultType.CONSUME;
            } else {
                EquipmentSlotType slotType = MobEntity.getEquipmentSlotForItem(handStack);
                if (handStack.isEmpty()) {
                    EquipmentSlotType clickedSlot = this.getClickedSlot(vec3D);
                    if (this.hasItemInSlot(clickedSlot) && this.swapItem(player, clickedSlot, handStack, hand)) {
                        return ActionResultType.SUCCESS;
                    }
                } else {
                    if (slotType.getType() == EquipmentSlotType.Group.HAND) {
                        return ActionResultType.FAIL;
                    }

                    if (this.swapItem(player, slotType, handStack, hand)) {
                        return ActionResultType.SUCCESS;
                    }
                }

                return ActionResultType.PASS;
            }
        } else {
            return ActionResultType.PASS;
        }
    }

    private EquipmentSlotType getClickedSlot(Vector3d vec3D) {
        EquipmentSlotType slotType = EquipmentSlotType.MAINHAND;
        EquipmentSlotType feetSlotType = EquipmentSlotType.FEET;
        double y = vec3D.y;

        if (y >= 0.1D && y < 0.1D + 0.45D && this.hasItemInSlot(feetSlotType)) {
            slotType = EquipmentSlotType.FEET;
        } else if (y >= 0.9D && y < 0.9D + 0.7D && this.hasItemInSlot(EquipmentSlotType.CHEST)) {
            slotType = EquipmentSlotType.CHEST;
        } else if (y >= 0.4D && y < 0.4D + 0.8D && this.hasItemInSlot(EquipmentSlotType.LEGS)) {
            slotType = EquipmentSlotType.LEGS;
        } else if (y >= 1.6D && this.hasItemInSlot(EquipmentSlotType.HEAD)) {
            slotType = EquipmentSlotType.HEAD;
        } else if (!this.hasItemInSlot(EquipmentSlotType.MAINHAND) && this.hasItemInSlot(EquipmentSlotType.OFFHAND)) {
            slotType = EquipmentSlotType.OFFHAND;
        }

        return slotType;
    }

    private boolean swapItem(PlayerEntity player, EquipmentSlotType slotType, ItemStack stack, Hand hand) {
        ItemStack stackBySlot = this.getItemBySlot(slotType);

        if (stackBySlot.isEmpty()) {
            return false;
        } else if (player.abilities.instabuild && stackBySlot.isEmpty() && !stack.isEmpty()) {
            ItemStack copyStack = stack.copy();
            copyStack.setCount(1);
            this.setItemSlot(slotType, copyStack);
            return true;
        } else if (!stack.isEmpty() && stack.getCount() > 1) {
            if (!stackBySlot.isEmpty()) {
                return false;
            } else {
                ItemStack copyStack = stack.copy();
                copyStack.setCount(1);
                this.setItemSlot(slotType, copyStack);
                stack.shrink(1);
                return true;
            }
        } else {
            this.setItemSlot(slotType, stack);
            player.setItemInHand(hand, stackBySlot);
            return true;
        }
    }
}
