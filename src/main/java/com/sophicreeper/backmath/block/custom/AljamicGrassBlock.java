package com.sophicreeper.backmath.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class AljamicGrassBlock extends SpreadableSnowyAljanDirtBlock implements IGrowable {
    public AljamicGrassBlock(Properties properties) {
        super(properties);
    }

    // Whether this IGrowable can grow.
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.above()).isAir();
    }

    public boolean isBonemealSuccess(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @SuppressWarnings("unchecked")
    public void performBonemeal(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();
        BlockState grass = Blocks.GRASS.defaultBlockState();

        label48:
        for (int i = 0; i < 128; ++i) {
            BlockPos abovePos1 = abovePos;

            for (int j = 0; j < i / 16; ++j) {
                abovePos1 = abovePos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!world.getBlockState(abovePos1.below()).is(this) || world.getBlockState(abovePos1).isCollisionShapeFullBlock(world, abovePos1)) {
                    continue label48;
                }
            }

            BlockState aboveState = world.getBlockState(abovePos1);
            if (aboveState.is(grass.getBlock()) && rand.nextInt(10) == 0) {
                ((IGrowable) grass.getBlock()).performBonemeal(world, rand, abovePos1, aboveState);
            }

            if (aboveState.isAir()) {
                BlockState stateToPlace;
                if (rand.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = world.getBiome(abovePos1).getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) continue;

                    ConfiguredFeature<?, ?> configuredFeature = list.get(0);
                    FlowersFeature flowersFeature = (FlowersFeature) configuredFeature.feature;
                    stateToPlace = flowersFeature.getRandomFlower(rand, abovePos1, configuredFeature.config());
                } else {
                    stateToPlace = grass;
                }

                if (stateToPlace.canSurvive(world, abovePos1)) world.setBlock(abovePos1, stateToPlace, 3);
            }
        }
    }
}
