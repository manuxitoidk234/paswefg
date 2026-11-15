package com.example.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.impl.movement.EntityControlModule;
import net.minecraft.class_1308;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(class_1308.class)
public abstract class MixinEntityControlMob {
   @ModifyReturnValue(method = "method_66672", at = @At("RETURN"))
   private boolean krypton$entityControlSpoofSaddle(boolean original) {
      return original || EntityControlModule.spoofSaddle();
   }
}
