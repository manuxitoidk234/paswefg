package com.example.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(class_437.class)
public abstract class MixinDuskBackdropScreen {
   @Shadow
   public int field_22789;
   @Shadow
   public int field_22790;

   private void krypton$duskBackdrop(class_332 var1, float var2, CallbackInfo var3) {
   }
}
