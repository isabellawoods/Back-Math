package melonystudios.backmath.item.behavior;

import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.UseAnim;

import java.util.function.Supplier;

public class FoodSettings {
    public Supplier<Item> defaultRemainder = null;
    public int useDuration = 32;
    public UseAnim animation = UseAnim.DRINK;
    public SoundEvent consumeSound = SoundEvents.GENERIC_DRINK;
    public DispenseItemBehavior dispenseBehavior = null;
    public final boolean isDrink;

    public FoodSettings(boolean drink) {
        this.isDrink = drink;
    }

    public FoodSettings() {
        this(false);
    }

    public FoodSettings defaultRemainder(Supplier<Item> remainderStack) {
        this.defaultRemainder = remainderStack;
        return this;
    }

    public FoodSettings useDuration(int ticks) {
        this.useDuration = ticks;
        return this;
    }

    public FoodSettings animation(UseAnim animation) {
        this.animation = animation;
        return this;
    }

    public FoodSettings consumeSound(SoundEvent sound) {
        this.consumeSound = sound;
        return this;
    }

    public FoodSettings dispenseBehavior(DispenseItemBehavior dispenseBehavior) {
        this.dispenseBehavior = dispenseBehavior;
        return this;
    }
}
