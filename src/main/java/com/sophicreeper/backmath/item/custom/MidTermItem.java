package com.sophicreeper.backmath.item.custom;

import com.google.common.collect.Lists;
import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.config.BMConfigs;
import com.sophicreeper.backmath.crystallizer.item.CrystallizerMaterialItem;
import com.sophicreeper.backmath.effect.BMEffects;
import com.sophicreeper.backmath.item.custom.food.VSConsumable;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMKeys;
import com.sophicreeper.backmath.util.TagTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MidTermItem extends CrystallizerMaterialItem {
    public MidTermItem(Properties properties) {
        super(properties);
    }

    private int superchargeDuration(ItemStack stack) {
        CompoundNBT settingsTag = stack.getTagElement("supercharge_settings");
        return settingsTag != null && settingsTag.contains("duration", TagTypes.ANY_NUMERIC) ? settingsTag.getInt("duration") : -1;
    }

    private ListNBT connectedPlayers(ItemStack stack) {
        CompoundNBT settingsTag = stack.getTagElement("supercharge_settings");
        return (settingsTag != null && settingsTag.contains("connected_players", TagTypes.LIST)) ? settingsTag.getList("connected_players", TagTypes.INTEGER_ARRAY) : new ListNBT();
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return superchargeDuration(stack) > -1;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        CompoundNBT settingsTag = stack.getTagElement("supercharge_settings");
        int superchargeDuration = superchargeDuration(stack);

        if (superchargeDuration > -1 && settingsTag != null) {
            settingsTag.putInt("duration", superchargeDuration + 1);
            if (superchargeDuration >= 12000) {
                stack.shrink(1);
                settingsTag.putInt("duration", -1);
                settingsTag.remove("connected_players");
                world.playSound(null, entity.blockPosition(), BMSounds.ITEM_MID_TERM_DEACTIVATE_BEACON, SoundCategory.PLAYERS, 1, 1);
                world.playSound(null, entity.blockPosition(), BMSounds.ITEM_MID_TERM_DEACTIVATE_ENDER_EYE, SoundCategory.PLAYERS, 1, 1);
            }
        }

        double x = entity.getX() + 0.55D - (double) (random.nextFloat() * 0.1F);
        double y = entity.getY() + 0.55D - (double) (random.nextFloat() * 0.1F);
        double z = entity.getZ() + 0.55D - (double) (random.nextFloat() * 0.1F);
        double offset = 0.4F - (random.nextFloat() + random.nextFloat()) * 0.4F;
        if (random.nextInt(5) == 0) {
//            world.addParticle(BMParticleTypes.WARMTERM.get(), x * offset, y * offset, z * offset, random.nextGaussian() * 0.05D, random.nextGaussian() * 0.05D, random.nextGaussian() * 0.05D);
//            world.addParticle(BMParticleTypes.COLDTERM.get(), x * offset, y * offset, z * offset, random.nextGaussian() * 0.05D, random.nextGaussian() * 0.05D, random.nextGaussian() * 0.05D);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return superchargeDuration(stack) > -1;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        int superchargeDuration = superchargeDuration(stack);
        return superchargeDuration > -1 ? ((double) superchargeDuration / 12000D) : 0;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return BMConfigs.COMMON_CONFIGS.midTermCustomDurabilityBar.get();
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && superchargeDuration(stack) <= -1) {
            world.playSound(null, player.blockPosition(), BMSounds.ITEM_MID_TERM_ACTIVATE_BEACON, SoundCategory.PLAYERS, 1, 1);
            world.playSound(null, player.blockPosition(), BMSounds.ITEM_MID_TERM_ACTIVATE_ENDER_EYE, SoundCategory.PLAYERS, 1, 1);

            //world.getNearbyPlayers(EntityPredicate.DEFAULT.ignoreInvisibilityTesting(), player, new AxisAlignedBB(player.blockPosition()).inflate(8))
            List<ServerPlayerEntity> nearbyPlayers = world.getServer().getPlayerList().getPlayers();
            for (PlayerEntity player1 : nearbyPlayers) {
                CompoundNBT settingsTag = stack.getOrCreateTagElement("supercharge_settings");
                if (!settingsTag.contains("connected_players", TagTypes.LIST)) {
                    ListNBT connectedPlayers = new ListNBT();
                    connectedPlayers.add(NBTUtil.createUUID(player1.getUUID()));
                    settingsTag.put("connected_players", connectedPlayers);
                    settingsTag.putInt("duration", 0);

                    if (!player1.hasEffect(BMEffects.SUPERCHARGED.get())) {
                        EffectInstance instance = new EffectInstance(BMEffects.SUPERCHARGED.get(), 12000, 0, true, false);
                        instance.setCurativeItems(Lists.newArrayList());
                        player1.addEffect(instance);
                    }
                }
            }

            VSConsumable.applyCooldown(stack, player, 40);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            return ActionResult.sidedSuccess(stack, false);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        int superchargeDuration = superchargeDuration(stack);
        if (superchargeDuration > -1) {
            tooltip.add(new TranslationTextComponent("tooltip.backmath.mid_term.supercharge_timer", StringUtils.formatTickDuration(superchargeDuration)).withStyle(TextFormatting.GRAY));
        }

        ListNBT connectedPlayers = connectedPlayers(stack);
        if (!connectedPlayers.isEmpty() && world != null) {
            IFormattableTextComponent component = new TranslationTextComponent("tooltip.backmath.mid_term.connected_players").withStyle(TextFormatting.GRAY);
            for (int i = 0; i < connectedPlayers.size(); ++i) {
                UUID uuid = UUIDCodec.uuidFromIntArray(connectedPlayers.getIntArray(i));

                if (i > 0) {
                    TranslationTextComponent separatorComponent = new TranslationTextComponent("tooltip.backmath.comma_separator");
                    if (i == connectedPlayers.size() - 1) separatorComponent = new TranslationTextComponent("tooltip.backmath.and_separator");
                    component.append(separatorComponent).withStyle(TextFormatting.GRAY);
                }
                PlayerEntity player = world.getPlayerByUUID(uuid);
                component.append(player == null ? new StringTextComponent("") : player.getName());
            }
            tooltip.add(component);
        }

        if (!BMKeys.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeys.getTranslation(BMKeys.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
        if (BMKeys.isShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeys.getTranslation(BMKeys.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.WHITE)).withStyle(TextFormatting.DARK_GRAY));
            tooltip.add(new TranslationTextComponent("tooltip.backmath.empty"));
            tooltip.add(new TranslationTextComponent("tooltip." + BackMath.MOD_ID + ".mid_term"));
        }
    }
}
