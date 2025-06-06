package com.sophicreeper.backmath.block.custom;

import com.sophicreeper.backmath.util.BMDamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PoisonRoseBlock extends FlowerBlock {
    public PoisonRoseBlock(Effect effect, int duration, Properties properties) {
        super(effect, duration, properties);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && world.getDifficulty() != Difficulty.PEACEFUL) {
            if (entity instanceof LivingEntity) {
                LivingEntity livEntity = (LivingEntity) entity;
                if (!livEntity.isInvulnerableTo(BMDamageSources.POISON_ROSE) || !livEntity.isInvulnerableTo(DamageSource.MAGIC)) {
                    livEntity.addEffect(new EffectInstance(Effects.POISON, 100));
                }
            }
        }
    }

    @Override
    @Nullable
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity mob) {
        return PathNodeType.DAMAGE_OTHER;
    }
}
