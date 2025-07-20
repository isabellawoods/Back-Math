package com.sophicreeper.backmath.item.custom.food;

import com.google.common.collect.Lists;
import com.sophicreeper.backmath.item.behavior.BMItemBehaviors;
import com.sophicreeper.backmath.item.behavior.FoodSettings;
import com.sophicreeper.backmath.item.behavior.ItemBehavior;
import com.sophicreeper.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import com.sophicreeper.backmath.item.custom.UseRemainders;
import com.sophicreeper.backmath.util.BMKeyBindings;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class BMFoodItem extends Item implements UseRemainders {
    protected final Supplier<ItemBehavior> behavior;
    private final FoodSettings settings;

    public BMFoodItem(FoodSettings settings, Supplier<ItemBehavior> behavior, Properties properties) {
        super(properties);
        this.settings = settings;
        this.behavior = behavior;
        if (this.settings.dispenseBehavior != null) DispenserBlock.registerBehavior(this, this.settings.dispenseBehavior);
    }

    public BMFoodItem(FoodSettings settings, Properties properties) {
        this(settings, BMItemBehaviors.NONE, properties);
    }

    @Nonnull
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity livEntity) {
        super.finishUsingItem(stack, world, livEntity);
        if (livEntity instanceof ServerPlayerEntity && !stack.isEdible()) {
            ServerPlayerEntity player = (ServerPlayerEntity) livEntity;
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!world.isClientSide) this.behavior.get().run(stack, null, livEntity, world);

        if (this.settings.defaultRemainder != null) {
            if (stack.isEmpty()) {
                return new ItemStack(this.settings.defaultRemainder.get());
            } else {
                if (livEntity instanceof PlayerEntity && !((PlayerEntity) livEntity).abilities.instabuild) {
                    ItemStack remainderStack = this.getFoodUseRemainder(stack);
                    PlayerEntity player = (PlayerEntity) livEntity;
                    stack.shrink(1);
                    if (!player.inventory.add(remainderStack)) player.drop(remainderStack, false);
                }
                return stack;
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (!this.behavior.get().effects.isEmpty()) tooltip.add(new TranslationTextComponent("tooltip.backmath.behavior.when_consumed").withStyle(TextFormatting.GRAY));
        for (Supplier<ItemBehaviorEffectType> type : this.behavior.get().effects) {
            List<ITextComponent> trueTooltip = Lists.newArrayList();
            List<ITextComponent> tooltips = type.get().addToTooltip(stack, world, trueTooltip, flag);
            if (tooltips.size() >= 8) {
                if (!BMKeyBindings.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift.show", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
                else tooltip.addAll(tooltips);
            } else {
                tooltip.addAll(tooltips);
            }
        }
    }

    @Nonnull
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return this.settings.isDrink ? DrinkHelper.useDrink(world, player, hand) : super.use(world, player, hand);
    }

    public boolean isFoil(ItemStack stack) {
        return this.behavior.get().hasGlint(stack) || super.isFoil(stack);
    }

    public int getUseDuration(ItemStack stack) {
        return this.settings.useDuration;
    }

    @Nonnull
    public UseAction getUseAnimation(ItemStack stack) {
        return this.settings.animation;
    }

    @Nonnull
    public SoundEvent getEatingSound() {
        return this.settings.consumeSound;
    }

    @Nonnull
    public SoundEvent getDrinkingSound() {
        return this.settings.consumeSound;
    }
}
