package com.sophicreeper.backmath.entity.custom.misc;

import com.sophicreeper.backmath.BackMath;
import com.sophicreeper.backmath.entity.BMEntities;
import com.sophicreeper.backmath.item.AxolotlTest;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class BMBoatEntity extends BoatEntity {
    private static final DataParameter<String> WOOD_TYPE = EntityDataManager.defineId(BMBoatEntity.class, DataSerializers.STRING);

    public BMBoatEntity(EntityType<? extends BoatEntity> type, World world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public BMBoatEntity(World world, double x, double y, double z) {
        this(BMEntities.BACK_MATH_BOAT.get(), world);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WOOD_TYPE, "aljanwood");
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setWoodType(tag.getString("Type"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Type", this.getWoodType());
    }

    public String getWoodType() {
        return this.entityData.get(WOOD_TYPE);
    }

    public void setWoodType(String wood) {
        this.entityData.set(WOOD_TYPE, wood);
    }

    @Override
    @Nonnull
    public Item getDropItem() {
        switch (this.getWoodType()) {
            case "crystalline_birch": return AxolotlTest.CRYSTALLINE_BIRCH_BOAT.get();
            case "goldenwood": return AxolotlTest.GOLDENWOOD_BOAT.get();
            case "guava": return AxolotlTest.GUAVA_BOAT.get();
            case "jabuticaba": return AxolotlTest.JABUTICABA_BOAT.get();
            case "cork_oak": return AxolotlTest.CORK_OAK_BOAT.get();
            case "aljancap": return AxolotlTest.ALJANCAP_BOAT.get();
            case "insomnian": return AxolotlTest.INSOMNIAN_BOAT.get();
            case "avondalic_willow": return AxolotlTest.AVONDALIC_WILLOW_BOAT.get();
            case "aljanwood": default: return AxolotlTest.ALJANWOOD_BOAT.get();
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(BackMath.backMath(this.getWoodType() + "_boat")));
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
