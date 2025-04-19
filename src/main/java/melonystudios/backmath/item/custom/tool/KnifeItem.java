package melonystudios.backmath.item.custom.tool;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import melonystudios.backmath.item.behavior.BMItemBehaviors;
import melonystudios.backmath.item.behavior.ItemBehavior;
import melonystudios.backmath.item.behavior.effecttype.ItemBehaviorEffectType;
import melonystudios.backmath.util.BMResourceLocations;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public class KnifeItem extends DiggerItem {
    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.KELP, Blocks.KELP_PLANT, Blocks.CRIMSON_ROOTS, Blocks.WARPED_ROOTS, Blocks.CRIMSON_FUNGUS, Blocks.WARPED_FUNGUS, Blocks.NETHER_SPROUTS, Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, Blocks.NETHER_WART, Blocks.SWEET_BERRY_BUSH, Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING, Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING);
    public static final Map<Block, ResourceLocation> CUTTING_MAP = new ImmutableMap.Builder<Block, ResourceLocation>().put(Blocks.PUMPKIN, BMResourceLocations.CUTTING_PUMPKIN).put(Blocks.CARVED_PUMPKIN, BMResourceLocations.CUTTING_CARVED_PUMPKIN).put(Blocks.JACK_O_LANTERN, BMResourceLocations.CUTTING_JACK_O_LANTERN).put(Blocks.MELON, BMResourceLocations.CUTTING_MELON).put(Blocks.HAY_BLOCK, BMResourceLocations.CUTTING_HAY_BLOCK).build();
    private final Supplier<ItemBehavior> behavior;

    public KnifeItem(float attackDamage, float swingSpeed, Supplier<ItemBehavior> behavior, Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_AXE, properties.attributes(createAttributes(tier, attackDamage, swingSpeed)));
        this.behavior = behavior;
    }

    public KnifeItem(float attackDamage, float swingSpeed, Tier tier, Properties properties) {
        this(attackDamage, swingSpeed, BMItemBehaviors.NONE, tier, properties);
    }

    @Override
    @NotNull
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack containerStack = stack.copy();
        containerStack.setDamageValue(containerStack.getDamageValue() - 1);
        if (containerStack.getDamageValue() <= 0) {
            return ItemStack.EMPTY;
        } else {
            return containerStack;
        }
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.behavior.get().hasGlint(stack) || super.isFoil(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return this.behavior.get().getDurabilityBarColor(stack, super.getBarColor(stack));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player attacker, Entity target) {
        if (target instanceof LivingEntity livEntity) this.behavior.get().run(stack, attacker, livEntity, target.level());
        return super.onLeftClickEntity(stack, attacker, target);
    }

    @Override
    @Nonnull
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState state = world.getBlockState(pos);
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (CUTTING_MAP.containsKey(state.getBlock())) {
            Collection<ItemStack> lootTableDrops = this.getLootTableDrops(CUTTING_MAP.get(state.getBlock()), context);
            world.destroyBlock(pos, false, player);
            lootTableDrops.forEach(stack1 -> Block.popResource(world, pos, stack1));
            if (player != null) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(InteractionHand.MAIN_HAND));
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    protected Collection<ItemStack> getLootTableDrops(ResourceLocation cuttingTable, UseOnContext context) {
        return Collections.emptyList(); // return BMLootTableUtils.dropFromCutting(cuttingTable, context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (!this.behavior.get().effects.isEmpty()) tooltip.add(Component.translatable("tooltip.backmath.behavior.when_used").withStyle(ChatFormatting.GRAY));
        for (Supplier<ItemBehaviorEffectType> type : this.behavior.get().effects) {
            if (type != null) type.get().addToTooltip(stack, context, tooltip, flag);
        }
    }
}
