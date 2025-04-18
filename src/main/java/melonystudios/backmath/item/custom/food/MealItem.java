package melonystudios.backmath.item.custom.food;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MealItem extends Item {
    public MealItem(int nutrition) {
        super(new Properties().stacksTo(8).rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(4.75F).build()));
    }
}
