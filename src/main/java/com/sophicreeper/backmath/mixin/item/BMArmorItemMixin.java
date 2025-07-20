package com.sophicreeper.backmath.mixin.item;

import com.sophicreeper.backmath.entity.outfit.OutfitProvider;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorItem.class)
public abstract class BMArmorItemMixin extends Item implements OutfitProvider {
    @Shadow
    public abstract IArmorMaterial getMaterial();

    public BMArmorItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getOutfitDefinition(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains("outfit", TagTypes.STRING)) return new ResourceLocation(stack.getTag().getString("outfit"));
        return new ResourceLocation(this.getMaterial().getName());
    }
}
