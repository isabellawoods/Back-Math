package melonystudios.backmath.item.custom.food.jam;

import melonystudios.backmath.item.behavior.FoodSettings;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.ItemBehaviorParameters;
import melonystudios.backmath.item.custom.food.BMFoodItem;

import java.util.function.Supplier;

public class JamItem extends BMFoodItem {
    public JamItem(FoodSettings settings, Supplier<ItemBehavior> behavior, Properties properties) {
        super(settings, behavior, properties);
    }

    public JamItem(Properties properties) {
        super(ItemBehaviorParameters.JAM, properties);
    }
}
