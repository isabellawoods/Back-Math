package com.sophicreeper.backmath.item.custom;

import com.sophicreeper.backmath.entity.outfit.OutfitProvider;
import com.sophicreeper.backmath.util.BMResourceLocations;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CrateItem extends BlockItem implements OutfitProvider {
    public CrateItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof LivingEntity) {
            LivingEntity livEntity = (LivingEntity) entity;
            if (livEntity.getItemBySlot(EquipmentSlotType.CHEST).getItem() != this) livEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 0, false, false));
        }
    }

    @Override
    public ResourceLocation getOutfitDefinition(ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        return tag != null && tag.contains("outfit", TagTypes.STRING) ? new ResourceLocation(tag.getString("outfit")) : BMResourceLocations.CRATE_OUTFIT_DEFINITION;
    }

    @Override
    @Nullable
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.CHEST;
    }
}
