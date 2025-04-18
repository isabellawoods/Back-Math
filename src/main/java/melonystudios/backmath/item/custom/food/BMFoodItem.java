package melonystudios.backmath.item.custom.food;

import com.google.common.collect.Lists;
import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.behavior.FoodSettings;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.item.custom.UseRemainders;
import melonystudios.backmath.util.BMKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

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
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livEntity) {
        super.finishUsingItem(stack, world, livEntity);
        if (livEntity instanceof ServerPlayer player && !stack.has(DataComponents.FOOD)) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!world.isClientSide) this.behavior.get().run(stack, null, livEntity, world);

        if (this.settings.defaultRemainder != null) {
            if (stack.isEmpty()) {
                return new ItemStack(this.settings.defaultRemainder.get());
            } else {
                if (livEntity instanceof Player && !((Player) livEntity).getAbilities().instabuild) {
                    ItemStack remainderStack = this.getFoodUseRemainder(stack);
                    Player player = (Player) livEntity;
                    stack.shrink(1);
                    if (!player.getInventory().add(remainderStack)) player.drop(remainderStack, false);
                }
                return stack;
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (!this.behavior.get().effects.isEmpty()) tooltip.add(Component.translatable("tooltip.backmath.behavior.when_consumed").withStyle(ChatFormatting.GRAY));
        for (Supplier<ItemBehaviorEffectType> type : this.behavior.get().effects) {
            List<Component> trueTooltip = Lists.newArrayList();
            List<Component> tooltips = type.get().addToTooltip(stack, context, trueTooltip, flag);
            if (tooltips.size() >= 8) {
                if (!BMKeys.isShiftDown()) tooltip.add(Component.translatable("tooltip.backmath.hold_shift.show", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
                else tooltip.addAll(tooltips);
            } else {
                tooltip.addAll(tooltips);
            }
        }
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return this.settings.isDrink ? ItemUtils.startUsingInstantly(world, player, hand) : super.use(world, player, hand);
    }

    public boolean isFoil(ItemStack stack) {
        return this.behavior.get().hasGlint(stack) || super.isFoil(stack);
    }

    public int getUseDuration(ItemStack stack, LivingEntity livEntity) {
        return this.settings.useDuration;
    }

    @Nonnull
    public UseAnim getUseAnimation(ItemStack stack) {
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
