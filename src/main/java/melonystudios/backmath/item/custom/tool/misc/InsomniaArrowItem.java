package melonystudios.backmath.item.custom.tool.misc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class InsomniaArrowItem extends ArrowItem {
    public InsomniaArrowItem(Properties properties) {
        super(properties);
        // DispenserBlock.registerBehavior(this, new InsomniaArrowDispenseBehavior());
    }

    @Override
    @Nonnull
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weaponStack) {
        return super.createArrow(world, stack, shooter, weaponStack); //return new InsomniaArrowEntity(world, shooter);
    }
}
