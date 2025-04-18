package melonystudios.backmath.component.custom;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record UseRemainder(ItemStack remainderStack) {
    public static final Codec<UseRemainder> CODEC = ItemStack.CODEC.xmap(UseRemainder::new, remainder -> remainder.remainderStack);
    public static final StreamCodec<RegistryFriendlyByteBuf, UseRemainder> STREAM_CODEC = ItemStack.STREAM_CODEC.map(UseRemainder::new, remainder -> remainder.remainderStack);

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof UseRemainder(ItemStack stack) && ItemStack.matches(this.remainderStack, stack);
        }
    }

    @Override
    public int hashCode() {
        return ItemStack.hashItemAndComponents(this.remainderStack);
    }

    @Override
    @NotNull
    public String toString() {
        return "UseRemainder[item=" + this.remainderStack + "]";
    }
}
