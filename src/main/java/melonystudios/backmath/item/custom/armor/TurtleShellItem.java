package melonystudios.backmath.item.custom.armor;

import melonystudios.backmath.item.custom.behavior.BMArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TurtleShellItem extends BMArmorItem {
    public TurtleShellItem(Holder<ArmorMaterial> material, Type slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (slot == this.getEquipmentSlot().getIndex(100) && entity instanceof LivingEntity livEntity) {
            livEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false, true));
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
