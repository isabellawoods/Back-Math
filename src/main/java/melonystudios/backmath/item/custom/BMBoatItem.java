package melonystudios.backmath.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.item.BoatItem;

import java.util.function.Predicate;

public class BMBoatItem extends BoatItem {
    private static final Predicate<Entity> SPECTATORS_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::canBeCollidedWith);
    private final String woodType;

    public BMBoatItem(boolean hasChest, String woodType, Properties properties) {
        super(hasChest, null, properties);
        this.woodType = woodType;
    }
}
