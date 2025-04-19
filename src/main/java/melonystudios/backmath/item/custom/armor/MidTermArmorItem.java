package melonystudios.backmath.item.custom.armor;

import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.custom.behavior.BMArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MidTermArmorItem extends BMArmorItem {
    public MidTermArmorItem(Holder<ArmorMaterial> material, Type slot, Properties properties) {
        super(material, slot, BMItemBehaviors.MID_TERM, properties);
    }

    // Needs more testing/changing to work.
    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (stack.getMaxDamage() <= stack.getDamageValue() && selected) {
            world.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 8, false, Level.ExplosionInteraction.BLOCK);
            stack.shrink(1);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
