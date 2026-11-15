package com.example.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.impl.movement.FlyModule;
import net.minecraft.class_1297;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(class_1297.class)
public abstract class MixinFlyEntity {
   @ModifyReturnValue(method = "method_5799", at = @At("RETURN"))
   private boolean krypton$flyIsInWater(boolean original) {
      return original && !this.krypton$flyAffects();
   }

   @ModifyReturnValue(method = "method_5771", at = @At("RETURN"))
   private boolean krypton$flyIsInLava(boolean original) {
      return original && !this.krypton$flyAffects();
   }

   @ModifyExpressionValue(method = "method_5790", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_1297;method_5869()Z"))
   private boolean krypton$flyIsUnderWater(boolean original) {
      return original && !this.krypton$flyAffects();
   }

   @Unique
   private boolean krypton$flyAffects() {
      return FlyModule.active != null && this == class_310.method_1551().field_1724;
   }
}
