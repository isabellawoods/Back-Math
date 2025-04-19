package melonystudios.backmath.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.AxolotlTest;
import net.minecraft.Util;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringUtil;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class BMUtils {
    public static final Codec<UUID> UUID_CODEC = Codec.INT.listOf().comapFlatMap(intList -> Util.fixedSize(intList, 4).map(
            intList1 -> uuidFromIntArray(intList.toArray(new Integer[0]))), uuid -> {
                int[] ints = UUIDUtil.uuidToIntArray(uuid);
                return List.of(ints[0], ints[1], ints[2], ints[3]);
    });
    public static final StreamCodec<ByteBuf, UUID> STREAM_UUID_CODEC = new StreamCodec<>() {
        @Override
        @NotNull
        public UUID decode(ByteBuf buffer) {
            Integer[] ints = readIntArray(buffer, buffer.readableBytes());
            return uuidFromIntArray(ints);
        }

        @Override
        public void encode(ByteBuf buffer, UUID uuid) {
            int[] ints = UUIDUtil.uuidToIntArray(uuid);
            buffer.writeInt(ints.length);
            for (int i : ints) buffer.writeInt(i);
        }
    };
    public static final MutableComponent TERMIAN_EMPIRE_BANNER_NAME = Component.translatable("block." + BackMath.MOD_ID + ".termian_empire_banner").withColor(0x1DC2D1);

    /// Plays the item pickup sound at a (server) player.
    public static void playItemPickupSound(ServerPlayer serverPlayer) {
        float pitch = ((serverPlayer.getRandom().nextFloat() - serverPlayer.getRandom().nextFloat()) * 0.7F + 1) * 2;
        serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, pitch);
    }

    /// Adds a tooltip line for an effect.
    public static MutableComponent addEffectTooltip(MobEffect effect, int duration, int amplifier, float ticksPerSecond) {
        MutableComponent component = Component.translatable("potion.withAmplifier", Component.translatable(effect.getDescriptionId()), Component.translatable("potion.potency." + amplifier));
        return Component.translatable("potion.withDuration", component, StringUtil.formatTickDuration(duration, ticksPerSecond)).withStyle(RVUtils.getFromRGB(effect.getColor()));
    }

    /// Adds the Bakugou armor set to the entity.
    /// <p>
    /// Used to replace the armor entirely.
    public static void addBakugouOutfit(LivingEntity livEntity) {
        if (livEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) livEntity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AxolotlTest.BAKUGOU_HAIR.get()));
        if (livEntity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) livEntity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AxolotlTest.BAKUGOU_BLOUSE.get()));
        if (livEntity.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) livEntity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(AxolotlTest.BAKUGOU_PANTS.get()));
        if (livEntity.getItemBySlot(EquipmentSlot.FEET).isEmpty()) livEntity.setItemSlot(EquipmentSlot.FEET, new ItemStack(AxolotlTest.BAKUGOU_SHOES.get()));
        livEntity.playSound(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 1, 1);
    }

    /// Returns a Termian Empire Banner. Used by termian patrollers.
    @SuppressWarnings("deprecation")
    public static ItemStack getTermianBannerInstance(HolderGetter<BannerPattern> patternRegistry) {
        ItemStack lightBlueBanner = new ItemStack(Items.LIGHT_BLUE_BANNER);
        BannerPatternLayers patterns = new BannerPatternLayers.Builder()
                .addIfRegistered(patternRegistry, BannerPatterns.GRADIENT_UP, DyeColor.PURPLE)
                .addIfRegistered(patternRegistry, BannerPatterns.STRIPE_CENTER, DyeColor.LIGHT_BLUE)
                .addIfRegistered(patternRegistry, BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN)
                .addIfRegistered(patternRegistry, BannerPatterns.FLOWER, DyeColor.RED)
                .addIfRegistered(patternRegistry, BannerPatterns.FLOWER, DyeColor.YELLOW).build();
        lightBlueBanner.set(DataComponents.BANNER_PATTERNS, patterns);
        lightBlueBanner.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        lightBlueBanner.set(DataComponents.ITEM_NAME, TERMIAN_EMPIRE_BANNER_NAME);
        return lightBlueBanner;
    }

    public static UUID uuidFromIntArray(Integer[] bits) {
        return new UUID((long) bits[0] << 32 | (long) bits[1] & 4294967295L, (long) bits[2] << 32 | (long) bits[3] & 4294967295L);
    }

    public static Integer[] readIntArray(ByteBuf buffer, int maxLength) {
        int i = buffer.readInt();
        if (i > maxLength) {
            throw new DecoderException("VarIntArray with size " + i + " is bigger than allowed " + maxLength);
        } else {
            Integer[] ints = new Integer[i];
            for (int j = 0; j < ints.length; j++) ints[j] = buffer.readInt();
            return ints;
        }
    }
}
