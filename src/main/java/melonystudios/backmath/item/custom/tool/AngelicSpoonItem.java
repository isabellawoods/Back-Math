package melonystudios.backmath.item.custom.tool;

import melonystudios.backmath.item.custom.behavior.BMShovelItem;
import net.minecraft.world.item.Tier;

public class AngelicSpoonItem extends BMShovelItem {
    public AngelicSpoonItem(Tier tier, float attackDamage, float swingSpeed, Properties properties) {
        super(tier, attackDamage, swingSpeed, properties);
        // DispenserBlock.registerBehavior(this, new MealCookingDispenseBehavior());
    }
}
