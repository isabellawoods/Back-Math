package melonystudios.backmath.component.custom;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record ChargedProjectile(ItemStack projectileStack) {
    public static final Codec<ChargedProjectile> CODEC = ItemStack.CODEC.xmap(ChargedProjectile::new, remainder -> remainder.projectileStack);
    public static final StreamCodec<RegistryFriendlyByteBuf, ChargedProjectile> STREAM_CODEC = ItemStack.STREAM_CODEC.map(ChargedProjectile::new, remainder -> remainder.projectileStack);

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof ChargedProjectile(ItemStack stack) && ItemStack.matches(this.projectileStack, stack);
        }
    }

    @Override
    public int hashCode() {
        return ItemStack.hashItemAndComponents(this.projectileStack);
    }

    @Override
    @NotNull
    public String toString() {
        return "ChargedProjectile[item=" + this.projectileStack + "]";
    }
}
