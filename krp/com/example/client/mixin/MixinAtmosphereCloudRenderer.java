package com.example.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.impl.render.AtmosphereModule;
import net.minecraft.class_9955;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(class_9955.class)
public abstract class MixinAtmosphereCloudRenderer {
   @ModifyVariable(method = "method_62168", at = @At("HEAD"), argsOnly = true, ordinal = 0)
   private int krypton$cloudColour(int color) {
      AtmosphereModule atmosphere = AtmosphereModule.get();
      return atmosphere != null ? atmosphere.clouds(color) : color;
   }
}
