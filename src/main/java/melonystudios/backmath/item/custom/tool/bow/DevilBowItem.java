package melonystudios.backmath.item.custom.tool.bow;

import melonystudios.backmath.item.behavior.BMItemBehaviors;

public class DevilBowItem extends BMBowItem {
    public DevilBowItem(Properties properties) {
        super(false, true, 0, 100, 72000, BMItemBehaviors.DEVIL, properties);
    }
}
