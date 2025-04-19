package melonystudios.backmath.util;

import melonystudios.backmath.BackMath;
import melonystudios.backmath.item.custom.tool.JanticRailgunItem;
import melonystudios.backmath.item.custom.tool.bow.BMBowItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

import java.time.LocalDate;
import java.time.Month;

import static net.minecraft.client.renderer.item.ItemProperties.register;

public class BMItemModelProperties {
    public static void makeBow(BMBowItem bow) {
        register(bow, ResourceLocation.withDefaultNamespace("pull"), (stack, world, livEntity, seed) -> {
            if (livEntity == null) {
                return 0;
            } else {
                return livEntity.getUseItem() != stack ? 0 : (float) (bow.getUseDuration(stack, livEntity) - livEntity.getUseItemRemainingTicks()) / Math.min(bow.getUseDuration(stack, livEntity), 20);
            }
        });
        register(bow, ResourceLocation.withDefaultNamespace("pulling"), (stack, world, livEntity, seed) -> livEntity != null && livEntity.isUsingItem() && livEntity.getUseItem() == stack ? 1 : 0);
    }

    public static void makeCrossbow(Item crossbow) {
        register(crossbow, ResourceLocation.withDefaultNamespace("pull"), (stack, world, livEntity, seed) -> {
            if (livEntity != null) return CrossbowItem.isCharged(stack) ? 0 : (float) (stack.getUseDuration(livEntity) - livEntity.getUseItemRemainingTicks()) / (float) CrossbowItem.getChargeDuration(stack, livEntity);
            return 0;
        });
        register(crossbow, ResourceLocation.withDefaultNamespace("pulling"), (stack, world, livEntity, seed) -> livEntity != null && livEntity.isUsingItem() && livEntity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1 : 0);
        register(crossbow, ResourceLocation.withDefaultNamespace("charged"), (stack, world, livEntity, seed) -> livEntity != null && CrossbowItem.isCharged(stack) ? 1 : 0);
        register(crossbow, ResourceLocation.withDefaultNamespace("firework"), (stack, world, livEntity, seed) -> {
            ChargedProjectiles projectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
            return projectiles != null && projectiles.contains(Items.FIREWORK_ROCKET) ? 1 : 0;
        });
    }

    public static void makeJanticRailgun(Item railgun) {
        register(railgun, ResourceLocation.withDefaultNamespace("pull"), (stack, world, livEntity, seed) -> {
            if (livEntity != null) return JanticRailgunItem.isCharged(stack) ? 0 : (float) (stack.getUseDuration(livEntity) - livEntity.getUseItemRemainingTicks()) / JanticRailgunItem.LOADING_TIME;
            return 0;
        });
        register(railgun, ResourceLocation.withDefaultNamespace("pulling"), (stack, world, livEntity, seed) -> livEntity != null && livEntity.isUsingItem() && livEntity.getUseItem() == stack && !JanticRailgunItem.isCharged(stack) ? 1 : 0);
        register(railgun, ResourceLocation.withDefaultNamespace("charged"), (stack, world, livEntity, seed) -> livEntity != null && JanticRailgunItem.isCharged(stack) ? 1 : 0);
    }

    public static void makeShield(Item shield) {
        register(shield, ResourceLocation.withDefaultNamespace("blocking"), (stack, world, livEntity, seed) -> livEntity != null && livEntity.isUsingItem() && livEntity.getUseItem() == stack ? 1 : 0);
    }

    public static void makeCarewni(Item sword) {
        register(sword, BackMath.backMath("june_check"), (stack, world, livEntity, seed) -> LocalDate.now().getMonth() == Month.JUNE ? 1 : 0);
    }
}
