package com.sophicreeper.backmath.block.custom;

import com.sophicreeper.backmath.blockentity.custom.BMSignBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BMWallSignBlock extends WallSignBlock {
    public BMWallSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BMSignBlockEntity();
    }
}
