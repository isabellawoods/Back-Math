package com.sophicreeper.backmath.entity.outfit;

import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/// Interface containing methods used to get {@link com.sophicreeper.backmath.entity.outfit.OutfitDefinition outfit definitions} from items.
public interface OutfitProvider {
    /// Gets a resource location for an {@link com.sophicreeper.backmath.entity.outfit.OutfitDefinition outfit definition} from an item.
    /// @param stack The item to pull the "<code>outfit</code>" tag from.
    ResourceLocation getOutfitDefinition(ItemStack stack);

    /// Gets the slot this outfit applies to.
    /// @param stack An item stack if needed for the future.
    default EquipmentSlotType getOutfitSlot(ItemStack stack) {
        return MobEntity.getEquipmentSlotForItem(stack);
    }
}
