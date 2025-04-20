package melonystudios.backmath.item.custom.tool;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.custom.behavior.BMSwordItem;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public class MidTermLongswordItem extends BMSwordItem {
    public MidTermLongswordItem(Tier tier, float attackDamage, float swingSpeed, Properties properties) {
        super(BMItemBehaviors.MID_TERM, tier, createAttributes(tier, attackDamage, swingSpeed), properties);
    }

    @NotNull
    public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float swingSpeed) {
        return SwordItem.createAttributes(tier, attackDamage, swingSpeed)
                .withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BackMath.backMath("longsword_entity_interaction_increase"), 2.5D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BackMath.backMath("longsword_block_interaction_increase"), 2.5D, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }
}
