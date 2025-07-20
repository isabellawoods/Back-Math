package com.sophicreeper.backmath.item.behavior.effecttype.custom;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.config.BMConfigs;
import com.sophicreeper.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import com.sophicreeper.backmath.util.TagTypes;
import com.sophicreeper.backmath.util.revaried.RVUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class GiveMilkedSwordItemEffectType extends ItemBehaviorEffectType {
    private final ItemStack bucketStack;

    public GiveMilkedSwordItemEffectType(ItemStack bucketStack) {
        this.bucketStack = bucketStack;
    }

    public GiveMilkedSwordItemEffectType() {
        this(new ItemStack(Items.MILK_BUCKET));
    }

    @Override
    public void runBehavior(ItemStack stack, PlayerEntity attacker, LivingEntity target, World world) {
        if (BMConfigs.COMMON_CONFIGS.milkedSwordsEnabled.get()) {
            CompoundNBT tag = stack.getTag();
            ItemStack bucketStack = this.bucketStack;
            if (tag != null && tag.contains("milked_sword_item", TagTypes.STRING)) {
                ResourceLocation itemLocation = ResourceLocation.tryParse(tag.getString("milked_sword_item"));
                if (itemLocation != null && ForgeRegistries.ITEMS.containsKey(itemLocation)) {
                    ItemStack tagStack = new ItemStack(ForgeRegistries.ITEMS.getValue(itemLocation));
                    tag.remove("milked_sword_item");
                    tag.put("milked_sword_item", RVUtils.saveStack(tagStack, new CompoundNBT()));
                }
            }
            if (tag != null && tag.contains("milked_sword_item", TagTypes.COMPOUND)) {
                ItemStack tagStack = RVUtils.loadStack(tag.getCompound("milked_sword_item"));
                if (!tagStack.isEmpty()) bucketStack = tagStack;
            }
            if (!attacker.inventory.add(bucketStack)) attacker.drop(bucketStack, false);
        }
    }

    @Override
    public List<ITextComponent> addToTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addToTooltip(stack, world, tooltip, flag);
        if (this.bucketStack != null) {
            IFormattableTextComponent component = new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.give_milked_sword_item", this.bucketStack.getCount(), this.bucketStack.getHoverName()).withStyle(RVUtils.getFromRGB(0xBDDCE4));
            tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".behavior.neutral_effect", component).withStyle(RVUtils.getFromRGB(0x7C979E)));
        }
        return tooltip;
    }
}
