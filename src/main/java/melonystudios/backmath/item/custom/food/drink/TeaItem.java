package melonystudios.backmath.item.custom.food.drink;

import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.ItemBehaviorParameters;
import melonystudios.backmath.item.custom.food.BMFoodItem;
import melonystudios.backmath.util.BMKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class TeaItem extends BMFoodItem {
    public TeaItem(Supplier<ItemBehavior> behavior, Properties properties) {
        super(ItemBehaviorParameters.DRINK, behavior, properties);
        // DispenserBlock.registerBehavior(this, new TeaDispenseBehavior());
    }

    public Supplier<ItemBehavior> getBehavior() {
        return this.behavior;
    }

    @NotNull
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livEntity, InteractionHand hand) {
        if (livEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (this.behavior.get() != null) this.behavior.get().run(stack, player, livEntity, livEntity.level());

        if (livEntity instanceof Player && !((Player) livEntity).getAbilities().instabuild) stack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.backmath.empty"));
        tooltip.add(Component.translatable(this.getDescriptionId() + ".quote").withStyle(ChatFormatting.GRAY));
        if (!BMKeys.isShiftDown()) tooltip.add(Component.translatable("tooltip.backmath.hold_shift", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
        if (BMKeys.isShiftDown()) {
            tooltip.add(Component.translatable("tooltip.backmath.hold_shift", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.translatable("tooltip.backmath.empty"));
            tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
    }
}
