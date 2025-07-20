package com.sophicreeper.backmath.mixin.command;

import net.minecraft.command.CommandSource;
import net.minecraft.command.impl.WeatherCommand;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Fixes https://bugs.mojang.com/browse/MC-186626 (/weather command not working in custom dimensions)
@Mixin(WeatherCommand.class)
public class BMWeatherCommandMixin {
    @Inject(method = "setClear", at = @At("HEAD"))
    private static void setClear(CommandSource source, int duration, CallbackInfoReturnable<Integer> callback) {
        for (ServerWorld world : source.getServer().getAllLevels()) world.setWeatherParameters(duration, 0, false, false);
    }

    @Inject(method = "setRain", at = @At("HEAD"))
    private static void setRain(CommandSource source, int duration, CallbackInfoReturnable<Integer> callback) {
        for (ServerWorld world : source.getServer().getAllLevels()) world.setWeatherParameters(0, duration, true, false);
    }

    @Inject(method = "setThunder", at = @At("HEAD"))
    private static void setThunder(CommandSource source, int duration, CallbackInfoReturnable<Integer> callback) {
        for (ServerWorld world : source.getServer().getAllLevels()) world.setWeatherParameters(0, duration, true, true);
    }
}
