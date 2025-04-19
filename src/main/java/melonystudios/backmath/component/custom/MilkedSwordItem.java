package melonystudios.backmath.component.custom;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public record MilkedSwordItem(ItemStack milkedStack) {
    public static final MilkedSwordItem INSTANCE = new MilkedSwordItem(new ItemStack(Items.MILK_BUCKET));
    public static final Codec<MilkedSwordItem> CODEC = ItemStack.CODEC.xmap(MilkedSwordItem::new, remainder -> remainder.milkedStack);
    public static final StreamCodec<RegistryFriendlyByteBuf, MilkedSwordItem> STREAM_CODEC = ItemStack.STREAM_CODEC.map(MilkedSwordItem::new, remainder -> remainder.milkedStack);

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof MilkedSwordItem(ItemStack stack) && ItemStack.matches(this.milkedStack, stack);
        }
    }

    @Override
    public int hashCode() {
        return ItemStack.hashItemAndComponents(this.milkedStack);
    }

    @Override
    @NotNull
    public String toString() {
        return "MilkedSwordItem[item=" + this.milkedStack + "]";
    }
}
