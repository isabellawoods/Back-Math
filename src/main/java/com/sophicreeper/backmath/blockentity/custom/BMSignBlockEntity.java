package com.sophicreeper.backmath.blockentity.custom;

import com.sophicreeper.backmath.blockentity.BMBlockEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class BMSignBlockEntity extends SignTileEntity {
    public BMSignBlockEntity() {
        super();
    }

    @Override
    @Nonnull
    public TileEntityType<?> getType() {
        return BMBlockEntities.SIGN.get();
    }
}
