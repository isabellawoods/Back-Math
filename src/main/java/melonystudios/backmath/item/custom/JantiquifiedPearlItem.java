package melonystudios.backmath.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class JantiquifiedPearlItem extends Item {
    public JantiquifiedPearlItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity livEntity) {
        return 0;
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (!state.is(Blocks.END_PORTAL_FRAME) || state.getValue(EndPortalFrameBlock.HAS_EYE)) {
            return InteractionResult.PASS;
        } else if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockState filledPortalFrame = state.setValue(EndPortalFrameBlock.HAS_EYE, true);
            Block.pushEntitiesUp(state, filledPortalFrame, world, pos);
            world.setBlock(pos, filledPortalFrame, 2);
            world.updateNeighbourForOutputSignal(pos, Blocks.END_PORTAL_FRAME);
            context.getItemInHand().shrink(1);
            world.levelEvent(1503, pos, 0);
            BlockPattern.BlockPatternMatch endPortalPatternMatcher = EndPortalFrameBlock.getOrCreatePortalShape().find(world, pos);
            if (endPortalPatternMatcher != null) {
                BlockPos frontTopLeftPos = endPortalPatternMatcher.getFrontTopLeft().offset(-3, 0, -3);

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        world.setBlock(frontTopLeftPos.offset(i, 0, j), Blocks.END_PORTAL.defaultBlockState(), 2);
                    }
                }
                world.globalLevelEvent(1038, frontTopLeftPos.offset(1, 0, 1), 0);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
        if (hitResult.getType() == HitResult.Type.BLOCK && world.getBlockState(hitResult.getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
            return InteractionResultHolder.pass(handStack);
        } else {
            player.startUsingItem(hand);
            if (world instanceof ServerLevel serverWorld) {
                BlockPos strongholdPos = serverWorld.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, player.blockPosition(), 100, false);
                if (strongholdPos != null) {
                    EyeOfEnder enderEye = new EyeOfEnder(world, player.getX(), player.getY(0.5), player.getZ());
                    enderEye.setItem(handStack);
                    enderEye.signalTo(strongholdPos);
                    world.gameEvent(GameEvent.PROJECTILE_SHOOT, enderEye.position(), GameEvent.Context.of(player));
                    world.addFreshEntity(enderEye);
                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.USED_ENDER_EYE.trigger(serverPlayer, strongholdPos);
                    }

                    float pitch = Mth.lerp(world.random.nextFloat(), 0.33F, 0.5F);
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 1.0F, pitch);
                    handStack.consume(1, player);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    player.swing(hand, true);
                    return InteractionResultHolder.success(handStack);
                }
            }

            return InteractionResultHolder.consume(handStack);
        }
    }
}
