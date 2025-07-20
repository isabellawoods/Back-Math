package com.sophicreeper.backmath.mixin.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

// Fixes https://bugs.mojang.com/browse/MC-188578 (sleeping in custom dimensions doesn't set time to day)
@Mixin(ServerWorld.class)
public abstract class BMServerWorldMixin {
    @Shadow
    @Nonnull
    public abstract MinecraftServer getServer();

    @Inject(method = "setDayTime", at = @At("HEAD"), cancellable = true)
    public void setDayTime(long time, CallbackInfo callback) {
        callback.cancel();
        for (ServerWorld world : this.getServer().getAllLevels()) world.serverLevelData.setDayTime(time);
    }

    @Inject(method = "stopWeather", at = @At("HEAD"), cancellable = true)
    private void stopWeather(CallbackInfo callback) {
        callback.cancel();
        for (ServerWorld world : this.getServer().getAllLevels()) {
            world.serverLevelData.setRainTime(0);
            world.serverLevelData.setRaining(false);
            world.serverLevelData.setThunderTime(0);
            world.serverLevelData.setThundering(false);
        }
    }
}
