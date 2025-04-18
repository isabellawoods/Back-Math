package melonystudios.backmath.item.custom.food.jam;

import melonystudios.backmath.item.AxolotlTest;
import melonystudios.backmath.item.custom.UseRemainders;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;

public class EffectJamItem extends Item implements UseRemainders {
    public EffectJamItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public ItemStack getDefaultInstance() {
        ItemStack defaultStack = super.getDefaultInstance();
        defaultStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
        return defaultStack;
    }

    @Override
    @Nonnull
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livEntity) {
        Player player = livEntity instanceof Player ? (Player) livEntity : null;
        if (player instanceof ServerPlayer serverPlayer) CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);

        if (!world.isClientSide) {
            PotionContents potionContents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            potionContents.forEachEffect(instance -> {
                if (instance.getEffect().value().isInstantenous()) {
                    instance.getEffect().value().applyInstantenousEffect(player, player, livEntity, instance.getAmplifier(), 1);
                } else {
                    livEntity.addEffect(instance);
                }
            });
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) stack.shrink(1);
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (stack.isEmpty()) return new ItemStack(AxolotlTest.JAM_POT.get());
            if (player != null) player.getInventory().add(this.getFoodUseRemainder(stack, new ItemStack(AxolotlTest.JAM_POT.get())));
        }

        livEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livEntity) {
        return 40;
    }

    @Override
    @Nonnull
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    @Nonnull
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    @Nonnull
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    @Override
    @Nonnull
    public String getDescriptionId(ItemStack stack) {
        return Potion.getName(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion(), this.getDescriptionId() + ".effect.");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        PotionContents potionContents = stack.get(DataComponents.POTION_CONTENTS);
        if (potionContents != null) potionContents.addPotionTooltip(tooltip::add, 1, context.tickRate());
    }
}
