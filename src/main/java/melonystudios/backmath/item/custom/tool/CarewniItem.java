package melonystudios.backmath.item.custom.tool;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.custom.behavior.BMSwordItem;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class CarewniItem extends BMSwordItem {
    public CarewniItem(Tier tier, int attackDamage, float swingSpeed, Properties properties) {
        super(tier, attackDamage, swingSpeed, properties.attributes(SwordItem.createAttributes(tier, attackDamage, swingSpeed)
                .withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BackMath.backMath("carewni_entity_interaction_increase"), 2.5D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BackMath.backMath("carewni_block_interaction_increase"), 2.5D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)));
    }
}
