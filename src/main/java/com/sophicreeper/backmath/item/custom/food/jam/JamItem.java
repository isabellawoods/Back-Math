package com.sophicreeper.backmath.item.custom.food.jam;

import com.sophicreeper.backmath.item.behavior.FoodSettings;
import com.sophicreeper.backmath.item.behavior.ItemBehavior;
import com.sophicreeper.backmath.item.behavior.ItemBehaviorParameters;
import com.sophicreeper.backmath.item.custom.food.BMFoodItem;

import java.util.function.Supplier;

public class JamItem extends BMFoodItem {
    public JamItem(FoodSettings settings, Supplier<ItemBehavior> behavior, Properties properties) {
        super(settings, behavior, properties);
    }

    public JamItem(Properties properties) {
        super(ItemBehaviorParameters.JAM, properties);
    }
}
