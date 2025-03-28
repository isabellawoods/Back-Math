package com.sophicreeper.backmath.item.behavior;

import com.sophicreeper.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.function.Supplier;

public class ItemBehavior extends ForgeRegistryEntry<ItemBehavior> {
    public final List<Supplier<ItemBehaviorEffectType>> effects;
    private Integer durabilityBarColor;
    private boolean hasGlint;

    public ItemBehavior(List<Supplier<ItemBehaviorEffectType>> effects) {
        this.effects = effects;
    }

    public void run(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        for (Supplier<ItemBehaviorEffectType> type : this.effects) {
            if (type.get() != null) type.get().runBehavior(stack, attacker, target, world);
            else LogManager.getLogger().error(new TranslationTextComponent("error.backmath.no_item_behavior_effect_type", this.getRegistryName()).getString());
        }
    }

    public ItemBehavior durabilityBarColor(Integer color) {
        this.durabilityBarColor = color;
        return this;
    }

    public ItemBehavior glint(boolean glint) {
        this.hasGlint = glint;
        return this;
    }

    public Integer getDurabilityBarColor(int oldColor) {
        return this.durabilityBarColor != null ? this.durabilityBarColor : oldColor;
    }

    public boolean hasGlint(ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("enchantment_glint_override", TagTypes.ANY_NUMERIC)) return tag.getBoolean("enchantment_glint_override");
        return this.hasGlint;
    }
}
