package melonystudios.backmath.item.custom.food.drink;

import melonystudios.backmath.item.behavior.FoodSettings;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.ItemBehaviorParameters;
import melonystudios.backmath.item.custom.food.BMFoodItem;

import java.util.function.Supplier;

public class JuiceItem extends BMFoodItem {
    public JuiceItem(FoodSettings settings, Supplier<ItemBehavior> behavior, Properties properties) {
        super(settings, behavior, properties);
    }

    public JuiceItem(Properties properties) {
        super(ItemBehaviorParameters.BOTTLE_DRINK, properties);
    }
}
