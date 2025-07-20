package com.sophicreeper.backmath.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Collection;

/// Utility class for rolling loot tables and returning their contents.
public class LootTableUtils {
    /// Rolls a loot table and returns the items to gift the player when opening a {@link com.sophicreeper.backmath.item.custom.BagItem BagItem}.
    /// @param giftTable The loot table to use.
    /// @param serverPlayer Used for the loot context builder.
    /// @return A collection of items from the loot table.
    public static Collection<ItemStack> giftFromPlayer(ResourceLocation giftTable, ServerPlayerEntity serverPlayer) {
        MinecraftServer server = serverPlayer.level.getServer();
        if (server == null) return ImmutableList.of();

        LootContext lootContext = new LootContext.Builder(serverPlayer.getLevel()).withParameter(LootParameters.THIS_ENTITY, serverPlayer).withParameter(LootParameters.ORIGIN, serverPlayer.position()).withLuck(serverPlayer.getLuck()).create(BMLootParameterSets.BAG);
        return server.getLootTables().get(giftTable).getRandomItems(lootContext);
    }

    /// Rolls a loot table and returns the items to drop from a {@link com.sophicreeper.backmath.dispenser.BagDispenseBehavior dispensed bag}.
    /// @param giftTable The loot table to use.
    /// @param source Used for the loot context builder.
    /// @return A collection of items from the loot table.
    public static Collection<ItemStack> giftFromDispenser(ResourceLocation giftTable, IBlockSource source) {
        MinecraftServer server = source.getLevel().getServer();

        LootContext lootContext = new LootContext.Builder(source.getLevel()).withParameter(LootParameters.BLOCK_STATE, source.getBlockState()).withParameter(LootParameters.BLOCK_ENTITY, source.getEntity()).withParameter(LootParameters.ORIGIN, new Vector3d(
                source.x(), source.y(), source.z())).withLuck(source.getLevel().getCurrentDifficultyAt(source.getPos()).getSpecialMultiplier()).create(BMLootParameterSets.BAG);
        return server.getLootTables().get(giftTable).getRandomItems(lootContext);
    }

    /// Rolls a loot table and returns the items to drop when cutting a block using a {@link com.sophicreeper.backmath.item.custom.tool.KnifeItem knife}.
    /// @param cuttingTable The loot table to use.
    /// @param context Used for the loot context builder.
    /// @return A collection of items from the loot table.
    public static Collection<ItemStack> dropFromCutting(ResourceLocation cuttingTable, ItemUseContext context) {
        MinecraftServer server = context.getLevel().getServer();
        if (server == null) return ImmutableList.of();

        if (context.getLevel() instanceof ServerWorld) {
            LootContext lootContext = new LootContext.Builder((ServerWorld) context.getLevel()).withParameter(LootParameters.BLOCK_STATE, context.getLevel().getBlockState(context.getClickedPos())).withParameter(LootParameters.ORIGIN, new Vector3d(
                    context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ())).withParameter(LootParameters.TOOL, context.getPlayer().getItemInHand(Hand.MAIN_HAND)).withParameter(LootParameters.THIS_ENTITY, context.getPlayer())
                    .create(LootParameterSets.BLOCK);
            return server.getLootTables().get(cuttingTable).getRandomItems(lootContext);
        }
        return ImmutableList.of();
    }

    /// Rolls a loot table and returns the items to pop out when right-clicking any {@link com.sophicreeper.backmath.block.custom.FruitLeavesBlock fruit leaves}.
    /// <p>Also used for {@link com.sophicreeper.backmath.block.custom.GrapeVinePostBlock grape vine posts}, but was commented out due to some issues.
    /// @param pickingTable The loot table to use.
    /// @param world Used for the loot context builder.
    /// @param state Used for the loot context builder.
    /// @param pos Used for the loot context builder.
    /// @param player Optionally used for the loot context builder.
    /// @return A collection of items from the loot table.
    public static Collection<ItemStack> pickFruits(ResourceLocation pickingTable, World world, BlockState state, BlockPos pos, @Nullable PlayerEntity player) {
        MinecraftServer server = world.getServer();
        if (server == null) return ImmutableList.of();

        if (world instanceof ServerWorld) {
            Vector3d vec3D = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
            LootContext.Builder builder = new LootContext.Builder((ServerWorld) world).withParameter(LootParameters.ORIGIN, vec3D).withParameter(LootParameters.BLOCK_STATE, state);
            if (player != null) builder.withParameter(LootParameters.THIS_ENTITY, player);
            LootContext context = builder.create(BMLootParameterSets.PICKING);
            return server.getLootTables().get(pickingTable).getRandomItems(context);
        }
        return ImmutableList.of();
    }

    /// Rolls a loot table and returns the items to equip the entity.
    /// @param equipmentTable The loot table to use.
    /// @param livEntity The entity to equip with the given items.
    /// @return A collection of items from the loot table.
    public static Collection<ItemStack> equipGear(ResourceLocation equipmentTable, LivingEntity livEntity) {
        MinecraftServer server = livEntity.getServer();
        if (server == null) return ImmutableList.of();

        if (livEntity.level instanceof ServerWorld) {
            LootContext context = new LootContext.Builder((ServerWorld) livEntity.level).withParameter(LootParameters.ORIGIN, livEntity.position()).withParameter(LootParameters.THIS_ENTITY, livEntity).withLuck(livEntity.level.getCurrentDifficultyAt(livEntity.blockPosition())
                    .getSpecialMultiplier()).create(BMLootParameterSets.EQUIPMENT);
            return server.getLootTables().get(equipmentTable).getRandomItems(context);
        }
        return ImmutableList.of();
    }
}
