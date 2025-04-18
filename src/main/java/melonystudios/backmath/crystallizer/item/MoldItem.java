package melonystudios.backmath.crystallizer.item;

import net.minecraft.world.item.Item;

public class MoldItem extends Item {
    private final String type;

    public MoldItem(String type, Properties properties) {
        super(properties);
        this.type = type;
    }
}
