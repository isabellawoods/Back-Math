package melonystudios.backmath.util;

import com.google.common.collect.Lists;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class RVUtils {
    /// Copied from Revaried and modified to work with Back Math's item behaviors.
    @Nullable
    public static List<MobEffectInstance> getAppliedEffectsFromNBT(@Nullable Level world, ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        List<MobEffectInstance> effects = Lists.newArrayList();

        if (customData != null) {
            CompoundTag tag = customData.copyTag();
            if (tag.contains("applied_effects", TagTypes.LIST)) {
                ListTag effectList = tag.getList("applied_effects", TagTypes.COMPOUND);

                for (int i = 0; i < effectList.size(); ++i) {
                    int duration = 20;
                    int amplifier = 0;
                    boolean ambient = false;
                    boolean showParticles = true;
                    boolean showIcon = true;
                    boolean noCounter = false;
                    List<ItemStack> cureStacks = Lists.newArrayList();
                    CompoundTag effectTag = effectList.getCompound(i);
                    if (effectTag.contains("duration", TagTypes.ANY_NUMERIC)) duration = effectTag.getInt("duration");
                    if (effectTag.contains("amplifier", TagTypes.ANY_NUMERIC)) amplifier = effectTag.getInt("amplifier");
                    if (effectTag.contains("ambient", TagTypes.ANY_NUMERIC)) ambient = effectTag.getBoolean("ambient");
                    if (effectTag.contains("show_particles", TagTypes.ANY_NUMERIC)) showParticles = effectTag.getBoolean("show_particles");
                    if (effectTag.contains("show_icon", TagTypes.ANY_NUMERIC)) showIcon = effectTag.getBoolean("show_icon");
                    if (effectTag.contains("no_counter", TagTypes.ANY_NUMERIC)) noCounter = effectTag.getBoolean("no_counter");
                    if (effectTag.contains("cures", TagTypes.LIST)) {
                        ListTag curativeList = effectTag.getList("cures", TagTypes.COMPOUND);
                        for (Tag value : curativeList) {
                            Optional<ItemStack> item = ItemStack.CODEC.parse(NbtOps.INSTANCE, value).resultOrPartial();
                            item.ifPresent(cureStacks::add);
                        }
                    }

                    MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(effectTag.getString("id")));
                    if (effect != null) {
                        MobEffectInstance instance = new MobEffectInstance(Holder.direct(effect), duration, amplifier, ambient, showParticles, showIcon);
//                    if (world != null && world.isClientSide) instance.setNoCounter(noCounter);
//                    if (!cureStacks.isEmpty()) instance.setCurativeItems(cureStacks);
                        effects.add(instance);
                    }
                }
                return effects;
            }
        }
        return null;
    }

    public static Style getFromRGB(int color) {
        return Style.EMPTY.withColor(color);
    }
}
