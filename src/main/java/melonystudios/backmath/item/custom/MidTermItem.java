package melonystudios.backmath.item.custom;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.component.custom.SuperchargeSettings;
import melonystudios.backmath.crystallizer.item.CrystallizerMaterialItem;
import melonystudios.backmath.misc.BMSounds;
import melonystudios.backmath.util.BMKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class MidTermItem extends CrystallizerMaterialItem {
    public MidTermItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        SuperchargeSettings settings = stack.get(BMDataComponents.SUPERCHARGE_SETTINGS);
        return (settings != null && settings.duration() > -1) || super.isFoil(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slotID, boolean isSelected) {
        SuperchargeSettings settings = stack.get(BMDataComponents.SUPERCHARGE_SETTINGS);
        if (settings != null && settings.duration() > -1) {
            stack.update(BMDataComponents.SUPERCHARGE_SETTINGS, settings, updated -> updated.tick(1));
            if (settings.duration() >= 12000) {
                stack.shrink(1);
                stack.set(BMDataComponents.SUPERCHARGE_SETTINGS, SuperchargeSettings.DEFAULT);
                world.playSound(null, entity.blockPosition(), BMSounds.MID_TERM_DEACTIVATE_BEACON.get(), SoundSource.PLAYERS, 1, 1);
                world.playSound(null, entity.blockPosition(), BMSounds.MID_TERM_DEACTIVATE_ENDER_EYE.get(), SoundSource.PLAYERS, 1, 1);
            }
        }
        super.inventoryTick(stack, world, entity, slotID, isSelected);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        SuperchargeSettings settings = stack.get(BMDataComponents.SUPERCHARGE_SETTINGS);
        return settings != null && settings.duration() > -1;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        SuperchargeSettings settings = stack.get(BMDataComponents.SUPERCHARGE_SETTINGS);
        if (settings != null) {
            return settings.duration() > -1 ? (int) ((double) settings.duration() / 12000) : 0;
        } else {
            return super.getBarWidth(stack);
        }
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x1DC2D1;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        SuperchargeSettings settings = stack.get(BMDataComponents.SUPERCHARGE_SETTINGS);
        if (!world.isClientSide && settings.duration() <= -1) {
            world.playSound(null, player.blockPosition(), BMSounds.MID_TERM_ACTIVATE_BEACON.get(), SoundSource.PLAYERS, 1, 1);
            world.playSound(null, player.blockPosition(), BMSounds.MID_TERM_ACTIVATE_ENDER_EYE.get(), SoundSource.PLAYERS, 1, 1);

            List<ServerPlayer> nearbyPlayers = world.getServer().getPlayerList().getPlayers();
            stack.update(BMDataComponents.SUPERCHARGE_SETTINGS, settings, SuperchargeSettings::clearPlayers);
            for (Player player1 : nearbyPlayers) {
                stack.update(BMDataComponents.SUPERCHARGE_SETTINGS, settings, updated -> updated.addPlayer(player1, 0));
                // apply Supercharged effect for 12000 ticks (no particles)
            }

            player.getCooldowns().addCooldown(stack.getItem(), 40);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            return InteractionResultHolder.sidedSuccess(stack, false);
        }

        return super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        SuperchargeSettings settings = stack.get(BMDataComponents.SUPERCHARGE_SETTINGS);
        if (settings != null) {
            if (settings.duration() > -1) tooltip.add(Component.translatable("tooltip.backmath.mid_term.supercharge_timer", StringUtil.formatTickDuration(settings.duration(), context.tickRate())).withStyle(ChatFormatting.GRAY));
            Level world = context.level();
            if (!settings.connectedPlayers().isEmpty() && world != null) {
                MutableComponent component = Component.translatable("tooltip.backmath.mid_term.connected_players").withStyle(ChatFormatting.GRAY);
                for (SuperchargeSettings.ConnectedPlayer connectedPlayer : settings.connectedPlayers()) {
                    UUID uuid = connectedPlayer.playerUUID();
                    Player player = world.getPlayerByUUID(uuid);
                    component.append(player == null ? Component.literal("") : player.getName());
                    component.append(Component.translatable("tooltip.backmath.comma_separator"));
                }
                tooltip.add(component);
            }
        }

        if (!BMKeys.isShiftDown()) tooltip.add(Component.translatable("tooltip.backmath.hold_shift", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
        if (BMKeys.isShiftDown()) {
            tooltip.add(Component.translatable("tooltip.backmath.hold_shift", BMKeys.SHOW_TOOLTIPS_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.translatable("tooltip.backmath.empty"));
            tooltip.add(Component.translatable("tooltip." + BackMath.MOD_ID + ".mid_term"));
        }
    }
}
