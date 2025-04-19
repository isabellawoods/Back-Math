package melonystudios.backmath.item.custom.tool;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.custom.behavior.BMSwordItem;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class MechMechSwordItem extends BMSwordItem {
    public MechMechSwordItem(Tier tier, int attackDamage, float swingSpeed, Properties properties) {
        super(BMItemBehaviors.MECH_MECH, tier, attackDamage, swingSpeed, properties.attributes(SwordItem.createAttributes(tier, attackDamage, swingSpeed)
                .withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BackMath.backMath("mech_mech_entity_interaction_decrease"), -1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)
                .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BackMath.backMath("mech_mech_block_interaction_decrease"), -1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HAND)));
    }
}
