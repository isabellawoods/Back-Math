package melonystudios.backmath.item.custom;

import melonystudios.backmath.component.BMDataComponents;
import melonystudios.backmath.util.BMUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BagItem extends Item {
    public BagItem(Properties properties) {
        super(properties);
//        DispenserBlock.registerBehavior(this, new BagDispenseBehavior());
    }

    public ResourceLocation getDefaultLootTable(ItemStack stack) {
        return stack.getOrDefault(BMDataComponents.BAG_LOOT_TABLE, ResourceLocation.withDefaultNamespace(""));
    }

    public ItemStack getStack(ResourceLocation lootTable) {
        ItemStack stack = new ItemStack(this);
        stack.set(BMDataComponents.BAG_LOOT_TABLE, lootTable);
        return stack;
    }

    protected ResourceLocation getLootTable(ItemStack stack) {
        return stack.get(BMDataComponents.BAG_LOOT_TABLE);
    }

//    protected Collection<ItemStack> getLootTableDrops(ItemStack handStack, ServerPlayer serverPlayer) {
//        return BMLootTableUtils.giftFromPlayer(getLootTable(handStack), serverPlayer);
//    }

    // Copied from LootContainerItem.java by SilentChaos512 (repository SilentLib)
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResultHolder.success(handStack);

        // Generate items from the bag loot table, and give those items to the player.
        /*Collection<ItemStack> lootTableDrops = this.getLootTableDrops(handStack, serverPlayer);

        if (lootTableDrops.isEmpty()) LogUtils.getLogger().warn(Component.translatable("backmath.message_template", Component.translatable("error.backmath.bag.no_drops", BuiltInRegistries.ITEM.getKey(handStack.getItem()),
                this.getLootTable(handStack))).getString());
        lootTableDrops.forEach(serverPlayer::addItem);*/

        // Play the item pickup sound.
        BMUtils.playItemPickupSound(serverPlayer);
        handStack.shrink(1);
        return InteractionResultHolder.success(handStack);
    }
}
