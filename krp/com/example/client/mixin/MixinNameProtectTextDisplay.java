package com.example.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krypton.client.module.impl.misc.NameProtectModule;
import net.minecraft.class_2561;
import net.minecraft.class_8113.class_8123;
import net.minecraft.class_8113.class_8123.class_8125;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(class_8123.class)
public abstract class MixinNameProtectTextDisplay {
   @Unique
   private int krypton$nameProtectGeneration;

   @ModifyExpressionValue(
      method = "method_48905",
      at = @At(
         value = "FIELD",
         opcode = 180,
         ordinal = 0,
         target = "Lnet/minecraft/class_8113$class_8123;field_42442:Lnet/minecraft/class_8113$class_8123$class_8125;"
      )
   )
   private class_8125 krypton$nameProtectRefresh(class_8125 cached) {
      int generation = NameProtectModule.generation();
      if (generation == this.krypton$nameProtectGeneration) {
         return cached;
      }

      this.krypton$nameProtectGeneration = generation;
      return null;
   }

   @ModifyExpressionValue(
      method = "method_48905",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/class_8113$class_8123$class_8230;comp_1334()Lnet/minecraft/class_2561;")
   )
   private class_2561 krypton$nameProtectText(class_2561 text) {
      return NameProtectModule.protect(text);
   }
}
