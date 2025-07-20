package com.sophicreeper.backmath.item.custom.food.drink;

import com.sophicreeper.backmath.dispenser.TeaDispenseBehavior;
import com.sophicreeper.backmath.item.behavior.ItemBehavior;
import com.sophicreeper.backmath.item.behavior.ItemBehaviorParameters;
import com.sophicreeper.backmath.item.custom.food.BMFoodItem;
import com.sophicreeper.backmath.util.BMKeyBindings;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class TeaItem extends BMFoodItem {
    public TeaItem(Supplier<ItemBehavior> behavior, Properties properties) {
        super(ItemBehaviorParameters.DRINK, behavior, properties);
        DispenserBlock.registerBehavior(this, new TeaDispenseBehavior());
    }

    public Supplier<ItemBehavior> getBehavior() {
        return this.behavior;
    }

    @Nonnull
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity livEntity, Hand hand) {
        if (livEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) livEntity;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (this.behavior.get() != null) this.behavior.get().run(stack, player, livEntity, livEntity.level);

        if (livEntity instanceof PlayerEntity && !((PlayerEntity) livEntity).abilities.instabuild) stack.shrink(1);
        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent("tooltip.backmath.empty"));
        tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".quote").withStyle(TextFormatting.GRAY));
        if (!BMKeyBindings.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
        if (BMKeyBindings.isShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.WHITE)).withStyle(TextFormatting.DARK_GRAY));
            tooltip.add(new TranslationTextComponent("tooltip.backmath.empty"));
            tooltip.add(new TranslationTextComponent(this.getDescriptionId() + ".desc").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
        }
    }
}
