package com.sophicreeper.backmath.item.custom;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.block.BMBlocks;
import com.sophicreeper.backmath.block.custom.AljanPortalStandBlock;
import com.sophicreeper.backmath.dispenser.JanticalDispenseBehavior;
import com.sophicreeper.backmath.misc.BMSounds;
import com.sophicreeper.backmath.util.BMKeyBindings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class JanticalItem extends Item {
    public JanticalItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, new JanticalDispenseBehavior());
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (state.is(BMBlocks.ALJAN_PORTAL_STAND.get()) && !state.getValue(AljanPortalStandBlock.JANTICAL)) {
            if (world.isClientSide) {
                return ActionResultType.SUCCESS;
            } else {
                BlockState withJantical = state.setValue(AljanPortalStandBlock.JANTICAL, true);
                Block.pushEntitiesUp(state, withJantical, world, pos);
                world.setBlock(pos, withJantical, 2);
                world.updateNeighbourForOutputSignal(pos, BMBlocks.ALJAN_PORTAL_STAND.get());
                context.getItemInHand().shrink(1);

                world.playSound(null, pos, BMSounds.BLOCK_ALJAN_PORTAL_STAND_FILL, SoundCategory.BLOCKS, 1, 1);
                for (int k1 = 0; k1 < 16; ++k1) {
                    double particleX = (double) pos.getX() + (5 + random.nextDouble() * 6) / 16;
                    double particleY = (double) pos.getY() + 0.8125D;
                    double particleZ = (double) pos.getZ() + (5 + random.nextDouble() * 6) / 16;
                    world.addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0, 0, 0);
                }

                return ActionResultType.CONSUME;
            }
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (!BMKeyBindings.isShiftDown()) tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
        if (BMKeyBindings.isShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.backmath.hold_shift", BMKeyBindings.getTranslation(BMKeyBindings.SHOW_TOOLTIPS_KEY).withStyle(TextFormatting.WHITE)).withStyle(TextFormatting.DARK_GRAY));
            tooltip.add(new TranslationTextComponent("tooltip.backmath.empty"));
            tooltip.add(new TranslationTextComponent("tooltip."  + BackMath.MOD_ID + ".jantical"));
        }
    }
}
